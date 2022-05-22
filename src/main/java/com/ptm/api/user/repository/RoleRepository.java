package com.ptm.api.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

	public Optional<Role> findByName(String role);
	public Optional<Role> findById(Long id);

}
