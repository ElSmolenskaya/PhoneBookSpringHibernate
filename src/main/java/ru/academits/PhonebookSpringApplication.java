package ru.academits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhonebookSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhonebookSpringApplication.class, args);
	}
}
