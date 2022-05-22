package com.ptm.api.user.controller.vo;

import com.ptm.api.user.entity.FinecialDetailEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFinDetailsVo {
	

	private String brnadName;

	private String registerCompany;

	private String registerAddress;

	private String gstNo;

	private String website;
	
	public UserFinDetailsVo(FinecialDetailEntity finecialDetailEntity) {
		
		this.brnadName=finecialDetailEntity.getBrnadName();
		this.registerCompany=finecialDetailEntity.getRegisterCompany();
		this.registerAddress=finecialDetailEntity.getRegisterAddress();
		this.gstNo=finecialDetailEntity.getGstNo();
		this.website=finecialDetailEntity.getWebsite();
		
	}
}
