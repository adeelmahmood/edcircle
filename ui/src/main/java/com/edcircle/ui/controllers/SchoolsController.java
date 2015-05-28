package com.edcircle.ui.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edcircle.store.entities.School;

@RestController
@RequestMapping("/schools")
public class SchoolsController {

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@RequestBody School school) {
		
	}
}
