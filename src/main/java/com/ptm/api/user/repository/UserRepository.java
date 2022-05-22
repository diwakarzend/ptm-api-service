package com.ptm.api.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ptm.api.config.constant.Eflags;
import com.ptm.api.user.entity.User;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByUsername(String login);

    Optional<User> findOneWithAuthoritiesById(Long id);

    Optional<User> findOneWithAuthoritiesByUsername(String login);

    Optional<User> findOneWithAuthoritiesByEmail(String email);

    Page<User> findAllByUsernameNot(Pageable pageable, String login);
    
    Page<User> findAllByParentIdAndUsernameNot(Pageable pageable,String pid, String login);

    Optional<User> findOneByPhonenumber(String phonenumber);
    
    Optional<User> findOneByQrCodeId(String qrcodeId);
    
    Page<User> findByUsername(String login,Pageable pageable);
    
    Optional<User> findOneByUsernameAndIsStatus(String login,Eflags status);




}
