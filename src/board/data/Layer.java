package board.data;

public class Layer {

	int number;
	String name;
	int color;
	int fill;
	boolean visible;
	boolean active;
	
	public Layer(int number, String name, int color, int fill, boolean visible, boolean active) {
		super();
		this.number = number;
		this.name = name;
		this.color = color;
		this.fill = fill;
		this.visible = visible;
		this.active = active;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getFill() {
		return fill;
	}
	public void setFill(int fill) {
		this.fill = fill;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
