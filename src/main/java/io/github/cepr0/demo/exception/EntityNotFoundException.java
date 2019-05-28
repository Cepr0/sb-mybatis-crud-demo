package io.github.cepr0.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	public <ID extends Serializable> EntityNotFoundException(final String entity, final ID id) {
		super(entity + " with ID " + id + " not found!");
	}
}
