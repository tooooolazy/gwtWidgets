package com.tooooolazy.gwt.forms.shared;

public class RequiredValidatorException extends ValidatorException {

	public RequiredValidatorException(String value) {
		super(value);
	}

	public RequiredValidatorException(String field, Object value) {
		super(field, value);
	}

}
