package com.edcircle.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edcircle.store.entities.School;
import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;
import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.repository.SchoolClassRepository;
import com.edcircle.store.repository.SchoolRepository;
import com.edcircle.store.repository.StudentRepository;

@Service
public class DefaultSchoolService implements SchoolService {

	private static final Logger log = LoggerFactory.getLogger(DefaultSchoolService.class);

	private final SchoolRepository schoolRepo;
	private final SchoolClassRepository schoolClassRepo;
	private final StudentRepository studentRepo;

	@Autowired
	public DefaultSchoolService(SchoolRepository schoolRepo, SchoolClassRepository schoolClassRepo,
			StudentRepository studentRepo) {
		this.schoolRepo = schoolRepo;
		this.schoolClassRepo = schoolClassRepo;
		this.studentRepo = studentRepo;
	}

	@Override
	public School save(School school) throws DataUpdateException {
		log.debug("saving school " + school.getName());
		try {
			// save school
			return schoolRepo.save(school);
		} catch (Exception e) {
			log.error("error in saving school " + school.getName(), e);
			throw new DataUpdateException("error in saving school " + school.getName(), e);
		}
	}

	@Override
	public SchoolClass addClass(School school, SchoolClass schoolClass, User teacher) throws DataUpdateException {
		log.debug("adding " + schoolClass + " to school " + school.getName() + " with teacher " + teacher);
		try {
			// set properties
			schoolClass.setSchool(school);
			schoolClass.setTeacher(teacher);
			teacher.addSchool(school);

			// save class
			return schoolClassRepo.save(schoolClass);
		} catch (Exception e) {
			log.error("error in adding class " + schoolClass, e);
			throw new DataUpdateException("error in adding class " + schoolClass, e);
		}
	}

	@Override
	public Student addStudent(SchoolClass schoolClass, Student student) throws DataUpdateException {
		log.debug("adding student " + student.getName() + " to class " + schoolClass);
		try {
			// set properties
			student.setStudentClass(schoolClass);

			// save student
			return studentRepo.save(student);
		} catch (Exception e) {
			log.error("error in adding student " + student.getName(), e);
			throw new DataUpdateException("error in adding student " + student.getName(), e);
		}
	}
}