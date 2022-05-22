package com.ptm.api.exception.code;

public enum CatalogExceptionCodeAndMassage {

	GENERAL_ERROR("ERR0000","Something went wrong."),
	NO_RECORD_FOUND("ERR0001","No record found ."),
	NO_SERVICE_FOUND("ERR0003","Serive name not found."),
	NO_USER_FOUND("ERR0004","User not found."),
	USER_ALREADY_EXISTS("ERR0005","User already exists.");
	
    private final String code;
    private final String msg;

    /**
     * @param text
     */
    private CatalogExceptionCodeAndMassage(final String errCode,final String errMsg) {
        this.code = errCode;
        this.msg = errMsg;
    }

    /**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return  code + ": " + msg;
	}

	/**
	 * Return the enum constant of this type with the specified numeric value.
	 * 
	 * @param statusCode
	 *            the numeric value of the enum to be returned
	 * @return the enum constant with the specified numeric value
	 * @throws IllegalArgumentException
	 *             if this enum has no constant for the specified numeric value
	 */

	public String getErrCode() {
		return code;
	}

	public String getErrMsg() {
		return msg;
	}
}
