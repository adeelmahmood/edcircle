package com.edcircle.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edcircle.store.entities.School;
import com.edcircle.store.services.SchoolService;

@RestController
@RequestMapping("/query/schools")
public class SchoolsQueryController {

	private final SchoolService service;

	@Autowired
	public SchoolsQueryController(SchoolService service) {
		this.service = service;
	}

	@RequestMapping
	public List<School> schools() {
		return service.list();
	}

	@RequestMapping("/{id}")
	public School school(@PathVariable long id) {
		return service.findById(id).orElse(new School());
	}

}
