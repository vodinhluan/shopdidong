package com.shopdidong.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopdidong.common.entity.Role;
import com.shopdidong.common.entity.User;

@DataJpaTest
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
}
