/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.tooooolazy.gwt.widgets.client.resources.Resources;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * TODO: finish <code>addSeparator</p>
 * <p>
 * A simple container tha acts as a toolbar. Any Widget can be added, but since it is a Tool bar
 * it is preferable that Action Images are added (like <code>ClickableImage</code> objects)
 * </p>
 * @author tooooolazy
 *
 */
public class ToolBar extends ComplexPanel implements Multilingual {

	public ToolBar() {
		Element div = DOM.createDiv();
		setElement(div);
		setStyleName("tlz-toolbar");
		refreshControls();
	}
	public void setHeight(int height) {
//		DOM.setStyleAttribute(getElement(), "height", height+"px");
	}
	protected void refreshControls() {
		clear();
		addTools();
	}
	/**
	 * override to add the required tools and separators in the toolbar
	 */
	protected void addTools() {
		
	}
	@Override
	public void add(Widget w) {
		w.addStyleName("tlz-inline"); // so tools appear next to each other
		add(w, getElement());
	}

	@Override
	public boolean remove(Widget w) {
		boolean removed = super.remove(w);
		return removed;
	}
	public void addSeparator() {
		add( new Image(Resources.common.separator()) );
	}

	@Override
	public void setTitles() {
		Iterator<Widget> it = getChildren().iterator();
		
		while (it.hasNext()) {
			Widget w = it.next();
			if (w instanceof Multilingual) {
				((Multilingual)w).setTitles();
			}
		}
		
	}
	@Override
	public String getMessage(String key) {
		return key;
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
