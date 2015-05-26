package com.edcircle.store.exceptions;

public class DataUpdateException extends Exception {

	private static final long serialVersionUID = -7966857750893682680L;

	public DataUpdateException(String msg) {
		super(msg);
	}

	public DataUpdateException(String msg, Throwable t) {
		super(msg, t);
	}
}
