package com.shinhan.assignment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		log.info("Running~~~~~~");
		SpringApplication.run(AssignmentApplication.class, args);
	}
}
