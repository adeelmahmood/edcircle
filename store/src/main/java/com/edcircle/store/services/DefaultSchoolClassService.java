package com.edcircle.store.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edcircle.store.entities.School;
import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.repository.SchoolClassRepository;
import com.edcircle.store.repository.SchoolRepository;
import com.edcircle.store.repository.UserRepository;

@Service
public class DefaultSchoolClassService implements SchoolClassService {

	private static final Logger log = LoggerFactory.getLogger(DefaultSchoolClassService.class);

	private final SchoolRepository schoolRepo;
	private final SchoolClassRepository schoolClassRepo;
	private final UserRepository userRepo;

	@Autowired
	public DefaultSchoolClassService(SchoolRepository schoolRepo, SchoolClassRepository schoolClassRepo,
			UserRepository userRepo) {
		this.schoolRepo = schoolRepo;
		this.schoolClassRepo = schoolClassRepo;
		this.userRepo = userRepo;
	}

	@Override
	public SchoolClass save(SchoolClass schoolClass) throws DataUpdateException {
		School school = schoolClass.getSchool();
		// make sure school is already saved
		if (school.getId() == null || !schoolRepo.exists(school.getId())) {
			throw new DataUpdateException("to add a class, school must already be saved");
		}

		// set pointers
		User teacher = schoolClass.getTeacher();
		teacher.addSchool(school);

		// save teacher
		userRepo.save(teacher);
		// save class
		return schoolClassRepo.save(schoolClass);
	}

	@Override
	public List<SchoolClass> findBySchool(long id) {
		Optional<School> school = schoolRepo.findById(id);
		return school.isPresent() ? schoolClassRepo.findBySchool(school.get()) : new ArrayList<SchoolClass>();
	}

	@Override
	public void delete(SchoolClass schoolClass) throws DataUpdateException {
		try {
			schoolClassRepo.delete(schoolClass);
		} catch (Exception e) {
			log.error("error in deleting class " + schoolClass, e);
			throw new DataUpdateException("error in deleting class " + schoolClass, e);
		}
	}

	@Override
	public Optional<SchoolClass> findById(long id) {
		return schoolClassRepo.findById(id);
	}

}
