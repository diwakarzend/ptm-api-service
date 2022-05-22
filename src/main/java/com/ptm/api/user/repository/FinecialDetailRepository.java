package com.ptm.api.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.user.entity.FinecialDetailEntity;

public interface FinecialDetailRepository extends JpaRepository<FinecialDetailEntity, Long> {
	
	Optional<FinecialDetailEntity> findByUserUuid(String uuid);

}
