package com.theoflu.Project.CryptoLive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProjectCryptoLiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectCryptoLiveApplication.class, args);
	}

}
