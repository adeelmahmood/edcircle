package com.edcircle.store.exceptions;

public class ImportException extends Exception {

	private static final long serialVersionUID = -7966857750893682680L;

	public ImportException(String msg) {
		super(msg);
	}

	public ImportException(String msg, Throwable t) {
		super(msg, t);
	}
}
