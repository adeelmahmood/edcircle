package com.edcircle.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.services.SchoolClassService;

@RestController
@RequestMapping("/query/classes")
public class ClassesQueryController {

	private final SchoolClassService service;

	@Autowired
	public ClassesQueryController(SchoolClassService service) {
		this.service = service;
	}

	@RequestMapping("/school/{id}")
	public List<SchoolClass> classes(@PathVariable long id) {
		return service.findBySchool(id);
	}

}
