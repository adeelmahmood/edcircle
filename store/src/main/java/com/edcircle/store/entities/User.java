package com.edcircle.store.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "edcircle_users")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_USER_ID")
public class User extends GenericEntity {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	private Set<UserRole> roles = new HashSet<UserRole>();

	@ManyToMany(mappedBy = "admins", fetch = FetchType.EAGER)
	@JsonIgnore
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
		role.setUser(this);
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