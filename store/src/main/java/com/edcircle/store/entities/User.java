package com.edcircle.store.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_users")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_USERS_ID")
public class User extends GenericEntity {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Set<UserRole> roles = new HashSet<UserRole>();

	@ManyToOne(cascade = CascadeType.PERSIST, targetEntity = School.class)
	@JoinColumn(name = "school_id", referencedColumnName = "id")
	private Set<School> schools = new HashSet<School>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addRole(UserRole role) {
		roles.add(role);
	}

	public void setRoles() {
		roles.clear();
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public void addSchool(School school) {
		schools.add(school);
	}

	public void resetSchools() {
		schools.clear();
	}

	public Set<School> getSchools() {
		return schools;
	}

	public void setSchools(Set<School> schools) {
		this.schools = schools;
	}

}