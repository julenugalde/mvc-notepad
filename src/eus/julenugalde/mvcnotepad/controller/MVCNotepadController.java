package eus.julenugalde.mvcnotepad.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import eus.julenugalde.mvcnotepad.model.TextFileSource;
import eus.julenugalde.mvcnotepad.model.TextSourceModel;
import eus.julenugalde.mvcnotepad.view.SwingView;
import eus.julenugalde.mvcnotepad.view.TextView;

public class MVCNotepadController 
implements Controller, ActionListener, KeyListener, WindowListener {
	private TextSourceModel model;
	private TextView view;
	
	private boolean textChanged;
	private Clipboard clipboard;
	private String fileName;
	private String source;
	private boolean previouslySaved;
	
	/** Controller constructor
	 * 
	 * @param modelType The type of model to be used. 
	 * Options: MODEL_TEXT_FILE, MODEL_DATABASE, MODEL_NETWORK
	 * @param viewType The type of view to be displayed. Options: SWING_VIEW
	 * @param source Source for the model. It can be the working directory path for 
	 * MODEL_TEXT_FILE, the database schema for MODEL_DATABASE or (NOT IMPLEMENTED FOR NETWORK).
	 */
	public MVCNotepadController(int modelType, int viewType, String source) {
		loadModel(modelType, source);
		initializeView(viewType);
		
		this.source = source;
		textChanged = false;
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		fileName = null;
		previouslySaved = false;
	}

	@Override
	public void loadModel(int modelType, String source) {
		switch (modelType) {
		case MODEL_TEXT_FILE:
			model = new TextFileSource(source);
			break;
		case MODEL_DATABASE:
			//TODO Pendiente implementar
			System.err.println("Error: a�n no implementado");
			break;
		case MODEL_NETWORK:
			//TODO Pendiente implementar
			System.err.println("Error: a�n no implementado");
			break;
		}
	}

	@Override
	public void initializeView(int viewType) {
		switch (viewType) {
		case SWING_VIEW:
			view = new SwingView(this);
			break;
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		confirmExit();
	}

	private void confirmExit() {
		if (textChanged) {
			switch(view.showPrompt("Exit without saving", 
					"There are unsaved data. Would you like to save before exiting?")) {
			case TextView.YES_OPTION:	//Data must be saved
				if (previouslySaved) {
					saveData(Controller.EXISTING_DATA_SOURCE);
				}
				else {
					saveData(Controller.NEW_DATA_SOURCE);
				}
				break;
			case TextView.NO_OPTION:
				view.closeView();
				break;
			case TextView.CANCEL_OPTION:	//User cancelled. Do nothing
				break;
			}			
		}
		else {	//No unsaved data. Exit without warning
			view.closeView();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {
		textChanged = true;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Action.FILE_OPEN.getCommand())) {
			view.setStatus("file open");
			String text = loadData();
			if (text != null) {
				view.displayText(text);
			}
		} 
		else if (e.getActionCommand().equals(Action.FILE_NEW.getCommand())) {
			view.setStatus("file new");
			if (textChanged) {
				switch(view.showPrompt("Unsaved data", 
						"The current text will be lost. Would you like to save it?")) {
				case TextView.YES_OPTION:	//Data must be saved
					//TODO save data
					break;
				case TextView.NO_OPTION:
					view.deleteText();
					break;
				case TextView.CANCEL_OPTION:	//User cancelled. Do nothing
					break;
				}			
			}
			else {	//No unsaved data. Delete text without warning
				view.deleteText();
			}
		}
		else if (e.getActionCommand().equals(Action.FILE_SAVE.getCommand())) {
			view.setStatus("file save");
			if (textChanged) {	//there are changes to save
				if (saveData(Controller.EXISTING_DATA_SOURCE)) {
					textChanged = false;
				}
			}
		}
		else if (e.getActionCommand().equals(Action.FILE_SAVE_AS.getCommand())) {
			view.setStatus("file save as");
			if (saveData(Controller.NEW_DATA_SOURCE)) {
				textChanged = false;
			}
		}
		else if (e.getActionCommand().equals(Action.FILE_EXIT.getCommand())) {
			confirmExit();
		}
		else if (e.getActionCommand().equals(Action.EDIT_COPY.getCommand())) {
			view.setStatus("edit copy");
			StringSelection selection = new StringSelection(view.copyText());
			clipboard.setContents(selection, selection);			
		}
		else if (e.getActionCommand().equals(Action.CHANGE_FONT_NAME.getCommand())) {
			view.setStatus("font name");
			view.updateFontName();
		}
		else if (e.getActionCommand().equals(Action.CHANGE_FONT_SIZE.getCommand())) {
			view.setStatus("font size");
			view.updateFontSize();
		}
		else if (e.getActionCommand().equals(Action.EDIT_CUT.getCommand())) {
			view.setStatus("edit cut");
			StringSelection selection = new StringSelection(view.cutText());
			clipboard.setContents(selection, selection);
		}
		else if (e.getActionCommand().equals(Action.EDIT_PASTE.getCommand())) {
			view.setStatus("edit paste");
			if (clipboard != null) {
				Transferable t = clipboard.getContents(null);
				try {
					if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						String text = (String)t.getTransferData(DataFlavor.stringFlavor);
						view.pasteText(text);
					}
					else {
						view.showError("Error pasting", "Operation not supported");
					}
				} catch (UnsupportedFlavorException ufex) {
					view.showError("Error pasting", "Operation not supported");
				} catch (IOException ioex) {
					view.showError("Error pasting", "Input/output error");
				}				
			}
		}
		else if (e.getActionCommand().equals(Action.EDIT_FIND.getCommand())) {
			view.setStatus("edit find");
			//first we check if there's anything in the clipboard
			String text = "";
			if (clipboard != null) {
				Transferable t = clipboard.getContents(null);
				try {
					if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						text = (String)t.getTransferData(DataFlavor.stringFlavor);						
					}					
				} 
				catch (UnsupportedFlavorException ufex) {} 
				catch (IOException ioex) {}				
			}
			
			//A prompt is opened to search for a String
			String searchString = view.showTextSearchDialog(text);
			if (searchString == null) {
				view.showPopupMessage("Cancelled", "Search operation cancelled");
			}
			else if (searchString.equals("")) {
				view.showError("Error", "The string to be search can't be empty");
			}
			else {
				int[] positions = searchString(view.getCurrentText(), searchString);
			
				if (positions == null) {	//The user cancelled the operation
					
				}
				else if (positions[0] == -1) {	//Text was not found
					view.showError("Not found", "The text was not found");				
				}
				else {	//Text found in 1 or more locations
					StringBuilder locations = new StringBuilder();
					for (int i=0; i<positions.length; i++) {
						locations.append(positions[i] + ", ");
					}
					if (locations.length() > 2) {
						locations.delete(locations.length()-2, locations.length()-1);
					}
					view.showPopupMessage("Search result", 
						"The text was found at the following locations: " + locations.toString());
				}
			}
		}
		else if (e.getActionCommand().equals(Action.EDIT_INVERT_COLORS.getCommand())) {
			view.toggleColors();
		}
		else if (e.getActionCommand().equals(Action.EDIT_DATE_TIME.getCommand())) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			view.pasteText(sdf.format(new Date()));
		}
		else if (e.getActionCommand().equals(Action.VIEW_STATUS_BAR.getCommand())) {
			view.toggleStatusBar();
		}
		else if (e.getActionCommand().equals(Action.VIEW_WORD_WRAP.getCommand())) {
			view.setStatus("word wrap");
			view.toggleWordWrap();
		}
		else if (e.getActionCommand().equals(Action.FONT_BOLD.getCommand())) {
			//Toggle between plain and bold
			if (view.isTextBold()) {
				view.setTextBold(false);
				view.setStatus("bold off");
			}
			else {
				view.setTextBold(true);
				view.setStatus("bold on");
			}
		}
		else if (e.getActionCommand().equals(Action.FONT_ITALIC.getCommand())) {
			//Toggle between plain and italic
			if (view.isTextItalic()) {
				view.setTextItalic(false);
				view.setStatus("italic off");
			}
			else {
				view.setTextItalic(true);
				view.setStatus("italic on");
			}
		}
		else if (e.getActionCommand().equals(Action.HELP_ABOUT.getCommand())) {
			view.showPopupMessage("About the application", createAboutText());
		}
		else {
			System.err.println("Comando desconocido");
		}
	}

	/** Saves the text in a data source
	 * 
	 * @param existingDataSource <code>Controller.EXISTING_DATA_SOURCE</code> indicates that  
	 * THE source to be used has already been accessed and the same will be used. 
	 * <code>Controller.NEW_DATA_SOURCE</code> indicates that a new data source will be used and
	 * the user will be prompted
	 * @return <code>true</code> if the save operation is successful, <code>false</code> if 
	 * somethings goes wrong.
	 */
	private boolean saveData(int existingDataSource) {
		// TODO Auto-generated method stub
		return false;
	}

	private String createAboutText() {
		StringBuilder sb = new StringBuilder();
		sb.append("MVC Notepad v0.1");
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		sb.append("Test application implementing the MVC pattern");
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		sb.append("2017  GNU GPLv3 ");
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		sb.append("The icons used are part of Google's material design set ");
		sb.append(System.getProperty("line.separator"));
		sb.append("(https://material.io/guidelines/style/icons.html#icons-system-icons), ");
		sb.append(System.getProperty("line.separator"));
		sb.append("available under the Apache License Version 2.0.");
		return sb.toString();
	}

	private int[] searchString(String currentText, String searchString) {
		StringBuilder sb = new StringBuilder(currentText);
		ArrayList<Integer> alResults = new ArrayList<Integer>();
		
		//Search for the string in the text area
		int position;
		int absolutePosition = 0;
		do {
			position = sb.toString().indexOf(searchString);
			if (position !=  -1) {
				absolutePosition += position;
				alResults.add(Integer.valueOf(absolutePosition));
				sb.delete(0, position + searchString.length());
			}
		} while (position != -1);
		
		//Return the results
		if (alResults.isEmpty()) {
			return new int[] {-1};
		}
		else {
			int[] results = new int[alResults.size()];
			for (int i=0; i<alResults.size(); i++) {
				results[i] = alResults.get(i);
			}
			return results;
		}
	}
	
	/** Requests the user to select a data source and opens it
	 * 
	 * @return {@link String} with the source name, <code>null</code> if no source was selected
	 */
	private String loadData() {
		//First the user is asked to choose a file
		String fullPath = view.chooseFile(source);
		if (fullPath != null) {
			int separator = fullPath.lastIndexOf(java.io.File.separator);
			source = fullPath.substring(0, separator);
			fileName = fullPath.substring(separator+1, fullPath.length());
			return model.readSource(source, fileName);
		}
		else {
			return null;
		}		
	}
}
