package com.edcircle.store.services;

import java.util.Optional;

import com.edcircle.store.entities.School;
import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;

public interface SchoolService {

	School save(School school) throws DataUpdateException;

	SchoolClass addClass(School school, SchoolClass schoolClass, User teacher) throws DataUpdateException;

	Optional<School> findByName(String name);

	Optional<School> findById(long id);
}
