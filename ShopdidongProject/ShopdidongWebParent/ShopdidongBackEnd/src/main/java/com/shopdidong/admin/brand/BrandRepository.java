package com.shopdidong.admin.brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.shopdidong.common.entity.Category;

public interface BrandRepository extends PagingAndSortingRepository<Category, Integer>
	,CrudRepository<Category, Integer> {
	
}