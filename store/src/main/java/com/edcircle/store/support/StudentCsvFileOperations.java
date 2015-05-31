package com.edcircle.store.support;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.edcircle.store.entities.Student;

@Component
public class StudentCsvFileOperations implements StudentFileOperations {

	private ICsvBeanReader reader;

	@Override
	public List<Student> readFile(File studentsFile) throws IOException {
		// initialize reader
		reader = new CsvBeanReader(new FileReader(studentsFile), CsvPreference.STANDARD_PREFERENCE);

		// the header elements are used to map the values to the bean (names
		// must match)
		final String[] header = reader.getHeader(true);
		final CellProcessor[] processors = getProcessors();

		// create students list
		List<Student> students = new ArrayList<Student>();
		Student student;
		while ((student = reader.read(Student.class, header, processors)) != null) {
			students.add(student);
		}
		return students;
	}

	private CellProcessor[] getProcessors() {
		// @formatter:off
		return new CellProcessor[] { 
				new NotNull(), // name
				new NotNull(), // parent name
				new NotNull() // parent email
		};
		// @formatter:on
	}

}
