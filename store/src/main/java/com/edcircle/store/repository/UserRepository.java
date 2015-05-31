package com.edcircle.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	List<User> findByUsernameLike(String username);

}
