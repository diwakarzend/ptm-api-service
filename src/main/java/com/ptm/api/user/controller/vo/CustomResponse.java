package com.ptm.api.user.controller.vo;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
	private final boolean  success;
	private final Object code;
	private final String msg;
	private  Map<String,Object> metaInfo;
	private final Object data;
	
	public CustomResponse(Object infoCode, String infoMsg, Map<String,Object> metaInfo) {
		this(infoCode, infoMsg, null, metaInfo);
	}

	public CustomResponse(Object infoCode, String infoMsg, Object data, Map<String,Object> metaInfo) {
		this.success = true;
		this.code = infoCode;
		this.msg = infoMsg;
		this.data = data;
		this.metaInfo = metaInfo;
	}
	
	public CustomResponse(Object infoCode, String infoMsg, Object data) {
		this.success = true;
		this.code = infoCode;
		this.msg = infoMsg;
		this.data = data;

	}

	public CustomResponse(Object infoCode, String infoMsg) {
		this(infoCode, infoMsg, null);
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getCode() {
		return code;
	}
	
	public Object getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}
	
	public Map<String,Object> getMetaInfo() {
		return metaInfo;
	}

	@Override
	public String toString() {
		return  "Success : "+Objects.toString(success)+
				"Info Code : "+Objects.toString(code,"InfoCode is null")+
				"Info Message : "+Objects.toString(msg,"InfoMsg is null")+
				"Meta Info : "+Objects.toString(metaInfo,"InfoCode is null")+
				"Data : "+Objects.toString(data,"Data is null");
	}
	
}
