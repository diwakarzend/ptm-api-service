package com.ptm.api.catalog.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptm.api.config.util.CatalogUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class CatalogueAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "updated_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();
    
    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted=false;
       
    @JsonIgnore
    @Column(name = "is_status")
    private Boolean isStatus=false;
  
    @JsonIgnore
    @Column(name = "is_published")
    private Boolean isPublished=false;
  
    @Column(name = "uuid")
    private String uuid;
    
	@PrePersist
	private void validationBeforeSaving() {
		if (StringUtils.isBlank(uuid)) {
			uuid = CatalogUtil.generateUniqueId();
		}
	}

   
}
