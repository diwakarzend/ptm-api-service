package com.ptm.api.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.catalog.entity.PtmSmsMaster;

public interface PtmSmsRepository extends JpaRepository<PtmSmsMaster, Long> {

	public Optional<PtmSmsMaster> findByServiceName(String serviceName);

}
