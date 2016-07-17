package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Wrapper for <code>ClickableImage</code>. Creates an initially hidden image that appears on mouse over.<br>
 * It is indirectly multilingual by using <ode>ClickableImage</code>, which is multilingual.
 * @author tooooolazy
 *
 */
public class HiddenClickableImage extends SimplePanel implements MouseOverHandler, MouseOutHandler, HasClickHandlers {
	protected ClickableImage image;

	public ClickableImage getImage() {
		return image;
	}
	public HiddenClickableImage(ClickableImage image) {
		this(image, false);
	}
	public HiddenClickableImage(ClickableImage image, boolean absolute) {
		super(image);
		this.image = image;
		setWidth(image.getWidth()+"px");
		setHeight(image.getHeight()+"px");
		addDomHandler(this, MouseOverEvent.getType());
		addDomHandler(this, MouseOutEvent.getType());
		image.addStyleName("selectedIsHidden");

		if (absolute)
			addStyleName("tlz-absolute");
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {
		image.removeStyleName("selectedIsHidden");
	}
	@Override
	public void onMouseOut(MouseOutEvent event) {
		image.addStyleName("selectedIsHidden");
	}
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return image.addClickHandler(handler);
	}
}
