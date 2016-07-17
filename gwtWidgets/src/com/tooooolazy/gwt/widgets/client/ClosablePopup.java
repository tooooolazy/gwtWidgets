/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.tooooolazy.gwt.widgets.client.resources.Resources;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * @author tooooolazy
 *
 */
public class ClosablePopup extends DialogBox implements Multilingual {

	private ClickableImage closeImage;
	protected FlexTable captionLayoutTable;

	/**
	 * Instantiates new closable popup.
	 * 
	 */
	public ClosablePopup(boolean modal) {
		super(false, modal);
		setGlassEnabled( modal );

		final ClosablePopup cp = this;
		closeImage = new ClickableImage( Resources.common.closeIcon() ) {
			@Override
			public String getMessage(String key) {
				return cp.getMessage(key);
			}
		};

		captionLayoutTable = new FlexTable();
		captionLayoutTable.setWidth("100%");
		captionLayoutTable.setWidget(0, 1, closeImage);
		captionLayoutTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.HorizontalAlignmentConstant.endOf(HasDirection.Direction.LTR));

		HTML caption = (HTML) getCaption();
		caption.getElement().appendChild(captionLayoutTable.getElement());

		caption.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EventTarget target = event.getNativeEvent().getEventTarget();
				Element targetElement = (Element) target.cast();

				if (targetElement == closeImage.getElement()) {
					onClose();
					hide();
				}
			}
		});
	}
	protected void onClose() {
		// TODO Auto-generated method stub
		
	}

	public void addCloseHandler(ClickHandler handler) {
		closeImage.addClickHandler(handler);
	}
	public void setCaptionTitle(String title) {
		captionLayoutTable.setText(0, 0, title);
	}

	@Override
	public void onAttach() {
		super.onAttach();
		addToHandler();
		setTitles();
	}
	@Override
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}
	@Override
	public void setTitles() {
		closeImage.setTitles();
	}
	@Override
	public void addToHandler() {
	}
	@Override
	public void removeFromHandler() {
	}
	@Override
	public String getMessage(String key) {
		return key;
	}
}
