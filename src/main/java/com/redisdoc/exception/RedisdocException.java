package com.redisdoc.exception;

public class RedisdocException extends Exception {

	private static final long serialVersionUID = 1L;

	public RedisdocException() {
	}

	public RedisdocException(String message) {
		super(message);
	}

}
