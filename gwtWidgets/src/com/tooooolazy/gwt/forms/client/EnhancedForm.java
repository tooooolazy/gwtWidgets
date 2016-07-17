/**
 * 
 */
package com.tooooolazy.gwt.forms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.tooooolazy.gwt.forms.shared.RelativeValidatorException;
import com.tooooolazy.gwt.forms.shared.ValidatorException;
import com.tooooolazy.gwt.widgets.client.MessagePanel;
import com.tooooolazy.gwt.widgets.shared.DBMultilingual;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * On subnit, the submit button is disabled and enabled again when a response is received from Server.
 * @author tooooolazy
 *
 * @param <T> class that declares the object that the form will use to store its data.
 */
public abstract class EnhancedForm<T> extends FormPanel implements DBMultilingual {
	protected DockPanel formLayout;
	protected FieldLayout fieldLayout;
	protected MessagePanel messagesPanel;
	protected Button submitBtn;
	private boolean built;

	protected Label infoMessageLabel;
	/**
	 * Used to avoid re initializing the form (since it is called through onAttach). Should be toggled manually in every derived class
	 */
	protected boolean initOk;
	protected Timer initTimer;

	protected ArrayList<FormField> fields;

	protected String dictionary;
	@Override
	public String getDictionary() {
		return dictionary;
	}
	/**
	 * Just a wrapper that can be used when subclassing Multilingual components
	 * that are added in the form (ie FormField elements) 
	 * @param key
	 * @return
	 */
	public String getDictionaryMessage(String key) {
		return getMessage(key);
	}

	protected class FieldLayout extends ComplexPanel {
		protected int columns;
		private int curCol;

		protected Element fieldContainer;
		protected Element currentTR, currentTD;
		protected boolean canReduceSpan;

		public FieldLayout(int columns) {
			curCol = 1;
			this.columns = columns;
			canReduceSpan = false;
			fieldContainer = DOM.createTable();


			setElement(fieldContainer);
			setStyleName("tlz-fieldLayout"); // has to be after setElement

		}
		protected void addField(FormField ff) {
			if (fields.contains( ff )) {
			    throw new UnsupportedOperationException(
			            "This field is already added: " + ff.captionKey);
			}

			if (ff.getHorizontalSpan() > columns) { // this field cannot fit in form!!
				if (!canReduceSpan) {
					throw new UnsupportedOperationException(
			            "This field is too big (horizontal span is "+ff.getHorizontalSpan()+ " and maximum is " + columns +"): " + ff.captionKey);
				} else {
					
				}
			}

			System.out.println("preparing row for field...");
			if (currentTR == null) {// no current row
				System.out.println("no currentRow");
				addNewRow(); // also sets currentTR
			}
			int hSpan = ff.getHorizontalSpan();
			if (curCol + hSpan -1 > columns) {
				System.out.println(ff.captionKey + " field is spanned and does not fit...");
				// spanned field does not fit! So...
				// add dummy fields??
				for (int i=curCol; i<=columns; i++) {
					System.out.println("adding dummy fields");
					// might not be needed after all
//					addDummyField();
				}
				// and then a new Row
				System.out.println("current row is now full");
				addNewRow();
			}

			// we have a row to use.
			System.out.println("placing field: " + ff.captionKey);
			// in theory the field can fit in form, but can it fit in current position?

			addInput(ff);
			handleField(ff);
		}
//		private void addDummyField() {
//			currentTR.appendChild(DOM.createTD());
//			currentTR.appendChild(DOM.createTD());
//		}

		private void addInput(FormField ff) {
			Element TD = DOM.createTD();
			TD.addClassName("tlz-field-label");

			currentTD = DOM.createTD();
			
			int hSpan = ff.getHorizontalSpan();

			currentTD.setAttribute("width", (100/columns)*hSpan+"%");
			if (hSpan > 1) {
				currentTD.setAttribute("colspan", hSpan+1+"");
			}
			currentTR.appendChild(TD);
			currentTR.appendChild(currentTD);


			// need to take into account cases were there is no field, but just text
			if (ff.getCaptionPosition() == FormField.LEFT) {
				TD.appendChild(ff.getLabel().getElement());
				if (ff.getInput() != null)
					add(ff.getInput(), currentTD);
			} else {
				currentTD.appendChild(ff.getLabel().getElement());
				if (ff.getInput() != null)
					add(ff.getInput(), TD);
			}


//			add(ff.getInput());

			curCol += hSpan;
			fields.add( ff );

			if (curCol > columns) {
				// no other field can fit! So...
				System.out.println("current row is full");
				// by using addNewRow we might end up with an empty TR (if there are no more fields to add)
				// so we set currentTR to null, which will need to a new Row only if another field needs to be added
				currentTR = null;
			}
		}
		/**
		 * Adds the required handlers to the form field.
		 * @param ff - the form field
		 */
		protected void handleField(final FormField ff) {
			if (ff.getInput() == null)
				return;

			if (ff.getInput() instanceof FocusWidget) {
				FocusWidget fw = (FocusWidget)ff.getInput();

				fw.addKeyPressHandler( new KeyPressHandler() {
					
					@Override
					public void onKeyPress(KeyPressEvent event) {
						clearErrors(ff);
					}
				});
				fw.addKeyDownHandler( new KeyDownHandler() {
					
					@Override
					public void onKeyDown(KeyDownEvent event) {
						clearErrors(ff);
					}
				});
			}
		}
		/**
		 * 
		 */
		protected void addNewRow() {
			System.out.println("adding new row");
			currentTR = DOM.createTR();
			fieldContainer.appendChild(currentTR);
			currentTR.addClassName("tlz-fieldContainerRow");
			curCol=1;
		}
		@Override
		public void add(Widget w) {
			add(w, currentTD);
		}

		@Override
		public boolean remove(Widget w) {
			boolean removed = super.remove(w);
			return removed;
		}
	}

	public EnhancedForm() {
		this(1);
	}
	/**
	 * @param columns how many columns should the form split the fields into
	 */
	public EnhancedForm(int columns) {
		// this constructor makes sure that no hidden iframe is created
		super(Document.get().createFormElement(), false);
//		super();

//		super("FormPanels");
//		setMethod(METHOD_POST);
		// setting the following action throws an exception
		// javax.servlet.ServletException: Content-Type was 'application/x-www-form-urlencoded'. Expected 'text/x-gwt-rpc'.
//		setAction("/gwtaitol/users");
		// seems that "You should use a standard HttpServlet and not a RemoveServiceServlet as POST target of your form."
		// see buildForm for more details...

		fields = new ArrayList<FormField>();
		createFormLayout();
		add(formLayout);

		createFieldLayout(columns);
		formLayout.add(fieldLayout, DockPanel.CENTER);
		formLayout.setCellWidth(fieldLayout, "100%");

		createMessagesLayout();
		built = false;
//		buildForm(); // moved in onAttach for lazy loading



//		addTestFields();

	}
	@Override
	public void reset() {
		for (int i=0; i<fields.size(); i++) {
			FormField ff = fields.get(i);
			ff.setValue(ff.defValue);
			ff.noErrors();
		}
		messagesPanel.clear();
	}
	@Override
	public void onAttach() {
		if (!initOk) {
			System.out.println("initializing form data...");
			init();
		}
//		initOk = true;
		super.onAttach();

		// might be required to use init() to call all of the following...
		// the reason is that init() uses ajax to retrieve data are most likely required to build the form or fill form fields.
		// the problem is init() is currently abstract and does not have access to services in this base class!!!
		// the solution is to use a Timer!!!
		if (!built) // create the form the lazy way (by doing it in onAttach)!!
			buildForm();

		initTimer = new Timer() {
			
			@Override
			public void run() {
				if (initOk) {
//					addToHandler();
//					setTitles();
//					setInitialFocus();
//					initTimer.cancel();
					onInitComplete();
				}
			}
		};
		initTimer.scheduleRepeating(100);
	}
	/**
	 * Use this function to do general form initialization. Can also be used to make a single form ajax call.
	 * <p>It is called <b>before</b> <code>buildForm</code> so that data is retrieved before a field needs to display it</p>
	 * to retrieve data for specific form fields (ie list boxes)
	 */
	protected void init() {
		System.out.println("No initialization required");
		initOk = true;
	}
	protected void onInitComplete() {
		System.out.println("initialization complete...");
		addToHandler();
		setTitles();
		setInitialFocus();
		initTimer.cancel();
	}
	public boolean isInitialized() {
		return initOk;
	}
	protected abstract void setInitialFocus();

	@Override
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}
	//////////////////////////////////////
	// Global FormField creation functions
	protected FormField createFormField(String captionKey) {
		return createFormField(null, captionKey, FormField.LEFT, false, null);
	}
	protected FormField createFormField(Widget field, String captionKey, boolean required) {
		return createFormField(field, captionKey, FormField.LEFT, required, null);
	}
	protected FormField createFormField(Widget field, String captionKey, boolean required, Object value) {
		return createFormField(field, captionKey, FormField.LEFT, required, value);
	}
	protected FormField createFormField(Widget field, String captionKey, int captionPosition, boolean required, Object value) {
		return new FormField(field, captionKey, captionPosition, required, value) {
			@Override
			public String getMessage(String key) {
				return getDictionaryMessage(key);
			}
		};
	}
	/////////////////////////////////////////////

	public void addField(FormField ff) {
		fieldLayout.addField(ff);
	}
	protected void setFocus(final FormField ff) {
		Scheduler.get().scheduleDeferred( new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				ff.focus();
			}
		});
	}

	/**
	 * Creates the layout of the form. 
	 * <ul>Basically, it uses a DockPanel to split the form in content areas:
	 * <li>header</li>
	 * <li>fields</li>
	 * <li>footer (a message panel)</li>
	 * <li>extra footer (form action buttons)</li>
	 * </ul>
	 * 
	 */
	protected void createFormLayout() {
		formLayout = new DockPanel();
		formLayout.setStyleName("tlz-formLayout", true);
	}
	protected void createFieldLayout(int columns) {
		fieldLayout = new FieldLayout(columns);
	}
	protected void createMessagesLayout() {
		final EnhancedForm<T> f = this;
		messagesPanel = new MessagePanel() {
			@Override
			public String getMessage(String key) {
				return f.getMessage(key);
			}
		};
		messagesPanel.setStyleName("tlz-message-panel", true);
	}
	protected void clearErrors(FormField ff) {
		ff.noErrors();
		if (ff.relatedField != null)
			ff.relatedField.noErrors();
		messagesPanel.clear();
	}

	protected void buildForm() {
		submitBtn = new Button("submit");
		addBelowSubmit();
		formLayout.add(submitBtn, DockPanel.SOUTH);
		addAboveSubmit();
		formLayout.setCellHorizontalAlignment(submitBtn, DockPanel.ALIGN_RIGHT);

		// placed above submit button
		formLayout.add(messagesPanel, DockPanel.SOUTH);

		submitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				submitBtn.setEnabled(false);
				messagesPanel.clear();
				submit();
			}
		});
		addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				try {
					event.cancel(); // this is done to avoid using the automatically created iframe
					// submission is handled by RPC calls in derived classes.
					// this way the event 'SubmitCompleteEvent' is never thrown...
					// the problem was that the default action of the form 
					// was loading the whole application again inside the iframe
					// Another way (better one) would be to set the action correctly!!
					// but it isn't as simple as it sounds...
					// "You should use a standard HttpServlet and not a RemoveServiceServlet as POST target of your form."
					if (!isValid()) {
						messagesPanel.error(getErrorKey());
					} else {
						_onFormSubmit();
					}
				} catch (RelativeValidatorException e) {
					messagesPanel.error( e.getErrorKey() );
					event.cancel();
					System.err.println(e.getClass() + ":" + ((ValidatorException)e).getName());
					submitBtn.setEnabled(true);
				} catch (ValidatorException e) {
					messagesPanel.error( e.getErrorKey() );
					event.cancel();
					System.err.println(e.getClass() + ":" + ((ValidatorException)e).getName());
					submitBtn.setEnabled(true);
				} catch (RuntimeException e) {
					e.printStackTrace();
					submitBtn.setEnabled(true);
				}
//				submitBtn.setEnabled(true);
			}
		});
		// never seems to be fired... don't know why yet - But, it does fire!!
		// since now there is NO hidden iframe this event will never be thrown!!
//		addSubmitCompleteHandler(new SubmitCompleteHandler() {
//
//			@Override
//			public void onSubmitComplete(SubmitCompleteEvent event) {
//				messagesPanel.info("submit complete");
//				_onFormSubmitComplete();
//			}
//		});
		// TODO: might want to reset it in overrides OR use an extra function customBuild()
		// right before setting the built variable to TRUE!!
		built = true;
	}
	/**
	 * Override to add something below submit button
	 * The element should be added to formLayout using DockPanel.SOUTH
	 */
	protected void addBelowSubmit() {
		if (getInfoMessageKey() != null) {
			infoMessageLabel = new Label( getInfoMessageKey() );
			formLayout.add(infoMessageLabel, DockPanel.SOUTH);
		}
	}
	protected void addAboveSubmit() {
	}
	/**
	 * Used to display a multilingual message inside the form below submit button.
	 * @return
	 */
	protected String getInfoMessageKey() {
		return null;
	}
	/**
	 * What to do when the form is submitted.
	 */
	protected abstract void _onFormSubmit();
	protected void _onFormSubmitComplete() {
	}
	/**
	 * If login is successful, a <code>UserChangeEvent</code> is fired.
	 * @param userDto
	 */
	protected void onSubmitSuccess(T res) {
		submitBtn.setEnabled(true);
	}
	protected void onSubmitFailure() {
		submitBtn.setEnabled(true);
	}

	////////////////////////////////////
	// Form validation process
	/**
	 * if null is returned, no message appears
	 * @return
	 */
	protected String getErrorKey() {
		return "error.key";
	}
	protected String getRequiredErrorKey() {
		return "Form.requiredMessage";
	}
	protected boolean isValid() {
		for (int i=0; i<fields.size(); i++) {
			FormField ff = fields.get(i);
			ff.validate();
		}
		return true;
	}
	@Override
	public void setTitles() {
		for (int i=0; i<fields.size(); i++) {
			FormField ff = fields.get(i);
			ff.setTitles();
		}
		messagesPanel.setTitles();
		if (infoMessageLabel != null)
			infoMessageLabel.setText( getMessage( getInfoMessageKey() ) );
	}
//	private void addTestFields() {
//		// test fields
//		FormField ff = new FormField(new CheckBox(), "test1", FormField.RIGHT, true, null) {
//			@Override
//			public String getMessage(String key) {
//				// TODO Auto-generated method stub
//				return key;
//			}
//		};
//		addField(ff);
//
//		FormField ff2a = new FormField(new TextBox(), "test wer wer2a", true) {
//			@Override
//			public String getMessage(String key) {
//				// TODO Auto-generated method stub
//				return key;
//			}
//		};
//
////		ff2a.setHorizontalSpan(2);
//		addField(ff2a);
//
//		FormField ff2 = new FormField(new TextBox(), "test wer wer2", true) {
//			@Override
//			public String getMessage(String key) {
//				// TODO Auto-generated method stub
//				return key;
//			}
//		};
//
//		ff2.setHorizontalSpan(2);
//		addField(ff2);
//
//		FormField ff3 = new FormField(new TextBox(), "test wer wer3", true) {
//			@Override
//			public String getMessage(String key) {
//				// TODO Auto-generated method stub
//				return key;
//			}
//		};
//
////		ff3.setHorizontalSpan(2);
//		addField(ff3);
//
//		FormField ff4 = new FormField(new TextBox(), "test wer4", true) {
//			@Override
//			public String getMessage(String key) {
//				// TODO Auto-generated method stub
//				return key;
//			}
//		};
//
//		ff4.setHorizontalSpan(2);
//		addField(ff4);
//		setFocus(ff4);
//		
//	}
}
