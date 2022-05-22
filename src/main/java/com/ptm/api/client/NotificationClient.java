package com.ptm.api.client;

import org.springframework.web.bind.annotation.RequestBody;

import com.ptm.api.payout.dto.PayoutDto;
import com.ptm.api.user.controller.dto.OtpDTO;
//@FeignClient(value = "notificationService", url = "http://165.22.208.28:8082/")
public interface NotificationClient {

	//@PostMapping("/api/sendSmsOtp")
	public void sendSmsOtp(@RequestBody OtpDTO otpDTO);
	
	//@PostMapping("/aeps/payout")
	public String payout(@RequestBody PayoutDto payoutDto);

}
