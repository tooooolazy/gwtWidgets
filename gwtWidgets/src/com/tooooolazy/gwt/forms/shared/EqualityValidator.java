/**
 * 
 */
package com.tooooolazy.gwt.forms.shared;


/**
 * @author tooooolazy
 *
 */
public class EqualityValidator extends RelativeValidator {
	public EqualityValidator(String captionKey) {
		super(captionKey);
	}

	@Override
	public void validate(Object value, Object value2) {
		if ( (value != null && !value.equals(value2)) ||
				(value2 != null && !value2.equals(value)) )
			throw new RelativeValidatorException(field, "notEqual");
	}

}
