/**
 * 
 */
package com.tooooolazy.gwt.login.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.tooooolazy.gwt.widgets.client.ClosablePopup;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * @author tooooolazy
 *
 */
public abstract class InitiateRegistrationComponent extends Anchor implements Multilingual {
	protected ClosablePopup pup;
	protected RegisterComponent rc;

	public InitiateRegistrationComponent(final LoginComponent lc) {
		super(true);
		pup = new ClosablePopup(false) {
			@Override
			protected void onClose() {
				if (lc != null) {
					if (lc.getInitiateLoginImage() != null)
						lc.getInitiateLoginImage().enable();
				}
			}
			@Override
			public void hide() {
				// overridden in order to re enable login image after registration!!
				// TODO might want to move this in ClosablePopup
				super.hide();
				onClose();
			}
		};
		rc = createRegisterComponent();
		pup.add( rc );
		addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				pup.center(); // seems to also trigger onAttach, but not sure
				rc.reset();
				if (lc != null) {
					if (lc.getInitiateLoginImage() != null)
						lc.getInitiateLoginImage().getPopup().hide();
				}
			}
		});
	}
	public InitiateRegistrationComponent() {
		this(null);
	}
	public ClosablePopup getPopup() {
		return pup;
	}
	protected abstract RegisterComponent createRegisterComponent();

	@Override
	public void onAttach() {
		super.onAttach();
		addToHandler();
	}
	@Override
	public void setTitles() {
		setText( getMessage("RegistrationButton.title") );
		pup.setCaptionTitle( getMessage("RegistrationButton.title") );
	}
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}
}
