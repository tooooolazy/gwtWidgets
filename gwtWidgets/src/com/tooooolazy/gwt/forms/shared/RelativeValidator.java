/**
 * 
 */
package com.tooooolazy.gwt.forms.shared;

/**
 * Introduces the notion of Validators that validate a field value in relation to another field value of the same bean.
 * For that reason another validate method with 2 parameters is declared.
 * @author tooooolazy
 *
 */
public abstract class RelativeValidator extends Validator {
	public RelativeValidator(String captionKey) {
		super(captionKey);
	}
	public final void validate(Object value) {
	}
	public abstract void validate(Object value, Object value2);

}
