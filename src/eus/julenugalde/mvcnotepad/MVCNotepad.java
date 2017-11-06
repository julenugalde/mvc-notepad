package eus.julenugalde.mvcnotepad;

import javax.swing.JOptionPane;

import eus.julenugalde.mvcnotepad.controller.*;
import eus.julenugalde.mvcnotepad.model.*;
import eus.julenugalde.mvcnotepad.view.*;

/** Controller for the MVCNotepad application */
public class MVCNotepad {
	private static Controller controller;
	/** Name of the schema for a database connection; current directory if file is used */
	private static String dataSource;
	
	/** Main function
	 * 
	 * @param args args[0] is the kind of data source to be used: "file" for local computer
	 * file, "db" for database. args[1] specifies the working directory for the files (if empty
	 * the current directory will be used); for a database connection it indicates the schema
	 */
	public static void main(String[] args) {
		if ((args.length) < 1)
			showError("The first parameter must specify the source type to be used");
		else {
			//The model will depend on the first parameter
			if (args[0].equals("file")) {		//Computer file as data source
				if (args.length == 1)	//No data source specified
					dataSource = ".";	//Current directory
				else 
					dataSource = args[1];
				
				controller = new MVCNotepadController(MVCNotepadController.MODEL_TEXT_FILE, 
						MVCNotepadController.SWING_VIEW, dataSource);
			} else if (args[0].equals("db")) {	//Database as data source
				if (dataSource.equals("")) {	//Schema not specified
					showError("The second parameter must specify a database schema name");
				}
				else {	//Load database
					controller = new MVCNotepadController(MVCNotepadController.MODEL_DATABASE,
							MVCNotepadController.SWING_VIEW, args[1]);
				}
					
			} else {
				showError("The value of the first parameter is not valid");
			}
		}
				
	}
		
	private static void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
