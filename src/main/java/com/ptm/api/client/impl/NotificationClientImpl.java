package com.ptm.api.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.client.NotificationClient;
import com.ptm.api.integration.mgmt.service.SmsService;
import com.ptm.api.integration.payout.service.PayoutService;
import com.ptm.api.payout.dto.PayoutDto;
import com.ptm.api.user.controller.dto.OtpDTO;

@Service
public class NotificationClientImpl implements NotificationClient {
	
	@Autowired
	SmsService ssmsService;
	
	@Autowired
	private PayoutService payoutService;

	@Override
	public void sendSmsOtp(OtpDTO otpDTO) {
		ssmsService.sendOtp(otpDTO);
		
	}

	@Override
	public String payout(PayoutDto payoutDto) {
		return payoutService.payout(payoutDto);
	}

}
