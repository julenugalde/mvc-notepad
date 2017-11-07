package eus.julenugalde.mvcnotepad.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextFileSource implements TextSourceModel {
	public TextFileSource(String directory) {
		//No need to be done anything in this case. If the directory does not exist or
		//is not valid, it will be addressed during file read/write operations
	}
	
	@Override
	public String listSources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeSource(String sourceName, String elementName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createSource(String sourceName, String elementName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String readSource(String sourceName, String elementName) {
		try {
			File file = new File(sourceName + java.io.File.separator + elementName);
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

}
