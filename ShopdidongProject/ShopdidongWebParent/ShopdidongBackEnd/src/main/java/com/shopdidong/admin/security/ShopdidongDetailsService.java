package com.shopdidong.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopdidong.admin.user.UserRepository;
import com.shopdidong.common.entity.User;

public class ShopdidongDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new ShopdidongUserDetails(user);
		}
		throw new UsernameNotFoundException("Không tìm thấy user "+email);
		
	}

}
