package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.tooooolazy.gwt.widgets.client.ClickableImage;
import com.tooooolazy.gwt.widgets.client.ClosablePopup;
import com.tooooolazy.gwt.widgets.client.NamedCommand;
import com.tooooolazy.gwt.widgets.client.resources.Resources;

/**
 * Displays an Action icon (<code>ClickableImage</code>) that opens the login popup and disables the icon.<br>
 * When the popup closes, either due to successful login or user action, the Action icon is again enabled.
 * @author tooooolazy
 *
 * @param <c> Credentials DTO
 * @param <U> User DTO
 */
public abstract class InitiateLoginImage<C, U> extends ClickableImage {
	protected ClosablePopup pup;
	protected LoginComponent<C, U> lc;

	public InitiateLoginImage() {
		super(Resources.login.key(), null);
		altTextKey = "InitiateLoginButton.loginTitle";

		final InitiateLoginImage<C, U> ilc = this;
		pup = new ClosablePopup(false) {
			@Override
			public String getMessage(String key) {
				return ilc.getMessage(key);
			}
			@Override
			public void onClose() {
				enable();
			}
		};
		lc = createLoginComponent();
		pup.add( lc );

		setupCommand( new NamedCommand() {
			@Override
			public void execute() {
				lc.reset();
//				pup.showRelativeTo(ilc);
				pup.center();
				lc.setInitialFocus();
				disable();
			}
		});
	}
	protected abstract LoginComponent<C, U> createLoginComponent();

	public ClosablePopup getPopup() {
		return pup;
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<U> handler) {
		return lc.addHandler(handler, UserChangeEvent.getType() );
	}
	public abstract String getMessage(String key);
	@Override
	public void setTitles() {
		super.setTitles();
		pup.setTitles();
		// no need to call the next. it is added in multilingual list
//		lc.setTitles();
		pup.setCaptionTitle( getMessage("InitiateLoginButton.loginTitle") );
	}
}
