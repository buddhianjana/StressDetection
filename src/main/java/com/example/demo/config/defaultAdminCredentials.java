package com.example.demo.Config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepo;

import jakarta.annotation.PostConstruct;

@Component
public class defaultAdminCredentials {
	
	private final UserRepo userRepository;
	private final PasswordEncoder passwordEncoder;

	public defaultAdminCredentials(UserRepo userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	public void initDefaultAdmin() {
		User defaultUser = new User();
		defaultUser.setId(1);
		defaultUser.setFirstName("DocFinder");
		defaultUser.setLastName("-");
		defaultUser.setEmail("admin@gmail.com");
		defaultUser.setPassword(passwordEncoder.encode("password"));
		defaultUser.setRole("ROLE_ADMIN");
		userRepository.save(defaultUser);
	}

}
