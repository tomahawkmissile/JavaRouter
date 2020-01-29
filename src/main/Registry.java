package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registry {

	public static final String MAIN = "main";
	public static final String ELEMENTS = "elements";
	public static final String LAYERS = "layers";
	public static final String VIAS = "vias";
	public static final String WIRES = "wires";
	public static final String SIGNALS = "signals";
	public static final String PADS = "pads";
	public static final String SMDS = "smds";
	
	private static List<Registry> subs = new ArrayList<Registry>();
	
	String name;

	public Registry(String name) {
		this.name=name;
	}
	public synchronized void finalize() {
		subs.add(this);
	}
	public synchronized static Registry getByName(String name) {
		for(Registry r:subs) {
			if(r.getName()==name) {
				return r;
			}
		}
		return null;
	}
	public synchronized static Registry getRegistry(String name) {
		if(subs.contains(Registry.getByName(name))) {
			return Registry.getByName(name);
		}
		return null;
	}
	
	Map<Object,Object> registry = new HashMap<Object,Object>();
	
	public String getName() {
		return name;
	}
	public synchronized void addRegister(Object key,Object value) {
		registry.putIfAbsent(key, value);
	}
	public synchronized void remRegister(String title) {
		registry.remove(title);
	}
	public synchronized Object getRegister(Object title) {
		if(registry.containsKey(title)) {
			return registry.get(title);
		}
		return null;
	}
	public synchronized Object[] listAllKeys() {
		return registry.keySet().toArray();
	}
	public synchronized Object[] listAllValues() {
		return registry.values().toArray();
	}
	public synchronized boolean hasRegister(Object key) {
		if(this.getRegister(key)!=null) {
			return true;
		}
		return false;
	}
}
