package com.shopdidong.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.data.domain.Pageable;

import com.shopdidong.common.entity.Role;
import com.shopdidong.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole()
	{
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userLuan = new User("luandinhvo2002@gmail.com","vodinhluan2002", "Vo Dinh", "Luan");
		userLuan.addRole(roleAdmin);
		
		User savedUser = repo.save(userLuan);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles()
	{
		User userHan = new User("minhnhat@gmail.com", "minhnhat2002","Nguyen Phan Minh", "Nhat");
		
		Role roleEditor = new Role(4);
		Role roleAssistant = new Role(6);
		
		userHan.addRole(roleEditor);
		userHan.addRole(roleAssistant);
		
		User savedUser = repo.save(userHan);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers()
	{
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));		
	}
	
	@Test
	public void testGetUserById() {
		User userBin = repo.findById(2).get();
		System.out.println(userBin);
		assertThat(userBin).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		User userBin = repo.findById(1).get();
		userBin.setEnabled(true);
		userBin.setEmail("luandeptrai@gmail.com");
		repo.save(userBin);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userHan = repo.findById(2).get();
		Role roleEditor = new Role(4);
		Role roleShipper = new Role(5);
		userHan.getRoles().remove(roleEditor);
		userHan.addRole(roleShipper);
		repo.save(userHan);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "luandeptrai@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testUsableUser() {
		Integer id = 102;
		repo.updateEnabledStatus(id, true);
	}

	@Test
	// sau mỗi lần code hàm trong Repository thì phải qua đây để test thử
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "Luan";

		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));	

		assertThat(listUsers.size()).isGreaterThan(0);
	}

}
