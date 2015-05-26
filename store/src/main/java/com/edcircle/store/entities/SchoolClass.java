package com.edcircle.store.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_classes")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_CLASS_ID")
public class SchoolClass extends GenericEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "school_id", referencedColumnName = "id")
	private School school;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	private User teacher;

	private String grade;
	private String section;
	private String subject;

	@Override
	public String toString() {
		return "SchoolClass [grade=" + grade + ", section=" + section + ", subject=" + subject + "]";
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

}
