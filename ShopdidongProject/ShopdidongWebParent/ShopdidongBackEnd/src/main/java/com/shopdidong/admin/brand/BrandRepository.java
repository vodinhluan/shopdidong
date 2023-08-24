package com.shopdidong.admin.brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopdidong.common.entity.Brand;
import com.shopdidong.common.entity.Category;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer>
	,CrudRepository<Brand, Integer> {
	
}