package com.ptm.api.catalog.collection;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ptm.api.catalog.dto.CommissionRangeDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class AepsComissionCofigCollection extends CollectionAbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String userName;
	private List<CommissionRangeDto> commissionRange;
}
