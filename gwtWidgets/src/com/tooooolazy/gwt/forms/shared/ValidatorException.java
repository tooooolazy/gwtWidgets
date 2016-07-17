package com.tooooolazy.gwt.forms.shared;

public class ValidatorException extends RuntimeException {
	protected Object value;
	protected String name;
	protected String errorKey;
	
	public ValidatorException(String captionKey) {
		super(captionKey);
		this.name = captionKey;
		this.errorKey = getClass().getName();
	}
	public ValidatorException(String captionKey, String errorKey) {
		this(captionKey);
		this.errorKey = errorKey;
	}
	public ValidatorException(String captionKey, Object value) {
		this(captionKey != null ? captionKey : value.toString());
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}
	public String getErrorKey() {
		return errorKey;
	}
}
