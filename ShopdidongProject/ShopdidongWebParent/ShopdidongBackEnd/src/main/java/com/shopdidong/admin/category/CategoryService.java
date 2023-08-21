package com.shopdidong.admin.category;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.shopdidong.admin.user.UserNotFoundException;
import com.shopdidong.common.entity.Category;
import com.shopdidong.common.entity.Role;
import com.shopdidong.common.entity.User;


@Service
public class CategoryService {
	private static final int ROOT_CATEGORIES_PER_PAGE = 4;
	
	@Autowired
	private CategoryRepository repo;
	
	// lấy từ database
	public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNum, String sortDir) {
		Sort sort = Sort.by("name");
		
		if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}
		// ĐỌC KĨ, HƠI KHÓ HIỂU
		
		Pageable pageable = PageRequest.of(pageNum -1, ROOT_CATEGORIES_PER_PAGE, sort);
		
		Page<Category> pageCategories = repo.findRootCategories(pageable);
		List<Category> rootCategories = pageCategories.getContent();
		
		pageInfo.setTotalElements(pageCategories.getTotalElements());
		pageInfo.setTotalPages(pageCategories.getTotalPages());
		return listHierarchicalCategories(rootCategories, sortDir);
	}
	
	private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
		List<Category> hierarchicalCategories = new ArrayList<>();
		
		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory));
			
			Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);
			
			for (Category subCategory : children) {
				String name = "--"+ subCategory.getName();				
				hierarchicalCategories.add(Category.copyFull(subCategory, name));

				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
			}
		}
		return hierarchicalCategories;
	}
	
	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories ,
			Category parent, int subLevel, String sortDir) {
		Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);
		int newSubLevel = subLevel + 1;

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
		}
	}
	
	public Category save(Category category) {
		return repo.save(category);
	}

//	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
//		repo.updateEnabledStatus(id, enabled);	
//	}
	
	public List<Category> listCategoriesUsedInform() {
		List<Category> categoriesUsedInform = new ArrayList();
		Iterable<Category> categoriesInDb = repo.findRootCategories(Sort.by("name").ascending());
		
		for (Category category : categoriesInDb) {
			if (category.getParent() == null) {
				categoriesUsedInform.add(Category.copyIdandName(category));
				
				System.out.println(category.getName());
				Set<Category> children = sortSubCategories(category.getChildren());
				
				for (Category subCategory : children) {
					String name = "--"+ subCategory.getName();
					categoriesUsedInform.add(Category.copyIdandName(subCategory.getId(), name));
					listSubCategoriesUsedInform(categoriesUsedInform, subCategory, 1);
				}
			}
		}
		return categoriesUsedInform;		
	}
	
	private void listSubCategoriesUsedInform(List<Category> categoriesUsedInform, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = sortSubCategories(parent.getChildren());

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}

			name += subCategory.getName();

			categoriesUsedInform.add(Category.copyIdandName(subCategory.getId(), name));

			listSubCategoriesUsedInform(categoriesUsedInform, subCategory, newSubLevel);
		}		
	}
	
	
	// Edit category
	public Category get(Integer id) throws CategoryNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with ID "+ id);
		}
	}
	
	// Delete category
	public void delete(Integer id) throws CategoryNotFoundException {
		Long countById =  repo.countById(id);
		if (countById == null || countById == 0) {
			throw new CategoryNotFoundException("Could not find any category with ID "+ id);
		}
		repo.deleteById(id);
	}
	
	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);
		Category categoryName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (categoryName != null)
			{
				return "DuplicateName";
			} else {
				Category categoryAlias = repo.findByAlias(alias);
				if (categoryAlias != null) {
					return "DuplicateAlias";
				}
			}
		} else {
			if (categoryName != null && categoryName.getId() != id) 
				return "DuplicateName";

			Category categoryAlias = repo.findByAlias(alias);
			if (categoryAlias != null && categoryAlias.getId() != id) {
				return "DuplicateAlias";
			}
		}
		return "OK";
	}
	
	private SortedSet<Category> sortSubCategories(Set<Category> children) {
		return sortSubCategories(children, "asc");
	}
	
	private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
		SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category cat1, Category cat2) {
				// TODO Auto-generated method stub
				Sort sort = Sort.by("name");
				if  (sortDir.equals("asc")) {
					return cat1.getName().compareTo(cat2.getName());
				} else {
					return cat2.getName().compareTo(cat1.getName());
				}	
			}
		});
		
		sortedChildren.addAll(children);
		return sortedChildren;
	}
	
}
	
	


