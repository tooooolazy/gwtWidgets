package com.tooooolazy.gwt.forms.shared;

public class RequiredValidator extends Validator {

	public RequiredValidator() {
		super(null);
	}

	public RequiredValidator(String captionKey) {
		super(captionKey);
	}

	@Override
	public void validate(Object value) {
		if (value == null || value.toString().trim().length() == 0)
			throw new RequiredValidatorException(field, value);
	}
}
