package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import files.XmlProcessor;
import ui.UI;

public class Main {
	
	public static boolean testmode = false;
	
	public static void stop(int code) {
		//TODO:
		System.out.println("Javarouter exiting with code "+code+".");
	}
	
	public static String path = System.getProperty("user.home")+"/javarouter";
	
	public static void main(String[] args) {
		
		Registry r = new Registry(Registry.MAIN);
		r.finalize();
		new Registry(Registry.ELEMENTS).finalize();
		Registry layerRegistry = new Registry(Registry.LAYERS);
		new Registry(Registry.VIAS).finalize();
		new Registry(Registry.PADS).finalize();
		new Registry(Registry.SIGNALS).finalize();
		new Registry(Registry.SMDS).finalize();
		new Registry(Registry.WIRES).finalize();
		layerRegistry.finalize();
		
		r.addRegister("system.cores", Runtime.getRuntime().availableProcessors());
		r.addRegister("system.memory", Runtime.getRuntime().freeMemory());
		r.addRegister("system.os.name", System.getProperty("os.name"));
		
		/*
		Arguments:
		-c/--config <file> : config file to load for parameters. Bypasses other arguments. Default file is config.yml in jar directory.
		-r/--rules <file> : input properties file for trace requirements. Without it guess all requirements based off names.
		-i/--input <file> : input EAGLE board file.
		-o/--output <file> : output EAGLE board file.
		--threads <number> : number of threads to use. Default to use all.
		-g/--gui : enable GUI. Default off.
		*/
		
		Main.log(args.length+" arguments given to main.",LogSeverity.INFO);
		
		File cfg = null;
		File rules = null;
		File input = null;
		File output = null;
		int threads = 2;
		boolean gui = false;
		for(String s:args) {
			if(s.equalsIgnoreCase("--testmode")) {
				Main.log("Test mode activated.", LogSeverity.DEBUG);
				testmode=true;
			}
		}
		for(int i=0;i<args.length;i++) {
			if(args[i].equalsIgnoreCase("-c") || args[i].equalsIgnoreCase("--config")) {
				if(args[++i]!=null) {
					cfg=new File(args[i]);
					if(!cfg.exists() && !testmode) {
						Main.log("Config is not a valid file!",LogSeverity.ERROR);
						System.exit(-1);
					}
				}
			} else if(args[i].equalsIgnoreCase("-r") || args[i].equalsIgnoreCase("--rules")) {
				if(args[++i]!=null) {
					rules=new File(args[i]);
					if(!rules.exists() && !testmode) {
						Main.log("Rules file is not a valid file!",LogSeverity.ERROR);
						System.exit(-1);
					}
				}
			} else if(args[i].equalsIgnoreCase("-i") || args[i].equalsIgnoreCase("--input")) {
				if(args[++i]!=null) {
					input=new File(args[i]);
					if(!input.exists() && !testmode) {
						Main.log("Input file is not a valid file!",LogSeverity.ERROR);
						System.exit(-1);
					}
				}
			} else if(args[i].equalsIgnoreCase("-o") || args[i].equalsIgnoreCase("--output")) {
				if(args[++i]!=null) {
					output=new File(args[i]);
				}
			} else if(args[i].equalsIgnoreCase("--threads")) {
				if(args[++i]!=null) {
					try {
						threads=Integer.parseInt(args[i]);
					} catch(NumberFormatException e) {
						Main.log("Threads flag is not a valid integer! Defaulting to 2.",LogSeverity.WARN);
						threads=2;
					}
				}
			} else if(args[i].equalsIgnoreCase("-g") || args[i].equalsIgnoreCase("--gui")) {
				if(args[++i]!=null) {
					try {
						gui=Boolean.parseBoolean(args[i]);
					} catch(Exception e) {
						Main.log("GUI flag is not a valid boolean. Defaulting to false/off.",LogSeverity.WARN);
						gui=false;
					}
				}
			}
		}
		//TODO: work with config.properties
		r.addRegister("rules file", rules);
		r.addRegister("input file", input);
		r.addRegister("output file", output);
		r.addRegister("max threads", threads);
		if(gui&&!testmode) {
			Main.log("GUI active.", LogSeverity.INFO);
			UI.initialize(args);
		}
		FileManager.createFolder(path);
		FileManager.createFile(path+"/launchinfo");
		
		Main.log("Main initialization complete.", LogSeverity.INFO);
		
		try {
			XmlProcessor x = new XmlProcessor(new File("B:\\javarouter_files\\AM6548.brd"));
			Map<String,String> map = new HashMap<>();
			map.put("NUMBER","1");
			map.put("name","tOP");
			map.put("color","4");
			map.put("fill","9999");
			map.put("visible","no");
			map.put("active","yes");
			x.writeElement("epic.gamer.minecraft", "what", map);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	public void sendHelp() {
		//TODO: send help message
	}
	public static void log(String message,LogSeverity severity) {
		System.out.println("[Javarouter] ["+severity.toString().toUpperCase()+"] "+message);
	}
	public enum LogSeverity {
		INFO,WARN,ERROR,SEVERE,DEBUG;
	}
}