package com.shopdidong.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopdidong.common.entity.Category;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String listAll(Model model) {
		List<Category> listCategories = service.listAll();
		model.addAttribute("listCategories",listCategories);
		return "categories/categories";
	}
	
//	@GetMapping("/categories/{id}/enabled/{status}")
//	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
//			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
//		service.updateCategoryEnabledStatus(id, enabled);
//		String status = enabled ? "enabled" : "disabled";
//		String message = "The category ID " + id + " has been " + status;
//		redirectAttributes.addFlashAttribute("message", message);
//		return "redirect:/categories";
//	}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		List<Category> listCategories = service.listCategoriesUsedInform();
		
		model.addAttribute("category", new Category());
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("pageTitle","Create New Category");
		return "categories/category_form";		
	}
	
}
