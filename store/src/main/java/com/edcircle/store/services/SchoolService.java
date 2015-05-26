package com.edcircle.store.services;

import com.edcircle.store.entities.School;
import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;
import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;

public interface SchoolService {

	School save(School school) throws DataUpdateException;

	SchoolClass addClass(School school, SchoolClass schoolClass, User teacher) throws DataUpdateException;

	Student addStudent(SchoolClass schoolClass, Student student) throws DataUpdateException;
}
