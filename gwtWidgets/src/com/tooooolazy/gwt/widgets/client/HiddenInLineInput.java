/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.tooooolazy.gwt.widgets.client.resources.Resources;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * Provides inline editing. It is triggered by clicking on an Image.
 * <p>
 * When in edit mode, the input field is shown along with an 'accept' button on the right.
 * Supports initial values, which are selected when entering edit mode.
 * 'Enter' submits current value.
 * </p>
 * @author tooooolazy
 *
 */
public class HiddenInLineInput extends ComplexPanel implements Multilingual {
	protected Element outerDiv; // container for everything
	protected Element inputDiv;
	protected SelectableClickableImage ci;
	protected Widget input;
	protected boolean inputVisible;

	/**
	 * @param imageRes the image to use as a trigger
	 * @param _input the input element to use (eg. TextBox)
	 * @param command the command to execute when done editing.
	 */
	public HiddenInLineInput(ImageResource imageRes, Widget _input, final Command command) {
		super();
		outerDiv = DOM.createDiv();
		inputDiv = DOM.createDiv();
		inputVisible = false;
		setElement(outerDiv);
		addStyleName("tlz-no-wrap");

		String altText = command.getClass().getName();
		if (command instanceof NamedCommand)
			altText = ((NamedCommand)command).getName();
		ci = createSelectableClickableImage(imageRes, altText);
		ci.addStyleName("tlz-inline");
		// we need to use 'add' for the event handlers to work 
		add(ci, outerDiv);
		this.input = _input;
		// When enter is pressed, by default, the given command is executed!!
		input.addDomHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent e) {
				Object o = e.getSource();
				if (KeyCodes.KEY_ENTER == e.getNativeEvent().getKeyCode()) {
					command.execute();
				}
			}
		}, KeyPressEvent.getType());
		input.addStyleName("tlz-inline-input");
		ClickableImage accept = createActionClickableImage(command);
//		ClickableImage cancel = new ClickableImage(Resources.common.cancelSmall(), hideInput());

		DOM.setStyleAttribute(inputDiv, "display", "none");
		// move the input control a bit higher
		DOM.setStyleAttribute(inputDiv, "position", "relative");
		DOM.setStyleAttribute(inputDiv, "top", "-4px");

		inputDiv.addClassName("tlz-inline");
		inputDiv.addClassName("tlz-no-wrap");

		DOM.appendChild(outerDiv, inputDiv);
		// we need to use 'add' for the event handlers to work 
		add(input);
		add(accept);
//		add(cancel);
	}
	/**
	 * @param imageRes
	 * @param _input
	 * @param command
	 * @param width the size of the input element in pixels
	 */
	public HiddenInLineInput(ImageResource imageRes, Widget _input, final Command command, int width) {
		this(imageRes, _input, command);
		input.setWidth(width+"px");
	}

	/**
	 * @param imageRes
	 * @param command 
	 * @return
	 */
	protected SelectableClickableImage createSelectableClickableImage(ImageResource imageRes, String altText) {
		final HiddenInLineInput hii = this;
		return new SelectableClickableImage(imageRes, altText, toggleInput()) {
			@Override
			public String getMessage(String key) {
				return hii.getMessage(key);
			}
		};
	}
	/**
	 * Creates the Action Icon that handles the user input value
	 * @param command
	 * @return
	 */
	protected ClickableImage createActionClickableImage(Command command) {
		final HiddenInLineInput hii = this;
		return new ClickableImage(Resources.common.acceptSmall(), "apply", command) {
			@Override
			public String getMessage(String key) {
				return hii.getMessage(key);
			}
		};
	}

	@Override
	public void add(Widget w) {
		add(w, inputDiv);
	}
	protected Command toggleInput() {
		return new Command() {
			
			@Override
			public void execute() {
				if (!inputVisible) {
					inputVisible = true;
					DOM.setStyleAttribute(inputDiv, "display", "");
					try {
						((FocusWidget)input).setFocus(true);
						((ValueBoxBase)input).setSelectionRange(0, ((ValueBoxBase)input).getValue().toString().length());
					} catch(Exception e) {
						
					}
				} else {
					inputVisible = false;
					DOM.setStyleAttribute(inputDiv, "display", "none");
				}
			}
		};
	}
	protected Command hideInput() {
		return new Command() {
			
			@Override
			public void execute() {
				DOM.setStyleAttribute(inputDiv, "display", "none");
			}
		};
	}

	@Override
	public void setTitles() {
		ci.setTitles();
	}

	@Override
	public String getMessage(String key) {
		return key;
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
