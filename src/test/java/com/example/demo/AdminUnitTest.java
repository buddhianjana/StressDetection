package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.AdminService;

@SpringBootTest
public class AdminUnitTest {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserRepo userRepo;

	@Test
	public void testAdminSave() {
		User user = new User();
		user.setId(2L);
		user.setFirstName("First Name");
		user.setLastName("Last Name");
		user.setEmail("Test@gmail.com");
		user.setPassword("password");
		user.setRole("ROLE_USER");
		userRepo.save(user);
		assertNotNull(userRepo.findById(2L).get());
	}

	@Test
	public void testReadAllProduct() {
		List<User> list1 = userRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}

	@Test
	public void testUpdateProduct() {
		User user = userRepo.findById(1L).get();
		user.setFirstName("Test");
		userRepo.save(user);
		assertNotEquals("First Name", userRepo.findById(1L).get().getFirstName());
	}

	@Test
	public void testDelete() {
		userRepo.deleteById(1L);
		assertThat(userRepo.existsById(1L)).isFalse();
	}

}
