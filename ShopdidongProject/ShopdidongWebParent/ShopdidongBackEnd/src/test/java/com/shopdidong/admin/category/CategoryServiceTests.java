package com.shopdidong.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopdidong.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	@MockBean
	private CategoryRepository repo;
	@InjectMocks
	private CategoryService service;
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Samsung";
		String alias = "abc";
		
		Category category = new Category(id, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(category);
		
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateName");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {
		Integer id = null;
		String name = "anbc";
		String alias = "samsung";
		
		Category category = new Category(id, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(null);
		
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnReturnOK() {
		Integer id = null;
		String name = "anbc";
		String alias = "samsung";
		
		Category category = new Category(id, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(null);
		
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id = 1;
		String name = "Samsung";
		String alias = "abc";
		
		Category category = new Category(2, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(category);
		
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateName");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {
		Integer id = 1;
		String name = "anbc";
		String alias = "samsung";
		
		Category category = new Category(2, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(null);
		
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnReturnOK() {
		Integer id = 1;
		String name = "anbc";
		String alias = "samsung";
		
		Category category = new Category(2, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(null);
		
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("OK");
	}
	
}