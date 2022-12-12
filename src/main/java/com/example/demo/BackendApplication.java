package com.example.demo;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

@SpringBootApplication
@CrossOrigin(origins= "*")
public class BackendApplication {
	   @Autowired
	    private UserRepository repository;

	    @PostConstruct
	    public void initUsers() {
	        List<User> users = Stream.of(
	                new User(101, "javatechie", "password", "javatechie@gmail.com"),
	                new User(102, "user1", "pwd1", "user1@gmail.com"),
	                new User(103, "user2", "pwd2", "user2@gmail.com"),
	                new User(104, "user3", "pwd3", "user3@gmail.com")
	        ).collect(Collectors.toList());
	        repository.saveAll(users);
	    }
	    @Bean 
	    public WebMvcConfigurer corsConfigurer() {
	    
	    	return new WebMvcConfigurer() {
	    		@Override
	    		public void addCorsMappings(CorsRegistry registry) {
	    			registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
	    			
	    		}
	    		
			};
	    }

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("me");
	}

}
