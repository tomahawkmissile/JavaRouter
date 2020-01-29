package files.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Element {

	String name;
	Map<String,String> args;
	String uuid;
	List<Element> children = new ArrayList<Element>();
	
	public Element(String name, Map<String,String> args) {
		this.name=name;
		this.args=args;
		this.uuid=UUID.randomUUID().toString();
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setArgs(Map<String,String> args) {
		this.args = args;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setChildren(List<Element> children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public Map<String,String> getArgs() {
		return args;
	}
	public String getUUID() {
		return uuid;
	}
	public List<Element> getChildren() {
		return children;
	}
	public void addChild(Element e) {
		children.add(e);
	}
	public void removeChild(Element e) {
		if(children.contains(e)) {
			children.remove(e);
		}
	}
}
