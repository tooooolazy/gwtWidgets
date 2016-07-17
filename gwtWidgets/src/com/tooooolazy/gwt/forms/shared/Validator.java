package com.tooooolazy.gwt.forms.shared;

/**
 * Base class for all Validators. The field member variable works as a hint that could be passed to the ValidatorException
 * so that we know which bean field failed validation.
 * @author tooooolazy
 *
 */
public abstract class Validator {
	protected String field;
	public Validator(String captionKey) {
		field = captionKey;
	}

	public abstract void validate(Object value);
}
