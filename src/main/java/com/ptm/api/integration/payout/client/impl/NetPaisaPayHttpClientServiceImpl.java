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
public class NetPaisaPayHttpClientServiceImpl implements PayoutClientService {
	
	@Autowired
	private ClientApiRequestResponseService clientApiRequestResponseService;

	@Override
	public String payout(PayoutDto payoutDto, String baseUrl) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("api_access_key", "2755870fa87c2aed97dffa43fa9e49db1");
		paramsMap.put("account_number", "919716763608");
		paramsMap.put("name", "Sandeep Kumar Gupta");
		paramsMap.put("ifsc_code", "PYTM12345");
		paramsMap.put("mobile", "9716763608");
		paramsMap.put("mode", payoutDto.getRoute());
		paramsMap.put("remarks", "integration api");
		paramsMap.put("amount", "11");
		paramsMap.put("agent_id", "12345678PT");
		baseUrl = "https://netpaisa.com/nps/api/payout/transaction";
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
		paramsMap.put("agent_trid", uuid);
		paramsMap.put("api_token","2755870fa87c2aed97dffa43fa9e49db1");
		

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
		return "NP";
	}
	
	


}
