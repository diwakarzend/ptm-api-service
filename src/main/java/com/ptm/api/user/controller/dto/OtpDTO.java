package com.ptm.api.user.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OtpDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String otp;
	private String tenantId;
	private String userName;
	private String serviceName;
	private String otpText;
	private String templateId;
	
	
	
	

}
