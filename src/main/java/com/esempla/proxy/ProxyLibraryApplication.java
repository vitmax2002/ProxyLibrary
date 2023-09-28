package com.esempla.proxy;

import com.esempla.proxy.config.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@EnableConfigurationProperties({MinioProperties.class})
@SpringBootApplication
public class ProxyLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyLibraryApplication.class, args);
	}

}
