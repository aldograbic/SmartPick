package com.project.SmartPick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartPickApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartPickApplication.class, args);
	}
}