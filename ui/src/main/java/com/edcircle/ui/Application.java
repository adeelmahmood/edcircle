package com.edcircle.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.edcircle.store.entities.User;
import com.edcircle.store.entities.UserRole;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.services.UserService;

@Configuration
@ComponentScan({ "com.edcircle.store", "com.edcircle.security", "com.edcircle.ui" })
@EnableAutoConfiguration(exclude = { GsonAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class,
		DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class, JpaBaseConfiguration.class, JmxAutoConfiguration.class,
		RepositoryRestMvcAutoConfiguration.class })
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	UserService userService;

	@PostConstruct
	public void init() throws DataUpdateException {
		User admin = new User();
		admin.setUsername("admin");
		admin.setFirstName("Admin");
		admin.setLastName("User");
		admin.setEmail("admin@test.com");
		admin.setPassword("pass");
		admin.addRole(new UserRole("ADMIN"));

		// save user
		userService.save(admin);

		User user = new User();
		user.setUsername("user");
		user.setFirstName("User");
		user.setLastName("Last");
		user.setEmail("user@test.com");
		user.setPassword("pass");
		user.addRole(new UserRole("USER"));

		// save user
		userService.save(user);
	}
}
