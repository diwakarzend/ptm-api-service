package com.ptm.api.user.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ptm.api.user.entity.User;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Piyush
 *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	@NotBlank
	//@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 10, max = 50,message = "please pass valid userName b/w 10-50 character ")
	protected String userName;

	@Size(max = 50)
	@NotNull(message = "firstName can not be null")
	@NotBlank(message = "firstName can not be blank")
	protected String firstName;

	@Size(max = 50)
	@NotNull(message = "lastName can not be null")
	@NotBlank(message = "lastName can not be blank")
	protected String lastName;

	@Email
	@Size(min = 5, max = 254)
	protected String email;

	@Size(min = 2, max = 6)
	protected String langKey;


	@NotNull(message = "Role can not be null")
	@NotBlank(message = "Role can not be blank")
	protected String role;

	protected Integer tenantId;

	@NotNull(message = "phoneNumber can not be null")
	@NotBlank(message = "phoneNumber can not be blank")
	protected String phoneNumber;

	//@Size(min = 10, max = 10)
	protected String dob;

	@Size(min = 5, max = 254)
	protected String address1;

	//@Size(max = 254)
	protected String address2;

	@Size(min = 6, max = 6)
	protected String pincode;

	@Size(max = 254)
	protected String landmark;
	
	@Size(max = 100)
	protected String qrCodeId;
	
	private List<String> apiPermission;

	public UserDTO() {
		// Empty constructor needed for Jackson.
	}

	public UserDTO(User user) {
		this.userName = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.langKey = user.getLangKey();
		this.dob = user.getDob();
		this.address1 = user.getAddress1();
		this.address2 = user.getAddress2();
		this.pincode = user.getPincode();
		this.landmark = user.getLandmark();
		this.email = user.getEmail();
		this.langKey = user.getLangKey();
		this.qrCodeId = user.getQrCodeId();
		this.role=user.getRoles().stream().map(mapper->mapper.getName()).findFirst().get();
		this.apiPermission= user.getUserApi().stream().map(mapper->mapper.getApiCode()).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", langKey=" + langKey  + ", role=" + role + ", tenantId="
				+ tenantId + ", phoneNumber=" + phoneNumber + "]";
	}

}
