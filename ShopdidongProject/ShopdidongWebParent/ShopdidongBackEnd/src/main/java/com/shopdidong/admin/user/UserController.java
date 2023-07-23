package com.shopdidong.admin.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopdidong.common.entity.Role;
import com.shopdidong.common.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
 		model.addAttribute("listUsers", listUsers);
		return "users";		
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRole = service.listRoles(); 
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRole", listRole);
		model.addAttribute("pageTitle", "Create New User");
		return "user_form";
	}
	
	@PostMapping("/users/save")
 	public String saveUser(User user, RedirectAttributes redirectAttributes) {
 		System.out.println(user);
 		service.save(user);

 		redirectAttributes.addFlashAttribute("message", "Lưu thành công!");

 		return "redirect:/users";
 	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		try {
			User user = service.get(id);
			List<Role> listRole = service.listRoles(); 
			model.addAttribute("user", user);
			model.addAttribute("listRole", listRole);
			model.addAttribute("pageTitle", "Edit User (ID = "+id+")");
			return "user_form";

		} catch(UserNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
	 		return "redirect:/users";
		}
	}	
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		try {
			service.delete(id);
	 		redirectAttributes.addFlashAttribute("message", 
	 				"Tài khoản với ID " + id + " đã xóa thành công");
		} catch(UserNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
 		return "redirect:/users";
	}
}