package com.edcircle.store.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.repository.SchoolClassRepository;
import com.edcircle.store.repository.StudentRepository;
import com.edcircle.store.support.StudentFileOperations;

@Service
public class DefaultStudentService implements StudentService {

	private static final Logger log = LoggerFactory.getLogger(DefaultStudentService.class);

	private final StudentRepository studentRepo;
	private final SchoolClassRepository schoolClassRepo;
	private final StudentFileOperations studentFileOperations;

	@Autowired
	public DefaultStudentService(StudentRepository studentRepo, SchoolClassRepository schoolClassRepo,
			StudentFileOperations studentFileOperations) {
		this.studentRepo = studentRepo;
		this.schoolClassRepo = schoolClassRepo;
		this.studentFileOperations = studentFileOperations;
	}

	@Override
	public List<Student> findByClass(long id) {
		Optional<SchoolClass> schoolClass = schoolClassRepo.findById(id);
		return schoolClass.isPresent() ? studentRepo.findByStudentClass(schoolClass.get()) : new ArrayList<Student>();
	}

	@Override
	public List<Student> importStudents(File studentsFile, SchoolClass schoolClass) throws DataUpdateException {
		log.debug("importing students from " + studentsFile.getPath() + " for class " + schoolClass);

		try {
			// read students from file into a list
			List<Student> students = studentFileOperations.readFile(studentsFile);

			// assign students to class
			students.forEach(student -> student.setStudentClass(schoolClass));

			// save students
			return studentRepo.save(students);
		} catch (IOException e) {
			log.error("error in importing students file " + studentsFile.getPath(), e);
			throw new DataUpdateException("error in importing students file " + studentsFile.getPath(), e);
		}
	}
}
