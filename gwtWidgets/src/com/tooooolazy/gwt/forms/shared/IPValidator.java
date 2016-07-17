/**
 * 
 */
package com.tooooolazy.gwt.forms.shared;

/**
 * @author tooooolazy
 *
 */
public class IPValidator extends Validator {
	public IPValidator(String captionKey) {
		super(captionKey);
	}

	/* (non-Javadoc)
	 * @see com.tooooolazy.gwt.forms.shared.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object value) {
		if (value == null)
			return;
		String ip = value.toString();
		if (!ip.matches("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b")) {
			throw new ValidatorException(field, value);
		}
	}

}
