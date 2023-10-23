package com.Votechainbackend.BackendofADEIVotechain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import java.util.Arrays;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		BackendofAdeiVotechainApplication.class,
		Jsr310JpaConverters.class
})
public class BackendofAdeiVotechainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendofAdeiVotechainApplication.class, args);

	}


	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}




}
