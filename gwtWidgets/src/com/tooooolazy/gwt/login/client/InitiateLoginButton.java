/**
 * 
 */
package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.tooooolazy.gwt.widgets.client.ClosablePopup;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * @author tooooolazy
 *
 * @param <c> credentials class
 * @param <U> user dto class
 */
public abstract class InitiateLoginButton<C, U> extends Button implements Multilingual {
	protected ClosablePopup pup;
	protected LoginComponent<C, U> lc;

	public InitiateLoginButton() {
		final InitiateLoginButton<C, U> ilc = this;
		pup = new ClosablePopup(false) {
			@Override
			public String getMessage(String key) {
				return ilc.getMessage(key);
			}
		};
		lc = createLoginComponent();
		pup.add( lc );
		addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lc.reset();
				pup.center();
				lc.setInitialFocus();
			}
		});
	}
	protected abstract LoginComponent<C, U> createLoginComponent();
	@Override
	public void onAttach() {
		super.onAttach();
		addToHandler();
		setTitles();
	}
	@Override
	public void setTitles() {
		setText( getMessage("InitiateLoginButton.loginTitle") );
		pup.setCaptionTitle( getMessage("InitiateLoginButton.loginTitle") );
		lc.setTitles();
	}
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}
	public ClosablePopup getPopup() {
		return pup;
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<U> handler) {
		return lc.addHandler(handler, UserChangeEvent.getType() );
	}

}
