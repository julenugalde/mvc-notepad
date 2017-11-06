package eus.julenugalde.mvcnotepad.controller;

public interface Controller {
	//Model types
	public static final int MODEL_TEXT_FILE = 1;
	public static final int MODEL_DATABASE = 2;
	public static final int MODEL_NETWORK = 3;
	
	//View types
	public static final int SWING_VIEW = 11;
	
	public void loadModel();
	public void initializeView ();
}
