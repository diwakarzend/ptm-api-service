package com.ptm.api.user.controller.vo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ptm.api.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIpVO {

	private List<String> ip;

	private String username;

	private String lastUpdated;

	private String name;

	public UserIpVO(User mapper) {
		this.ip = Arrays.asList(mapper.getIpAddress().split(",")).stream().collect(Collectors.toList());
		this.username = mapper.getUsername();
		this.lastUpdated = mapper.getLastModifiedDate().toString();
		this.name = mapper.getFirstName();
	}

}
