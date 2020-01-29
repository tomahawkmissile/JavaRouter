package files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import board.Board;
import board.data.Layer;
import board.data.Wire;
import main.FileManager;
import main.Registry;

public class XmlProcessor {
	
	NodeList elements;
	
	File file;
	
	public XmlProcessor(File file) throws ParserConfigurationException, SAXException, IOException {
		this.file=file;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(file);
		Element root = document.getDocumentElement();
		
		elements = root.getChildNodes();
	}
	public NodeList extractSection(String path) {
		String[] split = path.split(Pattern.quote("."));
		if(path==null||split.length==0) {
			return null;
		}
		NodeList current = elements;
		for(String s:split) {
			for(int i=0;i<current.getLength();i++) {
				Node n = current.item(i);
				if(n.getNodeType()==Node.ELEMENT_NODE) {
					if(n.hasChildNodes()) {
						if(n.getNodeName().equals(split[split.length-1])) {
							return n.getChildNodes();
						} else if(n.getNodeName().equals(s)) {
							current = n.getChildNodes();
						}
					}
				}
			}
		}
		return null;
	}
	public List<Element> getElements(String path) {
		List<Element> returns = new ArrayList<Element>();
		NodeList list = this.extractSection(path);
		if(list==null) {
			return null;
		}
		for(int i=0;i<list.getLength();i++) {
			if(list.item(i).getNodeType()==Node.ELEMENT_NODE) {
				Element e = (Element) list.item(i);
				returns.add(e);
			}
		}
		return returns;
	}
	public Element getElement(String path) {
		String[] split = path.split(Pattern.quote("."));
		if(path==null||split.length==0) {
			return null;
		}
		NodeList current = elements;
		for(int j=0;j<split.length;j++) {
			for(int i=0;i<current.getLength();i++) {
				Node n = current.item(i);
				if(n.getNodeType()==Node.ELEMENT_NODE) {
					if(n.getNodeName().equals(split[j])) {
						if(j==split.length-1) {
							Element e = (Element) n;
							return e;
						} else {
							if(n.hasChildNodes()) {
								current=n.getChildNodes();
							}
						}
					}
				}
			}
		}
		return null;
	}
	public void loadRegister(Registry r,Object key,Object value) {
		r.addRegister(key, value);
	}
	public void loadLayers() {
		List<Element> list = this.getElements("drawing.layers");
		for(Element e:list) {
			try {
				int number = Integer.parseInt(e.getAttribute("number"));
				String name = e.getAttribute("name");
				int color = Integer.parseInt(e.getAttribute("color"));
				int fill = Integer.parseInt(e.getAttribute("fill"));
				boolean visible = e.getAttribute("visible").equals("yes")?true:false;
				boolean active = e.getAttribute("active").equals("yes")?true:false;
				Layer l = new Layer(number,name,color,fill,visible,active);
				this.loadRegister(Registry.getByName(Registry.LAYERS), l.getNumber(), l);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void loadBoardDimensions() {
		Element grid = this.getElement("drawing.grid");
		List<Element> outline = this.getElements("drawing.board.plain");
		Board b = new Board();
		b.setGrid(Integer.parseInt(grid.getAttribute("distance")));
		b.setUnit(grid.getAttribute("unit"));
		List<Wire> wireOutline = new ArrayList<Wire>();
		for(Element e:outline) {
			if(e.getNodeName().equals("wire")) {
				double x1=Double.parseDouble(e.getAttribute("x1"));
				double y1=Double.parseDouble(e.getAttribute("y1"));
				double x2=Double.parseDouble(e.getAttribute("x2"));
				double y2=Double.parseDouble(e.getAttribute("y2"));
				double width=Double.parseDouble(e.getAttribute("width"));
				Layer layer = (Layer) Registry.getByName(Registry.LAYERS).getRegister(20);
				wireOutline.add(new Wire(x1,x2,y1,y2,width,layer,0));
			}
		}
		b.setDimensions(wireOutline);
	}
	public void loadAll() {
		this.loadLayers();
		this.loadBoardDimensions();
	}
	public void run() {
		this.loadAll();
		
	}
	
	public Element matchElement(String path,String name,Map<String,String> args) {
		for(Element e:this.getElements(path)) {				
			if(!e.hasAttributes() && args.isEmpty()) {
				if(e.getNodeName().equalsIgnoreCase(name)) {
					return e;
				}
			} else if(e.hasAttributes()) {
				List<Boolean> test = new ArrayList<Boolean>();
				for(String key:args.keySet()) {
					String value = args.get(key);
					if(e.getAttribute(key.toLowerCase()).equalsIgnoreCase(value)) {
						test.add(true);
					} else {
						test.add(false);
					}
				}
				if(!test.contains(false)) {
					return e;
				}
			}
		}
		return null;
	}
	/*
	private int ordinalIndexOf(String str, String substr, int n) {
	    int pos = str.indexOf(substr);
	    while (--n > 0 && pos != -1)
	        pos = str.indexOf(substr, pos + 1);
	    return pos;
	}
	public void writeElement(String path,files.types.Element e) {
		try {
			StringWriter sw = new StringWriter();
			XMLOutputFactory xF = XMLOutputFactory.newInstance();
			XMLStreamWriter xW = xF.createXMLStreamWriter(sw);
			xW.writeStartDocument();
			String[] split = path.split(Pattern.quote("."));
			int starts=0;
			for(int i=0;i<split.length;i++) {
				String p2=path.substring(0, this.ordinalIndexOf(path, ".", i));
				if(this.getElement(p2)==null) {
					xW.writeStartElement(split[i]);
					starts++;
				}
			}
			if(this.getElement(path)==null) {
				xW.writeStartElement(e.getName());
				Map<String,String> args = e.getArgs();
				for(String key:args.keySet()) {
					String value = args.get(key);
					xW.writeAttribute(key, value);
				}
				xW.writeEndElement();
			}
			for(int i=0;i<starts;i++) {
				xW.writeEndElement();
			}
			String xmlString = sw.getBuffer().toString();
			sw.close();
			FileManager.writeLine(Main.path+"/output.xml",xmlString, false);
		} catch(XMLStreamException | IOException e1) {
			e1.printStackTrace();
		}
	}
	*/
	private boolean elementHasAttributes(org.jdom2.Element e,Map<String,String> attr) {
		List<Attribute> copy = e.getAttributes();
		for(String key:attr.keySet()) {
			String value=attr.get(key);
			for(Attribute at:copy) {
				if(at.getName().equalsIgnoreCase(key) && at.getValue().equalsIgnoreCase(value)) {
					copy.remove(at);
				}
			}
		}
		if(copy.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	private boolean elementContainsElement(org.jdom2.Element a,org.jdom2.Element b) {
		if(!a.getChildren().isEmpty()) {
			for(org.jdom2.Element sub:a.getChildren()) {
				if(sub.getName().equals(b.getName())) {
					List<Attribute> copy = sub.getAttributes();
					for(Attribute attr:b.getAttributes()) {
						if(copy.contains(attr)) {
							copy.remove(attr);
						}
					}
					if(copy.isEmpty()) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}
	private List<org.jdom2.Element> getChildrenAtPath(org.jdom2.Element root,String path) {
		String[] split = path.split(Pattern.quote("."));
		org.jdom2.Element current = root;
		for(int i=0;i<split.length;i++) {
			for(org.jdom2.Element child:current.getChildren()) {
				if(child.getName().equalsIgnoreCase(split[i])) {
					current=child;
					if(!child.getChildren().isEmpty() && i==split.length-1) {
						return child.getChildren();
					}
				}
			}
		}
		return null;
	}
	public void writeElement(String path,String name,Map<String,String> attr) {
		File output = new File("B:\\javarouter_files\\am6548_copy.brd");
		FileManager.createFile(output.getAbsolutePath());
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			org.jdom2.Document d = saxBuilder.build(output);
			org.jdom2.Element root = d.getRootElement();
			List<org.jdom2.Element> elements = this.getChildrenAtPath(root, path);
			for(org.jdom2.Element e:elements) {
				if(e.getName().equalsIgnoreCase(name)) {
					if(this.elementHasAttributes(e, attr)) {
						return;
					} else {
						e.setAttributes(null);
						for(String key:attr.keySet()) {
							String value=attr.get(key);
							e.setAttribute(key,value);
							return;
						}
					}
				}
			}
			String[] str = path.split(Pattern.quote("."));
			org.jdom2.Element current = root;
			for(int i=0;i<str.length;i++) {
				org.jdom2.Element e = new org.jdom2.Element(str[i]);
				if(!this.elementContainsElement(current, e)) {
					current.addContent(e);
				}
				current=e;
			}
			org.jdom2.Element newElement = new org.jdom2.Element(name);
			for(String key:attr.keySet()) {
				String value=attr.get(key);
				newElement.setAttribute(key,value);
			}
			
			current.addContent(newElement);
				
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			FileOutputStream s = new FileOutputStream(output);
			xmlOutput.output(d, s);
			s.close();
		} catch(JDOMException | IOException e1) {
			e1.printStackTrace();
		}
	}
}
