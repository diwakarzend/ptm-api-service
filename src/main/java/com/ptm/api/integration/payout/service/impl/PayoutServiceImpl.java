package com.ptm.api.integration.payout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.integration.mgmt.collectionrepo.AepsCollectionRepo;
import com.ptm.api.integration.mgmt.collections.ClientApiCollection;
import com.ptm.api.integration.mgmt.constant.AepsConstant;
import com.ptm.api.integration.payout.service.MerchantService;
import com.ptm.api.integration.payout.service.PayoutService;
import com.ptm.api.payout.dto.PayoutDto;

@Service
public class PayoutServiceImpl  implements PayoutService{
	
	@Autowired
	private AepsCollectionRepo aepsCollectionRepo;
		
	@Autowired
	private MerchantService merchantService;
	
	@Override
	public String payout(PayoutDto payoutDto) {
		ClientApiCollection aepsCollection = aepsCollectionRepo
				.findByApiNameAndMerchantCode(AepsConstant.PAY_OUT.toString(), payoutDto.getMerchantCode());

		return merchantService.getPayoutService(payoutDto.getMerchantCode()).payout(payoutDto,
				aepsCollection.getBaseUrl());
	}

}
