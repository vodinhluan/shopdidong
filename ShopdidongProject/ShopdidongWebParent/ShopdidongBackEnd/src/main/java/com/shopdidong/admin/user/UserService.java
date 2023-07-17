package com.shopdidong.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopdidong.common.entity.Role;
import com.shopdidong.common.entity.User;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User>listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public List<Role>listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	
	public void save(User user) {
		// TODO Auto-generated method stub
		encodePassword(user);
		userRepo.save(user);
	}
	
	private void encodePassword(User user) { // encode = mã hóa
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}
	 
}
