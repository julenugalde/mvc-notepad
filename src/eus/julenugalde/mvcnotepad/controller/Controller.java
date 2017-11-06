package eus.julenugalde.mvcnotepad.controller;

public interface Controller {
	/** The text will be stored in a text file in the user's device */
	public static final int MODEL_TEXT_FILE = 1;
	/** The text will be stored in a database */
	public static final int MODEL_DATABASE = 2;
	/** The text will be stored in a network device */
	public static final int MODEL_NETWORK = 3;
	
	/** The application will be displayed as a Swing GUI */
	public static final int SWING_VIEW = 11;
	
	/** Loads the specified model
	 * 
	 * @param modelType Model type to be used: MODEL_TEXT_FILE, MODEL_DATABASE or MODEL_NETWORK.
	 * @param source Model's data source
	 */
	public void loadModel(int modelType, String source);
	
	/** Loads the specified view
	 * 
	 * @param viewType View type to be used, currently only SWING_VIEW implemented.
	 */
	public void initializeView (int viewType);
}
