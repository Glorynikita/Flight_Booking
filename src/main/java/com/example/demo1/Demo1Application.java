package com.example.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSpringDataWebSupport
public class Demo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

}
