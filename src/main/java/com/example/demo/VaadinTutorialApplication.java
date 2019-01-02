package com.example.demo;

import com.example.demo.Services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VaadinTutorialApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext=SpringApplication.run(VaadinTutorialApplication.class, args);
		CustomerService.getInstance();

	}
}
