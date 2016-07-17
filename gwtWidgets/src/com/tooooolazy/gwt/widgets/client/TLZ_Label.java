/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.user.client.ui.Label;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * @author tooooolazy
 * 
 */
public class TLZ_Label extends Label implements Multilingual {
	protected String key;

	public TLZ_Label() {
		super();
	}

	public TLZ_Label(String text) {
		this();
		setText(text);
	}

	@Override
	public void setTitles() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMessage(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToHandler() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFromHandler() {
		// TODO Auto-generated method stub

	}

}
