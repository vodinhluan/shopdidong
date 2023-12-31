package com.shopdidong.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopdidong.common.entity.Brand;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTests {
	@MockBean
	private BrandRepository repo;
	
	@InjectMocks
	private BrandService service;
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicate() {
		Integer id = null;
		String name = "DELL";
		Brand brand = new Brand(name);
		
		Mockito.when(repo.findByName(name)).thenReturn(brand);
		
		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("Duplicate");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer id = null;
		String name = "Oppo";
		
		Mockito.when(repo.findByName(name)).thenReturn(null);
		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("OK");
		
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicate() {
		Integer id = 3;
		String name = "Apple";
		Brand brand = new Brand(name);
		Mockito.when(repo.findByName(name)).thenReturn(brand);
		
		String result = service.checkUnique(2, "Apple");		
		assertThat(result).isEqualTo("Duplicate");	
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer id = 5;
		String name = "Oppo";
		
		Mockito.when(repo.findByName(name)).thenReturn(null);
		String result = service.checkUnique(6, "Oppa");
		assertThat(result).isEqualTo("OK");
	}
	
}
