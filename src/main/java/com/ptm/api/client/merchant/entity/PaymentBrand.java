package com.ptm.api.client.merchant.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptm.api.config.constant.Eflags;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_brand")
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentBrand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "brand_name")
	private String brandName;

	@Column(name = "register_company")
	private String registerCompany;

	@Column(name = "created_date")
	@JsonIgnore
	private LocalDateTime createdDate = LocalDateTime.now();

	@Column(name = "created_by", length = 100, updatable = false)
	@JsonIgnore
	private String createdBy;

	@Column(name = "updated_date")
	@JsonIgnore
	private LocalDateTime updatedDate;

	@Column(name = "updated_by", length = 100, updatable = false)
	@JsonIgnore
	private String updatedBy;

	@JsonIgnore
	@Column(name = "is_deleted")
	@Enumerated(EnumType.STRING)
	private Eflags isDeleted = Eflags.N;

	@JsonIgnore
	@Column(name = "is_status")
	@Enumerated(EnumType.STRING)
	private Eflags isStatus = Eflags.N;

	@JsonIgnore
	@Column(name = "is_published")
	@Enumerated(EnumType.STRING)
	private Eflags isPublished = Eflags.N;

}
