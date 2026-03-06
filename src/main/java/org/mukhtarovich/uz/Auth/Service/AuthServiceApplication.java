package org.mukhtarovich.uz.Auth.Service;

import org.mukhtarovich.uz.Auth.Service.entity.Users;
import org.mukhtarovich.uz.Auth.Service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		Users users = new Users();
		return (args) -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			users.setUsername("admin");
			users.setPassword(encoder.encode("admin"));
			users.setIsActive(true);
			userRepository.save(users);
		};
	}
}
