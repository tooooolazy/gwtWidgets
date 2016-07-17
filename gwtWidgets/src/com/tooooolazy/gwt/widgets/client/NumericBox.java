/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Extends <code>TextBox</code> so that only numbers are allowed as input. Also provides support for decimals.
 * @author tooooolazy
 *
 */
public class NumericBox extends TextBox {
	protected String decimalSeparator=".";
	protected int decimalSize;

	public NumericBox() {
		super();
		addStyleName("tlz-input-numeric");
		addKeyPressHandler(keyPressHandler);
	}
	public NumericBox(int defValue) {
		this();
		setValue(String.valueOf(defValue));
	}

	public String getDecimalSeparator() {
		return decimalSeparator;
	}

	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}

	public int getDecimalSize() {
		return decimalSize;
	}

	public void setDecimalSize(int decimalSize) {
		this.decimalSize = decimalSize;
	}

	private KeyPressHandler keyPressHandler = new KeyPressHandler() {

		public void onKeyPress(KeyPressEvent e) {
			char key = e.getCharCode();
			String s = ""+key;
			int dind = getValue().indexOf(decimalSeparator);

			if (e.isAnyModifierKeyDown()) {
				return;
			} else
			if (!e.isAnyModifierKeyDown() && Character.isDigit(e.getCharCode())) {
				if (dind > -1 && getValue().substring(dind).length() > decimalSize) {
					if (getCursorPos() > dind) {
						
						e.preventDefault();
					}
					return;
				} else
					return;
			} else
			if (decimalSeparator.equals(s) && dind == -1) {
				if (decimalSize == 0)
					e.preventDefault();
				return;
			} else
			if ( !Character.isDigit(e.getCharCode()) && !(key==0 || key==8 || key==9 || key==13 || key==27)) {
				e.preventDefault();
				return;
			}
		}
	};
	public Float getNumericValue() {
		try {
			return Float.parseFloat(getValue());
		} catch(NumberFormatException e) {
			return new Float(0);
		}
	}
	public Integer getIntegerValue() {
		try {
			return Integer.parseInt(getValue());
		} catch(NumberFormatException e) {
			return new Integer(0);
		}
	}
}
