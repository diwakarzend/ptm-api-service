package com.ptm.api.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.wallet.entity.UserWallet;

public interface BusinessWalletRepository extends JpaRepository<UserWallet,String>  {
	
	public Optional<UserWallet> findByUserid(String userid);

}
