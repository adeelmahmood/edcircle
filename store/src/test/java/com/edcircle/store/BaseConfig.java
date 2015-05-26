package com.edcircle.store;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootApplication
public class BaseConfig {

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}