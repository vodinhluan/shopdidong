package com.shopdidong.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopdidong.common.entity.Role;
import com.shopdidong.common.entity.User;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public List<User>listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public List<Role>listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	
	public void save(User user) {
		// TODO Auto-generated method stub
		userRepo.save(user);
	}
	
	 
}
