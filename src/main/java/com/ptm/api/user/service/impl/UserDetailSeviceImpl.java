package com.ptm.api.user.service.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptm.api.config.CustomUserDetails;
import com.ptm.api.config.constant.Eflags;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.user.entity.PtmApi;
import com.ptm.api.user.entity.Role;
import com.ptm.api.user.entity.User;
import com.ptm.api.user.repository.UserRepository;

@Service
public class UserDetailSeviceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailSeviceImpl.class);

    
	private final UserRepository userRepository;

	public UserDetailSeviceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	 

    @Override
    @Transactional
	public CustomUserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);

		if (new EmailValidator().isValid(login, null)) {
			/*
			 * Optional<User> userByEmailFromDatabase =
			 * userRepository.findOneWithAuthoritiesByEmail(login); return
			 * userByEmailFromDatabase.map(user -> createSpringSecurityUser(login, user))
			 * .orElseThrow(() -> new UsernameNotFoundException("User with email " + login +
			 * " was not found in the database"));
			 */
		}

		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		
		Optional<User> userByLoginFromDatabase = userRepository.findOneByUsernameAndIsStatus(lowercaseLogin,Eflags.N);
		valiadteUser(userByLoginFromDatabase);
        return userByLoginFromDatabase.map(user -> mapUserRoleAndPermission(lowercaseLogin, user))
            .orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND));
		
	}

	private void valiadteUser(Optional<User> userByLoginFromDatabase) {
		if (!userByLoginFromDatabase.isPresent() || userByLoginFromDatabase.get().getIsDeleted().equals(Eflags.Y)) {
			throw new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND);
		} 
	}


	private CustomUserDetails mapUserRoleAndPermission(String lowercaseLogin, User user) {
		Set<Role>  userRoles  = user.getRoles().stream().collect(Collectors.toSet());
		
		String grantedAuthorities = user.getUserApi().stream().map(PtmApi::getApiCode).collect(Collectors.joining(","));
		if(user.getIpAddress()==null) {
			user.setIpAddress("");
		}
		return new CustomUserDetails(user.getUsername(), user.getPassword(),
				user.getPhonenumber(), 
				userRoles,grantedAuthorities,user.getUuid(),user.getApiKey(),user.getIpAddress());

	}
}