/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * A simple <code>Image</code> that has (by adding a css style: clickable) a pointer as cursor (on mouseover) and performs an action onClick.<br>
 * It can be enabled and disabled.
 * 
 * @author tooooolazy
 *
 */
public class ClickableImage extends Image implements Multilingual, MouseOverHandler, MouseOutHandler, ClickHandler {
	protected String altTextKey;
	protected Command command;
	protected boolean isDisabled;

	public ClickableImage(ImageResource image) {
		this(image, null, null);
	}
	public ClickableImage(ImageResource image, Command command) {
		this(image, null, command);
	}
	public ClickableImage(String image) {
		this(image, null, null);
	}
	public void disable() {
		isDisabled = true;
		removeStyleName("clickable");
	}
	public void enable() {
		isDisabled = false;
		addStyleName("clickable");
	}
	/**
	 * @param image - the <code>ImageResource</code> to use
	 * @param altText - the text to display on mouseover
	 */
	public ClickableImage(ImageResource image, String altText, Command command) {
		super(image);

		if (altText == null && command != null) {
			altText = command.getClass().getName();
			if (command instanceof NamedCommand)
				altText = ((NamedCommand)command).getName();
		}
		this.altTextKey = altText;
		setupCommand(command);
		setStyleName("clickable", true);
		addMouseOverHandler(this);
		addMouseOutHandler(this);
	}

	/**
	 * @param image - the Image URI to use
	 * @param altText - the text to display on mouseover
	 */
	public ClickableImage(String image, String altText, Command command) {
		super(image);
		this.altTextKey = altText;
		setupCommand(command);
		setTitles();
		setStyleName("clickable", true);
		addMouseOverHandler(this);
		addMouseOutHandler(this);
	}
	public void setupCommand(Command command) {
		this.command = command;
		if (command != null)
			addClickHandler(this);
	}
	@Override
	public void onClick(ClickEvent event) {
		if (!isDisabled)
			command.execute();
	}

	@Override
	public void setTitles() {
		if (altTextKey != null) {
			setAltText( getMessage( altTextKey ) );
			setTitle( getMessage( altTextKey ) );
		}
	}
	@Override
	public void onAttach() {
		super.onAttach();
		if (altTextKey != null)
			addToHandler();
		setTitles();
	}
	@Override
	public void onDetach() {
		super.onDetach();
		if (altTextKey != null)
			removeFromHandler();
	}
	@Override
	public String getMessage(String key) {
		return key;
	}
	@Override
	public void addToHandler() {
		// TODO Auto-generated method stub
	}
	@Override
	public void removeFromHandler() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {
	}
	@Override
	public void onMouseOut(MouseOutEvent event) {
	}
}
