package files.types;

import java.util.ArrayList;
import java.util.List;

public class XmlPath {

	public XmlPath() {}
	
	List<Element> path = new ArrayList<Element>();
	
	public void add(Element e) {
		path.add(e);
	}
	public void removeLast() {
		path.remove(path.get(path.size()-1));
	}
	public List<Element> getPath() {
		return path;
	}
}
