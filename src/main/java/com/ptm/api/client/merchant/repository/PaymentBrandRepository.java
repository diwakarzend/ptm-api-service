package com.ptm.api.client.merchant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.client.merchant.entity.PaymentBrand;

public interface PaymentBrandRepository extends JpaRepository<PaymentBrand, Long> {
	
}
