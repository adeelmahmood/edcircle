package com.edcircle.store.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_schools")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_SCHOOLS_ID")
public class School extends GenericEntity {

	private String name;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<User> admins = new HashSet<User>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "school_id")
	private Set<ExternalResource> externalResources = new HashSet<ExternalResource>();

	@Enumerated
	private SchoolType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAdmin(User admin) {
		admins.add(admin);
		admin.addSchool(this);
	}

	public void resetAdmins() {
		admins.clear();
	}

	public Set<User> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<User> admins) {
		this.admins = admins;
	}

	public void addExternalResource(ExternalResource resource) {
		externalResources.add(resource);
	}

	public void resetExternalResources() {
		externalResources.clear();
	}

	public Set<ExternalResource> getExternalResources() {
		return externalResources;
	}

	public void setExternalResources(Set<ExternalResource> externalResources) {
		this.externalResources = externalResources;
	}

	public SchoolType getType() {
		return type;
	}

	public void setType(SchoolType type) {
		this.type = type;
	}

	public static enum SchoolType {
		MIDDLE_SCHOOL, HIGH_SCHOOL
	}
}
