package eus.julenugalde.mvcnotepad.model;

public class TextFileSource implements TextSourceModel {
	private String workingDirectory;
	private String currentFile;
	
	public TextFileSource(String directory) {
		workingDirectory = directory;
	}
	
	@Override
	public boolean loadSource(String sourceName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String readSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listSources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeSource(SourceModifier modifier) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createSource(String sourceName) {
		// TODO Auto-generated method stub
		return false;
	}

}
