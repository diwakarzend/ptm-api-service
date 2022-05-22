package com.ptm.api.integration.payout.service;

import com.ptm.api.integration.payout.client.PayoutClientService;

public interface MerchantService {

	public PayoutClientService getPayoutService(String merchantCode);
}
