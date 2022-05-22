package com.ptm.api.user.service.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.config.util.CatalogUtil;
import com.ptm.api.config.util.RandomUtil;
import com.ptm.api.user.controller.dto.UserDTO;
import com.ptm.api.user.entity.Role;
import com.ptm.api.user.entity.User;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 */
@Service
public class UserMapper {

	public UserDTO userToUserDTO(User user) {
		return new UserDTO(user);
	}

	public List<UserDTO> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
	}

	public User userDTOToUser(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		} else {
			User user = new User();
			user.setUsername(userDTO.getUserName());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setLangKey(userDTO.getLangKey());
			return user;
		}
	}

	public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
		return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
	}

	public User userFromId(Long id) {
		if (id == null) {
			return null;
		}
		User user = new User();
		user.setId(id);
		return user;
	}


	public User userRegister(UserDTO registerUserDTO, String encryptedPassword,
			Role role,String adminUuid) {

		User newUser = new User();
		newUser.setParentId(adminUuid);
		newUser.setUsername(registerUserDTO.getUserName());
		newUser.setFirstName(registerUserDTO.getFirstName());
		newUser.setLastName(registerUserDTO.getLastName());
		newUser.setPhonenumber(registerUserDTO.getPhoneNumber());
		newUser.setEmail(registerUserDTO.getEmail());
		newUser.setLangKey("en");
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		newUser.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
		newUser.setPassword(encryptedPassword);
		
		if (role != null) {
			newUser.setRoles(Arrays.asList(role));
		}
		newUser.setDob(registerUserDTO.getDob());
		newUser.setAddress1(registerUserDTO.getAddress1());
		newUser.setAddress2(registerUserDTO.getAddress2());
		newUser.setPincode(registerUserDTO.getPincode());
		newUser.setLandmark(registerUserDTO.getLandmark());
		newUser.setUuid(CatalogUtil.generateUniqueId());
		newUser.setApiKey(UUID.randomUUID().toString().replace("-", ""));

		return newUser;
	}
}
