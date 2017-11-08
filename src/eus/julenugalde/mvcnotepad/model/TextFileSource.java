package eus.julenugalde.mvcnotepad.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

//import eus.julenugalde.mvcnotepad.controller.Controller;

public class TextFileSource implements TextSourceModel {
	//private Controller controller;
	
	public TextFileSource(/*Controller controller*/) {
		//this.controller = controller;
		//No need to be done anything in this case. If the directory does not exist or
		//is not valid, it will be addressed during file read/write operations
	}

	@Override
	public boolean writeSource(String sourceLocation, String sourceName, String content) {
		File file = new File (sourceLocation, sourceName);
		try {
			FileWriter fw = new FileWriter(file);
			StringTokenizer st = new StringTokenizer(content, "\n", true);
			while (st.hasMoreTokens()) {
				fw.write(st.nextToken());
				fw.write(System.getProperty("line.separator"));
			}
			//fw.write(content);
			fw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean createSource(String sourceLocation, String sourceName) {
		File file = new File(sourceLocation, sourceName);
		try {
			return file.createNewFile();
		} catch (IOException ioex) {
			return false;
		}
	}

	@Override
	public String readSource(String sourceLocation, String sourceName) {
		try {
			File file = new File(sourceLocation + java.io.File.separator + sourceName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
            while((line = br.readLine()) != null) {
            	sb.append(line);
            }
            br.close();
            return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean existsSource(String sourceLocation, String sourceName) {
		File file = new File (sourceLocation, sourceName);
		return (file.exists());
	}

	@Override
	public void closeSource() {
		// Nothing needs to be done
	}

	@Override
	public boolean openSource(String sourceLocation) {
		File dir = new File(sourceLocation);
		return (dir.exists() & dir.isDirectory());
	}

	@Override
	public String[] listSources(String sourceLocation) {
		File directory = new File(sourceLocation);
		if (!directory.exists() | !directory.isDirectory()) return null;
		
		File[] files = directory.listFiles();
		if (files == null) return null;
		
		String[] sources = new String[files.length];
		for (int i=0; i<files.length; i++) {
			sources[i] = new String(files[i].getName());
		}		
		return sources;
	}
}
