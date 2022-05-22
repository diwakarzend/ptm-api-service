package com.ptm.api.exception.code;

public enum SuccessCode {

	GENERAL_SUCCESSFULLY_STATUS("INFO000", "SUCCESS"),
	SAVED_SUCCESSFULLY("INFO001", "Role saved Successfully"),
	REGISTERED_SUCCESSFULLY("INFO002", "register Successfully"),
	ACTIVATED_SUCCESSFULLY("INFO003", "activated Successfully"),
	MPIN_SUCCESSFULLY("INFO004", "mpin verified Successfully"),
	SEND_OTP_SUCCESSFULLY("INFO005", "otp send Successfully"),
	OTP_SUCCESSFULLY("INFO006", "otp verified Successfully"),
	PASSWORD_RESET_SUCCESSFULLY("INFO007", "Password reset Successfully"),
	RESET_PASSWORD_CODE_SEND_SUCCESSFULLY("INFO008", "Reset password code  send Successfully"),
	PASSWORD_CHANGED_SUCCESSFULLY("INFO009", "Password changed Successfully"),
	PROFILE_UPDATED_SUCCESSFULLY("INFO010", "Profile updated Successfully"),
	USER_CREATED_SUCCESSFULLY("INFO011", "New user created Successfully"),
	USER_SERACH_RESULT_SUCCESSFULLY("INFO012", "User result found Successfully"),
	USER_AUTHORITY_SUCCESSFULLY("INFO013", "Users authority"),
	USER_DELETED_SUCCESSFULLY("INFO013", "Users deleted"),
	SERVICE_CREATED_SUCCESSFULLY("INFO002", "Service created successfully"),
	DEPOSIT_SUCCESSFULLY("INFO014", "Amount deposited  successfully"),
	DEBIT_SUCCESSFULLY("INFO015", "Amount debit  successfully"),
	RECHARGE_OPERATOR_FETCHED_SUCCESSFULLY("INFO016", "Operator details"),
	CREATED_SUCCESSFULLY("INFO017", "created successfully"),
	RECORD_FETCHED_SUCCESSFULLY("INFO018", "Fetched successfully"),
	SERVICE_MAPPED_SUCCESSFULLY("INFO019", "Service mapped successfully"),
	WALLET_FETCHED_SUCCESSFULLY("INFO020", "user wallet infomation "),
	USER_HIERARCHY_CREATED_SUCCESSFULLY("INFO021", "User Hierarchy created successfully"),
	RECHARGE_PLAN_FETCHED_SUCCESSFULLY("INFO022", "Recharge plan details"),
	RECHARGE_DONE_SUCCESSFULLY("INFO023", "Recharge done successfully"),
	DTH_DETAILS_FETCH_SUCCESSFULLY("INFO024", "DTH details"),
	SERVICE_TYPE_TRANSACTIONS_SUCCESSFULLY("INFO025", "User service type transactions"),
	RECHARGE_STATUS("INFO026", "Recharge status"),
	FUND_REQUEST_SUCCESSFULLY("INFO027", "Fund request submmited!"),
	FUND_APPROVED_SUCCESSFULLY("INFO028", "Requested fund approved!"),
	PROVIDER_FETCHED_SUCCESSFULLY("INFO027", "Provider fetched successfully"),
	BILL_PAYMENT_DONE_SUCCESSFULLY("INFO028", "Bill payment done successfully"),
	BILL_FETCH_SUCCESSFULLY("INFO029", "Bill fetch successfully"),
	BILL_STATUS_SUCCESSFULLY("INFO030", "Bill status fetch successfully!"),
	PAYOUT_INIT_SUCCESSFULLY("INFO031", "PayOut Initiated successfully!"),
	PAYOUT_SUCCESSFULLY("INFO031", "PayOut done successfully!");


	private final String code;
	private final String msg;

	private SuccessCode(String successCode, String successMessage) {
		this.code = successCode;
		this.msg = successMessage;
	}

	public String getSuccessCode() {
		return code;
	}

	public String getSuccessMessage() {
		return msg;
	}

}