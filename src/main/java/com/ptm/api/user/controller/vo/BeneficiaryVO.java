package com.ptm.api.user.controller.vo;

import com.ptm.api.config.constant.Estatus;
import com.ptm.api.user.collections.BeneficiarylCollection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryVO {

	private String bankName;

	private String accountNumber;

	private String ifscCode;

	private String mobile;

	private String firstName;

	private String lastName;

	private Estatus status;

	public BeneficiaryVO setBeneficiaryDetails(BeneficiarylCollection beneficiarylCollection) {

		return BeneficiaryVO.builder().accountNumber(beneficiarylCollection.getAccountNumber())
				.bankName(beneficiarylCollection.getBankName()).ifscCode(beneficiarylCollection.getIfscCode())
				.firstName(beneficiarylCollection.getFirstName()).lastName(beneficiarylCollection.getLastName())
				.mobile(beneficiarylCollection.getMobile()).status(beneficiarylCollection.getStatus()).build();
	}

}
