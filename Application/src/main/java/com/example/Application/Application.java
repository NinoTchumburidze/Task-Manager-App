package com.example.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.Application", "com.example.Application.service"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		printMessage();
	}

	private static void printMessage(){

	}

}
