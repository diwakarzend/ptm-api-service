package com.ptm.api.payout.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptm.api.config.constant.Eflags;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class PayOutAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "updated_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    @JsonIgnore
    private LocalDateTime lastModifiedDate;
    
    @JsonIgnore
    @Column(name = "is_deleted")
	@Enumerated(EnumType.STRING)
    private Eflags isDeleted=Eflags.N;
       
    @JsonIgnore
    @Column(name = "is_status")
    @Enumerated(EnumType.STRING)
    private Eflags isStatus=Eflags.N;
  
    @JsonIgnore
    @Column(name = "is_published")
    @Enumerated(EnumType.STRING)
    private Eflags isPublished=Eflags.N;

   
}
