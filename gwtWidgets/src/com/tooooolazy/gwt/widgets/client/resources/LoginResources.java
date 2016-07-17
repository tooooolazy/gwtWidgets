package com.tooooolazy.gwt.widgets.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface LoginResources extends ClientBundle {
	@Source("images/icons/login/key.gif")
	ImageResource key();

	@Source("images/icons/login/broken_key.gif")
	ImageResource brokenKey();
}
