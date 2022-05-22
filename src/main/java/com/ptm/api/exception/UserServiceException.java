package com.ptm.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptm.api.exception.code.UserExceptionCodeAndMassage;


public class UserServiceException extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private  String code;
    
    private  String msg;

    private final List<String> paramList = new ArrayList<>();
    public UserServiceException() {}
    public UserServiceException(UserExceptionCodeAndMassage userexceptioncodeandmassage) {
        this.code = userexceptioncodeandmassage.getErrCode();
        this.msg = userexceptioncodeandmassage.getErrMsg();       
    }
    
    public UserServiceException(String errorCode,String errorMsg) {
        this.code = errorCode;
        this.msg = errorMsg;      
    }
    
    public UserServiceException(UserExceptionCodeAndMassage userexceptioncodeandmassage, String... params) {
        this.code = userexceptioncodeandmassage.getErrCode();
        this.msg = userexceptioncodeandmassage.getErrMsg(); 
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramList.add(params[i]);
            }
        }
    }
    
    public UserServiceException(String code, String msg, String... params) {
        this.code = code;
        this.msg = msg;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramList.add(params[i]);
            }
        }
    }

    
    public UserServiceException(String code, String msg, Exception detailedException,String... params) {
		this.code = code;
		this.msg = msg;
		log.info("Exception  {}",detailedException);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				paramList.add(params[i]);
			}
		}
	}
    public UserServiceException(String code,String msg, List<String> paramList) {
        this.code = code;
        this.msg = msg;
        this.paramList.addAll(paramList);
    }

    public ParameterizedErrorVM getErrorVM() {
        return new ParameterizedErrorVM(code, msg, paramList);
    }
    
}
