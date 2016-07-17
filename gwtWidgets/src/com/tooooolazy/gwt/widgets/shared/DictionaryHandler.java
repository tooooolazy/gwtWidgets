package com.tooooolazy.gwt.widgets.shared;

/**
 * Used to mark a component as a dictionary handler that it used to retrieve multilingual messages from db
 * and setup the required dictionary variable in javascript.
 * <p>Currently implemented by <code>GwtApplication</code>.</p>
 * @author tooooolazy
 *
 */
public interface DictionaryHandler {
	public void setupDictionary(final String lang);
	public void setupDictionaries(final String lang);
}
