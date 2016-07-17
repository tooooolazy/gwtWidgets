package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author tooooolazy
 *
 * @param <U> User DTO
 */
public abstract class UserInfoBar<U> extends ComplexPanel implements ValueChangeHandler<U> {
	protected Element outerDiv; // container for everything
	protected U user;

	protected Label username;

	protected UserInfoBar() {
		super();

		outerDiv = DOM.createDiv();
		setElement(outerDiv);
		setStyleName("tlz-user-infobar");
		addStyleName("tlz-inline");

		username = new Label();
		add(username, outerDiv);
	}
	public void setUser(U user) {
		this.user = user;
		setup();
	}

	@Override
	public void onValueChange(ValueChangeEvent<U> event) {
		UserChangeEvent<U> ev = (UserChangeEvent<U>)event;
		U newUser = event.getValue();

		if (ev.isLogout())
			user = null;
		else
			user = newUser;

		setup();
	}

	protected abstract void setup();
}
