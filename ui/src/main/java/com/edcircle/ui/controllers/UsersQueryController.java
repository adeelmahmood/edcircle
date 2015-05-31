package com.edcircle.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edcircle.store.services.UserService;

@RestController
@RequestMapping("/query/users")
public class UsersQueryController {

	private final UserService service;

	@Autowired
	public UsersQueryController(UserService service) {
		this.service = service;
	}

}
