package com.tooooolazy.gwt.widgets.shared;

public class LanguageNotFoundException extends RuntimeException {
	protected String lang;

	public LanguageNotFoundException(String lang) {
		super(lang);
		this.lang = lang;
	}

	public String getLanguage() {
		return lang;
	}
}
