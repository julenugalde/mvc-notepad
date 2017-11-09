package eus.julenugalde.mvcnotepad.model;

public class NetworkSource implements TextSourceModel {
	@SuppressWarnings("unused")
	private String sourceLocation;
	
	public NetworkSource (String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	@Override
	public String readSource(Source source) {
		// TODO Network model implementation
		return null;
	}

	@Override
	public boolean writeSource(Source source, String content) {
		// TODO Network model implementation
		return false;
	}

	@Override
	public boolean existsSource(Source source) {
		// TODO Network model implementation
		return false;
	}

	@Override
	public boolean createSource(Source source) {
		// TODO Network model implementation
		return false;
	}

	@Override
	public void closeSource() {
		// TODO Network model implementation
		
	}

	@Override
	public boolean openSource(String sourceLocation) {
		// TODO Network model implementation
		return false;
	}

	@Override
	public String[] listSources(String sourceLocation) {
		// TODO Network model implementation
		return null;
	}
}
