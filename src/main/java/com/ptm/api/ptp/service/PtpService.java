package com.ptm.api.ptp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptm.api.client.merchant.entity.PaymentBrand;
import com.ptm.api.ptp.dto.PtpDTO;

public interface PtpService {

	public PtpDTO createPTP(PtpDTO ptpDTO);

	// public UserDTO updatePTP(UserDTO userDTO);

	public Page<PtpDTO> getAllManagedPtp(Pageable pageable);

	public Page<PtpDTO> getAllPtpByMerchantRole(Pageable pageable, String uuid);

	public Page<PaymentBrand> getAllVendorDetails(Pageable pageable);
}
