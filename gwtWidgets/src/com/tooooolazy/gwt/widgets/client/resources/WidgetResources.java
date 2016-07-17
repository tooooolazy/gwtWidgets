/**
 * 
 */
package com.tooooolazy.gwt.widgets.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

/**
 * @author tooooolazy
 *
 */
public interface WidgetResources extends ClientBundle {
	@Source("images/icons/acceptItem.gif")
	@ImageOptions(width=10, height = 7)
	ImageResource acceptSmall();
	@Source("images/icons/deleteItem.gif")
	@ImageOptions(width=7, height = 7)
	ImageResource cancelSmall();

	@Source("images/icons/acceptItem.gif")
	ImageResource accept();
	@Source("images/icons/deleteItem.gif")
	ImageResource cancel();

	@Source("images/icons/target.png")
	ImageResource target();
	@Source("images/icons/erase.png")
	ImageResource erase();
	@Source("images/icons/close.png")
	ImageResource closeIcon();

	@Source("images/icons/separator.gif")
	ImageResource separator();

}
