package com.edcircle.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edcircle.store.entities.Student;
import com.edcircle.store.services.StudentService;

@RestController
@RequestMapping("/query/students")
public class StudentsQueryController {

	private final StudentService service;

	@Autowired
	public StudentsQueryController(StudentService service) {
		this.service = service;
	}

	@RequestMapping("/class/{id}")
	public List<Student> students(@PathVariable long id) {
		return service.findByClass(id);
	}

}
