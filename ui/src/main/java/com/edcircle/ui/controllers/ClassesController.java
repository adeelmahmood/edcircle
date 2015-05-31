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

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.services.SchoolClassService;

@RestController
@RequestMapping("/classes")
public class ClassesController {

	private final SchoolClassService service;

	@Autowired
	public ClassesController(SchoolClassService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody SchoolClass schoolClass) {
		try {
			SchoolClass saved = service.save(schoolClass);
			return new ResponseEntity<>(saved, HttpStatus.ACCEPTED);
		} catch (DataUpdateException e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			Optional<SchoolClass> classResult = service.findById(id);
			if (classResult.isPresent()) {
				service.delete(classResult.get());
			}
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (DataUpdateException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
