package com.tooooolazy.gwt.flexlayout.client;

import java.util.Iterator;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.tooooolazy.gwt.flexlayout.shared.dto.FlexCellStructureDTO;
import com.tooooolazy.gwt.flexlayout.shared.dto.FlexLayoutStructureDTO;
import com.tooooolazy.gwt.widgets.client.ClickableImage;
import com.tooooolazy.gwt.widgets.client.HiddenClickableImage;
import com.tooooolazy.gwt.widgets.client.HiddenInLineInput;
import com.tooooolazy.gwt.widgets.client.NamedCommand;
import com.tooooolazy.gwt.widgets.client.NumericBox;
import com.tooooolazy.gwt.widgets.client.SelectableClickableImage;
import com.tooooolazy.gwt.widgets.client.ToolBar;
import com.tooooolazy.gwt.widgets.client.resources.Resources;
import com.tooooolazy.gwt.widgets.shared.Multilingual;

/**
 * Fires <code>LayoutChangedEvent</code> events when a structural modification is made to the layout.<br>
 * Those events are handled by objects that implement <code>LayoutChangedHandler</code> interface.
 * @author tooooolazy
 *
 */
public class DynamicFlexLayout extends FlexLayout implements Multilingual {
	protected DynamicFlexCell lastMenuPanelIn;
	protected int menuMode;
	protected PopupPanel menuPanel;

	public DynamicFlexLayout() {
		this(new FlexLayoutStructureDTO());
	}
	public DynamicFlexLayout(FlexLayoutStructureDTO fls) {
		super(fls);
		menuPanel = new PopupPanel(false);
		
		add( creareLayoutMenuTrigger(), outerDiv );
	}
	protected FlexCell[][] createFlexCellArray(int x, int y) {
		return new DynamicFlexCell[x][y];
	}
	/** 
	 * Overridden in order to create a DynamicFlexCell
	 * @see com.tooooolazy.gwt.flexlayout.client.FlexLayout#createFlexCell(com.tooooolazy.gwt.flexlayout.shared.dto.FlexCellStructureDTO)
	 */
	protected FlexCell createFlexCell(FlexCellStructureDTO fcs) {
		return new DynamicFlexCell( fcs );
	}
	protected FlexLayout getLayout() {
		return this;
	}

	/**
	 * Convenience class that overrides default <code>getMessages</code> and delegates to the one in <code>DynamicFlexLayout</code>
	 * @author tooooolazy
	 *
	 */
	protected class FlexSelectableClickableImage extends SelectableClickableImage {
		public FlexSelectableClickableImage(ImageResource imageRes, Command command) {
			super(imageRes, command);
		}

		@Override
		public String getMessage(String key) {
			return ((DynamicFlexLayout)getLayout()).getMessage(key);
		}
	}
	/**
	 * TODO: move refresh in super class???
	 * @author tooooolazy
	 *
	 */
	public class ControlsPanel extends ToolBar {
		public ControlsPanel() {
			super();
			setHeight(17);
		}

		protected void addTools() {
			add( new FlexSelectableClickableImage(Resources.flex.incCols(), new FlexLayoutIncColsCommand() ) );
			add( new FlexSelectableClickableImage(Resources.flex.incRows(), new FlexLayoutIncRowsCommand()) );
			addSeparator();
			add( new FlexSelectableClickableImage(Resources.flex.decCols(), new FlexLayoutDecColsCommand()) );
			add( new FlexSelectableClickableImage(Resources.flex.decRows(), new FlexLayoutDecRowsCommand()) );
		}
	}

	protected HiddenClickableImage creareLayoutMenuTrigger() {
		ClickableImage ci = new ClickableImage(Resources.flex.menu(), new FlexLayoutShowMenuCommand() ) {
			@Override
			public String getMessage(String key) {
				return ((DynamicFlexLayout)getLayout()).getMessage(key);
			}
		};
		return new HiddenClickableImage(ci, true);
	}
	/////////////////////////////////
	// FlexLayout Commands
	protected class FlexLayoutCommand extends NamedCommand {
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			System.out.println(getName() + " command executed...");
			LayoutChangedEvent.fire(getLayout());
		}
	}
	protected class FlexLayoutShowMenuCommand extends FlexLayoutCommand {
		@Override
		public void execute() {
			if (menuMode == fls.getMaxMenuModes()) {
				hideMenu();
			} else {
				handleLayoutMenuMode();
			}
		}
	}
	protected class FlexLayoutIncColsCommand extends FlexLayoutCommand {
		@Override
		public void execute() {
			incCols();
			super.execute();
		}
	}
	protected class FlexLayoutDecColsCommand extends FlexLayoutCommand {
		@Override
		public void execute() {
			if (decCols())
				super.execute();
		}
	}
	protected class FlexLayoutIncRowsCommand extends FlexLayoutCommand {
		@Override
		public void execute() {
			incRows();
			super.execute();
		}
	}
	protected class FlexLayoutDecRowsCommand extends FlexLayoutCommand {
		@Override
		public void execute() {
			if (decRows())
				super.execute();
		}
	}

	protected void hideMenu() {
		lastMenuPanelIn = null;
		menuMode = 0;
		menuPanel.hide();
	}
	protected void handleLayoutMenuMode() {
		// only one cell menu at a time
		if (lastMenuPanelIn != null) {
			lastMenuPanelIn.hideMenu();
			lastMenuPanelIn = null;
		}
		
		if (menuMode == 0) {
			setupLayoutControls();
			menuMode = 1;
		} else
		if (menuMode == 1) {
//			setupLayoutMenu();
			menuMode = 2;
		}
	}
	protected void setupLayoutControls() {
		menuPanel.clear();
		menuPanel.add(new ControlsPanel());
		menuPanel.setPopupPosition(outerDiv.getAbsoluteLeft()+10, outerDiv.getAbsoluteTop());
		menuPanel.show();
	}

	//////////////////////////////////
	
	protected class DynamicFlexCell extends FlexCell implements Multilingual {
		protected HiddenClickableImage menuTrigger;
		protected int menuMode;
		protected PopupPanel menuPanel;

		/**
		 * No need to override <code>getMessage</code> since this is done directly for the 'toolbar' items!
		 * @author tooooolazy
		 *
		 */
		public class ControlsPanel extends ToolBar {
			public ControlsPanel() {
				super();
				setHeight(18);
			}

			protected void addTools() {
				if (!isExpCol()) {
					NumericBox nb = new NumericBox(fcs.getWidth());
					add( new HiddenInLineInput(Resources.flex.fixedPxHsize(), nb, setWidthCommand(nb), 20) {
						@Override
						public String getMessage(String key) {
							return ((DynamicFlexLayout)getLayout()).getMessage(key);
						}
					});
					add( new FlexSelectableClickableImage(Resources.flex.autoHsize(), setExpColCommand()) );
				} else
					add( new FlexSelectableClickableImage(Resources.flex.fixedHsize(), noExpColCommand()) );

				if (!isExpRow()) {
					NumericBox nb = new NumericBox(fcs.getHeight());
					add( new HiddenInLineInput(Resources.flex.fixedPxVsize(), nb, setHeightCommand(nb)) {
						@Override
						public String getMessage(String key) {
							return ((DynamicFlexLayout)getLayout()).getMessage(key);
						}
					});
					add( new FlexSelectableClickableImage(Resources.flex.autoVsize(), setExpRowCommand()) );
				} else
					add( new FlexSelectableClickableImage(Resources.flex.fixedVsize(), noExpRowCommand()) );

				if (canIncYspan())
					add( new FlexSelectableClickableImage(Resources.flex.expandDown(), expandDownCommand()) );
				if (canIncXspan())
					add( new FlexSelectableClickableImage(Resources.flex.expandRight(), expandRightCommand()) );
				if (fcs.getySpan() > 1)
					add( new FlexSelectableClickableImage(Resources.flex.collapseDown(), collapseDownCommand()) );
				if (fcs.getxSpan() > 1)
					add( new FlexSelectableClickableImage(Resources.flex.collapseRight(), collapseRightCommand()) );
			}
		}

		public class MenuPanel extends SimplePanel {
			public MenuPanel() {
				super();
				MenuBar mb = new MenuBar(true);
				mb.addItem( new MenuItem(getWidth()+"x"+getHeight(), new Command() {
					
					@Override
					public void execute() {
						// TODO Auto-generated method stub
						menuPanel.hide();
						menuMode = 0;
					}
				}));
				mb.addItem( new MenuItem("test 2", new Command() {
					
					@Override
					public void execute() {
						// TODO Auto-generated method stub
						menuPanel.hide();
						menuMode = 0;
					}
				}));
				add(mb);
			}
		}

		public DynamicFlexCell(FlexCellStructureDTO cellStructure) {
			super(cellStructure);

			menuPanel = new PopupPanel(false);

			menuTrigger = creareCellMenuTrigger();
			add( menuTrigger );
		}
		/**
		 * 
		 */
		protected void setupCellControls() {
			menuPanel.clear();
			menuPanel.add(new ControlsPanel());
			menuPanel.setPopupPosition(cellDiv.getAbsoluteLeft()+10, cellDiv.getAbsoluteTop());
			menuPanel.show();
		}
		/**
		 * 
		 */
		protected void setupCellMenu() {
			menuPanel.clear();
				menuPanel.add(new MenuPanel());
//			ToolBar tb = new ToolBar();
//			tb.add( new ClickableImage(Resources.flex.autoHsize(), setExpColCommand()) );
//			menuPanel.add(tb);
			menuPanel.setPopupPosition(cellDiv.getAbsoluteLeft()+10, cellDiv.getAbsoluteTop());
			menuPanel.show();
		}
		protected void refreshMenu() {
			if (lastMenuPanelIn == null)
				return;
			
			if (menuMode == 1) {
				setupCellControls();
			} else
			if (menuMode == 2) {
				setupCellMenu();
			}
		}
		protected void handleCellMenuMode() {
			// only one cell menu at a time
			if (lastMenuPanelIn != null && lastMenuPanelIn != this)
				lastMenuPanelIn.hideMenu();
			// we should remember which cell has a menu showing
			lastMenuPanelIn = this;
			
			if (menuMode == 0) {
				setupCellControls();
				menuMode = 1;
			} else
			if (menuMode == 1) {
				setupCellMenu();
				menuMode = 2;
			}
		}
		/**
		 * 
		 */
		protected void hideMenu() {
			lastMenuPanelIn = null;
			menuMode = 0;
			menuPanel.hide();
		}
		/**
		 * @return
		 */
		protected HiddenClickableImage creareCellMenuTrigger() {
			ClickableImage ci = new ClickableImage(Resources.flex.menu(), showMenuCommand()) {
				@Override
				public String getMessage(String key) {
					return ((DynamicFlexLayout)getLayout()).getMessage(key);
				}
			};
			return new HiddenClickableImage(ci, true);
		}

		////////////////////////////////
		// supported Flex Cell Commands
		public class FlexCellCommand extends FlexLayoutCommand {
			@Override
			public void execute() {
				super.execute();
				refreshMenu();
			}
		}
		protected class FlexCellShowMenuCommand extends FlexCellCommand {
			@Override
			public void execute() {
				if (menuMode == fcs.getMaxMenuModes()) {
					hideMenu();
				} else {
					handleCellMenuMode();
				}
			}
		}
		protected class FlexCellExpandDownCommand extends FlexCellCommand {
			@Override
			public void execute() {
				if (incYspan()) {
					super.execute();
				}
			}
		}
		protected class FlexCellExpandRightCommand extends FlexCellCommand {
			@Override
			public void execute() {
				if (incXspan()) {
					super.execute();
				}
			}
		}
		protected class FlexCellCollapseDownCommand extends FlexCellCommand {
			@Override
			public void execute() {
				if (decYspan()) {
					super.execute();
				}
			}
		}
		protected class FlexCellCollapseRightCommand extends FlexCellCommand {
			@Override
			public void execute() {
				if (decXspan()) {
					super.execute();
				}
			}
		}
		protected class FlexCellSetExpColCommand extends FlexCellCommand {
			@Override
			public void execute() {
				setExpCol(fcs.getxPos());
				super.execute();
			}
		}
		protected class FlexCellSetExpRowCommand extends FlexCellCommand {
			@Override
			public void execute() {
				setExpRow(fcs.getyPos());
				super.execute();
			}
		}
		protected class FlexCellNoExpColCommand extends FlexCellCommand {
			@Override
			public void execute() {
				setExpCol(-1);
				super.execute();
			}
		}
		protected class FlexCellNoExpRowCommand extends FlexCellCommand {
			@Override
			public void execute() {
				setExpRow(-1);
				super.execute();
			}
		}
		protected class FlexCellSetWidthCommand extends FlexCellCommand {
			protected NumericBox nb;

			public FlexCellSetWidthCommand(NumericBox nb) {
				this.nb = nb;
			}
			@Override
			public void execute() {
				int i = nb.getIntegerValue();
				if (setColWidth(fcs.getxPos(), i)) {
					super.execute();
				}
			}
		}
		protected class FlexCellSetHeightCommand extends FlexCellCommand {
			protected NumericBox nb;

			public FlexCellSetHeightCommand(NumericBox nb) {
				this.nb = nb;
			}
			@Override
			public void execute() {
				int i = nb.getIntegerValue();
				if (setRowHeight(fcs.getyPos(), i)) {
					super.execute();
				}
			}
		}

		protected Command showMenuCommand() {
			return new FlexCellShowMenuCommand();
		}
		public Command expandDownCommand() {
			return new FlexCellExpandDownCommand();
		}
		public Command expandRightCommand() {
			return new FlexCellExpandRightCommand();
		}
		public Command collapseDownCommand() {
			return new FlexCellCollapseDownCommand();
		}
		public Command collapseRightCommand() {
			return new FlexCellCollapseRightCommand();
		}

		public Command setExpColCommand() {
			return new FlexCellSetExpColCommand();
		}
		public Command setExpRowCommand() {
			return new FlexCellSetExpRowCommand();
		}
		public Command noExpColCommand() {
			return new FlexCellNoExpColCommand();
		}
		public Command noExpRowCommand() {
			return new FlexCellNoExpRowCommand();
		}

		public Command setWidthCommand(NumericBox nb) {
			return new FlexCellSetWidthCommand(nb);
		}
		public Command setHeightCommand(NumericBox nb) {
			return new FlexCellSetHeightCommand(nb);
		}
		//////////////////////////////////
		@Override
		public void setTitles() {
			if (menuTrigger != null)
				menuTrigger.getImage().setTitles();
			Widget w = menuPanel.getWidget();
			if (w instanceof Multilingual) {
				((Multilingual)w).setTitles();
			}
		}
		@Override
		public String getMessage(String key) {
			return ((DynamicFlexLayout)getLayout()).getMessage(key);
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
	@Override
	public void setTitles() {
		Widget w = menuPanel.getWidget();
		if (w instanceof Multilingual) {
			((Multilingual)w).setTitles();
		}

		Iterator<Widget> it = getChildren().iterator();

		while (it.hasNext()) {
			Widget cell = it.next();
			if (cell instanceof Multilingual) {
				((Multilingual)cell).setTitles();
			}
		}
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
	@Override
	public void onAttach() {
		super.onAttach();
		addToHandler();
	}
	@Override
	public void onDetach() {
		super.onDetach();
		removeFromHandler();
	}
}
