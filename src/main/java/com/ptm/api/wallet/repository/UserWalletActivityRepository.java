package com.ptm.api.wallet.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.wallet.entity.UserWalletActivity;

public interface UserWalletActivityRepository extends JpaRepository<UserWalletActivity, Long> {

	public List<UserWalletActivity> findByUserid(String userid,Pageable pageable);
	
	public List<UserWalletActivity> findByUseridAndBusinessWalletWalletType(String userid,EwalletType ewalletType,Pageable pageable);

	public List<UserWalletActivity> findByUseridAndServiceType(String userid,String serviceType,Pageable pageable);


}
