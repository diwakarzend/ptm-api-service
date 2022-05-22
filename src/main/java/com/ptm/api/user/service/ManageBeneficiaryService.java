package com.ptm.api.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ptm.api.user.controller.dto.AddBeneficiaryDTO;
import com.ptm.api.user.controller.vo.BeneficiaryVO;

public interface ManageBeneficiaryService {
	
	public void addBeneficiary( AddBeneficiaryDTO addBeneficiaryDTO);

	void updateBeneficiary(AddBeneficiaryDTO addBeneficiaryDTO);

	void deleteBeneficiary(String accountNumber);

	List<BeneficiaryVO> getBeneficiary(Pageable pageable);

}
