package com.ptm.api.payout.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ptm.api.config.AuthoritiesConstants;
import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.payout.dto.PayoutReportDto;
import com.ptm.api.payout.dto.PtmPage;
import com.ptm.api.payout.dto.ReportFilterDto;
import com.ptm.api.payout.entity.PayOutEntity;
import com.ptm.api.payout.repository.PayOutRequestRepository;
import com.ptm.api.payout.service.mapper.PayOutMapper;
import com.ptm.api.payout.vo.MonthlyTransactionReportVo;
import com.ptm.api.payout.vo.StatusTransactionReportVo;

@Service
public class PayOutReportservice {

	@Autowired
	private PayOutRequestRepository payOutRequestRepository;

	public PtmPage<List<PayoutReportDto>> fetchReport(ReportFilterDto reportFilterDto, Pageable pageable) {

		Page<PayOutEntity> payoutList;
		List<PayoutReportDto> payoutReportList;
		if (SecurityUtils.getCurrentUserRole().equals(AuthoritiesConstants.VENDOR.toString())) {

			System.out.println("::::::::::::::::::vendor:::::");
			reportFilterDto.setVendorId(SecurityUtils.getCurrentUserUuid().get());
			payoutList = payOutRequestRepository
					.findAll(Specification.where(PayOutMapper.prepareQueryForFiter(reportFilterDto)), pageable);
			payoutReportList = payoutList.stream().map(mapper -> new PayOutMapper().mapVedorReportDto(mapper))
					.collect(Collectors.toList());

		} else {
			payoutList = payOutRequestRepository
					.findAll(Specification.where(PayOutMapper.prepareQueryForFiter(reportFilterDto)), pageable);
			payoutReportList = payoutList.stream().map(mapper -> new PayOutMapper().mapReportDto(mapper))
					.collect(Collectors.toList());

		}

		return new PtmPage<List<PayoutReportDto>>(payoutReportList, payoutList.getNumber(),
				payoutList.getNumberOfElements(), payoutList.getTotalElements(), payoutList.getTotalPages());
	}
	
	
	
	
	
	public Map<EtransactionStatus, Long> transactionStatus(){
		List<PayOutEntity> payOutEntities = getPayoutOnRoleType();
		Map<EtransactionStatus, Long> transactionStatusMap= payOutEntities.stream()
		.collect(Collectors.groupingBy(PayOutEntity::getStatus,Collectors.counting()));
		transactionStatusMap.putIfAbsent(EtransactionStatus.INITIATED, 0l);
		transactionStatusMap.putIfAbsent(EtransactionStatus.DONE, 0l);
		transactionStatusMap.putIfAbsent(EtransactionStatus.REFUNDED, 0l);
		transactionStatusMap.putIfAbsent(EtransactionStatus.REJECTED, 0l);
		transactionStatusMap.putIfAbsent(EtransactionStatus.FAIL, 0l);

		
		return transactionStatusMap;
	}

	private List<PayOutEntity> getPayoutOnRoleType() {
		List<PayOutEntity> payOutEntities;
		if (SecurityUtils.getCurrentUserRole().equals(AuthoritiesConstants.VENDOR.toString())) {
			 payOutEntities= payOutRequestRepository.findByUserId(SecurityUtils.getCurrentUserUuid().get());
		}else {
			payOutEntities=	payOutRequestRepository.findAll();
		}
		return payOutEntities;
	}
	
	public List<MonthlyTransactionReportVo> monthlyTransaction(){

		Map<String, Double> monthyProfitMap = payOutRequestRepository.findAll(PayOutMapper.getByRoleTypeTransaction()).stream()
				.collect(Collectors.groupingBy(v->String.valueOf(v.getCreatedDate().getYear()+"-"+v.getCreatedDate().getMonth()),Collectors.summingDouble(PayOutEntity::getProfitAmount)));
			     
		return monthyProfitMap.entrySet().stream().map(mapper->new MonthlyTransactionReportVo(mapper))
		.collect(Collectors.toList());
	}
	
	
	public List<StatusTransactionReportVo> statusAndTranAmountReport(){
           SecurityUtils.getCurrentUserPermittedApi();
		
		System.out.println(SecurityUtils.getCurrentUserRole());
		Map<EtransactionStatus, List<PayOutEntity>> transactionStatusMap= getPayoutOnRoleType()
				.stream().collect(Collectors.groupingBy(PayOutEntity::getStatus));
		
		return transactionStatusMap.entrySet().stream().map(mapper->new StatusTransactionReportVo(mapper))
		.collect(Collectors.toList());
	}
	
	
	
	
	
}
