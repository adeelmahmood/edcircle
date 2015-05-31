package com.edcircle.ui.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edcircle.store.entities.SchoolClass;
import com.edcircle.store.entities.Student;
import com.edcircle.store.exceptions.DataUpdateException;
import com.edcircle.store.services.SchoolClassService;
import com.edcircle.store.services.StudentService;

@RestController
@RequestMapping("/students")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 50, // 50 MB
maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100)
public class StudentsController {

	private static final Logger log = LoggerFactory.getLogger(StudentsController.class);

	private final SchoolClassService classService;
	private final StudentService service;

	@Autowired
	public StudentsController(SchoolClassService classService, StudentService service) {
		this.classService = classService;
		this.service = service;
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public ResponseEntity<?> importStudents(@RequestParam("file") MultipartFile file, @RequestParam long classId) {
		// get a hold of class
		Optional<SchoolClass> schoolClass = classService.findById(classId);
		if (!schoolClass.isPresent()) {
			return new ResponseEntity<>("no class was found for given id " + classId, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// upload file
		File uploadedFile = uploadFile(file.getOriginalFilename(), file);
		// check file object was created successfully
		if (uploadedFile == null) {
			return new ResponseEntity<>("error in uploading file", HttpStatus.BAD_REQUEST);
		}

		int count = 0;
		try {
			// import students from uploaded file
			List<Student> importedStudents = service.importStudents(uploadedFile, schoolClass.get());
			// keep track of how many students were imported
			count = importedStudents.size();
		} catch (DataUpdateException e) {
			return new ResponseEntity<>("error in importing students = " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// return success reposnse
		return new ResponseEntity<>(count + " students successfully imported", HttpStatus.OK);
	}

	private File uploadFile(String fileName, MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				File uploadFile = new File(FileUtils.getTempDirectory(), fileName);
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile));
				stream.write(bytes);
				stream.close();
				log.debug("successfully uploaded " + file.getName() + " into " + uploadFile.getPath());
				return uploadFile;
			} catch (Exception e) {
				log.error("error in uploading file", e);
			}
		} else {
			log.warn("uploaded file " + file.getName() + " is empty");
		}
		return null;
	}
}
