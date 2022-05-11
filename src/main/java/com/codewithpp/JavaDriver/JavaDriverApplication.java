package com.codewithpp.JavaDriver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaDriverApplication {

	@Value("${spring.datasource.url}")
	static
	String db;
	public static void main(String[] args) {
		SpringApplication.run(JavaDriverApplication.class, args);
	}

}
