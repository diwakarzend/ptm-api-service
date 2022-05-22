package com.ptm.api.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.user.entity.PtmApi;

public interface PtmApiRepository extends JpaRepository<PtmApi, String> {
	
	List<PtmApi> findByGroupId(Long groupId);
	

}
