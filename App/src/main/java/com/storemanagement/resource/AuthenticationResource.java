package com.storemanagement.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storemanagement.configuration.JWTTokenService;
import com.storemanagement.dto.AuthenticationRequest;
import com.storemanagement.dto.AuthenticationResponse;
import com.storemanagement.service.AdminUserService;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationResource {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenService jwtutils;

	@Autowired
	private AdminUserService adminUserService;

	@PostMapping("/token")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = adminUserService.loadUserByUsername(authenticationRequest.getUserName());

		final String token = jwtutils.generateToken(userDetails);
		AuthenticationResponse response = new AuthenticationResponse();
		response.setToken(token);
		response.setTokenExpiryDate(jwtutils.extractExpiration(token));
		response.setUserName(authenticationRequest.getUserName());
		return ResponseEntity.ok(response);
	}
}
