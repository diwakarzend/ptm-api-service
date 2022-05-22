package com.ptm.api.client.merchant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.client.merchant.repository.MerchantPayOutConfigRepository;

@Service
public class MerchantService {
	
	@Autowired
	private MerchantPayOutConfigRepository configRepository;
	
	public Double getPayOutConfigAmount(double minRange,double maxRange ) {
		
		return configRepository.findByMinRangeAndMaxRange(minRange, maxRange).getAmount();
	}

}
