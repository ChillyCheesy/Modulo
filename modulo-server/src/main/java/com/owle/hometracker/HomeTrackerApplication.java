package com.owle.hometracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeTrackerApplication {

	public static void main(String[] args)  {
		final SpringApplication application = new SpringApplication(HomeTrackerApplication.class);
		application.run(args);
	}

}
