package com.shinhan.assignment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class AssignmentApplication {

	public static void main(String[] args) {
		log.info("Running~~~~~~");
		SpringApplication.run(AssignmentApplication.class, args);
	}
}
