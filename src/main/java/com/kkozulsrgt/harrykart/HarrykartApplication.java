package com.kkozulsrgt.harrykart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.kkozulsrgt.harrykart.*")
@SpringBootApplication
public class HarrykartApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarrykartApplication.class, args);
	}

}
