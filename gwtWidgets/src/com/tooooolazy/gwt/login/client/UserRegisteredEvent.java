/**
 * 
 */
package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * @author tooooolazy
 *
 */
public class UserRegisteredEvent<R> extends ValueChangeEvent<R> {

	public static <R> UserRegisteredEvent<R> createUserRegisteredEvent(R registrationDataDto) {
		return new UserRegisteredEvent<R>(registrationDataDto);
	}

	protected UserRegisteredEvent(R value) {
		// we might want to use a copy of the DTO 
		super(value);
	}

}
