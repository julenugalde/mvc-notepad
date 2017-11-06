package eus.julenugalde.mvcnotepad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import eus.julenugalde.mvcnotepad.model.TextFileSource;
import eus.julenugalde.mvcnotepad.model.TextSourceModel;
import eus.julenugalde.mvcnotepad.view.SwingView;
import eus.julenugalde.mvcnotepad.view.TextView;

public class MVCNotepadController 
implements Controller, ActionListener, KeyListener, WindowListener {

	private TextSourceModel model;
	private TextView view;
	private String source;
	
	/** Controller constructor
	 * 
	 * @param modelType The type of model to be used. 
	 * Options: MODEL_TEXT_FILE, MODEL_DATABASE, MODEL_NETWORK
	 * @param viewType The type of view to be displayed. Options: SWING_VIEW
	 * @param source Source for the model. It can be the working directory path for 
	 * MODEL_TEXT_FILE, the database schema for MODEL_DATABASE or (NOT IMPLEMENTED FOR NETWORK).
	 */
	public MVCNotepadController(int modelType, int viewType, String source) {
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
		
		switch (viewType) {
		case SWING_VIEW:
			view = new SwingView(this);
			break;
		}
	}

	@Override
	public void loadModel() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initializeView() {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("boton pulsado");
	}

}
