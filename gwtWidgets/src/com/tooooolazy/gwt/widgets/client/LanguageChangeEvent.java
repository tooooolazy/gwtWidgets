/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * @author tooooolazy
 *
 */
public class LanguageChangeEvent<L> extends ValueChangeEvent<L> {
	public static <L> LanguageChangeEvent<L> createLanguageChangeEvent(L langDto) {
		return new LanguageChangeEvent<L>(langDto);
	}
	protected LanguageChangeEvent(L langDto) {
		// we might want to use a copy of the DTO 
		super(langDto);
	}
}
