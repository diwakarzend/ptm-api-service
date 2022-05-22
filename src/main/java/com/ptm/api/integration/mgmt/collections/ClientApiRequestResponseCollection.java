package com.ptm.api.integration.mgmt.collections;

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
public class ClientApiRequestResponseCollection {
	@Id
	private String id;
	
	private String serviceType;
	
	private String requestJson;
	
	private String responseJson;


}
