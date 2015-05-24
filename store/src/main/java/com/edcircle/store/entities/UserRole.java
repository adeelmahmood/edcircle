package com.edcircle.store.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_user_roles")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_SCHOOLS_ID")
public class UserRole extends GenericEntity {

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}