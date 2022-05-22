package com.ptm.api.payout.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PtmPage<T extends Object> {
	
	private T content;
	private int number;
	private int numberOfElements;
	private long totalElements;
	private int totalPages;

	public PtmPage(T content, int number, int numberOfElements, long totalElements, int totalPages) {
		this.content = content;
		this.number = number;
		this.numberOfElements = numberOfElements;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

}
