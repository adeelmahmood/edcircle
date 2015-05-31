package com.edcircle.store.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edcircle.store.entities.School;
import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.repository.SchoolRepository;
import com.edcircle.store.repository.UserRepository;

@Service
public class DefaultSchoolService implements SchoolService {

	private static final Logger log = LoggerFactory.getLogger(DefaultSchoolService.class);

	private final SchoolRepository schoolRepo;
	private final UserRepository userRepo;

	@Autowired
	public DefaultSchoolService(SchoolRepository schoolRepo, UserRepository userRepo) {
		this.schoolRepo = schoolRepo;
		this.userRepo = userRepo;
	}

	@Override
	public School save(School school) throws DataUpdateException {
		boolean isNew = school.getId() == null;
		try {
			if (isNew) {
				return schoolRepo.save(school);
			}

			// extract admins
			Set<User> admins = new HashSet<User>();
			admins = school.getAdmins();
			school.setAdmins(new HashSet<User>());
			// save school without admins
			School saved = schoolRepo.save(school);
			// add admins individually
			admins.forEach((admin) -> {
				saved.addAdmin(admin);
				if (admin.getId() == null) {
					userRepo.save(admin);
				}
			});

			// save school again with admins
			return schoolRepo.save(saved);

		} catch (Exception e) {
			log.error("error in saving school " + school.getName(), e);
			throw new DataUpdateException("error in saving school " + school.getName(), e);
		}
	}

	@Override
	public void delete(School school) throws DataUpdateException {
		try {
			schoolRepo.delete(school);
		} catch (Exception e) {
			log.error("error in deleting school " + school.getName(), e);
			throw new DataUpdateException("error in deleting school " + school.getName(), e);
		}
	}

	@Override
	public Optional<School> findByName(String name) {
		return schoolRepo.findByName(name);
	}

	@Override
	public Optional<School> findById(long id) {
		return schoolRepo.findById(id);
	}

	@Override
	public List<School> list() {
		return schoolRepo.findAll();
	}

}