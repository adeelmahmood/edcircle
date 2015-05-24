package com.edcircle.store.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_permissions")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_PERM_ID")
public class Permission extends GenericEntity {

	@OneToOne(fetch = FetchType.EAGER)
	private Student student;

	@OneToOne(fetch = FetchType.EAGER)
	private ExternalResource externalResource;

	private String reason;

	@Embedded
	private PermissionStatus status;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public ExternalResource getExternalResource() {
		return externalResource;
	}

	public void setExternalResource(ExternalResource externalResource) {
		this.externalResource = externalResource;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public PermissionStatus getStatus() {
		return status;
	}

	public void setStatus(PermissionStatus status) {
		this.status = status;
	}

	public static enum PermissionStatus {
		REQUESTED, APPROVED, DECLINED;
	}
}
