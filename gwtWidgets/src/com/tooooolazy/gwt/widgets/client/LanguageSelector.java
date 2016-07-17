/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.tooooolazy.gwt.widgets.client.ImageSelector;
import com.tooooolazy.gwt.widgets.shared.SelectItem;
import com.tooooolazy.gwt.widgets.shared.SupportedLanguage;

/**
 * @author tooooolazy
 *
 */
public class LanguageSelector<L extends SelectItem> extends ImageSelector<L> {
	protected LanguageSelector() {
	}

	public LanguageSelector(L[] items, Object defValue) {
		super(HORIZONTAL, items, defValue);
		setStyleName( "tlz-language-selector" );
	}

	protected String getCommonImagePath() {
		return "../images/flags/";
	}

	@Override
	protected String getValue(L item) {
		SupportedLanguage sl = (SupportedLanguage)item;
		return sl.getLanguage();
	}

	@Override
	protected String getImageName(L item) {
		SupportedLanguage sl = (SupportedLanguage)item;
		return sl.getLanguage();
	}

	@Override
	protected String getCommonImageType() {
		return ".png";
	}

	@Override
	protected String getImageHoverValue(L item) {
		SupportedLanguage sl = (SupportedLanguage)item;
		return sl.getDisplayValue();
	}

	@Override
	protected Type<ValueChangeHandler<?>> getEventType() {
		return LanguageChangeEvent.getType();
	}
	@Override
	protected void fireSelectionEvent(L item) {
		fireEvent(LanguageChangeEvent.createLanguageChangeEvent( item ));
	}

	@Override
	public String getMessage(String key) {
		return key;
	}
	@Override
	public void addToHandler() {
//		GwtApplication.multilingual.add(this);
	}
	@Override
	public void removeFromHandler() {
//		GwtApplication.multilingual.remove(this);
	}

}
