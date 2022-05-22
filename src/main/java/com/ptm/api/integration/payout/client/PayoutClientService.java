package com.ptm.api.integration.payout.client;

import com.ptm.api.payout.dto.PayoutDto;

public interface PayoutClientService {
	
	public String payoutStatus(String uuid,String  baseUrl);

	public String payout(PayoutDto payoutDto, String baseUrl);
	
	public String payoutMerchantCode();
}
