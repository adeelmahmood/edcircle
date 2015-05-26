package com.edcircle.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edcircle.store.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
