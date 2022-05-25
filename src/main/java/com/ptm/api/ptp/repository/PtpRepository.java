package com.ptm.api.ptp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.ptp.dto.PtpDTO;
import com.ptm.api.ptp.entity.Ptp;

public interface PtpRepository extends JpaRepository<Ptp, Long> {

	Page<Ptp> findAllByUserUUID(Pageable pageable,String uuid);
	
}
