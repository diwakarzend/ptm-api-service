package com.ptm.api.user.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptm.api.payout.dto.PtmPage;
import com.ptm.api.user.controller.dto.RegisterUserDTO;
import com.ptm.api.user.controller.dto.UserDTO;
import com.ptm.api.user.controller.dto.UserIpDto;
import com.ptm.api.user.controller.vo.JWTTokenVO;
import com.ptm.api.user.controller.vo.KeyAndPasswordVM;
import com.ptm.api.user.controller.vo.LoginVM;
import com.ptm.api.user.controller.vo.UserFinDetailsVo;
import com.ptm.api.user.controller.vo.UserIpVO;
import com.ptm.api.user.entity.User;

public interface UserService {

	public Optional<User> activateRegistration(String key);

	public void completePasswordReset(KeyAndPasswordVM keyAndPassword);

	public void requestPasswordReset(String mail);

	public User registerUser(RegisterUserDTO registerUserDTO);

	public UserDTO createUser(UserDTO userDTO);

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user
	 * @param lastName  last name of user
	 * @param email     email id of user
	 * @param langKey   language key
	 */
	public void updateCurrentUser(UserDTO userDTO);

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update
	 * @return updated user
	 */
	public UserDTO updateUser(UserDTO userDTO);

	public void deleteUser(String login);

	public void changePassword(String currentClearTextPassword, String newPassword);

	public Page<UserDTO> getAllManagedUsers(Pageable pageable);

	public UserDTO getUserByUserName(String login);

	public Optional<User> getUserWithAuthorities(Long id);

	public UserDTO getUserWithAuthorities();

	public JWTTokenVO validateUser(LoginVM loginVM);

	public JWTTokenVO getRefeshToken(HttpServletRequest request);

	public void validateToken(HttpServletRequest request);

	public UserFinDetailsVo getUserFinecialDetails();

	void saveUpdateFinecialDetails(String brnadName, String registerCompany, String registerAddress, String gstNo,
			String website);

	public void updateApiKey();

	public void updateIp(UserIpDto userIpDto);

	public PtmPage<List<UserIpVO>> getUserIps(String username, Pageable pageable);

	public void updateUserStatus(String userId,String status);
	
	public String getApiKey();

}
