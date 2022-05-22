package com.ptm.api.exception.code;

public enum UserExceptionCodeAndMassage {
	
	GENERAL_ERROR("ERR0000","Something went wrong!"),
	NO_USER_FOUND("ERR0001","Invalid user!"),
	NO_ROLE_FOUND("ERR0002","No role found!"),
	PASSWORD_FORMAT_ERROR("ERR0003","Password format incorrect."),
	USER_ALREADY_EXIST("ERR0004","User name already taken."),
	USER_EMAIL_ALREADY_EXIST("ERR0005","User email id already taken."),
	USER_EMAIL_DO_NOT_EXIST("ERR0006","User email do not exist."),
	WRONG_ROLE("ERR0007","User Role type is wrong."),
	WRONG_PERMISSION("ERR0008","User permission type is wrong."),
	NO_TENANT_FOUND("ERR0009","No tenant found"),	
	OTP_EXPIRE("ERR0010","OTP expire "),
	WRONG_OTP("ERR0011","Wrong OTP "),
	UNAUTHORISED_USER("ERR0012","Un-Authorised user"),
	PHONE_NUMBER_NOT_FOUND("ERR0013","Invalide phone number"),
	INVALID_PASSWORD_CHANGE("ERR0014","Old password is invalid"),
	OLD_NEW_PASSWORD_SAME("ERR0015","New password can not same as old password"),
	USER_WALLET_AMOUNT_INSUFFICIENT("ERR0016","User wallet balance less than debit amount"),
	MOBILE_RECHARGE_FAILED("ERR0017","mobile recharge failed!"),
	DTH_RECHARGE_FAILED("ERR0018","DTH recharge failed!"),
	USER_WALLET_NOT_FOUD("ERR0019","User wallet not found!"),
	USER_WALLET_VALIDATION("ERR0020","User wallet already created !"),
	PAYOUT_FAIL("ERR0021","Only more then 500 withdrawal amount allowed"),
	USER_LOG_OUT("ERR0022","Please login again !"),
	RECHARGE_STATUS_FAILED("ERR0023","recharge status failed!"),
	FUND_REQUEST_NOT_FOUND("ERR0024","Fund request not found!"),
	SENDER_RECIVER_USER_SAME("ERR0025","Sender and Reciver user can not be  same!"),
	FUND_REQUEST_FOR_SELF("ERR0026","User can not raise fund request for self!"),
	PAST_FUND_REQUEST_NOT_PROCCESSED("ERR0027","Fund request raise already!"),
	BILL_PAYMENT_STATUS_FAILED("ERR0024","Bill payment failed"),
	BILL_PAYMENT_STATUS_PENDING("ERR0025","Bill payment status pending"),
	BILL_PAYMENT_STATUS_IN_PROCESS("ERR0026","Bill payment status in process"),
	PAYOUT_INITIATED("ERR0027","payout already initiated/pending !"),
	CLIENT_PAYOUT_PENDING("ERR0028","payout pending from merchant !"),
	CLIENT_PAYOUT_FAIL("ERR0029","payout fail from merchant !"),
	USER_PERMISSION_FAIL("ERR0030","User do not have permission to access this api!"),
	USER_PHONE_NUMBER_EXIST("ERR0030","User phone number already taken."),
	BENEFICIARY_EXIST("ERR0031","Account Number alrrady register"),
	BENEFICIARY_NOT_EXIST("ERR0031","Account Number not register"),
	PAYOUT_NOT_FOUND("ERR0032","PayOut already proccessed or not found!"),
	PAYOUT_REQUEST_EXPIRE("ERR0033","PayOut transaction expired! try new rquest."),
	PAYOUT_APPROVAL_REQUIRED("ERR0034","PayOut Approval required!"),
	USER_FIN_DETAILS_NOT_FOUND("ERR0035","User details not found!"),
	PAYOUT_REQUEST_ERROR("ERR0036","User balance is less than requested amount!");


    private final String code;
    private final String msg;

    /**
     * @param text
     */
    private UserExceptionCodeAndMassage(final String errCode,final String errMsg) {
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