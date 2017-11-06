package eus.julenugalde.mvcnotepad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import eus.julenugalde.mvcnotepad.model.TextFileSource;
import eus.julenugalde.mvcnotepad.model.TextSourceModel;
import eus.julenugalde.mvcnotepad.view.SwingView;
import eus.julenugalde.mvcnotepad.view.TextView;

public class MVCNotepadController 
implements Controller, ActionListener, KeyListener, WindowListener {

	private TextSourceModel model;
	private TextView view;
	
	private boolean textChanged;
	private String currentText;
	
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
		
		textChanged = false;	//Initially the save button is disabled
		currentText = "";			
	}

	@Override
	public void loadModel(int modelType, String source) {
		switch (modelType) {
		case MODEL_TEXT_FILE:
			model = new TextFileSource(source);
			break;
		case MODEL_DATABASE:
			//TODO Pendiente implementar
			System.err.println("Error: aún no implementado");
			break;
		case MODEL_NETWORK:
			//TODO Pendiente implementar
			System.err.println("Error: aún no implementado");
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
		//TODO Create method in the view to open a confirmation dialog		
		//If the text has changed, open a dialog to save the file.
		if (textChanged) {
			int exitCode = JOptionPane.showConfirmDialog(
		              (JComponent)view,        //parent component
		              "Mensaje de confirmación", //message
		              "Confirme, por favor",            //title
		              JOptionPane.YES_NO_CANCEL_OPTION, //option type
		              JOptionPane.QUESTION_MESSAGE);
			switch (exitCode) {
			case JOptionPane.YES_OPTION:
				//TODO Open dialog to save the file
				view.setStatus("Ha pulsado si");
				
				break;
			default:
				view.setStatus("resto opciones");
				break;
			}
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
	public void keyReleased(KeyEvent arg0) {
		view.setStatus("Tecla pulsada: " + arg0.getKeyChar());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Action.FILE_OPEN.getCommand())) {
			view.setStatus("file open"); //TODO implementar
		} 
		else if (e.getActionCommand().equals(Action.FILE_NEW.getCommand())) {
			view.setStatus("file new");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.FILE_SAVE.getCommand())) {
			view.setStatus("file save");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.FILE_SAVE_AS.getCommand())) {
			view.setStatus("file save as");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.FILE_EXIT.getCommand())) {
			view.setStatus("file exit");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.EDIT_COPY.getCommand())) {
			view.setStatus("edit copy");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.CHANGE_FONT_NAME.getCommand())) {
			view.setStatus("font name");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.CHANGE_FONT_SIZE.getCommand())) {
			view.setStatus("font size");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.EDIT_CUT.getCommand())) {
			view.setStatus("edit cut");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.EDIT_PASTE.getCommand())) {
			view.setStatus("edit paste");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.EDIT_FIND.getCommand())) {
			view.setStatus("edit find");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.EDIT_INVERT_COLORS.getCommand())) {
			view.toggleColors();
		}
		else if (e.getActionCommand().equals(Action.EDIT_DATE_TIME.getCommand())) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			view.appendText(sdf.format(new Date()));
		}
		else if (e.getActionCommand().equals(Action.VIEW_STATUS_BAR.getCommand())) {
			view.toggleStatusBar();
		}
		else if (e.getActionCommand().equals(Action.VIEW_WORD_WRAP.getCommand())) {
			view.setStatus("word wrap");//TODO implementar
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
		else if (e.getActionCommand().equals(Action.FONT_UNDERLINE.getCommand())) {
			view.setStatus("font underline");//TODO implementar
		}
		else if (e.getActionCommand().equals(Action.HELP_ABOUT.getCommand())) {
			StringBuilder sb = new StringBuilder();
			sb.append("MVC Notepad v0.1");
			sb.append(System.getProperty("line.separator"));
			sb.append("Test application implementing the MVC pattern");
			sb.append(System.getProperty("line.separator"));
			sb.append("2017  GNU GPLv3 ");
			view.showPopupMessage(sb.toString());
		}
		else {
			System.err.println("Comando desconocido");//TODO Cambiar
		}
	}
}
