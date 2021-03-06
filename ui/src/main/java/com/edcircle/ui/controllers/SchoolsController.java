package com.edcircle.ui.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edcircle.store.entities.School;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.services.SchoolService;

@RestController
@RequestMapping("/schools")
public class SchoolsController {

	private final SchoolService service;

	@Autowired
	public SchoolsController(SchoolService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody School school) {
		try {
			School saved = service.save(school);
			return new ResponseEntity<>(saved, HttpStatus.ACCEPTED);
		} catch (DataUpdateException e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			Optional<School> schoolResult = service.findById(id);
			if (schoolResult.isPresent()) {
				service.delete(schoolResult.get());
			}
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (DataUpdateException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
