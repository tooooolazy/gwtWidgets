package com.tooooolazy.gwt.login.client;


import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.tooooolazy.gwt.forms.client.EnhancedForm;
import com.tooooolazy.gwt.forms.client.FormField;

/**
 * @author tooooolazy
 *
 * @param <C> credentials class
 * @param <U> user dto class
 */
public abstract class LoginComponent<C, U> extends EnhancedForm<U> implements HasValueChangeHandlers<U> {
	protected FormField ff_un;
	protected FormField ff_p;
	protected FormField ff_rememberMe;
	protected InitiateLoginButton<C, U> ilc;
	protected InitiateLoginImage<C, U> ili;
	
	
	protected InitiateRegistrationComponent registerLink;
	protected Anchor forgotPasswordLink;

	public LoginComponent() {
		this( null );
//		super(2,2,EnhancedFormPanel.ROW_FIRST);
	}
	public LoginComponent(InitiateLoginButton<C, U> ilc) {
		super();
		this.ilc = ilc;
//		super(2,2,EnhancedFormPanel.ROW_FIRST);
	}
	@Override
	public void setInitialFocus() {
		setFocus(ff_un);
	}

	@Override
	public void setTitles() {
		super.setTitles();
		submitBtn.setText( getMessage("LoginComponent.submitBtn") );
		if (registerLink != null)
			registerLink.setTitles();
		if (forgotPasswordLink != null)
			forgotPasswordLink.setText( getMessage("ForgotPasswordButton.title") );
	}

	@Override
	protected void buildForm() {
		super.buildForm();

		ff_un = createFormField(new TextBox(), "username", true);
		ff_p = createFormField(new PasswordTextBox(), "password", true);
		ff_rememberMe = createFormField(new CheckBox(), "remember.me", FormField.RIGHT, false, true);

//		ff_un = new FormField(new TextBox(), "username", true) {
//			@Override
//			public String getMessage(String key) {
//				return getDictionaryMessage( key );
//			}
//		};
//		ff_p = new FormField(new PasswordTextBox(), "password", true) {
//			@Override
//			public String getMessage(String key) {
//				return getDictionaryMessage( key );
//			}
//		};
//		ff_rememberMe = new FormField(new CheckBox(), "remember.me", FormField.RIGHT, false,true) {
//			@Override
//			public String getMessage(String key) {
//				return getDictionaryMessage( key );
//			}
//		};

		addField(ff_un);
		addField(ff_p);
		addField(ff_rememberMe);
		
		// test for adding free text to the form
//		addField( new FormField("ForgotPasswordComponent.subInfo").setSpan(2, 1).buildField() );
	}

	@Override
	protected void addBelowSubmit() {
		registerLink = createRegistrationLink();
		// TODO as above
		forgotPasswordLink = new Anchor( true );

		if (forgotPasswordLink != null)
			formLayout.add(forgotPasswordLink, DockPanel.SOUTH);
		if (registerLink != null)
			formLayout.add(registerLink, DockPanel.SOUTH);
	}

	protected abstract InitiateRegistrationComponent createRegistrationLink();

	@Override
	protected boolean isValid() {
		return super.isValid();
	}

	@Override
	protected String getErrorKey() {
		return "LoginComponent.error";
	}

	/**
	 * @param userDto
	 */
	protected void fireUserChangedEvent(U userDto) {
		fireEvent( UserChangeEvent.createUserLoginEvent(userDto) );
	}
	/**
	 * If login is successful, a <code>UserChangeEvent</code> is fired.
	 * @param userDto
	 */
	protected void onSubmitSuccess(U res) {
		super.onSubmitSuccess(res);
		fireUserChangedEvent(res);
	}

	public InitiateLoginButton<C, U> getInitiateLoginButton() {
		return ilc;
	}
	public InitiateLoginImage<C, U> getInitiateLoginImage() {
		return ili;
	}
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<U> handler) {
		return addHandler(handler, UserChangeEvent.getType() );
	}
}
