package com.ptm.api.client.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientPayOutResponseModel {
	
	@SerializedName("STATUS")
	private String status;
	
	@SerializedName("message")
	private String message;
	
	@SerializedName("RRN")
	private String rrn;

}
