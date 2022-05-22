package com.ptm.api.wallet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class WalletAbstractAuditingEntity implements Serializable {

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
    private LocalDateTime lastModifiedDate = LocalDateTime.now();
    
    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted=false;
       
    @JsonIgnore
    @Column(name = "is_status")
    private Boolean isStatus=false;
  
    @JsonIgnore
    @Column(name = "is_published")
    private Boolean isPublished=false;

    @JsonIgnore
    @Column(name = "remarks")
    private String remarks;
    

   
}
