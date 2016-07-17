package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.tooooolazy.gwt.widgets.client.resources.Resources;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * Implementation of a simple Multilingual Messages panel.<br>
 * For text formatting based on message types, CSS styles are used:
 * <li>message-error</li>
 * <li>message-warning</li>
 * <li>message-info</li>
 * 
 * 
 * @author tooooolazy
 *
 */
public class MessagePanel extends DockPanel implements Multilingual {

	protected Image messageIcon;
	protected Label message;
	protected String messageKey;

	public MessagePanel() {
		super();
		setWidth("100%");
		addToHandler();

		message = new Label("");
		messageIcon = new Image();
		add(messageIcon, DockPanel.WEST);
		setCellHorizontalAlignment(messageIcon, DockPanel.ALIGN_RIGHT);
		message.setStyleName("tlz-message-text");
		add(message, DockPanel.CENTER);
	}
	@Override
	public String getMessage(String key) {
		return key;
	}
	@Override
	public void addToHandler() {
	}
	@Override
	public void removeFromHandler() {
	}
	@Override
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}
	@Override
	public void setTitles() {
		if (messageKey != null)
			message.setText( getMessage( messageKey ) );
		else
			message.setText("");
	}
	public void clear() {
		messageKey = null;
		message.setText("");
		message.removeStyleName("tlz-message-error");
		message.removeStyleName("tlz-message-warning");
		message.removeStyleName("tlz-message-info");
		messageIcon.setVisible(false);
	}
	/**
	 * Centers the text message in the panel's available space
	 */
	public void center() {
		setCellHorizontalAlignment(message, ALIGN_CENTER);
	}

	public void error(String msg) {
		messageKey = msg;
		message.addStyleName("tlz-message-error");
		message.removeStyleName("tlz-message-warning");
		message.removeStyleName("tlz-message-info");
		messageIcon.setResource( Resources.msg.error17() );
		messageIcon.setVisible(true);
		setTitles();
	}
	public void warning(String msg) {
		messageKey = msg;
		message.addStyleName("tlz-message-warning");
		message.removeStyleName("tlz-message-info");
		message.removeStyleName("tlz-message-error");
		messageIcon.setResource( Resources.msg.warn17() );
		messageIcon.setVisible(true);
		setTitles();
	}
	public void info(String msg) {
		this.info(msg, true);
	}
	public void info(String msg, boolean showIcon) {
		messageKey = msg;
		message.addStyleName("tlz-message-info");
		message.removeStyleName("tlz-message-warning");
		message.removeStyleName("tlz-message-error");
		messageIcon.setResource( Resources.msg.info17() );
		messageIcon.setVisible(showIcon);
		setTitles();
	}
}
