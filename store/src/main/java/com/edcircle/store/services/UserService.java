package com.edcircle.store.services;

import java.util.Optional;

import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;

public interface UserService {

	User save(User user) throws DataUpdateException;

	Optional<User> findByUsername(String username);
}
