package com.edcircle.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
