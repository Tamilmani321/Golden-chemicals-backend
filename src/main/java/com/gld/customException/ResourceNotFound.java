package com.gld.customException;

public class ResourceNotFound extends RuntimeException {

	public ResourceNotFound(String msg) {
		super(msg);
	}
}
