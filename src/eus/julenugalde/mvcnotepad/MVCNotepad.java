package eus.julenugalde.mvcnotepad;

import javax.swing.JOptionPane;

import eus.julenugalde.mvcnotepad.controller.*;

/** Controller for the MVCNotepad application */
public class MVCNotepad {
	@SuppressWarnings("unused")
	private static Controller controller;
	
	/** Main function
	 * 
	 * @param args <code>args[0]</code> is the kind of data source to be used: "file" for local 
	 * text file, "db" for database, "network" for network connection. 
	 * <code>args[1]</code> specifies the working directory for the files (if empty
	 * the current directory will be used); for a database connection it indicates the schema; for
	 * a network connection indicates the location of the resource.
	 */
	public static void main(String[] args) {
		if ((args.length) < 1)
			showError("The first parameter must specify the source type to be used");
		else {
			//The model will depend on the first parameter
			if (args[0].equals("file")) {		//Computer file as data source
				if (args.length == 1) {	//No data source specified
					controller = new MVCNotepadController(MVCNotepadController.MODEL_TEXT_FILE, 
							MVCNotepadController.SWING_VIEW, ".");
				}
				else { 
					controller = new MVCNotepadController(MVCNotepadController.MODEL_TEXT_FILE, 
							MVCNotepadController.SWING_VIEW, args[1]);
				}
				
			} 
			
			else if (args[0].equals("db")) {	//Database as data source
				if (args.length < 2) {
					showError("The second parameter must specify a database schema name");
				}				
				else if (args[1].equals("")) {	//Schema not specified
					showError("The second parameter must specify a database schema name");
				}
				else {	//Load database
					controller = new MVCNotepadController(MVCNotepadController.MODEL_DATABASE,
							MVCNotepadController.SWING_VIEW, args[1]);
				}					
			} 
			
			else if (args[0].equals("network")) {	//Network connection
				if (args.length < 2) {
					showError("The second parameter must specify a database schema name");
				}
				else if (args[1].equals("")) {
					showError("The second parameter must specify a network location");
				}
				else {
					controller = new MVCNotepadController(MVCNotepadController.MODEL_NETWORK,
							MVCNotepadController.SWING_VIEW, args[1]);
				}
			}
			
			else {
				showError("The value of the first parameter is not valid.\n" + 
						"Available values are 'file', 'db', 'network'");
			}
		}			
	}
		
	private static void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}	
}
