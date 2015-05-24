package com.edcircle.store.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_students")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_STUD_ID")
public class Student extends GenericEntity {

	private String name;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "class_id")
	private SchoolClass studentClass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SchoolClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(SchoolClass studentClass) {
		this.studentClass = studentClass;
	}
}
