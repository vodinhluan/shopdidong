package com.shopdidong.admin.category;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopdidong.common.entity.Category;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll() {
		return (List<Category>) repo.findAll();
	}

//	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
//		repo.updateEnabledStatus(id, enabled);	
//	}
	
	public List<Category> listCategoriesUsedInform() {
		List<Category> categoriesUsedInform = new ArrayList();
		Iterable<Category> categoriesInDb = repo.findAll();
		
		for (Category category : categoriesInDb) {
			if (category.getParent() == null) {
				categoriesUsedInform.add(new Category(category.getName()));
				System.out.println(category.getName());
				Set<Category> children = category.getChildren();
				
				for (Category subCategory : children) {
					String name = "--"+ subCategory.getName();
					categoriesUsedInform.add(new Category(name));
					listChildren(categoriesUsedInform, subCategory, 1);
				}
			}
			 
		}
		
		return categoriesUsedInform;		
	}
	private void listChildren(List<Category> categoriesUsedInform, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}

			name += subCategory.getName();

			categoriesUsedInform.add(new Category(name));

			listChildren(categoriesUsedInform, subCategory, newSubLevel);
		}		
	}
	
}
	
	


