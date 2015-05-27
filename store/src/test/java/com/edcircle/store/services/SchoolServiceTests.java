package com.edcircle.store.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.edcircle.store.BaseConfig;
import com.edcircle.store.entities.ExternalResource;
import com.edcircle.store.entities.School;
import com.edcircle.store.entities.School.SchoolType;
import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;
import com.edcircle.store.entities.User;
import com.edcircle.store.entities.UserRole;
import com.edcircle.store.exceptions.DataUpdateException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BaseConfig.class)
@IntegrationTest({ "env=local", "db.provider=mysql" })
public class SchoolServiceTests {

	@Autowired
	private SchoolService service;
	@Autowired
	private UserService userService;

	@Test
	@Rollback(false)
	public void testCreateSchool() throws DataUpdateException {
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

		// external resoure
		ExternalResource resource = new ExternalResource();
		resource.setUrl("github.com");
		school.addExternalResource(resource);

		// save school
		School savedSchool = service.save(school);

		// create teacher user
		User teacher = new User();
		UserRole teacherRole = new UserRole("SCHOOL_TEACHER");
		teacher.addRole(teacherRole);
		teacher.setUsername("teacher");
		teacher.setPassword("pass");
		teacher.setEmail("teacher@user.com");
		User savedTeacher = userService.save(teacher);

		// create a class
		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setGrade("8th");
		schoolClass.setSection("a");
		schoolClass.setSubject("mathematics");

		// add class
		service.addClass(savedSchool, schoolClass, savedTeacher);
	}

	@Test
	public void testAddStudent() throws DataUpdateException {
		School school = new School();
		school.setName("test school 2");
		school.setType(SchoolType.HIGH_SCHOOL);

		// save school
		School savedSchool = service.save(school);

		// create a class
		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setGrade("8th");
		schoolClass.setSection("b");
		schoolClass.setSubject("mathematics");

		// create teacher user
		User teacher = new User();
		UserRole teacherRole = new UserRole("SCHOOL_TEACHER");
		teacher.addRole(teacherRole);
		teacher.setUsername("teacher2");
		teacher.setPassword("pass");
		teacher.setEmail("teacher2@user.com");
		User savedTeacher = userService.save(teacher);

		SchoolClass savedClass = service.addClass(savedSchool, schoolClass, savedTeacher);

		// create student user
		Student student = new Student();
		student.setName("test student");
		student.setStudentClass(schoolClass);

		// add student
		service.addStudent(savedClass, student);
	}
}
