package com.ptm.api.config.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ptm.api.config.CustomUserDetails;
import com.ptm.api.config.constant.JWTconstant;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.user.service.impl.UserDetailSeviceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	private static final String AUTHORITIES_KEY = "auth";
	private static final String PRIVILEGE_KEY = "privilege";

	private long tokenValidityInMilliseconds;

	private long tokenValidityInMillisecondsForRememberMe;

	@Autowired
	private UserDetailSeviceImpl userDetailServiceImpl;

	@PostConstruct
	public void init() {

		this.tokenValidityInMilliseconds = 1000000 * 10;
		this.tokenValidityInMillisecondsForRememberMe = 1000000 * 10;
	}

	public Map<JWTconstant, String> createToken(Authentication authentication, boolean rememberMe) {
		
		Map<JWTconstant, String> tokenMap=new HashMap<>();
		long now = (new Date()).getTime();
		Date validity;
		if (rememberMe) {
			validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
		} else {
			validity = new Date(now + this.tokenValidityInMilliseconds);
		}

		String accessToken= Jwts.builder().setSubject(authentication.getName()).signWith(SignatureAlgorithm.HS512, secret).setExpiration(validity).compact();
		tokenMap.put(JWTconstant.ACCESS, accessToken);
	
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		CustomUserDetails object = (CustomUserDetails) authentication.getPrincipal();
		String apiCodes = object.getAuthApi();

		

		String apiToken= Jwts.builder().setSubject(authentication.getName()).claim(PRIVILEGE_KEY, authorities)
				.claim( AUTHORITIES_KEY, apiCodes).signWith(SignatureAlgorithm.HS512, secret+object.getApiKey()).setExpiration(validity)
				.compact();
		
		tokenMap.put(JWTconstant.API, apiToken);
		
		return tokenMap;

	
	}
	
	
	public Authentication getAuthentication(String token,String apiJwtToken,String ip) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		CustomUserDetails principal = userDetailServiceImpl.loadUserByUsername(claims.getSubject());
		validateToken(apiJwtToken,secret+principal.getApiKey());

		Claims apiClaims = Jwts.parser().setSigningKey(secret+principal.getApiKey()).parseClaimsJws(apiJwtToken).getBody();

		boolean ipmatch= principal.getIpAddress().stream().anyMatch(predicate->predicate.equals(ip));
		if(!ipmatch) {
		//	throw new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND);

		}
		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(apiClaims.get(AUTHORITIES_KEY).toString().split(","))
				.filter(predicate->predicate.length()>0)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());		

		return new UsernamePasswordAuthenticationToken(principal, apiJwtToken, authorities);
	}

	public boolean validateToken(String authToken,String secret) {
		try {
			if (Objects.isNull(secret))
				Jwts.parser().setSigningKey(this.secret).parseClaimsJws(authToken);
			else
				Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);

			return true;
		} catch (SignatureException e) {
			log.info("Invalid JWT signature.");
			log.trace("Invalid JWT signature trace: {}", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}
	
	public boolean validateApiToken(String authToken) {
		try {
			if (Objects.isNull(secret))
				Jwts.parser().setSigningKey(this.secret).parseClaimsJws(authToken);
			else
				Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);

			return true;
		} catch (SignatureException e) {
			log.info("Invalid JWT signature.");
			log.trace("Invalid JWT signature trace: {}", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}
}
