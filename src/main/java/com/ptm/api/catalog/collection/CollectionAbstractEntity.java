package com.ptm.api.catalog.collection;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class CollectionAbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

	
	
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
  
   
   
}
