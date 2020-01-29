package board.data;

public class Via {

	ViaExtent extent;
	double x,y;
	double drill;
	
	public Via(ViaExtent extent, double x, double y, double drill) {
		super();
		this.extent = extent;
		this.x = x;
		this.y = y;
		this.drill = drill;
	}
	public ViaExtent getExtent() {
		return extent;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getDrill() {
		return drill;
	}
}
