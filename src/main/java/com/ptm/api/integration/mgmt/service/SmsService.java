package com.ptm.api.integration.mgmt.service;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ptm.api.integration.config.IntegrationRestTemplate;
import com.ptm.api.integration.mgmt.dto.OtpDTOConsumer;
import com.ptm.api.user.controller.dto.OtpDTO;

@Service
public class SmsService {

	public void sendOtp(OtpDTO  otpDTOConsumer) {
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			requestHeaders.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");


			System.out.println("{\"sender_id\": \"PAYMOB\",\"to\": [" + otpDTOConsumer.getUserName()
			+ "],\"route\":\"otp\",\"template_id\":"+"\"" +otpDTOConsumer.getTemplateId()+"\""+","+"\"message\":"+"\"" +otpDTOConsumer.getOtpText()+"\""+"}");
			
			HttpEntity<String> requestEntity = new HttpEntity<>(
					"{\"sender_id\": \"PAYMOB\",\"to\": [" + otpDTOConsumer.getUserName()
					+ "],\"route\":\"otp\",\"template_id\":"+"\"" +otpDTOConsumer.getTemplateId()+"\""+","+"\"message\":"+"\"" +otpDTOConsumer.getOtpText()+"\""+"}",
					requestHeaders);

			ResponseEntity<String> responseBody = new IntegrationRestTemplate<String>().exchange(
					"https://api.trustsignal.io/v1/sms?api_key=f0af0fe4-d9d0-46d1-96a7-49b4b3472b76", HttpMethod.POST,
					requestEntity, String.class);
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendTransactionsl(OtpDTOConsumer otpDTOConsumer) {
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			requestHeaders.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");


			System.out.println("{\"sender_id\": \"PAYMOB\",\"to\": [" + otpDTOConsumer.getUserName()
			+ "],\"route\":\"otp\",\"template_id\":"+"\"" +otpDTOConsumer.getTemplateId()+"\""+","+"\"message\":"+"\"" +otpDTOConsumer.getOtpText()+"\""+"}");
			
			
			HttpEntity<String> requestEntity = new HttpEntity<>(
					"{\"sender_id\": \"PAYMOB\",\"to\": [" + otpDTOConsumer.getUserName()
							+ "],\"route\":\"transactional\",\"template_id\":"+"\"" +otpDTOConsumer.getTemplateId()+"\""+","+"\"message\":"+"\"" +otpDTOConsumer.getOtpText()+"\""+"}",
					requestHeaders);

			ResponseEntity<String> responseBody = new IntegrationRestTemplate<String>().exchange(
					"https://api.trustsignal.io/v1/sms?api_key=f0af0fe4-d9d0-46d1-96a7-49b4b3472b76", HttpMethod.POST,
					requestEntity, String.class);
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

