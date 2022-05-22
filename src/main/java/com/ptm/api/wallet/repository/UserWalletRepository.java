package com.ptm.api.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.wallet.entity.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, String> {

	public Optional<UserWallet> findByUseridAndWalletType(String id, EwalletType ewalletType);

	public List<UserWallet> findByWalletType(Pageable pageable, EwalletType ewalletType);

	public List<UserWallet> findByUserid(String id);

	public Page<UserWallet> findAll(Pageable pageable);

}
