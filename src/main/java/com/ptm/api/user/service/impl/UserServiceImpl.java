package com.ptm.api.user.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ptm.api.catalog.collection.PayoutConfigCollection;
import com.ptm.api.catalog.collectionRepo.PayoutConfigRepository;
import com.ptm.api.catalog.service.SmsService;
import com.ptm.api.catalog.service.impl.DefaultCondigureServiceImpl;
import com.ptm.api.config.constant.Constants;
import com.ptm.api.config.constant.Eflags;
import com.ptm.api.config.constant.EotpType;
import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.config.constant.JWTconstant;
import com.ptm.api.config.jwt.JWTConfigurer;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.config.jwt.TokenProvider;
import com.ptm.api.config.util.RandomUtil;
import com.ptm.api.config.util.Utility;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.payout.dto.PtmPage;
import com.ptm.api.user.controller.dto.OtpDTO;
import com.ptm.api.user.controller.dto.RegisterUserDTO;
import com.ptm.api.user.controller.dto.UserDTO;
import com.ptm.api.user.controller.dto.UserIpDto;
import com.ptm.api.user.controller.vo.JWTTokenVO;
import com.ptm.api.user.controller.vo.KeyAndPasswordVM;
import com.ptm.api.user.controller.vo.LoginVM;
import com.ptm.api.user.controller.vo.UserFinDetailsVo;
import com.ptm.api.user.controller.vo.UserIpVO;
import com.ptm.api.user.entity.FinecialDetailEntity;
import com.ptm.api.user.entity.PtmApi;
import com.ptm.api.user.entity.Role;
import com.ptm.api.user.entity.TempUserOtp;
import com.ptm.api.user.entity.User;
import com.ptm.api.user.entity.UserOtp;
import com.ptm.api.user.repository.FinecialDetailRepository;
import com.ptm.api.user.repository.PtmApiRepository;
import com.ptm.api.user.repository.RoleRepository;
import com.ptm.api.user.repository.TempUserOtpRepository;
import com.ptm.api.user.repository.UserOtpRepository;
import com.ptm.api.user.repository.UserRepository;
import com.ptm.api.user.service.UserService;
import com.ptm.api.user.service.mapper.UserMapper;
import com.ptm.api.wallet.service.UserWalletService;

@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserOtpRepository userOtpRepository;

	@Autowired
	TempUserOtpRepository tempUserOtpRepository;

	@Autowired
	private SmsService smsService;

	@Autowired
	private UserWalletService userWalletService;

	@Value("${admin.uuid}")
	private String adminUuid;

	@Autowired
	private FinecialDetailRepository finecialDetailRepository;

	@Autowired
	private PtmApiRepository ptmApiRepository;
	
	@Autowired
	DefaultCondigureServiceImpl defaultCondigureServiceImpl;
	
	@Autowired
	private PayoutConfigRepository payoutConfigRepository;

	public JWTTokenVO validateUser(LoginVM loginVM) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());
		/// validation pending
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(authenticationToken);
		} catch (AuthenticationException e) {
			throw new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginVM.getRememberMe() == null) ? false : loginVM.getRememberMe();
		Map<JWTconstant, String> tokenMap = tokenProvider.createToken(authentication, rememberMe);
		return new JWTTokenVO(tokenMap.get(JWTconstant.ACCESS), tokenMap.get(JWTconstant.API));
	}

	public JWTTokenVO getRefeshToken(HttpServletRequest request) {

		String jwt = "";
		String apiJwt = "";
		String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
		String bearerApiToken = request.getHeader(JWTConfigurer.API_AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && StringUtils.hasText(bearerApiToken) && bearerToken.startsWith("Bearer ")
				&& bearerApiToken.startsWith("Bearer ")) {

			jwt = bearerToken.substring(7, bearerToken.length());
			apiJwt = bearerToken.substring(7, bearerToken.length());

		}
		String ip = request.getRemoteAddr();

		Map<JWTconstant, String> tokenMap = tokenProvider.createToken(tokenProvider.getAuthentication(jwt, apiJwt, ip),
				false);
		return new JWTTokenVO(tokenMap.get(JWTconstant.ACCESS), tokenMap.get(JWTconstant.API));
	}

	@Transactional
	public Optional<User> activateRegistration(String key) {

		log.debug("Activating user for activation key {}", key);
		Optional<User> userdata = userRepository.findOneByActivationKey(key).map(user -> {
			// activate given user for the registration key.
			user.setActivationKey(null);

			log.debug("Activated user: {}", user);
			return user;
		});
		if (!userdata.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);
		}

		return userdata;

	}

	@Transactional
	public void completePasswordReset(KeyAndPasswordVM keyAndPassword) {
		log.debug("Reset user password for reset key {}", keyAndPassword);
		if (!Utility.checkPasswordLength(keyAndPassword.getNewPassword())) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PASSWORD_FORMAT_ERROR);
		}
		Optional<UserOtp> userOtpOptional = userOtpRepository.findByUserUsernameAndOtpAndOtpType(
				keyAndPassword.getPhoneNumber(), keyAndPassword.getOtp(), EotpType.FORGET_PASSWORD);
		validateUserOtpAnOtpType(keyAndPassword.getOtp(), userOtpOptional);

		if (!userOtpOptional.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND);

		} else {
			Optional<User> userDetails = userRepository.findOneByUsername(keyAndPassword.getPhoneNumber()).map(user -> {
				user.setPassword(passwordEncoder.encode(keyAndPassword.getNewPassword()));
				user.setResetDate(LocalDateTime.now());
				return user;
			});
			userRepository.save(userDetails.get());

		}
	}

	@Transactional
	public void requestPasswordReset(String phoneNumber) {

		Optional<User> userData = userRepository.findOneByPhonenumber(phoneNumber)
				.filter(f -> f.getIsPublished().equals(Eflags.N));
		if (!userData.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PHONE_NUMBER_NOT_FOUND);
		}

		// Implement reset count max
		Optional<UserOtp> userOtpOptional = userOtpRepository.findByUserId(userData.get().getId());
		generateUserOtp(userData, userOtpOptional, EotpType.FORGET_PASSWORD);

		/*
		 * mailService.sendPasswordResetMail(userData .orElseThrow(() -> new
		 * UserServiceException(UserExceptionCodeAndMassage.PHONE_NUMBER_NOT_FOUND)));
		 */

	}

	@Transactional
	public User registerUser(RegisterUserDTO registerUserDTO) {

		if (!Utility.checkPasswordLength(registerUserDTO.getPassword())) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PASSWORD_FORMAT_ERROR);
		}
		validateRegistrationOTP(registerUserDTO.getPhoneNumber(), registerUserDTO.getOtp());
		validateUserDetails(registerUserDTO);

		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(registerUserDTO.getPassword());
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Role role = setRole(registerUserDTO);

		UserMapper userMapper = new UserMapper();
		newUser = userMapper.userRegister(registerUserDTO, encryptedPassword, role, adminUuid);
		mapUserApi(role, newUser);
		User user = userRepository.save(newUser);
		log.info("Created Information for User: {}", newUser);
		userWalletService.createUserWallet(user.getUsername(), EwalletType.MAIN_WALLET);

		return newUser;
	}

	private Role setRole(UserDTO userDTO) {
		Role role = null;
		if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
			role = roleRepository.findByName(userDTO.getRole())
					.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.WRONG_ROLE));
		}
		return role;
	}

	private void mapUserApi(Role role, User newUser) {
		List<PtmApi> apiRoleList = ptmApiRepository.findByGroupId(role.getId());
		newUser.setUserApi(apiRoleList);
	}

	private void validateRegistrationOTP(String phoneNumber, String otp) {
		Optional<TempUserOtp> tempUserOtpOptional = tempUserOtpRepository.findByPhoneNumberAndOtp(phoneNumber, otp);
		if (tempUserOtpOptional.isPresent() && EotpType.REGISTER_OTP.equals(tempUserOtpOptional.get().getOtpType())) {
			TempUserOtp tempUserOtp = tempUserOtpOptional.get();
			tempUserOtpRepository.delete(tempUserOtp);
		} else {
			// throw new UserServiceException(UserExceptionCodeAndMassage.WRONG_OTP);

		}
	}

	private void validateUserDetails(UserDTO registerUserDTO) {
		userRepository.findOneByUsername(registerUserDTO.getUserName().toLowerCase()).ifPresent(u -> {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_ALREADY_EXIST);
		});
		userRepository.findOneByEmailIgnoreCase(registerUserDTO.getEmail()).ifPresent(u -> {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_EMAIL_ALREADY_EXIST);
		});

		userRepository.findOneByPhonenumber(registerUserDTO.getPhoneNumber()).ifPresent(u -> {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_PHONE_NUMBER_EXIST);
		});
	}

	@Transactional
	public UserDTO createUser(UserDTO userDTO) {

		validateUserDetails(userDTO);

		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Role role = setRole(userDTO);
		User loggedInuser = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin().get()).get();

		UserMapper userMapper = new UserMapper();
		newUser = userMapper.userRegister(userDTO, encryptedPassword, role, loggedInuser.getUuid());
		mapUserApi(role, newUser);

		User user = userRepository.save(newUser);
		log.info("Created Information for User: {}", newUser);
		userWalletService.createUserWallet(user.getUsername(), EwalletType.MAIN_WALLET);
		PayoutConfigCollection payoutConfigCollection=defaultCondigureServiceImpl.getDefaultPayoutConfigConfig("PAYOUT", userDTO.getUserName());
		payoutConfigRepository.save(payoutConfigCollection);
		return userDTO;
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user
	 * @param lastName  last name of user
	 * @param email     email id of user
	 * @param langKey   language key
	 */
	public void updateCurrentUser(UserDTO userDTO) {
		final String userLogin = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND));
		Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getUsername().equalsIgnoreCase(userLogin))) {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_EMAIL_ALREADY_EXIST);
		}
		Optional<User> user = userRepository.findOneByUsername(userLogin);
		if (!user.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_EMAIL_ALREADY_EXIST);
		}
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).ifPresent(currentuser -> {
			currentuser.setFirstName(userDTO.getFirstName());
			currentuser.setLastName(userDTO.getLastName());
			currentuser.setEmail(userDTO.getEmail());
			currentuser.setLangKey(userDTO.getLangKey());
			log.debug("Changed Information for User: {}", user);
		});
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update
	 * @return updated user
	 */
	@Transactional
	public UserDTO updateUser(UserDTO userDTO) {

		Optional<User> existingUser = userRepository.findOneByUsername(userDTO.getUserName().toLowerCase());
		if (!existingUser.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND);
		}
		Optional<User> userdata = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (!Objects.equals(userdata.get().getUsername().toLowerCase(), userDTO.getUserName().toLowerCase())) {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_EMAIL_ALREADY_EXIST);
		}

		return Optional.of(existingUser).filter(Optional::isPresent).map(Optional::get).map(user -> {

			user.setUsername(userDTO.getUserName());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setLangKey(userDTO.getLangKey());

			return user;
		}).map(UserDTO::new).orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR));
	}

	@Transactional
	public void deleteUser(String login) {
		userRepository.findOneByUsername(login).ifPresent(user -> {
			userRepository.delete(user);

			log.debug("Deleted User: {}", user);
		});
	}

	
	@Transactional
	public void updateUserStatus(String userId,String status) {
		userRepository.findOneByUsername(userId).ifPresent(user -> {
			user.setIsStatus(Eflags.valueOf(status));
			userRepository.save(user);
			log.debug("updated User: {}", user);
		});
	}

	public void changePassword(String currentClearTextPassword, String newPassword) {
		if (!Utility.checkPasswordLength(newPassword)) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PASSWORD_FORMAT_ERROR);
		} else if (Objects.equals(currentClearTextPassword, newPassword)) {
			throw new UserServiceException(UserExceptionCodeAndMassage.OLD_NEW_PASSWORD_SAME);

		}
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).ifPresent(user -> {
			String currentEncryptedPassword = user.getPassword();
			if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
				throw new UserServiceException(UserExceptionCodeAndMassage.INVALID_PASSWORD_CHANGE);
			}
			String encryptedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encryptedPassword);
			userRepository.save(user);
			log.debug("Changed password for User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
		User loggedInuser = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin().get()).get();
		return userRepository
				.findAllByParentIdAndUsernameNot(pageable, loggedInuser.getUuid(), Constants.ANONYMOUS_USER)
				.map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public UserDTO getUserByUserName(String login) {
		return userRepository.findOneByUsername(login).map(UserDTO::new).orElse(new UserDTO());
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(Long id) {
		return userRepository.findOneWithAuthoritiesById(id);
	}

	@Transactional(readOnly = true)
	public UserDTO getUserWithAuthorities() {
		return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByUsername)
				.map(UserDTO::new)
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND));
	}

	@Transactional
	public Map<String, Object> sendLoginOtp() {

		Optional<User> existingUser = userRepository
				.findOneByUsername(SecurityUtils.getCurrentUserLogin().get().toLowerCase());

		Optional<UserOtp> userOtpOptional = userOtpRepository.findByUserId(existingUser.get().getId());
		Map<String, Object> map = generateUserOtp(existingUser, userOtpOptional, EotpType.LOGIN);

		return map;
	}

	private Map<String, Object> generateUserOtp(Optional<User> existingUser, Optional<UserOtp> userOtpOptional,
			EotpType otpType) {
		String otp = RandomUtil.generateOTP();
		OtpDTO otpDto = new OtpDTO();
		otpDto.setOtp(otp);
		otpDto.setUserName(existingUser.get().getPhonenumber());
		UserOtp userOtp;
		if (!userOtpOptional.isPresent()) {
			userOtp = new UserOtp();
		} else {
			userOtp = userOtpOptional.get();
		}
		userOtp.setCreatedAt(LocalDateTime.now());
		userOtp.setExpireAt(LocalDateTime.now().plusMinutes(3));
		userOtp.setUser(existingUser.get());
		userOtp.setOtpTries(0);
		userOtp.setOtp(otp);
		userOtp.setOtpType(otpType);
		userOtpRepository.save(userOtp);
		Map<String, Object> map = new HashMap<>();
		map.put("expireTime", userOtp.getExpireAt());
		// ptmKafkaProducer.publishOtp(otpDto);
		smsService.sendSms(otp, existingUser.get().getPhonenumber(), otpType.toString());
		return map;
	}

	public void verifyLoginOtp(String otp) {

		Optional<UserOtp> userOtpOptional = userOtpRepository
				.findByUserUsername(SecurityUtils.getCurrentUserLogin().get());
		validateOtp(otp, userOtpOptional);
		validateUserOtpAnOtpType(otp, userOtpOptional);

	}

	private void validateUserOtpAnOtpType(String otp, Optional<UserOtp> userOtpOptional) {
		if (userOtpOptional.isPresent() && otp.equals(userOtpOptional.get().getOtp())
				&& LocalDateTime.now().isBefore(userOtpOptional.get().getExpireAt())) {

			UserOtp userOtp = userOtpOptional.get();
			int tries = userOtp.getOtpTries() + 1;
			userOtp.setOtpTries(tries);
			userOtp.setOtp(null);
			userOtpRepository.save(userOtp);

		} else if (userOtpOptional.isPresent() && !otp.equals(userOtpOptional.get().getOtp())) {
			UserOtp userOtp = userOtpOptional.get();
			int tries = userOtp.getOtpTries() + 1;
			userOtp.setOtpTries(tries);
			userOtpRepository.save(userOtp);
			throw new UserServiceException(UserExceptionCodeAndMassage.WRONG_OTP);
		} else if (userOtpOptional.isPresent() && LocalDateTime.now().isAfter(userOtpOptional.get().getExpireAt())) {
			UserOtp userOtp = userOtpOptional.get();
			userOtp.setOtp(null);
			userOtpRepository.save(userOtp);
			throw new UserServiceException(UserExceptionCodeAndMassage.OTP_EXPIRE);
		} else {
			throw new UserServiceException(UserExceptionCodeAndMassage.UNAUTHORISED_USER);
		}
	}

	private void validateOtp(String otp, Optional<UserOtp> userOtpOptional) {

		if (userOtpOptional.isPresent() && !(EotpType.LOGIN.equals(userOtpOptional.get().getOtpType())
				&& otp.equals(userOtpOptional.get().getOtp()))) {
			throw new UserServiceException(UserExceptionCodeAndMassage.WRONG_OTP);

		}

	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		/*
		 * 
		 * List<User> users = userRepository
		 * .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3,
		 * ChronoUnit.DAYS)); for (User user : users) {
		 * log.debug("Deleting not activated user {}", user.getUsername());
		 * userRepository.delete(user); }
		 * 
		 */}

	public void sendNewUserRestraionOtp(String phoneNumber) {

		Optional<TempUserOtp> tempUserOtpOptional = tempUserOtpRepository.findByPhoneNumber(phoneNumber);
		TempUserOtp tempUserOtp;
		if (tempUserOtpOptional.isPresent()) {
			tempUserOtp = tempUserOtpOptional.get();
		} else {
			tempUserOtp = new TempUserOtp();

		}
		String otp = RandomUtil.generateOTP();
		tempUserOtp.setCreatedAt(LocalDateTime.now());
		tempUserOtp.setExpireAt(LocalDateTime.now().plusMinutes(3));
		tempUserOtp.setPhoneNumber(phoneNumber);
		tempUserOtp.setOtpTries(0);
		tempUserOtp.setOtp(otp);
		tempUserOtp.setOtpType(EotpType.REGISTER_OTP);

		tempUserOtpRepository.save(tempUserOtp);
		smsService.sendSms(otp, phoneNumber, "REGISTER OTP");
	}

	@Override
	public void validateToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			tokenProvider.validateToken(bearerToken.substring(7, bearerToken.length()), null);
		}
	}

	@Override
	public UserFinDetailsVo getUserFinecialDetails() {
		FinecialDetailEntity finecialDetailEntity = finecialDetailRepository
				.findByUserUuid(SecurityUtils.getCurrentUserUuid().get())
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.USER_FIN_DETAILS_NOT_FOUND));

		return new UserFinDetailsVo(finecialDetailEntity);

	}

	@Override
	public void saveUpdateFinecialDetails(String brnadName, String registerCompany, String registerAddress,
			String gstNo, String website) {
		Optional<FinecialDetailEntity> finecialDetail = finecialDetailRepository
				.findByUserUuid(SecurityUtils.getCurrentUserUuid().get());

		if (finecialDetail.isPresent()) {
			FinecialDetailEntity finecialDetailEntity = setFinDetails(brnadName, registerCompany, registerAddress,
					gstNo, website, finecialDetail.get());
			finecialDetailRepository.save(finecialDetailEntity);
		} else {
			FinecialDetailEntity finecialDetailEntity = new FinecialDetailEntity();
			finecialDetailEntity = setFinDetails(brnadName, registerCompany, registerAddress, gstNo, website,
					finecialDetailEntity);
			finecialDetailEntity.setUserUuid(SecurityUtils.getCurrentUserUuid().get());

			finecialDetailRepository.save(finecialDetailEntity);
		}

	}

	private FinecialDetailEntity setFinDetails(String brnadName, String registerCompany, String registerAddress,
			String gstNo, String website, FinecialDetailEntity finecialDetailEntity) {
		finecialDetailEntity.setBrnadName(brnadName);
		finecialDetailEntity.setRegisterCompany(registerCompany);
		finecialDetailEntity.setRegisterAddress(registerAddress);
		finecialDetailEntity.setGstNo(gstNo);
		finecialDetailEntity.setWebsite(website);
		return finecialDetailEntity;
	}

	public void updateApiKey() {
		Optional<User> existingUser = userRepository
				.findOneByUsername(SecurityUtils.getCurrentUserLogin().get().toLowerCase());
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			user.setApiKey(UUID.randomUUID().toString().replace("-", ""));
			userRepository.save(user);
		}
	}
	
	public String getApiKey() {
		Optional<User> existingUser = userRepository
				.findOneByUsername(SecurityUtils.getCurrentUserLogin().get().toLowerCase());
				
		if (existingUser.isPresent()) {
			return existingUser.get().getApiKey();
		}else {
			return "";
		}
	}

	public void updateIp(UserIpDto userIpDto) {
		Optional<User> existingUser = userRepository
				.findOneByUsername(userIpDto.getUsername());
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			user.setIpAddress(userIpDto.getIp());
			userRepository.save(user);
		}
	}
	
	public PtmPage<List<UserIpVO>> getUserIps(String username, Pageable pageable) {
		Page<User> existingUser;
		if (username != null) {
			 existingUser = userRepository.findByUsername(username, pageable);
		} else {
			 existingUser = userRepository.findAll(pageable);

		}
		List<UserIpVO> list = existingUser.getContent().stream().map(mapper -> new UserIpVO(mapper))
				.collect(Collectors.toList());
		return new PtmPage<List<UserIpVO>>(list, existingUser.getNumber(), existingUser.getNumberOfElements(),
				existingUser.getTotalElements(), existingUser.getTotalPages());
	}

}
