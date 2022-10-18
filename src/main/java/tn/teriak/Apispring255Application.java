package tn.teriak;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import tn.teriak.model.ERole;
import tn.teriak.model.Role;

import tn.teriak.repository.RoleRepository;



@SpringBootApplication
public class Apispring255Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Apispring255Application.class, args);
	}
	 @Bean
	    public CommandLineRunner demoData(RoleRepository repo) {
	        return args -> {
	            repo.save(new Role(1, ERole.ROLE_ADMIN ));
	            repo.save(new Role(2, ERole.ROLE_USER ));
	            repo.save(new Role(3, ERole.ROLE_MODERATOR ));
	        };
	    }
	 
	
}
