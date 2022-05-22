package com.ptm.api.catalog.service;

import java.util.Map;

import com.ptm.api.catalog.entity.PtmSmsMaster;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;

public interface SmsService {

	PtmSmsMaster getOtpTxtByService(String serviceName);
	void sendSms(String otp, String phoneNumber,String serviceName);
	public Map<String, Object> sendOtp(String otpType);
	public void verifyOtp(String otp,String otpType);
	public void sendWalletTransSms(UserWalletActionDTO userWalletActionDTO, Double totalAmount, String serviceName);

}
