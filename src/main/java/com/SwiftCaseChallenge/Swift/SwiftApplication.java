package com.SwiftCaseChallenge.Swift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.SwiftCaseChallenge.Swift")
public class SwiftApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftApplication.class, args);
	}

}
