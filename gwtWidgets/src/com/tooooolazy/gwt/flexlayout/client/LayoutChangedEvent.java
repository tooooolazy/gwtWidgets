package com.tooooolazy.gwt.flexlayout.client;

import com.google.gwt.event.shared.GwtEvent;

public class LayoutChangedEvent extends GwtEvent<LayoutChangedHandler> {

	/**
	 * Handler type.
	 */
	private static Type<LayoutChangedHandler> TYPE;

	/**
	 * Gets the type associated with this event.
	 * 
	 * @return returns the handler type
	 */
	public static Type<LayoutChangedHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<LayoutChangedHandler>();
		}
		return TYPE;
	}

	public static boolean fire(HasLayoutChangedHandlers source) {
		if (TYPE != null) {
			LayoutChangedEvent event = new LayoutChangedEvent();
			source.fireEvent(event);
		}
		return true;
	}

	protected LayoutChangedEvent() {
	}

	@Override
	public Type<LayoutChangedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LayoutChangedHandler handler) {
		handler.onChange(this);
	}

}
