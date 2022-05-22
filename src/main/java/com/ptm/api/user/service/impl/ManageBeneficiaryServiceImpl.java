package com.ptm.api.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptm.api.config.constant.Estatus;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.user.collectionRepository.BeneficiaryCollectionRepository;
import com.ptm.api.user.collections.BeneficiarylCollection;
import com.ptm.api.user.controller.dto.AddBeneficiaryDTO;
import com.ptm.api.user.controller.vo.BeneficiaryVO;
import com.ptm.api.user.service.ManageBeneficiaryService;

@Service
public class ManageBeneficiaryServiceImpl implements ManageBeneficiaryService {

	@Autowired
	BeneficiaryCollectionRepository beneficiaryCollectionRepository;

	@Override
	public void addBeneficiary(AddBeneficiaryDTO addBeneficiaryDTO) {

		boolean isAccountExist = beneficiaryCollectionRepository
				.findByPtmUseruuidAndAccountNumberAndStatus(SecurityUtils.getCurrentUserUuid().get(),addBeneficiaryDTO.getAccountNumber(), Estatus.ACTIVE.toString())
				.isPresent();
		if (isAccountExist) {
			throw new UserServiceException(UserExceptionCodeAndMassage.BENEFICIARY_EXIST);
		}

		BeneficiarylCollection beneficiaryDetailCollection = BeneficiarylCollection.builder()
				.bankName(addBeneficiaryDTO.getBankName())
				.mobile(addBeneficiaryDTO.getMobileNumber()).accountNumber(addBeneficiaryDTO.getAccountNumber())
				.ifscCode(addBeneficiaryDTO.getIfscCode()).firstName(addBeneficiaryDTO.getFirstName())
				.lastName(addBeneficiaryDTO.getLastName()).ptmUseruuid(SecurityUtils.getCurrentUserUuid().get())
				.status(Estatus.ACTIVE).build();
		beneficiaryDetailCollection.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
		beneficiaryCollectionRepository.save(beneficiaryDetailCollection);
	}

	@Override
	public void updateBeneficiary(AddBeneficiaryDTO addBeneficiaryDTO) {

		BeneficiarylCollection beneficiaryDetailCollection = beneficiaryCollectionRepository
				.findByPtmUseruuidAndAccountNumberAndStatus(SecurityUtils.getCurrentUserUuid().get(),addBeneficiaryDTO.getAccountNumber(), Estatus.ACTIVE.toString())
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.BENEFICIARY_NOT_EXIST));

		if (!beneficiaryDetailCollection.getPtmUseruuid().equals(SecurityUtils.getCurrentUserUuid().get())) {
			throw new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);

		}
		beneficiaryDetailCollection.setBankName(addBeneficiaryDTO.getBankName());
		beneficiaryDetailCollection.setMobile(addBeneficiaryDTO.getMobileNumber());
		beneficiaryDetailCollection.setIfscCode(addBeneficiaryDTO.getIfscCode());
		beneficiaryDetailCollection.setFirstName(addBeneficiaryDTO.getFirstName());
		beneficiaryDetailCollection.setLastName(addBeneficiaryDTO.getLastName());
		beneficiaryDetailCollection.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());

		beneficiaryCollectionRepository.save(beneficiaryDetailCollection);
	}

	@Override
	public void deleteBeneficiary(String accountNumber) {

		BeneficiarylCollection beneficiaryDetailCollection = beneficiaryCollectionRepository
				.findByPtmUseruuidAndAccountNumberAndStatus(SecurityUtils.getCurrentUserUuid().get(),accountNumber, Estatus.ACTIVE.toString())
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.BENEFICIARY_NOT_EXIST));

		if (!beneficiaryDetailCollection.getPtmUseruuid().equals(SecurityUtils.getCurrentUserUuid().get())) {
			throw new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);

		}

		beneficiaryDetailCollection.setStatus(Estatus.DELETED);
		beneficiaryCollectionRepository.save(beneficiaryDetailCollection);
	}

	@Override
	public List<BeneficiaryVO> getBeneficiary(Pageable pageable) {

		List<BeneficiarylCollection> beneficiaryDetailCollection = beneficiaryCollectionRepository
				.findByPtmUseruuidAndStatus(pageable, SecurityUtils.getCurrentUserUuid().get(),Estatus.ACTIVE.toString());
		
		return beneficiaryDetailCollection.stream()
		.map(mapper->new BeneficiaryVO().setBeneficiaryDetails(mapper)).collect(Collectors.toList());

	}
	
	
}
