package com.shopdidong.admin.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopdidong.common.entity.Category;
import com.shopdidong.common.entity.User;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>
	,CrudRepository<Category, Integer> {

}