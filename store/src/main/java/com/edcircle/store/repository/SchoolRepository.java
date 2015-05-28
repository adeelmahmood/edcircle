package com.edcircle.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.School;

public interface SchoolRepository extends JpaRepository<School, Long> {

	Optional<School> findByName(String name);
}
