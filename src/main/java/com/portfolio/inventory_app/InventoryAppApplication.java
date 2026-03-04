package com.portfolio.inventory_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryAppApplication {

	public static void main(String[] args) {

//		SpringApplication.run(InventoryAppApplication.class, args);
		SpringApplication app = new SpringApplication(InventoryAppApplication.class);
		app.setAllowBeanDefinitionOverriding(true);
		app.run(args);
	}

}
