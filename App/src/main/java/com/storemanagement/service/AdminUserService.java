package com.storemanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.storemanagement.model.AdminUserEntity;
import com.storemanagement.repository.AdminUserRepository;

@Service
public class AdminUserService implements UserDetailsService {

	@Autowired
	private AdminUserRepository adminRepository;

	private static final Logger logger = LoggerFactory.getLogger(AdminUserService.class);

	@Override
	public AdminUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
		return adminRepository.findUserByUserName(username).orElseThrow(() -> {
			logger.debug("No Admin User Found with userName: {}", username);
			return new UsernameNotFoundException("No Admin User Found with userName: " + username);
		});
	}
	
}
