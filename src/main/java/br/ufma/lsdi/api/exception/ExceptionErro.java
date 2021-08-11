package br.ufma.lsdi.api.exception;

import java.io.Serializable;

public class ExceptionErro implements Serializable {
	private String message;
	
	public ExceptionErro(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}