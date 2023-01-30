package com.chillycheesy.moduloserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModuloApplication {

	public static void main(String[] args) {
		final SpringApplication springApplication = new SpringApplication(ModuloApplication.class);
		springApplication.run(args);
	}

}
