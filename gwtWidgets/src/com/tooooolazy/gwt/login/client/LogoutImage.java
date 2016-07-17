package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.tooooolazy.gwt.widgets.client.ClickableImage;
import com.tooooolazy.gwt.widgets.client.resources.Resources;

/**
 * @author tooooolazy
 *
 * @param <U> User DTO
 */
public abstract class LogoutImage<U> extends ClickableImage implements HasValueChangeHandlers<U> {
	protected U user;

	public LogoutImage() {
		super(Resources.login.brokenKey(), null);
		altTextKey = "InitiateLoginButton.logoutTitle";
		setupCommand(new Command() {
			
			@Override
			public void execute() {
				logout();
			}
		});
	}
	protected abstract void logout();
	public void setUser(U user) {
		this.user = user;
	}
	/**
	 * @param userDto
	 */
	protected void fireUserChangedEvent(U userDto) {
		fireEvent( UserChangeEvent.createUserLoginEvent(userDto) );
	}

	@Override
	public abstract String getMessage(String key);
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<U> handler) {
		return addHandler(handler, UserChangeEvent.getType() );
	}
}
