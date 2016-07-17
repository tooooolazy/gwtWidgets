/**
 * 
 */
package com.tooooolazy.gwt.flexlayout.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author tooooolazy
 *
 */
public interface HasLayoutChangedHandlers extends HasHandlers {
	  /**
	   * Adds a {@link UpdateEvent} handler.
	   * 
	   * @param handler the handler
	   * @return the registration for the event
	   */
	  HandlerRegistration addLayoutChangedHandler(LayoutChangedHandler handler);
}
