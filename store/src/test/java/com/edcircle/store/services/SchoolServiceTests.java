package com.edcircle.store.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.edcircle.store.BaseConfig;
import com.edcircle.store.entities.ExternalResource;
import com.edcircle.store.entities.School;
import com.edcircle.store.entities.School.SchoolType;
import com.edcircle.store.entities.User;
import com.edcircle.store.entities.UserRole;
import com.edcircle.store.exceptions.DataUpdateException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BaseConfig.class)
@IntegrationTest({ "db.provider=h2" })
public class SchoolServiceTests {

	@Autowired
	private SchoolService service;
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testRegisterNewSchool() throws DataUpdateException, JsonProcessingException {
		School school = new School();
		school.setName("test school");
		school.setType(SchoolType.HIGH_SCHOOL);

		// external resoure
		ExternalResource resource = new ExternalResource();
		resource.setUrl("github.com");
		school.addExternalResource(resource);

		// save school
		School savedSchool = service.save(school);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedSchool));
		assertThat(savedSchool.getId(), notNullValue());
		assertThat(savedSchool.getName(), equalTo(school.getName()));
		assertThat(savedSchool.getExternalResources().size(), equalTo(1));
		assertThat(savedSchool.getAdmins().size(), equalTo(0));
	}

	@Test
	public void testRegisterNewSchoolWithAdmins() throws DataUpdateException, JsonProcessingException {
		School school = new School();
		school.setName("test school");
		school.setType(SchoolType.HIGH_SCHOOL);

		// admin user
		User admin = new User();
		UserRole adminRole = new UserRole("SCHOOL_ADMIN");
		admin.addRole(adminRole);
		admin.setUsername("admin");
		admin.setPassword("pass");
		admin.setEmail("admin@user.com");
		school.addAdmin(admin);

		// save school
		School savedSchool = service.save(school);
		System.out.println("SAVED => " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedSchool));
		assertThat(savedSchool.getId(), notNullValue());
		assertThat(savedSchool.getName(), equalTo(school.getName()));
		assertThat(savedSchool.getAdmins().size(), equalTo(1));

		// retrieve school
		School retrieved = service.findById(savedSchool.getId()).get();
		System.out.println("RETRIEVED => " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(retrieved));
		assertThat(retrieved.getId(), equalTo(savedSchool.getId()));
		assertThat(retrieved.getAdmins().size(), equalTo(1));
	}

	@Test
	public void testAddAdminsToExistingSchool() throws DataUpdateException, JsonProcessingException {
		School school = new School();
		school.setName("test school");
		school.setType(SchoolType.HIGH_SCHOOL);

		// save school
		School saved = service.save(school);
		assertThat(saved.getId(), notNullValue());
		assertThat(saved.getAdmins().size(), equalTo(0));

		// retrieve saved school
		School retrieved = service.findById(saved.getId()).get();
		assertThat(retrieved.getAdmins().size(), equalTo(0));

		// admin user
		User admin = new User();
		UserRole adminRole = new UserRole("SCHOOL_ADMIN");
		admin.addRole(adminRole);
		admin.setUsername("admin");
		admin.setPassword("pass");
		admin.setEmail("admin@user.com");
		retrieved.addAdmin(admin);

		// save school
		School savedAgain = service.save(retrieved);
		// retrieve school
		School retrievedAgain = service.findById(savedAgain.getId()).get();
		System.out
				.println("RETRIEVED => " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(retrievedAgain));
		assertThat(retrievedAgain.getId(), equalTo(savedAgain.getId()));
		assertThat(retrievedAgain.getAdmins().size(), equalTo(1));
	}

	@Test
	public void testAddMoreAdmins() throws DataUpdateException, JsonProcessingException {
		School school = new School();
		school.setName("test school");
		school.setType(SchoolType.HIGH_SCHOOL);

		// admin user
		User admin = new User();
		UserRole adminRole = new UserRole("SCHOOL_ADMIN");
		admin.addRole(adminRole);
		admin.setUsername("admin");
		admin.setPassword("pass");
		admin.setEmail("admin@user.com");
		school.addAdmin(admin);

		// save school
		School savedSchool = service.save(school);
		assertThat(savedSchool.getId(), notNullValue());
		assertThat(savedSchool.getAdmins().size(), equalTo(1));
		System.out.println("SAVED => " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedSchool));

		// retrieve saved school
		School retrieved = service.findById(savedSchool.getId()).get();
		System.out.println("RETRIEVED => " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(retrieved));

		// normal user (second admin)
		User user = new User();
		UserRole userRole = new UserRole("SCHOOL_ADMIN");
		user.addRole(userRole);
		user.setUsername("user");
		user.setPassword("pass");
		user.setEmail("user@user.com");
		retrieved.addAdmin(user);

		// save school ahain
		service.save(retrieved);
		School retrievedAgain = service.findById(savedSchool.getId()).get();
		System.out.println("RETRIEVED AGAIN => "
				+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(retrievedAgain));
		assertThat(retrievedAgain.getAdmins().size(), equalTo(2));
	}

	@Test
	public void testAddAdminsAndUpdateExisting() throws DataUpdateException, JsonProcessingException {
		School school = new School();
		school.setName("test school");
		school.setType(SchoolType.HIGH_SCHOOL);

		// admin user
		User admin = new User();
		UserRole adminRole = new UserRole("SCHOOL_ADMIN");
		admin.addRole(adminRole);
		admin.setUsername("admin");
		admin.setPassword("pass");
		admin.setEmail("admin@user.com");
		school.addAdmin(admin);

		// save school
		service.save(school);
		School retrieved = service.findById(service.save(school).getId()).get();

		// normal user (second admin)
		User user = new User();
		UserRole userRole = new UserRole("SCHOOL_ADMIN");
		user.addRole(userRole);
		user.setUsername("user");
		user.setPassword("pass");
		user.setEmail("user@user.com");
		retrieved.addAdmin(user);

		// modify existing admin
		retrieved.getAdmins().stream().filter((a) -> a.getUsername().equals("admin")).findFirst().get()
				.setUsername("admin-changed");

		// save school ahain
		service.save(retrieved);
		School retrievedAgain = service.findById(retrieved.getId()).get();
		System.out
				.println("RETRIEVED => " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(retrievedAgain));
		assertThat(retrievedAgain.getAdmins().size(), equalTo(2));
		assertThat(retrievedAgain.getAdmins().stream().filter((a) -> a.getUsername().startsWith("admin")).findFirst()
				.get().getUsername(), equalTo("admin-changed"));
	}
}
