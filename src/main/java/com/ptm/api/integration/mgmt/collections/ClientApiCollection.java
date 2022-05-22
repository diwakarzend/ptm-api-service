package com.ptm.api.integration.mgmt.collections;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientApiCollection {
	
	@Id
	private String id;
	private String baseUrl;
	private String cleintId;
	private String requestType;
	private String apiName;
	private List<String> parameters;
	private String merchantCode;

	

}
