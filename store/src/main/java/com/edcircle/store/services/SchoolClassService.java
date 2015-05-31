package com.edcircle.store.services;

import java.util.List;
import java.util.Optional;

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.exceptions.DataUpdateException;

public interface SchoolClassService {

	SchoolClass save(SchoolClass schoolClass) throws DataUpdateException;

	List<SchoolClass> findBySchool(long id);

	void delete(SchoolClass schoolClass) throws DataUpdateException;
	
	Optional<SchoolClass> findById(long id);
}
