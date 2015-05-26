package com.edcircle.store.services;

import com.edcircle.store.entities.User;
import com.edcircle.store.exceptions.DataUpdateException;

public interface UserService {

	User save(User user) throws DataUpdateException;
}
