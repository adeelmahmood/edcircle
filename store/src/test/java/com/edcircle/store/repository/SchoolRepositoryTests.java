package com.edcircle.store.repository;

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
import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.User;
import com.edcircle.store.entities.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BaseConfig.class)
@IntegrationTest({ "env=local", "db.provider=mysql" })
public class SchoolRepositoryTests {

	@Autowired
	private SchoolRepository schoolRepo;
	@Autowired
	private SchoolClassRepository schoolClassRepo;

	@Test
	public void testCreateSchool() {
		School school = new School();
		school.setName("test school");
		school.setType(SchoolType.HIGH_SCHOOL);

		// admin user
		User admin = new User();
		UserRole adminRole = new UserRole();
		adminRole.setRole("SCHOOL_ADMIN");
		admin.addRole(adminRole);
		admin.setUsername("admin");
		admin.setPassword("pass");
		admin.setEmail("admin@user.com");
		school.addAdmin(admin);

		// external resoure
		ExternalResource resource = new ExternalResource();
		resource.setUrl("github.com");
		school.addExternalResource(resource);

		//teacher user
		User teacher = new User();
		UserRole teacherRole = new UserRole();
		teacherRole.setRole("SCHOOL_TEACHER");
		teacher.addRole(teacherRole);
		teacher.setUsername("teacher");
		teacher.setPassword("pass");
		teacher.setEmail("teacher@user.com");
		
		// create a class
		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setSchool(school);
		schoolClass.setTeacher(teacher);
		schoolClass.setGrade("8th");
		schoolClass.setSection("a");
		schoolClass.setSubject("mathematics");
		
		//save school
		schoolRepo.save(school);
		schoolClassRepo.save(schoolClass);
	}
}
