package com.ptm.api.exception;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * View Model for sending a parameterized error message.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterizedErrorVM implements Serializable {

	private static final long serialVersionUID = 1L;

    private final String code;
    private final String msg;
    private  List<String> errorCodeList;
    private final boolean success;

    public ParameterizedErrorVM(String code, String message, List<String> errorCodeList) {
		this.success = false;
		this.code = code;
		this.msg = message;
		this.errorCodeList = errorCodeList;
    }
    
    public ParameterizedErrorVM(String exception, String message) {
		this.success = false;
		this.code = exception;
		this.msg = message;
    }

    public String getCode() {
		return code;
	}
    
    public String getMsg() {
		return msg;
	}

	public List<String> getErrorCodeList() {
		return errorCodeList;
    }

    public boolean isSuccess() {
    	return success;
    }
}
