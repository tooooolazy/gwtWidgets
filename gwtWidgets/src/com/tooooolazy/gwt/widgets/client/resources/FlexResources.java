package com.tooooolazy.gwt.widgets.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface FlexResources extends ClientBundle {
	@Source("images/icons/menu.gif")
	ImageResource menu();

	@Source("images/icons/flexlayout/auto-H-size.gif")
	ImageResource autoHsize();
	@Source("images/icons/flexlayout/auto-V-size.gif")
	ImageResource autoVsize();
	@Source("images/icons/flexlayout/fixed-H-size.gif")
	ImageResource fixedHsize();
	@Source("images/icons/flexlayout/fixed-V-size.gif")
	ImageResource fixedVsize();
	@Source("images/icons/flexlayout/fixed-px-H-size.gif")
	ImageResource fixedPxHsize();
	@Source("images/icons/flexlayout/fixed-px-V-size.gif")
	ImageResource fixedPxVsize();

	@Source("images/icons/flexlayout/incCols.gif")
	ImageResource incCols();
	@Source("images/icons/flexlayout/incRows.gif")
	ImageResource incRows();
	@Source("images/icons/flexlayout/decCols.gif")
	ImageResource decCols();
	@Source("images/icons/flexlayout/decRows.gif")
	ImageResource decRows();

	@Source("images/icons/flexlayout/expandDown.gif")
	ImageResource expandDown();
	@Source("images/icons/flexlayout/expandRight.gif")
	ImageResource expandRight();
	@Source("images/icons/flexlayout/collapseDown.gif")
	ImageResource collapseDown();
	@Source("images/icons/flexlayout/collapseRight.gif")
	ImageResource collapseRight();

	@Source("images/icons/space.gif")
	ImageResource space();
}
