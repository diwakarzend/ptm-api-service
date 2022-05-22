package com.ptm.api.user.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTTokenVO {

	private String idToken;
	
	private String apiToken;

	public JWTTokenVO(String idToken,String apiToken) {
		this.idToken = idToken;
		this.apiToken=apiToken;
	}

	@JsonProperty("id_token")
	public String getIdToken() {
		return idToken;
	}
	
	@JsonProperty("api_token")
	public String getApiIdToken() {
		return apiToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
}
