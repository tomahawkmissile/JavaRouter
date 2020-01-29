package board.data;

public class Wire {

	double x1,x2,y1,y2;
	double width;
	Layer layer;
	double curve;
	
	public Wire(double x1, double x2, double y1, double y2, double width, Layer layer, double curve) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.width = width;
		this.layer = layer;
		this.curve = curve;
	}
	public double getX1() {
		return x1;
	}
	public double getX2() {
		return x2;
	}
	public double getY1() {
		return y1;
	}
	public double getY2() {
		return y2;
	}
	public double getWidth() {
		return width;
	}
	public Layer getLayer() {
		return layer;
	}
	public double getCurve() {
		return curve;
	}
}
