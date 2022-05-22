package com.ptm.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptm.api.exception.code.CatalogExceptionCodeAndMassage;

public class CatalogServiceException extends RuntimeException  {
	
	private static final long serialVersionUID = 1L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String code;
    
    private final String msg;

    private final List<String> paramList = new ArrayList<>();
    
    public CatalogServiceException(CatalogExceptionCodeAndMassage catalogExceptioncodeandmassage) {
        this.code = catalogExceptioncodeandmassage.getErrCode();
        this.msg = catalogExceptioncodeandmassage.getErrMsg();       
    }
    
    public CatalogServiceException(String code, String msg, String... params) {
        this.code = code;
        this.msg = msg;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramList.add(params[i]);
            }
        }
    }

    
    public CatalogServiceException(String code, String msg, Exception detailedException,String... params) {
		this.code = code;
		this.msg = msg;
		log.info("Exception  {}",detailedException);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				paramList.add(params[i]);
			}
		}
	}
    public CatalogServiceException(String code,String msg, List<String> paramList) {
        this.code = code;
        this.msg = msg;
        this.paramList.addAll(paramList);
    }

    public ParameterizedErrorVM getErrorVM() {
        return new ParameterizedErrorVM(code, msg, paramList);
    }
    
}
