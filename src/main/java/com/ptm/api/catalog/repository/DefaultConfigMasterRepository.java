package com.ptm.api.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.catalog.entity.DefaultConfigMaster;

public interface DefaultConfigMasterRepository extends JpaRepository<DefaultConfigMaster, Long> {

	DefaultConfigMaster findByServiceName(String serviceCode);

}
