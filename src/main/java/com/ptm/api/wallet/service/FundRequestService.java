package com.ptm.api.wallet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.user.entity.User;
import com.ptm.api.user.repository.UserRepository;
import com.ptm.api.wallet.controller.dto.FundRequestDTO;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;
import com.ptm.api.wallet.controller.vo.FundRequestVO;
import com.ptm.api.wallet.entity.RequestForFund;
import com.ptm.api.wallet.repository.RequestForFundRepository;
import com.ptm.api.wallet.service.mapper.RequestForFundMapper;



@Service
public class FundRequestService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestForFundRepository requestForFundRepository;
	
	@Autowired
	private WalletTransferService walletTransferService;
	
	@Transactional
	public void saveFundRequest(FundRequestDTO fundRequestDTO) {
		List<RequestForFund> initRequestForFund=requestForFundRepository.findByFromReqstUserIdAndApproveStatus(SecurityUtils.getCurrentUserLogin().get(), EtransactionStatus.INITIATED);
		if(initRequestForFund.size()>0) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PAST_FUND_REQUEST_NOT_PROCCESSED);

		}
		User user = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin().get()).get();
		if(user.getParentId().equals(user.getUuid())) {
			throw new UserServiceException(UserExceptionCodeAndMassage.FUND_REQUEST_FOR_SELF);
		}
		RequestForFund requestForFund = new RequestForFundMapper().map(fundRequestDTO, user);
		requestForFundRepository.save(requestForFund);
	}
	
	@Transactional
	public List<FundRequestVO> getFundRequestUserList(Pageable pageable ) {
		User user = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin().get()).get();
		return requestForFundRepository.findByRequesterParentId(pageable,user.getUuid())
				.stream()
				.map(mapper -> new FundRequestVO(mapper))
				.collect(Collectors.toList());

	}

	@Transactional
	public void approveFundRequest(String reqstfunduuid) {
		User user = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin().get()).get();

		RequestForFund requestForFund = requestForFundRepository.findByReqstfunduuidAndRequesterParentId(reqstfunduuid,user.getUuid())
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.FUND_REQUEST_NOT_FOUND));
		
		
		validateStatus(requestForFund);
		
		UserWalletActionDTO userWalletActionDTO = UserWalletActionDTO.builder()
				.amount(requestForFund.getTxnAmt())
				.userId(requestForFund.getFromReqstUserId())
				.remark("fund request accepted")
				.serviceType("FUND_REQUEST")
				.ewalletType(EwalletType.MAIN_WALLET).build();
		walletTransferService.transferWalletAmount(userWalletActionDTO,SecurityUtils.getCurrentUserLogin().get(), EtransactionType.DEBIT, EtransactionType.CREDIT);
		requestForFund.setApproveStatus(EtransactionStatus.DONE);
		requestForFund.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
		requestForFund.setLastModifiedDate(LocalDateTime.now());
		requestForFundRepository.save(requestForFund);
	}
	
	// decline 
	
	@Transactional
	public void rejectFundRequest(String reqstfunduuid) {
		User user = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin().get()).get();

		RequestForFund requestForFund = requestForFundRepository.findByReqstfunduuidAndRequesterParentId(reqstfunduuid,user.getUuid())
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.FUND_REQUEST_NOT_FOUND));
		
		validateStatus(requestForFund);
		requestForFund.setApproveStatus(EtransactionStatus.REJECTED);
		requestForFund.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
		requestForFund.setLastModifiedDate(LocalDateTime.now());
		requestForFundRepository.save(requestForFund);
	}

	private void validateStatus(RequestForFund requestForFund) {
		if (requestForFund.getApproveStatus().equals(EtransactionStatus.DONE)
				|| requestForFund.getApproveStatus().equals(EtransactionStatus.SUCCESS)
				|| requestForFund.getApproveStatus().equals(EtransactionStatus.REJECTED)
				|| requestForFund.getApproveStatus().equals(EtransactionStatus.FAIL)) {

			throw new UserServiceException(UserExceptionCodeAndMassage.FUND_REQUEST_NOT_FOUND);
		}
	}
	
	public List<FundRequestVO> userFundRequest(String status) {

		return requestForFundRepository
				.findByFromReqstUserIdAndApproveStatus(SecurityUtils.getCurrentUserLogin().get(),
						EtransactionStatus.valueOf(status))
				.stream().map(mapper -> new FundRequestVO(mapper)).collect(Collectors.toList());

	}

}
