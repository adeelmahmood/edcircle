package com.edcircle.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.User;

public interface UserRepository extends JpaRepository<User, Long > {

}
