package com.shopdidong.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopdidong.common.entity", "com.shopdidong.admin.user"})

public class ShopdidongBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopdidongBackEndApplication.class, args);
	}

}
