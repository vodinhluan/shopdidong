package com.shopdidong.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopdidong.common.entity.Brand;
import com.shopdidong.common.entity.Category;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
	@Autowired
	private BrandRepository repo;
	
	@Test
	// 1 category
	public void testCreateBrand1() {
		Category laptops = new Category(8);
		Brand dell = new Brand("DELL");
		dell.getCategories().add(laptops);
		
		Brand savedBrand = repo.save(dell);
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	// 2 category
	public void testCreateBrand2() {
		Category dienthoais = new Category(1);
		Category tablets = new Category(39);
		Brand apple = new Brand("Apple");
		apple.getCategories().add(dienthoais);
		apple.getCategories().add(tablets);
		Brand savedBrand = repo.save(apple);
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateBrand3() {
		Brand samsung = new Brand("Samsung");
		samsung.getCategories().add(new Category(25));
		samsung.getCategories().add(new Category(26));
		Brand savedBrand = repo.save(samsung);
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void findAllBrands() {
		Iterable<Brand> brands = repo.findAll();
		brands.forEach(System.out::println);
		assertThat(brands).isNotEmpty();
	}
	
	@Test
	public void updateNameBrand() {
		String newName="Apple";
		Brand findBrand = repo.findById(3).get();
		findBrand.setName(newName);
		
		Brand savedBrand = repo.save(findBrand);
		assertThat(savedBrand.getName()).isEqualTo(newName);
	}
	
	@Test
	public void testDelete() {
		Integer id = 7;
		repo.deleteById(id);
		
		Optional<Brand> result = repo.findById(id);
		assertThat(result.isEmpty());
	}
}
