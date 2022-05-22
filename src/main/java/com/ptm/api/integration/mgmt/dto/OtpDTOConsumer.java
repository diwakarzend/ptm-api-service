package com.ptm.api.integration.mgmt.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OtpDTOConsumer implements Serializable {

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
