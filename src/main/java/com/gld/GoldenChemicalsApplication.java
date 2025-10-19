package com.gld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GoldenChemicalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenChemicalsApplication.class, args);    
	}

}
