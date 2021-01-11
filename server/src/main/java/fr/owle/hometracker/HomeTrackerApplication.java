package fr.owle.hometracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.loader.LaunchedURLClassLoader;

@SpringBootApplication
public class HomeTrackerApplication {

	private final static String START_QUOTE = "Ooh mooey mooey I love you!";

	public static void main(String[] args)  {
		System.out.println(START_QUOTE);
		SpringApplication.run(HomeTrackerApplication.class, args);
	}

}
