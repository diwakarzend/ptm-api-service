package com.ptm.api.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ptm.api.user.entity.Role;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String username;
	private final String password;
	private final String mobileNumber;
	private String authApi;
	private Set<Role> roles ;
	private String userUuid;
	private String apiKey;
	private List<String> ipAddress;

	public CustomUserDetails(String username, String password, String mobileNumber, Set<Role> roles,
			String  authApi,String userUuid,String apiKey,String ipAddress) {
		super();
		this.username = username;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.authApi = authApi;
		this.roles = roles ;
		this.userUuid=userUuid;
		this.apiKey=apiKey;
		this.ipAddress=Arrays.asList(ipAddress.split(",")).stream().collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	public void setAuthorities(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> roleList = roles.stream().map(mapper->mapper.getName()).collect(Collectors.toList());
		return roleList;
	}

	private Set<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getAuthApi() {
		return authApi;
	}

	public List<String> getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
	
	
	
	
}
