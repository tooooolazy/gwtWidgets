/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tooooolazy.gwt.widgets.shared.Multilingual;
import com.tooooolazy.gwt.widgets.shared.SelectItem;

/**
 * Abstract class that creates a panel that holds and displays a set of <code>ClickableImage</code> Objects
 * that are created using Objects that implement <code>SelectItem</code> interface.
 * <p>
 * It has a default value and hides the selected value from the displayed items list. When a selection is made an event is fired.
 * </p>
 * @author tooooolazy
 *
 */
public abstract class ImageSelector<T extends SelectItem> extends SimplePanel implements Multilingual, HasValueChangeHandlers<T> {
	public static int HORIZONTAL = 1;
	public static int VERTICAL = 2;

	protected int orientation;
	protected T[] items;
	protected Object defValue;
	protected ClickableImage[] imgItems;

	protected ImageSelector() {
	}
	public ImageSelector(T[] items) {
		this(HORIZONTAL, items, null);
	}
	public ImageSelector(int orientation, T[] items, Object defValue) {
		this.orientation = orientation;
		this.items = items;
		this.defValue = defValue;

		createPanel();
	}
	@Override
	public void onAttach() {
		super.onAttach();
		addToHandler();

		addSelections();
		setTitles();
	}
	@Override
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}

	protected void createPanel() {
		CellPanel cp = null;
		if (orientation == HORIZONTAL) {
			cp = new HorizontalPanel();
		} else {
			cp = new VerticalPanel();
		}
		setWidget(cp);

		cp.setSpacing(5);
		cp.setStyleName( "ImageSelector" );

	}
	protected CellPanel getPanel() {
		return (CellPanel)getWidget();
	}
	public void setSelectedValue(String value) {
		for (int i=0; i< items.length; i++) {
			final T item = items[i];
			if (getValue(item).equals( value )) {
				imgItems[i].setStyleName("selectedIsHidden", true);
			} else {
				imgItems[i].removeStyleName("selectedIsHidden");
			}
		}		
	}
	protected void addSelections() {
		getPanel().clear();
		imgItems = new ClickableImage[items.length];

		for (int i=0; i< items.length; i++) {
			final T item = items[i];

			String img = getCommonImagePath() + getImageName(item) + getCommonImageType();
			imgItems[i] = new ClickableImage( img, null, onClickCommand(item) );

			if (getValue(item).equals( defValue )) {
				imgItems[i].setStyleName("selectedIsHidden", true);
			}
			getPanel().add( imgItems[i] );
		}
	}
	protected Command onClickCommand(final T item) {
		return new Command() {
			
			@Override
			public void execute() {
				System.out.println("clicked item: " + getValue(item) );
				setSelectedValue( getValue(item) );

				fireSelectionEvent( item );
			}
		};
	}
	protected abstract void fireSelectionEvent(T item);

	@Override
	public void setTitles() {
		for (int i=0; i< items.length; i++) {
			final T item = items[i];
			imgItems[i].setAltText( getImageHoverValue( item ) );
			imgItems[i].setTitle( getImageHoverValue( item ) );
		}
	}

	/**
	 * @return
	 */
	protected abstract String getValue(T item);
	protected abstract String getCommonImagePath();
	protected abstract String getImageName(T item);
	protected abstract String getCommonImageType();
	protected abstract String getImageHoverValue(T item);

	protected abstract Type<ValueChangeHandler<?>> getEventType();

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return addHandler(handler, getEventType() );
	}
}
