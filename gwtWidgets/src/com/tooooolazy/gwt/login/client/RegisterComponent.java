/**
 * 
 */
package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.tooooolazy.gwt.forms.client.EnhancedForm;
import com.tooooolazy.gwt.forms.client.FormField;
import com.tooooolazy.gwt.forms.shared.EmailValidator;
import com.tooooolazy.gwt.forms.shared.EqualityValidator;

/**
 * @author tooooolazy
 *
 * @param <R> RegistrationData dto class
 */
public abstract class RegisterComponent<R> extends EnhancedForm<R> implements HasValueChangeHandlers<R> {
	protected FormField ff_email;
	protected FormField ff_p;
	protected FormField ff_pv;

	protected InitiateRegistrationComponent irc;

	public RegisterComponent() {
		super();
	}
	public RegisterComponent(InitiateRegistrationComponent irc) {
		super();
		this.irc = irc;
	}
	@Override
	public void setInitialFocus() {
		setFocus(ff_email);
	}
	@Override
	public void setTitles() {
		super.setTitles();
		submitBtn.setText( getMessage("RegistrationComponent.submitBtn") );
		if (irc != null)
			irc.setTitles();
	}

	@Override
	protected void buildForm() {
		super.buildForm();
		ff_email = createFormField(new TextBox(), "email", true);
		ff_p = createFormField(new PasswordTextBox(), "password", true);
		ff_pv = createFormField(new PasswordTextBox(), "passwordVerify", true);

//		ff_email = new FormField(new TextBox(), "email", true) {
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
//		ff_pv = new FormField(new PasswordTextBox(), "passwordVerify", true) {
//			@Override
//			public String getMessage(String key) {
//				return getDictionaryMessage( key );
//			}
//		};
		
		ff_email.addValidator(new EmailValidator("email"));
		ff_pv.addValidator(new EqualityValidator("passwordVerify"));
		ff_pv.setRelatedField(ff_p);

		addField(ff_email);
		addField(ff_p);
		addField(ff_pv);
	}
	protected String getInfoMessageKey() {
		if (requiresEmailVerification())
			return "RegisterComponent.subInfo";
		else
			return null;
	}
	/**
	 * If returns true then an appropriate message is displayed at the bottom of the form
	 * and an email is sent (if new user registers successfully) with a link to activate the account.<br>
	 * <b>The email part needs to be manually implemented on the server side.</b>
	 * <code>_onFormSubmit</code> of the extended class does not need to let the server know
	 * if verification is required as the server already knows!
	 * @return true/false
	 * @see DB_UserController.createUser(...)
	 */
	protected abstract boolean requiresEmailVerification();

	@Override
	protected boolean isValid() {
		return super.isValid();
	}

	@Override
	protected String getErrorKey() {
		return "RegistrationComponent.error";
	}

	/**
	 * @param registrationDataDto
	 */
	protected void fireUserRegisteredEvent(R registrationDataDto) {
		fireEvent( UserRegisteredEvent.createUserRegisteredEvent(registrationDataDto) );
	}
	protected void onSubmitSuccess(R res) {
		super.onSubmitSuccess(res);
		fireUserRegisteredEvent(res);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<R> handler) {
		return addHandler(handler, UserRegisteredEvent.getType() );
	}

}
