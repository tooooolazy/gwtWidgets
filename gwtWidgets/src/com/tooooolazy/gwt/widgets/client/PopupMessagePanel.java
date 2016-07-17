/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

/**
 * @author tooooolazy
 *
 */
public class PopupMessagePanel extends ClosablePopup {
	protected MessagePanel mp;
	protected String titleKey;

	public PopupMessagePanel(boolean modal, String titleKey, String messageKey) {
		super(modal);
		this.titleKey = titleKey;

		mp = createMessagePanel();
		mp.info(messageKey);
		mp.center();
		add( mp );
	}

	/**
	 * @return
	 */
	protected MessagePanel createMessagePanel() {
		final PopupMessagePanel pmp = this;
		return new MessagePanel() {
			@Override
			public String getMessage(String key) {
				return pmp.getMessage(key);
			}
		};
	}
	@Override
	public void setTitles() {
		super.setTitles();
		setCaptionTitle(getMessage(titleKey));
		mp.setTitles();
	}
}
