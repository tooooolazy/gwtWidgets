/**
 * 
 */
package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * @author tooooolazy
 *
 */
public class UserChangeEvent<U> extends ValueChangeEvent<U> {
	private boolean logout;

	public static <U> UserChangeEvent<U> createUserLoginEvent(U userDto) {
		return new UserChangeEvent<U>(userDto);
	}
	public static <U> UserChangeEvent<U> createUserLogoutEvent(U userDto) {
		return new UserChangeEvent<U>(userDto, true);
	}
	protected UserChangeEvent(U value) {
		// we might want to use a copy of the DTO 
		super(value);
		logout = false;
	}
	protected UserChangeEvent(U value, boolean logout) {
		// we might want to use a copy of the DTO 
		super(value);
		this.logout = logout;
	}
	public boolean isLogout() {
		return logout;
	}
}
