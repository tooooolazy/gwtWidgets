/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * Wrapper for <code>ClickableImage</code>. Provides visual aid for selected image (on mouse over).
 * @author tooooolazy
 *
 */
public class SelectableClickableImage extends SimplePanel implements Multilingual, MouseOverHandler, MouseOutHandler, HasClickHandlers {
	protected ClickableImage image;

	public SelectableClickableImage(ImageResource imageRes, Command command) {
		this(imageRes, null, command);
	}
	public SelectableClickableImage(ImageResource imageRes, String altText, Command command) {
		super(DOM.createDiv());

		final SelectableClickableImage sci = this;

		if (altText == null && command != null) {
			altText = command.getClass().getName();
			if (command instanceof NamedCommand)
				altText = ((NamedCommand)command).getName();
		}

		this.image = new ClickableImage(imageRes, altText, command) {
			@Override
			public String getMessage(String key) {
				return sci.getMessage(key);
			}
		};
		addDomHandler(this, MouseOverEvent.getType());
		addDomHandler(this, MouseOutEvent.getType());
		setStyleName("tlz-selectable");
		add(image);
		DOM.setStyleAttribute(getElement(), "height", image.getHeight()+"px");
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {
		addStyleName("tlz-selected");
	}
	@Override
	public void onMouseOut(MouseOutEvent event) {
		removeStyleName("tlz-selected");
	}
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return image.addClickHandler(handler);
	}
	@Override
	public void setTitles() {
		image.setTitles();
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

}
