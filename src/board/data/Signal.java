package board.data;

import java.util.List;

public class Signal {

	String name;
	int netclass;
	boolean airwireshidden;
	public Signal(String name, int netclass, boolean airwireshidden) {
		super();
		this.name = name;
		this.netclass = netclass;
		this.airwireshidden = airwireshidden;
	}
	public String getName() {
		return name;
	}
	public int getNetclass() {
		return netclass;
	}
	public boolean isAirwireshidden() {
		return airwireshidden;
	}
	List<Wire> wires;
	
}
