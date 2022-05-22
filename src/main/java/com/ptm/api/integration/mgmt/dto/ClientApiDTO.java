package com.ptm.api.integration.mgmt.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ClientApiDTO {
	private String baseUrl;
	private String cleintId;
	private String requestType;
	private String apiName;
	private List<String> parameters;
	private String merchantCode;

}
