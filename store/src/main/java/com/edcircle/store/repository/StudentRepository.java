package com.edcircle.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByStudentClass(SchoolClass schoolClass);
}
