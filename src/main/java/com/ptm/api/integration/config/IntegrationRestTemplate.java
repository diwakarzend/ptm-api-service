package com.ptm.api.integration.config;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class IntegrationRestTemplate<T> {

	private RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<T> exchange(String url, HttpMethod httpMethod, HttpEntity<?> requestEntity,
			Class<T> classType) {
		try {

			return restTemplate.exchange(url, httpMethod, requestEntity, classType);

		} catch (Exception exp) {
			throw exp;
		}
	}

	public ResponseEntity<T> exchange(String url, HttpMethod httpMethod, HttpEntity<T> requestEntity,
			Class<T> classType, Map<String, String> params) {
		try {

			return restTemplate.exchange(url, httpMethod, requestEntity, classType, params);

		} catch (Exception exp) {
			throw exp;
		}
	}

	public ResponseEntity<T> exchangePost(String url, HttpEntity<Map<String, String>> requestEntity, Class<T> classType) {
		try {

			return restTemplate.postForEntity(url, requestEntity, classType);

		} catch (Exception exp) {
			throw exp;
		}
	}
}
