package eus.julenugalde.mvcnotepad.model;

public class NetworkSource implements TextSourceModel {
	@SuppressWarnings("unused")
	private String sourceLocation;
	
	public NetworkSource (String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	@Override
	public String readSource(String sourceLocation, String sourceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeSource(String sourceLocation, String sourceName, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsSource(String sourceLocation, String sourceName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createSource(String sourceLocation, String sourceName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeSource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean openSource(String sourceLocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] listSources(String sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

}
