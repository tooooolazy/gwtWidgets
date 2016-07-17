package com.tooooolazy.gwt.widgets.shared;


/**
 * Marks a class as a multilingual component that might need a certain group of labels from DB
 * @author tooooolazy
 *
 */
public interface DBMultilingual extends Multilingual {
	public String getDictionary();
}
