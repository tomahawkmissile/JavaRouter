package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {

	public static void createFolder(String path) {
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
	}
	public static void createFile(String path) {
		File f = new File(path);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void delete(String path) {
		File f = new File(path);
		if(f.exists()) {
			f.delete();
		}
	}
	public static void refreshFile(String path) {
		FileManager.delete(path);
		FileManager.createFile(path);
	}
	public static void writeLine(String path, String line, boolean append) throws IOException {
		try {
			FileWriter fw = new FileWriter(path,append);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			out.write(line);
			out.close();
			bw.close();
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeLines(String path, String[] lines, boolean append) throws IOException {
		try {
			FileWriter fw = new FileWriter(path,append);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			for(String s:lines) {
				out.println(s);
			}
			out.close();
			bw.close();
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
