package com.Votechainbackend.BackendofADEIVotechain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendofAdeiVotechainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendofAdeiVotechainApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(String[] args) {
		return runner -> {
			System.out.println("Hello World");
		};
	}





}
