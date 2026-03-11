package org.mukhtarovich.uz.Auth.Service;

import org.mukhtarovich.uz.Auth.Service.entity.Role;
import org.mukhtarovich.uz.Auth.Service.entity.RoleName;
import org.mukhtarovich.uz.Auth.Service.entity.Users;
import org.mukhtarovich.uz.Auth.Service.repository.RoleRepository;
import org.mukhtarovich.uz.Auth.Service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.mukhtarovich.uz.Auth.Service.entity.RoleName.*;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
		Users users = new Users();
		for (RoleName rn : values()) {
			roleRepository.findByRoleName(rn)
					.orElseGet(() -> roleRepository.save(new Role(rn)));
		}
		return (args) -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			users.setName("Dilbek");
			users.setSurname("Mukhtarov");
			users.setPatronymic("Mukhtarovich");
			users.setPhoneNumber("+998770700279");
			users.setPassword(encoder.encode("admin"));
			users.setIsActive(true);
			Role adminRole = roleRepository.findByRoleName(ADMIN).get();
			users.setRoles(Set.of(adminRole));
			userRepository.save(users);
		};

	}
}
