package com.ptm.api.catalog.service.impl;

import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptm.api.catalog.entity.PtmSmsMaster;
import com.ptm.api.catalog.repository.PtmSmsRepository;
import com.ptm.api.catalog.service.SmsService;
import com.ptm.api.client.NotificationClient;
import com.ptm.api.config.constant.EotpType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.config.util.RandomUtil;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.user.controller.dto.OtpDTO;
import com.ptm.api.user.entity.User;
import com.ptm.api.user.entity.UserOtp;
import com.ptm.api.user.repository.UserOtpRepository;
import com.ptm.api.user.repository.UserRepository;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private PtmSmsRepository otpTextMasterRepository;

	@Autowired
	private NotificationClient notificationClient;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserOtpRepository userOtpRepository;

	@Override
	public PtmSmsMaster getOtpTxtByService(String serviceName) {
		PtmSmsMaster otpTextMaster = otpTextMasterRepository.findByServiceName(serviceName).get();
		return otpTextMaster;
	}

	@Override
	public void sendSms(String otp, String phoneNumber, String serviceName) {
		OtpDTO otpDto = new OtpDTO();
		PtmSmsMaster smsDetails=getOtpTxtByService(serviceName);
		otpDto.setOtp(otp);
		otpDto.setTenantId("1");
		otpDto.setUserName(phoneNumber);
		String smsTxt=smsDetails.getSmsTxt().replace("{OTP}", otp);
		otpDto.setOtpText(smsTxt);
		otpDto.setTemplateId(smsDetails.getTemplateId());
		System.out.println("OTP text show ["+ smsTxt +"]");
		System.out.println("OTP template id ["+ smsDetails.getTemplateId() +"]");
		notificationClient.sendSmsOtp(otpDto);

	}
	
	@Override
	public void sendWalletTransSms(UserWalletActionDTO userWalletActionDTO, Double totalAmount, String serviceName) {
		PtmSmsMaster smsDetails=getOtpTxtByService(serviceName);
        Formatter amountFormatter = new Formatter();
        amountFormatter.format("%.2f", totalAmount);

		String sms=smsDetails.getSmsTxt().replace("{TRANSACTION}", userWalletActionDTO.getAmount().toString())
				.replace("{BALANCE}", amountFormatter.toString()).replace("{REMARK}", userWalletActionDTO.getRemark());
		OtpDTO otpDto = new OtpDTO();
		otpDto.setTenantId("1");
		otpDto.setUserName(userWalletActionDTO.getUserId());
		otpDto.setOtpText(sms);
		otpDto.setTemplateId(smsDetails.getTemplateId());
		notificationClient.sendSmsOtp(otpDto);
	}

	@Transactional
	@Override
	public Map<String, Object> sendOtp(String otpType) {

		Optional<User> existingUser = userRepository
				.findOneByUsername(SecurityUtils.getCurrentUserLogin().get().toLowerCase());

		Optional<UserOtp> userOtpOptional = userOtpRepository.findByUserId(existingUser.get().getId());
		Map<String, Object> map = generateUserOtp(existingUser, userOtpOptional, EotpType.valueOf(otpType));

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
		sendSms(otp, SecurityUtils.getCurrentUserLogin().get().toLowerCase(), otpType.toString());
		return map;
	}

	@Override
	public void verifyOtp(String otp,String otpType) {

		Optional<UserOtp> userOtpOptional = userOtpRepository
				.findByUserUsername(SecurityUtils.getCurrentUserLogin().get());
		validateOtp(otp, userOtpOptional,otpType);
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

	private void validateOtp(String otp, Optional<UserOtp> userOtpOptional,String otpType) {

		if (userOtpOptional.isPresent() && !(EotpType.valueOf(otpType).equals(userOtpOptional.get().getOtpType())
				&& otp.equals(userOtpOptional.get().getOtp()))) {
			throw new UserServiceException(UserExceptionCodeAndMassage.WRONG_OTP);

		}

	}
}
