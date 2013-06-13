package com.redisdoc.exception;

public class UnknownCommandException extends RedisdocException {

	private static final long serialVersionUID = 1L;

	public UnknownCommandException() {
		super("Unknown command!");
	}
}
