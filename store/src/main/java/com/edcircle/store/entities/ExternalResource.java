package com.edcircle.store.entities;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "edcircle_external_resources")
@SequenceGenerator(name = "idSeqGen", sequenceName = "ED_CIR_EXT_RES_ID")
public class ExternalResource extends GenericEntity {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
