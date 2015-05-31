package com.edcircle.store.services;

import java.util.List;
import java.util.Optional;

import com.edcircle.store.entities.School;
import com.edcircle.store.exceptions.DataUpdateException;

public interface SchoolService {

	School save(School school) throws DataUpdateException;

	Optional<School> findByName(String name);

	Optional<School> findById(long id);

	List<School> list();

	void delete(School school) throws DataUpdateException;
}
