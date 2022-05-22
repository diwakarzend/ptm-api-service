package com.ptm.api.payout.vo;

import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyTransactionReportVo {

	private String month;

	private String year;

	private Double totalRevenue;
	
	public MonthlyTransactionReportVo() {}
	

	public MonthlyTransactionReportVo(Entry<String, Double> mapper) {
		String[] yearMonth=mapper.getKey().split("-");
		this.year=yearMonth[0];
		this.month=yearMonth[1];
		this.totalRevenue=mapper.getValue();
	}

}
