/**
 * 
 */
package com.tooooolazy.gwt.datepicker.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.tooooolazy.gwt.widgets.client.resources.Resources;
import com.tooooolazy.gwt.widgets.client.utils.UIUtils;

/**
 * @author tooooolazy
 * 
 */
public class DefaultTimeSelector extends TimeSelector {
	private Grid grid;
	private ListBox mins, hours;
	private Image accept, clear, now;

	public DefaultTimeSelector() {
		this.intervalsPerHour = 0;
	}
	public DefaultTimeSelector(int intervalsPerHour) {
		this.intervalsPerHour = intervalsPerHour;
	}

	@Override
	public void setTitles() {
		clear.setAltText( getMessage("DatePicker.clear") );
		clear.setTitle( getMessage("DatePicker.clear") );
		accept.setAltText( getMessage("DatePicker.apply") );
		accept.setTitle( getMessage("DatePicker.apply") );
		now.setAltText( getMessage("DatePicker.now") );
		now.setTitle( getMessage("DatePicker.now") );
	}
	@Override
	public void addToHandler() {
	}
	@Override
	public void removeFromHandler() {
	}

	@Override
	protected void refresh() {
		Date d = getModel().getCurrentMonth();
		hours.setSelectedIndex(UIUtils.getListItemIndexByValue(hours,
				String.valueOf(d.getHours())));
		mins.setSelectedIndex(UIUtils.getListItemIndexByValue(mins,
				String.valueOf(d.getMinutes())));
	}

	@Override
	protected void setup() {
		grid = new Grid(1, 7);

		hours = new ListBox();
		hours.setStyleName(css().timeSelectorHours());
		hours.addItem("0");
		hours.addItem("1");
		hours.addItem("2");
		hours.addItem("3");
		hours.addItem("4");
		hours.addItem("5");
		hours.addItem("6");
		hours.addItem("7");
		hours.addItem("8");
		hours.addItem("9");
		hours.addItem("10");
		hours.addItem("11");
		hours.addItem("12");
		hours.addItem("13");
		hours.addItem("14");
		hours.addItem("15");
		hours.addItem("16");
		hours.addItem("17");
		hours.addItem("18");
		hours.addItem("19");
		hours.addItem("20");
		hours.addItem("21");
		hours.addItem("22");
		hours.addItem("23");
		grid.setWidget(0, 1, hours);

		mins = new ListBox();
		mins.setStyleName(css().timeSelectorMins());
		mins.addItem("0");
		mins.addItem("15");
		mins.addItem("30");
		mins.addItem("45");
		grid.setWidget(0, 2, new Label(":"));
		grid.setWidget(0, 3, mins);
		grid.getCellFormatter().setWidth(0, 0, "100%");

		accept = new Image(Resources.common.accept());
		accept.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				int h = Integer.parseInt(hours.getItemText(hours
						.getSelectedIndex()));
				int m = Integer.parseInt(mins.getItemText(mins
						.getSelectedIndex()));
				setMinutes(m);
				setHours(h);
				Date d = getDatePicker().getValue();
				if (d == null)
					d = new Date();
				d.setHours(h);
				d.setMinutes(m);

				getDatePicker().setValue(d, true, true);
			}
		});
		accept.setStyleName("clickable", true);
		grid.setWidget(0, 6, accept);

		clear = new Image(Resources.common.erase());
		clear.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getDatePicker().setValue(null, true, true);
			}
		});
		clear.setStyleName("clickable", true);
		grid.setWidget(0, 4, clear);

		now = new Image(Resources.common.target());
		now.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getDatePicker().setValue(new Date(), true, true);
			}
		});
		now.setStyleName("clickable", true);
		grid.setWidget(0, 5, now);

		grid.setStyleName(css().timeSelector());

		initWidget(grid);
		setTitles();

	}

}
