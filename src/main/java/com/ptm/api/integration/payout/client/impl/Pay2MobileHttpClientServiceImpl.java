package com.ptm.api.integration.payout.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ptm.api.integration.config.IntegrationRestTemplate;
import com.ptm.api.integration.mgmt.constant.AepsConstant;
import com.ptm.api.integration.mgmt.service.ClientApiRequestResponseService;
import com.ptm.api.integration.payout.client.PayoutClientService;
import com.ptm.api.payout.dto.PayoutDto;

@Component
public class Pay2MobileHttpClientServiceImpl implements PayoutClientService {
	
	@Autowired
	private ClientApiRequestResponseService clientApiRequestResponseService;

	@Override
	public String payout(PayoutDto payoutDto ,String  baseUrl) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("userId", "911");
		paramsMap.put("apikey","M8NBCQHWCC");
		paramsMap.put("BeneficiaryName", payoutDto.getBeneficiaryName());
		paramsMap.put("MobileNumber", payoutDto.getMobileNumber());
		paramsMap.put("RemittanceAmount", payoutDto.getRemittanceAmount());
		paramsMap.put("AccountNumber", payoutDto.getAccountNumber());
		paramsMap.put("IFSCCode", payoutDto.getIfscCode());
		paramsMap.put("clientId", payoutDto.getClientId());
		paramsMap.put("type", payoutDto.getType());

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Map<String, String>> request = new HttpEntity<>(paramsMap, headers);
		ResponseEntity<String> responseBody = new IntegrationRestTemplate<String>().exchangePost(baseUrl, request,
				String.class);
		clientApiRequestResponseService.save(request.getBody().toString(), responseBody.getBody(),
				AepsConstant.PAY_OUT.toString());
		return responseBody.getBody();
	}
	
	@Override
	public String payoutStatus(String uuid,String  baseUrl) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("client_id", uuid);
		paramsMap.put("api_token","690b8064c1f799155f573c541db147848b74c2c681d1fb0cb3dd1e8336f3b2b7");
		

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Map<String, String>> request = new HttpEntity<>(paramsMap, headers);
		ResponseEntity<String> responseBody = new IntegrationRestTemplate<String>().exchangePost(baseUrl, request,
				String.class);
		clientApiRequestResponseService.save(request.getBody().toString(), responseBody.getBody(),
				AepsConstant.PAY_OUT.toString());
		return responseBody.getBody();
		
	}

	@Override
	public String payoutMerchantCode() {
		return "PAY2_MOBILE";
	}
	
	


}
