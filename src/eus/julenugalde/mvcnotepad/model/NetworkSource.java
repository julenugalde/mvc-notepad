package eus.julenugalde.mvcnotepad.model;

public class NetworkSource implements TextSourceModel {
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

}
