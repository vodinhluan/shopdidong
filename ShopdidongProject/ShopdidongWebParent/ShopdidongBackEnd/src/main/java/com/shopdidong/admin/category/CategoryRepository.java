package com.shopdidong.admin.category;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopdidong.common.entity.Category;

import jakarta.transaction.Transactional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>
	,CrudRepository<Category, Integer> {

//	void updateEnabledStatus(Integer id, boolean enabled);
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories(Sort sort);
	
// update enabled status
	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	@Transactional
	public void updateEnabledStatus(Integer id, boolean enabled);
	
// delete
	public Long countById(Integer id);
	
	public Category findByName(String name);
	
	public Category findByAlias(String alias);



}
