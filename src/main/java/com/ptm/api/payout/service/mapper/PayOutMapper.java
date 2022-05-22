package com.ptm.api.payout.service.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.jpa.domain.Specification;

import com.ptm.api.config.AuthoritiesConstants;
import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.payout.dto.PayoutDto;
import com.ptm.api.payout.dto.PayoutReportDto;
import com.ptm.api.payout.dto.ReportFilterDto;
import com.ptm.api.payout.entity.PayOutEntity;

public class PayOutMapper {
	
	
	public PayOutEntity map(PayoutDto payoutDto, String txnId) {
		
		PayOutEntity payOutRequestEntity=PayOutEntity.builder()
				.accountNumber(payoutDto.getAccountNumber())
				.beneficiaryName(payoutDto.getBeneficiaryName())
				.clientId(txnId) // this is txn id on ptm required for 3rd party
				.ifscCode(payoutDto.getIfscCode())
				.mobileNumber(payoutDto.getMobileNumber())
				.remittanceAmount(Double.parseDouble(payoutDto.getRemittanceAmount()))
				.route(payoutDto.getRoute())
				.userId(SecurityUtils.getCurrentUserUuid().get())
				.type(payoutDto.getType())
				.txnId(txnId)
				.status(EtransactionStatus.INITIATED)
				.expireAt(LocalDateTime.now().plusMinutes(3))
				.build();
		
		return payOutRequestEntity;
	}
	
	
	public PayoutReportDto mapReportDto(PayOutEntity entity) {
		return PayoutReportDto.builder()
		.accountNumber(entity.getAccountNumber())
		.approvalRequired(entity.getApprovalRequired())
		.beneficiaryName(entity.getBeneficiaryName())
		.txnId(entity.getTxnId())
		.closingBalance(entity.getClosingBalance())
		.openingBalance(entity.getOpeningBalance())
		.createdDate(entity.getCreatedDate())
		.lastModifiedDate(entity.getLastModifiedDate())
		.merchantTxnId(entity.getMerchantTxnId())
		.ifscCode(entity.getIfscCode())
		.payoutChanrge(entity.getPayoutChanrge())
		.profitAmount(entity.getProfitAmount())
		.remittanceAmount(entity.getRemittanceAmount())
		.route(entity.getRoute())
		.merchantCode(entity.getMerchantCode())
		.status(entity.getStatus()).build();
	}
	
	
	public PayoutReportDto mapVedorReportDto(PayOutEntity entity) {
		return PayoutReportDto.builder()
		.accountNumber(entity.getAccountNumber())
		.beneficiaryName(entity.getBeneficiaryName())
		.txnId(entity.getTxnId())
		.closingBalance(entity.getClosingBalance())
		.openingBalance(entity.getOpeningBalance())
		.createdDate(entity.getCreatedDate())
		.lastModifiedDate(entity.getLastModifiedDate())
		.merchantTxnId(entity.getMerchantTxnId())
		.ifscCode(entity.getIfscCode())
		.payoutChanrge(entity.getPayoutChanrge())
		.remittanceAmount(entity.getRemittanceAmount())
		.route(entity.getRoute())
		.merchantCode(entity.getMerchantCode())
		.status(entity.getStatus()).build();
	}
	
	
	public static Specification<PayOutEntity> prepareQueryForFiter(ReportFilterDto reportFilterDto) {
		Specification<PayOutEntity> query = null;

		if (reportFilterDto.getAccountNumber() != null) {

			query = query == null ? hasAccountNumber(reportFilterDto.getAccountNumber())
					: query.and(hasAccountNumber(reportFilterDto.getAccountNumber()));
		}
		if (reportFilterDto.getClientId() != null) {
			query = query == null ? hasClientId(reportFilterDto.getClientId())
					: query.and(hasClientId(reportFilterDto.getClientId()));
		}

		if (reportFilterDto.getTxnId() != null) {
			query = query == null ? hasTxnId(reportFilterDto.getTxnId())
					: query.and(hasTxnId(reportFilterDto.getTxnId()));
		}
		if (reportFilterDto.getVendorId() != null) {
			query = query == null ? hasVendorId(reportFilterDto.getVendorId())
					: query.and(hasVendorId(reportFilterDto.getVendorId()));
		}
		if (reportFilterDto.getRoute() != null) {
			query = query == null ? hashRoute(reportFilterDto.getRoute())
					: query.and(hashRoute(reportFilterDto.getRoute()));
		}

		if (reportFilterDto.getStatus() != null) {
			query = query == null ? hasStatus(reportFilterDto.getStatus())
					: query.and(hasStatus(reportFilterDto.getStatus()));
		}

		if (reportFilterDto.getDate() != null) {
			query = query == null ? hasDate(reportFilterDto.getDate())
					: query.and(hasDate(reportFilterDto.getDate()));
		}
		
		

		if (query == null) {
			query=hasDate(reportFilterDto.getDate());
		}
		return query;

	}

	
	private static Specification<PayOutEntity> hasClientId(String clientId) {
		return (payout, cq, cb) -> cb.equal(payout.get("clientId"), clientId);
	}

	private static Specification<PayOutEntity> hasTxnId(String txnId) {
		return (payout, cq, cb) -> cb.equal(payout.get("txnId"), txnId);
	}
	
	
	private static Specification<PayOutEntity> hasAccountNumber(String accountNumber) {
		return (payout, cq, cb) -> cb.equal(payout.get("accountNumber"), accountNumber);
	}

	private static Specification<PayOutEntity> hasVendorId(String merchantId) {
		return (payout, cq, cb) -> cb.equal(payout.get("userId"), merchantId);
	}
	
	private static Specification<PayOutEntity> hashRoute(String route) {
		return (payout, cq, cb) -> cb.equal(payout.get("route"), route);
	}
	
	private static Specification<PayOutEntity> hasStatus(String status) {
		
		return (payout, cq, cb) -> cb.equal(payout.get("status"), EtransactionStatus.valueOf(status));
	}
	
	
	
	private static Specification<PayOutEntity>  hasDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		if(date!=null) {
		date =date +" 00:00:00";
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return (payout, cq, cb) -> cb.and(
		          cb.greaterThanOrEqualTo(payout.get("createdDate"), dateTime),
		          cb.lessThan(payout.get("createdDate"), dateTime.plusDays(1)) );
		}else {
			LocalDateTime dateTime = LocalDateTime.parse(LocalDate.now()+" 00:00:00", formatter);

			return (payout, cq, cb) -> cb.and(
			          cb.greaterThanOrEqualTo(payout.get("createdDate"), dateTime),
			          cb.lessThan(payout.get("createdDate"), LocalDateTime.now()));
		}
		
	}
	
	
	public static Specification<PayOutEntity>  hasFilterDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		date =date +" 00:00:00";
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return (payout, cq, cb) -> cb.and(
		          cb.greaterThanOrEqualTo(payout.get("createdDate"), dateTime),
		          cb.lessThan(payout.get("createdDate"),LocalDateTime.now()) );
		
	}
	
	public static Specification<PayOutEntity> getByRoleTypeTransaction() {

		Specification<PayOutEntity> query = hasFilterDate(LocalDate.now().minusMonths(6).toString());

		if (SecurityUtils.getCurrentUserRole().equals(AuthoritiesConstants.VENDOR.toString())) {
			query.and(hasVendorId(SecurityUtils.getCurrentUserUuid().get()));
		}
		return query;
	}
}
