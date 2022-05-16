package com.project.sooktoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SooktoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SooktoringApplication.class, args);
	}
}
