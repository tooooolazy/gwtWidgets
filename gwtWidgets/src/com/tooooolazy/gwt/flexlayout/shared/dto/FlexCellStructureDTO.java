package com.tooooolazy.gwt.flexlayout.shared.dto;

public class FlexCellStructureDTO {
	protected int xPos, yPos;
	protected int xSpan, ySpan;
	protected int width, height;
	protected int maxMenuModes;

	public FlexCellStructureDTO() {
		xSpan = 1;
		ySpan = 1;
		width = 60;
		height = 60;
		maxMenuModes = 2;
	}
	public FlexCellStructureDTO(int xPos, int yPos) {
		this();
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public int getxSpan() {
		return xSpan;
	}
	public void setxSpan(int xSpan) {
		this.xSpan = xSpan;
	}
	public int getySpan() {
		return ySpan;
	}
	public void setySpan(int ySpan) {
		this.ySpan = ySpan;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getMaxMenuModes() {
		return maxMenuModes;
	}
	public void setMaxMenuModes(int maxMenuModes) {
		this.maxMenuModes = maxMenuModes;
	}
}
