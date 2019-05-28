package io.github.cepr0.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.CONFLICT)
public class OptimisticLockException extends RuntimeException {
	public <ID extends Serializable> OptimisticLockException(final String entity, final ID id, final Integer version) {
		super(entity + " with id " + id + " and version " + version + " not found!");
	}
}
