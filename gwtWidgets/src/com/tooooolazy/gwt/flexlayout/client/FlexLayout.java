/**
 * 
 */
package com.tooooolazy.gwt.flexlayout.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tooooolazy.gwt.flexlayout.shared.dto.FlexCellStructureDTO;
import com.tooooolazy.gwt.flexlayout.shared.dto.FlexLayoutStructureDTO;

/**
 * Not ready yet
 * fires change events
 * @author tooooolazy
 * 
 */
public class FlexLayout extends ComplexPanel implements ResizeHandler, HasLayoutChangedHandlers {
	protected FlexLayoutStructureDTO fls;
	protected Element outerDiv, innerDiv;
	// the calculated pixels that can be used for expanding a cell
	protected int expWidth, expHeight;
	// no need to initialize this. is done automatically in 'onAttcah' by 'addCells'
	protected FlexCell[][] cells;
	protected ArrayList<String> placed;

	protected boolean showOutlines;

	public FlexLayout() {
		this(new FlexLayoutStructureDTO());
	}
	public FlexLayout(FlexLayoutStructureDTO fls) {
		placed = new ArrayList<String>();
		this.fls = fls;
		showOutlines = false;

		outerDiv = DOM.createDiv();
		innerDiv = DOM.createDiv();
		DOM.appendChild(outerDiv, innerDiv);

		innerDiv.addClassName("tlz-flexlayout-inner");

		setBorderWidth(10);
		setElement(outerDiv);
		setStyleName("tlz-flexlayout"); // has to be after setElement

		Window.addResizeHandler(this);
	}
	/**
	 * Instructs the layout to place a border around each cell.
	 */
	public void showOutlines() {
		if (showOutlines)
			return;

		showOutlines = true;
		// we should NOT call add cells before FlexLayout is attached somewhere 
		if (isAttached())
			apllyStructure();
	}
	/**
	 * Instructs the layout to hide each cell border.
	 */
	public void hideOutlines() {
		if (!showOutlines)
			return;

		showOutlines = false;
		// we should NOT call add cells before FlexLayout is attached somewhere 
		if (isAttached())
			apllyStructure();
	}
	public void onAttach() {
		super.onAttach();
		addCells();

		// just for testing
		cells[0][0].incXspan();
		cells[0][0].incYspan();
//		incCols();
//		decCols();
//		setColWidth(0, 120);
	}
	/**
	 * The main algorithm for the layout. It calculates the required variables for
	 * the layout structure to be displayed correctly. Should be called by all functions
	 * that could modify the size of the layout.
	 */
	public void setSizes() {
		Widget w = getParent();

		// first get the available container area
		// (we'll need it to calculate the size of expandable cells)
		int availableWidth = w.getElement().getClientWidth();
		int availableHeight = w.getElement().getClientHeight();
		
		// in order to calculate expandable size we also need to know total width and height of all cells
		expWidth = availableWidth - (getTotalCellWidth() + fls.getxBorder()*2);
		expHeight = availableHeight - (getTotalCellHeight() + fls.getyBorder()*2);
		
		// if expandable sizes are <0 it means that there is no space for a cell to expand in.
		// Scrollbars might be needed to show the whole layout
		if (expWidth <= 0) {
			expWidth = 0;
		}
		if (expHeight <= 0) {
			expHeight = 0;
		}

		// set the outer and inner layout sizes
		// getInnerWidth uses ph.getWidth which takes into account expandable fls.getRows() and columns...
		DOM.setStyleAttribute(innerDiv, "width", getInnerWidth() +"px");
		DOM.setStyleAttribute(outerDiv, "width", getOuterWidth() +"px");
		DOM.setStyleAttribute(innerDiv, "height", getInnerHeight()+"px");
		DOM.setStyleAttribute(outerDiv, "height", getOuterHeight()+"px");

		// now set placeholder sizes!!
		// we start from ph[0][0] and set both width and height.
		// We know top and left to be zero and that fcs.getfcs.getySpan and ySpan CANNOT be 0!!!
		apllyStructure();
	}
	public void apllyStructure() {
		cells[0][0].place(0, 0);
	}
	public FlexCell getCell(int x, int y) {
		if (x<fls.getCols() && y<fls.getRows())
			return cells[x][y];
		return null;
	}

	public void setBorderWidth(int width) {
		// TODO refactor this
		DOM.setStyleAttribute(innerDiv, "marginLeft", fls.getxBorder()+"px");
		DOM.setStyleAttribute(innerDiv, "marginRight", fls.getxBorder()+"px");
		DOM.setStyleAttribute(innerDiv, "marginTop", fls.getyBorder()+"px");
		DOM.setStyleAttribute(innerDiv, "marginBottom", fls.getyBorder()+"px");
	}
	protected int getInnerWidth() {
		int iWidth = getTotalCellWidth();
		if (fls.getExpCol() > -1)
			iWidth += expWidth;
		return iWidth;
	}
	protected int getInnerHeight() {
		int iHeight =  getTotalCellHeight();
		if (fls.getExpRow() > -1)
			iHeight += expHeight;
		return iHeight;
	}
	/**
	 * Calculates the total width of the layout ignoring the expandable column.
	 * It is used to determine the available width for horizontal cell expansion.
	 * @return
	 */
	protected int getTotalCellWidth() {
		int iWidth = 0;
		for (int x=0; x<fls.getCols(); x++) {
			iWidth += fls.getFlexCellStructure(x, 0).getWidth();
		}
		return iWidth;
	}
	/**
	 * Calculates the total width of the layout ignoring the expandable row.
	 * It is used to determine the available height for vertical cell expansion.
	 * @return
	 */
	protected int getTotalCellHeight() {
		int iHeight = 0;
		for (int y=0; y<fls.getRows(); y++) {
			iHeight += fls.getFlexCellStructure(0, y).getHeight();
		}
		return iHeight;
	}
	protected int getOuterWidth() {
		return getInnerWidth() + fls.getxBorder()*2;
	}
	protected int getOuterHeight() {
		return getInnerHeight() + fls.getyBorder()*2;
	}

	public void incCols() {
		int cols = fls.getCols();
		fls.setCols(cols+1);
		
		addCells();
	}
	public void incRows() {
		int rows = fls.getRows();
		fls.setRows(rows+1);
		
		addCells();
	}

	public void setExpRow(int row) {
		if (row < fls.getRows() && row >= -1 && row != fls.getExpRow()) {
			fls.setExpRow(row);
			setSizes();
		}
	}
	public void setExpCol(int col) {
		if (col < fls.getCols() && col >= -1 && col != fls.getExpCol()) {
			fls.setExpCol(col);
			setSizes();
		}
	}

	public boolean setColWidth(int col, int width) {
		if (col >= 0 && col < fls.getCols() && width >= 15) {
			for (int r=0; r<fls.getRows(); r++) {
				fls.getFlexCellStructure(col, r).setWidth(width);
			}
			setSizes();
			return true;
		}
		return false;
	}
	public boolean setRowHeight(int row, int height) {
		if (row >= 0 && row < fls.getRows() && height >= 15) {
			for (int c=0; c<fls.getCols(); c++) {
				fls.getFlexCellStructure(c, row).setHeight(height);
			}
			setSizes();
			return true;
		}
		return false;
	}
	/**
	 * Called by <code>FlexLayout.incCols</code> and <code>FlexLayout.incRows</code>.<br>
	 * Adds DIV elements representing PlaceHolders to the DOM.
	 * If the structure of the Place holder is not defined in FlexLayoutStructureDTO.fcs,
	 * then a new default cell structure is created and used.
	 */
	protected void addCells() {
		FlexCell[][] _cells = createFlexCellArray(fls.getCols(), fls.getRows());

		for (int x=0; x<fls.getCols(); x++) {
			for (int y=0; y<fls.getRows(); y++) {
				try {
					_cells[x][y] = cells[x][y];
				} catch(Exception e) {
					FlexCellStructureDTO fcs = fls.getFlexCellStructure(x, y);
					if (fcs == null) {
						fcs = new FlexCellStructureDTO(x, y);
						// the new cell must have the same width (or height) with the one on its left (or top)
						try {
							fcs.setWidth( fls.getFlexCellStructure(x, y-1).getWidth());
						} catch(Exception ee) {}
						try {
							fcs.setHeight( fls.getFlexCellStructure(x-1, y).getHeight());
						} catch(Exception ee) {}
						fls.getFcs().add(fcs);
					}
					_cells[x][y] = createFlexCell(fcs);
				}
			}
		}
		cells = _cells;
		setSizes();
	}
	/**
	 * @param x 
	 * @param y 
	 * @return
	 */
	protected FlexCell[][] createFlexCellArray(int x, int y) {
		return new FlexLayout.FlexCell[x][y];
	}
	/**
	 * @param fcs
	 * @return
	 */
	protected FlexCell createFlexCell(FlexCellStructureDTO fcs) {
		return new FlexCell( fcs );
	}
	/**
	 * Called by <code>FlexLayout.decCols</code> and <code>FlexLayout.decRows</code>.<br>
	 * Removes DIV elements representing PlaceHolders from the DOM.
	 * Since these Place holders no longer exist, their structure is also deleted from Layout structure. 
	 */
	protected void remCells(int newCols,int newRows) {
		FlexCell[][] _cells = createFlexCellArray(newCols, newRows);

		for (int x=0; x<fls.getCols(); x++) {
			for (int y=0; y<fls.getRows(); y++) {
				try {
					_cells[x][y] = cells[x][y];
				} catch(Exception e) {
					remCell(cells[x][y], true);
				}
			}
		}
		fls.setCols(newCols);
		fls.setRows(newRows);
		cells = _cells;
		setSizes();
	}
	/**
	 * Adds the given Cell to the DOM
	 * @param fc
	 * @param andStructure if true cell's structure is also 'stored'
	 */
	protected void addCell(FlexCell fc, boolean andStructure) {
		System.out.println((andStructure?"added ":"visible... ") + fc.fcs.getxPos() + "." + fc.fcs.getyPos());
		if (andStructure) {
			// we first need to delete the structure (if it exists)
			// otherwise we'll have 2 structures for the same cell
			fls.deleteCellStructure(fc.fcs.getxPos(), fc.fcs.getyPos());
			fls.getFcs().add(fc.fcs);
		}
		add(fc);
	}
	public boolean addWidget(Widget w, int x, int y) {
		FlexCell fc = getCell(x, y);
		fc.add( w );
		return false;
	}
	/**
	 * Removes selected Cell from the DOM
	 * @param fc
	 * @param andStructure if true cell's structure is also deleted
	 */
	protected void remCell(FlexCell fc, boolean andStructure) {
		System.out.println((andStructure?"deleted ":"non visible... ") + fc.fcs.getxPos() + "." + fc.fcs.getyPos());
		if (andStructure)
			fls.deleteCellStructure(fc.fcs.getxPos(), fc.fcs.getyPos());
		remove(fc);
	}
	public boolean decCols() {
		if (!canDecCols())
			return false;

		remCells(fls.getCols()-1, fls.getRows());

		return true;
	}
	public boolean decRows() {
		if (!canDecRows())
			return false;

		remCells(fls.getCols(), fls.getRows()-1);

		return true;
	}
	public boolean canDecCols() {
		// if any cell of the last column is part of an expanded cell, action should be prevented
		for (int y=0; y<fls.getRows(); y++) {
			if (!cells[fls.getCols()-1][y].isEnabled())
				return false;
		}
		return true;
	}
	public boolean canDecRows() {
		// if any cell of the last row is part of an expanded cell, action should be prevented
		for (int x=0; x<fls.getCols(); x++) {
			if (!cells[x][fls.getRows()-1].isEnabled())
				return false;
		}
		return true;
	}

	@Override
	public void add(Widget w) {
		add(w, innerDiv);
	}

	@Override
	public boolean remove(Widget w) {
		boolean removed = super.remove(w);
		return removed;
	}

	protected class FlexCell extends ComplexPanel implements MouseOverHandler, MouseOutHandler {

		protected FlexCellStructureDTO fcs;
		protected Element cellDiv;

		protected FlexCell() {
			this(new FlexCellStructureDTO());
		}
		public FlexCell(FlexCellStructureDTO cellStructure) {
			this.fcs = cellStructure;

			cellDiv = DOM.createDiv();

			setElement(cellDiv);
			
			addDomHandler(this, MouseOverEvent.getType());
			addDomHandler(this, MouseOutEvent.getType());
		}
		@Override
		public void add(Widget w) {
			add(w, cellDiv);
		}
		@Override
		public void onAttach() {
			super.onAttach();
			setStyleName("tlz-flexcell"); // cannot be in constructor
			if (showOutlines) {
				addStyleName("tlz-flexcell-border");
			}
		}

		/**
		 * If false is returned it means the cell is part of a spanned cell
		 * and thus it is not visible nor enabled.
		 * @return
		 */
		public boolean isEnabled() {
			if (fcs.getxSpan() == 0 || fcs.getySpan() == 0) {
				return false;
			}
			return true;
		}
		public boolean isSpaned() {
			if ( (fcs.getxSpan() > 1 || fcs.getySpan() > 1)
				&& (fcs.getxSpan() > 0 && fcs.getySpan() > 0) )
				return true;

			return false;
		}
		public boolean isExpRow() {
			if (fcs.getyPos() == fls.getExpRow())
				return true;
			return false;
		}
		public boolean isExpCol() {
			if (fcs.getxPos() == fls.getExpCol())
				return true;
			return false;
		}
		/**
		 * This is the placement/sizing algorithm for the cells.
		 * In order for the full layout to be placed correctly,
		 * it must be called on Cell at position 0x0 (Top Left cell) 
		 * for which we know its position to be 0px, 0px.
		 * <p>
		 * Once the cell is placed, then it calls the same function on the cell to the right and the cell below.
		 * In order to avoid re-placing the same cell, <code>placed</code> ArrayList is used to keep track of them.
		 * </p>
		 * @param top in pexels
		 * @param left in pixels
		 */
		public void place(int top, int left) {
			// if this is the top left placeholder, means the process just started,
			// so clear the array of placed placeholders
			if (fcs.getxPos() == 0 && fcs.getyPos() == 0)
				placed.clear();

			if (fcs.getxSpan() > 0 && fcs.getySpan() > 0) {
				if (placed.contains(fcs.getxPos() +"_"+ fcs.getyPos())) {
					// this means the cell has already been placed in the layout so skip it
					System.out.println(fcs.getxPos() +"_"+ fcs.getyPos() + " already placed!");
				} else {
					DOM.setStyleAttribute(cellDiv, "top", top+"px");
					DOM.setStyleAttribute(cellDiv, "left", left+"px");
		
					DOM.setStyleAttribute(cellDiv, "width", getWidth()+"px");
					DOM.setStyleAttribute(cellDiv, "height", getHeight()+"px");

					addCell(this, false);

					placed.add(fcs.getxPos() +"_"+ fcs.getyPos());

					// now place the cell below and the cell on the right
					if (fcs.getxPos()+fcs.getxSpan() < fls.getCols())
						cells[fcs.getxPos()+fcs.getxSpan()][fcs.getyPos()].place(top, left+getWidth());
					if (fcs.getyPos()+fcs.getySpan() < fls.getRows())
						cells[fcs.getxPos()][fcs.getyPos()+fcs.getySpan()].place(top+getHeight(), left);

					if (showOutlines)
						addStyleName("tlz-flexcell-border");
					else
						removeStyleName("tlz-flexcell-border");
				}
			} else {
				remCell(this, false);
			}
		}
		public int getWidth() {
			// TODO  need to rethink this.
			// Might be relevant to layout not been displayed correctly inside another layout 

			// added to make sure expandable cells have min width
			int eWidth = expWidth;
			if (eWidth < 0)
				eWidth = fcs.getWidth();
				
			int phWidth = 0;

			if (fcs.getxSpan() == 0)
				return 0;
			else {
				for (int i=0; i<fcs.getxSpan(); i++) {
					int fcsWidth = fls.getFlexCellStructure(fcs.getxPos()+i, fcs.getyPos()).getWidth();
					phWidth += (fcs.getxPos()+i != fls.getExpCol() ? fcsWidth : eWidth+fcsWidth);
				}
			}
			return phWidth;
		}
		public int getHeight() {
			// TODO  need to rethink this.
			// Might be relevant to layout not been displayed correctly inside another layout 
			
			// added to make sure expandable cells have min height
			int eHeight = expHeight;
			if (eHeight < 0)
				eHeight = fcs.getHeight();

			int phHeight = 0;

			if (fcs.getySpan() == 0)
				return 0;
			else {
				for (int i=0; i<fcs.getySpan(); i++) {
					int fcsHeight = fls.getFlexCellStructure(fcs.getxPos(), fcs.getyPos()+i).getHeight();
					phHeight += (fcs.getyPos()+i != fls.getExpRow() ? fcsHeight : eHeight+fcsHeight);
				}
			}
			return phHeight;
		}

		public boolean canIncXspan() {
			// if this is the last column OR is the last due to xSpan 
			// then just return false;
			if (fcs.getxPos() + fcs.getxSpan() >= fls.getCols())
				return false;
			// we need verify that cells on the right are NOT spanned or part of a spanned cell!!
			// we need to check ySpan cells on the right
			for (int y=fcs.getyPos(); y<fcs.getyPos()+fcs.getySpan(); y++) {
				if (cells[fcs.getxPos()+fcs.getxSpan()][y].isSpaned()
						|| !cells[fcs.getxPos()+fcs.getxSpan()][y].isEnabled())
					return false;
			}
			return true;
		}
		public boolean canIncYspan() {
			// if this is the last row OR is the last due to ySpan 
			// then just return false;
			if (fcs.getyPos() + fcs.getySpan() >= fls.getRows())
				return false;
			// we need verify that cells below are NOT spanned or part of a spanned cell!!
			// we need to check xSpan cells below
			for (int x=fcs.getxPos(); x<fcs.getxPos()+fcs.getxSpan(); x++) {
				if (cells[x][fcs.getyPos()+fcs.getySpan()].isSpaned()
						|| !cells[x][fcs.getyPos()+fcs.getySpan()].isEnabled())
					return false;
			}
			return true;
		}
		public boolean canDecXspan() {
			if (fcs.getxSpan() > 1 && fcs.getySpan() > 0)
				return true;
			return false;
		}
		public boolean canDecYspan() {
			if (fcs.getySpan() > 1 && fcs.getxSpan() > 0)
				return true;
			return false;
		}

		public boolean incXspan() {
			if (canIncXspan()) {
				for (int y=fcs.getyPos(); y<fcs.getyPos()+fcs.getySpan(); y++) {
					cells[fcs.getxPos()+fcs.getxSpan()][y].fcs.setxSpan(0);
					remCell(cells[fcs.getxPos()+fcs.getxSpan()][y], false);
				}
				fcs.setxSpan(fcs.getxSpan()+1);
				apllyStructure();
				return true;
			}
			return false;
		}
		public boolean incYspan() {
			if (canIncYspan()) {
				for (int x=fcs.getxPos(); x<fcs.getxPos()+fcs.getxSpan(); x++) {
					cells[x][fcs.getyPos()+fcs.getySpan()].fcs.setySpan(0);
					remCell(cells[x][fcs.getyPos()+fcs.getySpan()], false);
				}
				fcs.setySpan(fcs.getySpan()+1);
				apllyStructure();
				
				return true;
			}
			return false;
		}
		public boolean decXspan() {
			if (canDecXspan()) {
				for (int y=fcs.getyPos(); y<fcs.getyPos()+fcs.getySpan(); y++) {
					cells[fcs.getxPos()+fcs.getxSpan()-1][y].fcs.setxSpan(1);
					cells[fcs.getxPos()+fcs.getxSpan()-1][y].fcs.setySpan(1);
					addCell(cells[fcs.getxPos()+fcs.getxSpan()-1][y], true);
				}
				fcs.setxSpan(fcs.getxSpan()-1);
				apllyStructure();

				return true;
			}
			return false;
		}
		public boolean decYspan() {
			if (canDecYspan()) {
				for (int x=fcs.getxPos(); x<fcs.getxPos()+fcs.getxSpan(); x++) {
					cells[x][fcs.getyPos()+fcs.getySpan()-1].fcs.setxSpan(1);
					cells[x][fcs.getyPos()+fcs.getySpan()-1].fcs.setySpan(1);
					addCell(cells[x][fcs.getyPos()+fcs.getySpan()-1], true);
				}
				fcs.setySpan(fcs.getySpan()-1);
				apllyStructure();

				return true;
			}
			return false;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			onMouseOverCell(event);
		}
		@Override
		public void onMouseOut(MouseOutEvent event) {
			onMouseOutCell(event);
		}
	}

	public void onMouseOverCell(MouseOverEvent event) {
		((FlexCell)event.getSource()).addStyleDependentName("hover");
	}
	public void onMouseOutCell(MouseOutEvent event) {
		((FlexCell)event.getSource()).removeStyleDependentName("hover");
	}

	@Override
	public void onResize(ResizeEvent event) {
		Scheduler.get().scheduleDeferred( new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				setSizes();
			}
		});
		
	}
	@Override
	public HandlerRegistration addLayoutChangedHandler(LayoutChangedHandler handler) {
		return addHandler(handler, LayoutChangedEvent.getType());
	}
}
