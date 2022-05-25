package com.ptm.api.ptp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptm.api.config.constant.Eflags;
import com.ptm.api.user.entity.AbstractAuditingEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Aman Garg
 */
@Entity
@Table(name = "ptm_ptp_detail")
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ptp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "user_uuid", length = 100, nullable = false)
	private String userUUID;

	@JsonIgnore
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "vendor_id", length = 100, nullable = false)
	private String vendorId;

	@JsonIgnore
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "qr_details", length = 100, nullable = false)
	private String qrDetails;

	@JsonIgnore
	@NotNull
	@Column(name = "total_limit")
	private Double totalLimit;

	@JsonIgnore
	@NotNull
	@Column(name = "daily_limit")
	private Double dailyLimit;

	@Column(name = "running_date")
	private LocalDateTime runningDate;

	@CreatedDate
	@Column(name = "created_date")
	@JsonIgnore
	private LocalDateTime createdDate = LocalDateTime.now();

	@CreatedBy
	@Column(name = "created_by", length = 100, updatable = false)
	@JsonIgnore
	private String createdBy;

	@LastModifiedDate
	@Column(name = "updated_date")
	@JsonIgnore
	private LocalDateTime updatedDate;

	@LastModifiedBy
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
