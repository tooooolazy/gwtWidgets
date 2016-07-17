package com.tooooolazy.gwt.widgets.client.resources;

import com.google.gwt.core.client.GWT;

/**
 * Helper class that includes all available resources
 * @author tooooolazy
 *
 */
public class Resources {
	public static final WidgetResources common = GWT.create(WidgetResources.class);
	public static final FlexResources flex = GWT.create(FlexResources.class);
	public static final MessagePanelResources msg = GWT.create(MessagePanelResources.class);
	public static final LoginResources login = GWT.create(LoginResources.class);

}
