package com.tooooolazy.gwt.widgets.client.utils;

import com.google.gwt.user.client.ui.ListBox;

public class UIUtils {
	public static int getListItemIndexByValue(ListBox lb, String value) {
		for (int i=0; i<lb.getItemCount(); i++) {
			String s = lb.getValue(i);
			if (s.equals(value)) {
				return i;
			}
		}
		return -1;
	}
}
