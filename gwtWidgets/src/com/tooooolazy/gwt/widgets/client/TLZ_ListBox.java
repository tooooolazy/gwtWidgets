/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.user.client.ui.ListBox;
import com.tooooolazy.gwt.widgets.shared.SelectItem;

/**
 * @author tooooolazy
 *
 */
public abstract class TLZ_ListBox<T extends SelectItem> extends ListBox {
	protected T[] items;

	public TLZ_ListBox() {
		this(null);
	}
	public TLZ_ListBox(T[] items) {
		if (items != null)
			setItems(items);
	}
	@Override
	public void onAttach() {
		super.onAttach();
	}
	public void setItems(T[] items) {
		this.items = items;
		addSelections();
	}
	protected void addSelections() {
		clear();

		for (int i=0; i< items.length; i++) {
			final T item = items[i];

			addItem(item.getPK().toString());
			setItemText(i, item.getDisplayValue( getCurrentLang() ) );
		}
	}
	public void updateSelections() {
		if (items == null)
			return;
		for (int i=0; i< items.length; i++) {
			final T item = items[i];

			setItemText(i, item.getDisplayValue( getCurrentLang() ) );
		}
	}
	protected abstract String getCurrentLang();
}
