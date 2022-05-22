package com.ptm.api.integration.payout.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.integration.payout.client.PayoutClientService;
import com.ptm.api.integration.payout.service.MerchantService;

@Service
public class MerchantServiceImpl implements MerchantService {

	private final Map<String, PayoutClientService> servicesByMerchantCode;


	@Autowired
	public MerchantServiceImpl(List<PayoutClientService> regionServices) {
		servicesByMerchantCode = regionServices.stream()
				.collect(Collectors.toMap(PayoutClientService::payoutMerchantCode, Function.identity()));
	}

	public PayoutClientService getPayoutService(String merchantCode) {
		return this.servicesByMerchantCode.get(merchantCode);
	}

}
