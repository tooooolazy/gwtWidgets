package com.tooooolazy.gwt.widgets.shared;

/**
 * if an entity implements this interface then the entity can be used as a source to fill a combo box.
 * Also DTOs can Implement this interface to mark them as objects that can be used in UI.<br>
 * It is declared inside a 'shared' package so it can be used both by client and server objects
 * @author tooooolazy
 * @see com.tooooolazy.gwt.widgets.client.ImageSelector
 *
 */
public interface SelectItem {
	public Object getPK();
	/**
	 * The idea is that for multilingual entities (eg Country) where we have ***ML tables as well (CountryML)
	 * we need to retrieve a language specific value from the ***ML record.
	 * It is more than good enough for server side applications, but not very handy for client side since
	 * we would have to transfer values in all available languages to the client.
	 * OR we could make an ajax call each time language changes and retrieve values only for one language.
	 * We are going to need an ajax call anyway in order to retrieve the list of DB items we want to add in the list
	 * @param lang
	 * @return
	 */
	public String getDisplayValue(String lang);
	public String getDefaultValue();
	public boolean hasLangDefined(String lang);
}
