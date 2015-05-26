package com.edcircle.store;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

@Configuration
@EnableJpaRepositories
@EnableJpaAuditing
public class StoreConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.edcircle.store.entities" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		System.out.println("=== Datasource Configuration ===");
		DataSource dataSource = new DataSource();

		String environment = env.getRequiredProperty("env");
		System.out.println("environment => " + environment);
		String dbProvider = env.getRequiredProperty("db.provider");
		System.out.println("dbProvider => " + dbProvider);
		String driver = env.getProperty("db." + dbProvider + ".driver", "");
		System.out.println("driver => " + driver);
		String url = env.getProperty("db." + dbProvider + "." + environment + ".url", "");
		String user = env.getProperty("db." + dbProvider + "." + environment + ".user", "");
		String pass = env.getProperty("db." + dbProvider + "." + environment + ".password", "");

		System.out.println("url => " + url);
		System.out.println("user => " + user);
		System.out.println("pass => " + (StringUtils.isEmpty(pass) ? "[EMPTY]" : "[PROTECTED]"));
		System.out.println("=== Datasource Configuration END ===");

		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(pass);

		// pool settings
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setTestOnBorrow(true);
		dataSource.setValidationInterval(60000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(60);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Value("${hibernate.ddl.mode:update}")
	private String mode;

	@Value("${db.${db.provider}.dialect}")
	private String dialect;

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", mode);
		properties.setProperty("hibernate.dialect", dialect);
//		 properties.setProperty("hibernate.show_sql", "true");
//		 properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}
}