package com.edcircle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebMvcConfigurerAdapter {

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private UserDetailsService userDetailsService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}
	}

	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.anyRequest().fullyAuthenticated()
					.and()
				.httpBasic()
					.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.and()
				.exceptionHandling()
				.accessDeniedPage("/access?error");
			// @formatter:on
		}

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/access").setViewName("access");
	}
}