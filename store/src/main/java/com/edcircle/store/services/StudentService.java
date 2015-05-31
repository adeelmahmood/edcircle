package com.edcircle.store.services;

import java.io.File;
import java.util.List;

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;
import com.edcircle.store.exceptions.DataUpdateException;

public interface StudentService {

	List<Student> findByClass(long id);

	List<Student> importStudents(File studentsFile, SchoolClass schoolClass) throws DataUpdateException;
}
