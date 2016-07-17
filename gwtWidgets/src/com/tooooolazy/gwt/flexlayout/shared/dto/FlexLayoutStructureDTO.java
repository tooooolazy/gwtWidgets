package com.tooooolazy.gwt.flexlayout.shared.dto;

import java.util.ArrayList;
import java.util.Iterator;

public class FlexLayoutStructureDTO {
	protected int cols, rows;
	protected int colWidth, rowHeight;
	protected int xBorder, yBorder;
	// maximum one column and one row can be expandable
	protected int expCol, expRow;
	protected ArrayList<FlexCellStructureDTO> fcs;
	protected int maxMenuModes;

	public FlexLayoutStructureDTO() {
		cols = 4;
		rows = 3;
		colWidth = 60;
		rowHeight = 60;
		xBorder = 10;
		yBorder = 10;
		expCol = 1;
		expRow = 1;
		maxMenuModes = 2;
		fcs = new ArrayList<FlexCellStructureDTO>();
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColWidth() {
		return colWidth;
	}
	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}
	public int getRowHeight() {
		return rowHeight;
	}
	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}
	public int getxBorder() {
		return xBorder;
	}
	public void setxBorder(int xBorder) {
		this.xBorder = xBorder;
	}
	public int getyBorder() {
		return yBorder;
	}
	public void setyBorder(int yBorder) {
		this.yBorder = yBorder;
	}
	public int getExpCol() {
		return expCol;
	}
	public void setExpCol(int expCol) {
		this.expCol = expCol;
	}
	public int getExpRow() {
		return expRow;
	}
	public void setExpRow(int expRow) {
		this.expRow = expRow;
	}
	public ArrayList<FlexCellStructureDTO> getFcs() {
		return fcs;
	}
	public void setFcs(ArrayList<FlexCellStructureDTO> fcs) {
		this.fcs = fcs;
	}
	public FlexCellStructureDTO getFlexCellStructure(int x, int y) {
		if (fcs == null)
			return null;

		Iterator<FlexCellStructureDTO> it = fcs.iterator();
		while (it.hasNext()) {
			FlexCellStructureDTO dto = it.next();
			if (dto.getxPos() == x && dto.getyPos() == y)
				return dto;
		}
		return null;
	}
	public void deleteCellStructure(int x, int y) {
		if (fcs == null)
			return;

		fcs.remove(getFlexCellStructure(x,y));
	}
	public int getMaxMenuModes() {
		return maxMenuModes;
	}
	public void setMaxMenuModes(int maxMenuModes) {
		this.maxMenuModes = maxMenuModes;
	}

}
