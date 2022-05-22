package com.ptm.api.client.merchant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.client.merchant.entity.MerchantPayOutConfigEntity;

public interface MerchantPayOutConfigRepository extends JpaRepository<MerchantPayOutConfigEntity, Long> {

	MerchantPayOutConfigEntity findByMinRangeAndMaxRange(double minRange, double maxRange);

}
