package com.shopdidong.admin.product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopdidong.common.entity.Product;
import jakarta.transaction.Transactional;

@Service
@Transactional
// phương thức hoặc lớp 
// được đánh dấu cần được thực thi trong một giao dịch.
// đảm bảo rằng tất cả các thay đổi
// được thực hiện trong phương thức 
// hoặc lớp đó sẽ được lưu vào database cùng một lúc.

public class ProductService {
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}
	
	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		
		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replace(" ", "-");
			product.setAlias(defaultAlias);
		} else {
			product.getAlias().replace(" ", "-");
		}
		
		product.setUpdatedTime(new Date());
		
		return repo.save(product);
	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (productByName != null) return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		return "OK";
	}
	
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
}
