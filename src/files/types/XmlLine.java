package files.types;

import java.util.Map;

public class XmlLine {

	public enum XmlLineType {
		OPENING,CLOSING,BOTH,DOCTYPE,VERSION;
	}
	
	XmlLineType type;
	String tag;
	Map<String,String> attributes;
	
	public XmlLine(XmlLineType type, String tag, Map<String, String> attributes) {
		super();
		this.type = type;
		this.tag = tag;
		this.attributes = attributes;
	}
	public XmlLineType getType() {
		return type;
	}
	public String getTag() {
		return tag;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
}
