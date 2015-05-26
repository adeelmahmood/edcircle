package com.edcircle.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.repository.UserRepository;

@Service
public class DefaultUserService implements UserService {

	private static final Logger log = LoggerFactory.getLogger(DefaultUserService.class);

	private final UserRepository userRepo;

	@Autowired
	public DefaultUserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public User save(User user) throws DataUpdateException {
		try {
			return userRepo.save(user);
		} catch (Exception e) {
			log.error("error in saving user " + user.getUsername(), e);
			throw new DataUpdateException("error in saving user " + user.getUsername(), e);
		}
	}
}