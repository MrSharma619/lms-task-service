package com.example.lmstaskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LmsTaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsTaskServiceApplication.class, args);
	}

}
