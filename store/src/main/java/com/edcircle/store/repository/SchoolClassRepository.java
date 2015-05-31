package com.edcircle.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.School;
import com.edcircle.store.entities.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

	List<SchoolClass> findBySchool(School school);

	Optional<SchoolClass> findById(Long id);

}
