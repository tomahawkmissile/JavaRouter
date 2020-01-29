package board;

import java.util.List;

import board.data.Wire;

public class Board {
	
	List<Wire> dimensions;
	int grid;
	String unit;

	public Board() {}
	
	public void setDimensions(List<Wire> frame) {
		this.dimensions=frame;
	}
	public void setGrid(int grid) {
		this.grid=grid;
	}
	public void setUnit(String unitName) {
		unit=unitName;
	}
}
