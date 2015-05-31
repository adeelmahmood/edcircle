package com.edcircle.store.support;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.edcircle.store.entities.Student;

public interface StudentFileOperations {

	List<Student> readFile(File studentsFile) throws IOException;
}
