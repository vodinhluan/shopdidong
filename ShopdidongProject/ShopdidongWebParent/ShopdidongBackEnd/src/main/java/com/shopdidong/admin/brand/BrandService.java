package com.shopdidong.admin.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopdidong.common.entity.Brand;

@Service
public class BrandService {
	@Autowired
	private BrandRepository repo;
	
	public List<Brand> listAll() {
		return (List<Brand>) repo.findAll();
	}
}