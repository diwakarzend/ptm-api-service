
package com.ptm.api.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ptm.api.user.controller.vo.JWTTokenVO;
import com.ptm.api.user.controller.vo.LoginVM;
import com.ptm.api.user.service.UserService;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JWTTokenVO> createAuthenticationToken(@Valid @RequestBody LoginVM loginVM) throws Exception {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();

		String ip = request.getRemoteAddr();
		System.out.println("\n \n ip :"+ip+"\n \n ");
		JWTTokenVO jwt = userService.validateUser(loginVM);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwt.getIdToken());
		httpHeaders.add("Api-Authorization", "Bearer " + jwt.getApiIdToken());
		return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
	}

	@GetMapping(value = "/refreshToken")
	public ResponseEntity<JWTTokenVO> createRefreshToken(HttpServletRequest request) {

		JWTTokenVO jwt = userService.getRefeshToken(request);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwt);
		return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
	}

	@GetMapping(value = "/validateToken")
	public ResponseEntity<?> validate(HttpServletRequest request) {
		userService.validateToken(request);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/update-apikey")
	public ResponseEntity<?> updateApiKey() {
		userService.updateApiKey();

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/apikey")
	public ResponseEntity<?> getApiKey() {
		userService.getApiKey();

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/logout")
	public ResponseEntity<?> logout() {
		userService.updateApiKey();

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
