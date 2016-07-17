/**
 * 
 */
package com.tooooolazy.gwt.forms.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.tooooolazy.gwt.forms.shared.RelativeValidator;
import com.tooooolazy.gwt.forms.shared.RelativeValidatorException;
import com.tooooolazy.gwt.forms.shared.RequiredValidator;
import com.tooooolazy.gwt.forms.shared.Validator;
import com.tooooolazy.gwt.forms.shared.ValidatorException;
import com.tooooolazy.gwt.widgets.client.utils.UIUtils;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * @author tooooolazy
 *
 */
public abstract class FormField implements Multilingual {
	public static int LEFT = 2;
	public static int RIGHT = 3;

	protected Label caption;
	protected Widget field;
	protected boolean required;
	protected String captionKey;
	protected int captionPosition;
	protected int hSpan;
	private boolean hasErrors;
	protected Object defValue;
	public void setDefValue(Object defValue) {
		this.defValue = defValue;
	}

	protected ArrayList<Validator> validators;
	protected FormField relatedField;
	public void setRelatedField(FormField ff) {
		relatedField = ff;
	}

	public Label getLabel() {
		return caption;
	}
	public Widget getInput() {
		return field;
		
	}
	public FormField(Widget field, String captionKey) {
		this(field, captionKey, false, null);
	}
	public FormField(Widget field, String captionKey, boolean required) {
		this(field, captionKey, required, null);
	}
	public FormField(Widget field, String captionKey, boolean required, Object value) {
		this(field, captionKey, LEFT, required, value);
	}
	public FormField(Widget field, String captionKey, int captionPosition, boolean required, Object value) {
		super();
		validators = new ArrayList<Validator>();
		if (required)
			validators.add(new RequiredValidator(captionKey));

		this.field = field;
		this.captionKey = captionKey;
		this.required = required;
		this.hSpan = 1;
		this.captionPosition = captionPosition;
		defValue = value;
		setValue(value);
		caption = new Label(captionKey);
		if (field != null)
			field.setStyleName("tlz-field", true);
	}
	public int getCaptionPosition() {
		return captionPosition;
	}
	public void setCaptionPosition(int captionPosition) {
		this.captionPosition = captionPosition;
	}
	public FormField setHorizontalSpan(int hSpan) {
		this.hSpan = hSpan;
		return this;
	}
	public int getHorizontalSpan() {
		return hSpan;
	}
	public boolean isRequired() {
		return required;
	}
	public void setValue(Object value) {
		if (field instanceof CheckBox) {
			((CheckBox)field).setValue((Boolean)value);
		}
		if (field instanceof ValueBoxBase) {
			((ValueBoxBase)field).setValue(value);
		}
		if (field instanceof DateBox) {
			((DateBox)field).setValue((Date)value);
		}
		if (field instanceof ListBox) {
			((ListBox)field).setSelectedIndex(UIUtils.getListItemIndexByValue((ListBox)field, (String)value));
		}
	}
	public Object getValue() {
		if (field instanceof CheckBox)
			return ((CheckBox)field).getValue();
		if (field instanceof ValueBoxBase)
			return ((ValueBoxBase)field).getValue();
		if (field instanceof DateBox)
			return ((DateBox)field).getValue();
		if (field instanceof ListBox)
			return ((ListBox)field).getValue( ((ListBox)field).getSelectedIndex() );

		return "-";
	}
	public void hasErrors(boolean b) {
		hasErrors = b;
		if ( hasErrors ) {
			caption.setStyleName("tlz-field-label-has-error", true);
			field.setStyleName("tlz-field-has-error", true);
		} else {
			caption.removeStyleName("tlz-field-label-has-error");
			field.setStyleName("tlz-field-has-error", true);
		}
	}
	public void noErrors() {
		caption.removeStyleName("tlz-field-label-has-error");
		if (field != null)
			field.removeStyleName("tlz-field-has-error");
	}
	public void addValidator(Validator validator) {
		validators.add(validator);
	}
	protected void validate() {
		Iterator<Validator> it = validators.iterator();
		while (it.hasNext()) {
			Validator v = it.next();
			try {
				if (v instanceof RelativeValidator)
					((RelativeValidator)v).validate(getValue(), relatedField.getValue());
				else
					v.validate(getValue());
			} catch (RelativeValidatorException e) {
				relatedField.hasErrors(true);
				hasErrors(true);
				focus();
				throw e;
			} catch (ValidatorException e) {
				hasErrors(true);
				focus();
				throw e;
			}
		}
	}
	public void focus() {
		if (field instanceof FocusWidget)
			((FocusWidget)field).setFocus( true );
		if (field instanceof TextBox) {
			TextBox fw = (TextBox)field;
			fw.setSelectionRange(0, fw.getValue().length());
		}
	}

	@Override
	public void setTitles() {
		String captionText = getMessage(captionKey);
		if (required)
			captionText += " *";
		caption.setText(captionText);
		if (field instanceof Multilingual)
			((Multilingual) field).setTitles();
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
