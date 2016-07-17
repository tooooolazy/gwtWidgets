package com.tooooolazy.gwt.forms.shared;

/**
 * @author tooooolazy
 * 
 */
public class EmailValidator extends Validator {
	public EmailValidator(String captionKey) {
		super(captionKey);
	}
	@Override
	public void validate(Object email){
		String value = email.toString();
		if (!value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new ValidatorException(field, email);
		}
	}

}
