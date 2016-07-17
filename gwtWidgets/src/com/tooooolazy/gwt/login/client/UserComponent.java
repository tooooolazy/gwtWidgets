package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Wraps login. logout and user info in one widget. Can be used instead of having 3 separate widgets.
 * <p>
 * It is also a value change handler for UserChanged events so that it can handle its components accordingly.
 * </p>
 * @author tooooolazy
 *
 * @param <c> Credentials DTO
 * @param <U> User DTO
 * @param <A> ValueChangeHandler class (could be the GwtApplication class) - this handler will be responsible for changing content!
 */
public abstract class UserComponent<C, U, A extends ValueChangeHandler<U>> extends ComplexPanel implements ValueChangeHandler<U> {
	protected Element outerDiv; // container for everything
	protected U user;
	protected A userHandler;

	protected InitiateLoginImage<C, U> ili;
	protected LogoutImage<U> li;
	protected UserInfoBar<U> uib;

	protected UserComponent() {
		super();

		outerDiv = DOM.createDiv();
		setElement(outerDiv);
		addStyleName("tlz-user-component");
	}
	public UserComponent(A handler) {
		this();

		userHandler = handler;
		uib = createUserInfoBar();

		ili = createInitiateLoginImage();
		ili.addValueChangeHandler(handler);
		ili.addValueChangeHandler(this);
		ili.addValueChangeHandler(uib);

		li = createLogoutImage();
		li.addValueChangeHandler(handler);
		li.addValueChangeHandler(this);
		li.addValueChangeHandler(uib);
	}
	public UserComponent(U user, A handler) {
		this(handler);

		setUser(user);
	}
	@Override
	public void onAttach() {
		super.onAttach();

		setup();
	}
	/**
	 * 
	 */
	protected void setup() {
		clear();

		if (user == null) {
			add(ili, outerDiv);
		} else {
			add(li, outerDiv);
			add(uib, outerDiv);
		}
	}
	protected abstract InitiateLoginImage<C, U> createInitiateLoginImage();
	protected abstract LogoutImage<U> createLogoutImage();
	protected abstract UserInfoBar<U> createUserInfoBar();
	public void setUser(U user) {
		this.user = user;
		li.setUser(user);
		uib.setUser(user);
	}

	@Override
	public void onValueChange(ValueChangeEvent<U> event) {
		UserChangeEvent<U> ev = (UserChangeEvent<U>)event;
		U newUser = event.getValue();
		if (ev.isLogout())
			setUser( null );
		else
			setUser( newUser );
		setup();
		
//		if (currentUser != null) { // we are logging out!
//			// newUser is the user logging out!!
//			setCurrentUser( null );
//
//			if (newUser.getErrorMessage() != null) {
//				// we got an error message to show!!
//			}
//			afterLogout(newUser);
//		} else {// we are logging in!
//			setCurrentUser( newUser );
//			afterLogin();
//
//			// just in case user has a different default language
//			sutupDictionary( getDefLang() );
//		}
	}
}
