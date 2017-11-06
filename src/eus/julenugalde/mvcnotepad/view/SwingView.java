package eus.julenugalde.mvcnotepad.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.management.remote.JMXConnectorServerMBean;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import eus.julenugalde.mvcnotepad.controller.Controller;

@SuppressWarnings("serial")
public class SwingView extends JFrame implements TextView {
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_METAL = 0;
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_NIMBUS = 1;
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_CDE_MOTIF = 2;
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_WINDOWS = 3;
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_WINDOWS_CLASSIC = 4;
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_WIN_VISTA = 6;
	@SuppressWarnings("unused")
	private static final int LOOK_FEEL_MACINTOSH = 7;
	
	private Controller controller;
	
	private JButton jbNew;
	private JButton jbOpen;
	private JButton jbSave;
	private JButton jbSaveAs;
	private JButton jbCopy;
	private JButton jbCut;
	private JButton jbPaste;
	private JButton jbFind;
	private JButton jbDateTime;
	private JButton jbInvertColors;
	private JButton jbFontBold;
	private JButton jbFontItalics;
	private JButton jbFontUnderline;
	private JComboBox jcbFont;
	private JTextField jtfFontSize;
	private JTextArea jtaEditor;
	private JLabel jlStatus;
	
	private JMenu jmFile;
	private JMenu jmEdit;
	private JMenu jmView;
	private JMenu jmHelp;
	private JMenuItem jmiNew;
	private JMenuItem jmiOpen;
	private JMenuItem jmiSave;
	private JMenuItem jmiSaveAs;
	private JMenuItem jmiExit;
	private JMenuItem jmiCut;
	private JMenuItem jmiCopy;
	private JMenuItem jmiPaste;
	private JMenuItem jmiFind;
	private JMenuItem jmiDateTime;
	private JCheckBoxMenuItem jcbmiInvertColors;
	private JCheckBoxMenuItem jcbmiShowStatusBar;
	private JMenuItem jmiAbout;
	
	public SwingView(Controller controller) {
		this.controller = controller;
		
		initializeComponents();
		assignListeners();
		setLayout();
		initializeWindow();
	}
	
	
	private void initializeWindow() {
		//Set window's look&feel
		UIManager.LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
		try {
			UIManager.setLookAndFeel(laf[LOOK_FEEL_WINDOWS].getClassName());			
		} catch (Exception e) {
			showError(e.getLocalizedMessage());
		}

		setTitle("MVC Notepad");
		setSize(800, 600);
		setLocation(100, 50);
		setEnabled(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}


	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);		
	}


	private void setLayout() {
		//Buttons toolbar
		JPanel jpGeneral = new JPanel(new BorderLayout(10, 10));
		JPanel jpButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		jpButtons.add(jbNew);
		jpButtons.add(jbOpen);
		jpButtons.add(jbSave);
		jpButtons.add(jbSaveAs);
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(jtaEditor);
		jpButtons.add(jtfFontSize);
		jpButtons.add(jbFontBold);
		jpButtons.add(jbFontItalics);
		jpButtons.add(jbFontUnderline);
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(jbCopy);
		jpButtons.add(jbCut);
		jpButtons.add(jbPaste);
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(jbFind);
		jpButtons.add(jbDateTime);
		jpButtons.add(jbInvertColors);
		
		//Menubar
		JMenuBar jmb = new JMenuBar();		
		jmFile.add(jmiNew);
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmFile.add(jmiSaveAs);
		jmFile.addSeparator();
		jmFile.add(jmiExit);
		jmb.add(jmFile);
		jmEdit.add(jmiCut);
		jmEdit.add(jmiCopy);
		jmEdit.add(jmiPaste);
		jmEdit.add(jmiFind);
		jmEdit.add(jmiDateTime);
		jmEdit.add(jcbmiInvertColors);
		jmb.add(jmEdit);
		jmView.add(jcbmiShowStatusBar);
		jmb.add(jmView);
		jmHelp.add(jmiAbout);
		jmb.add(jmHelp);
		this.setJMenuBar(jmb);
		
		//General layout
		jpGeneral.add(jpButtons, BorderLayout.NORTH);
		JScrollPane jspScrollBars = new JScrollPane(jtaEditor);
		jspScrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspScrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jpGeneral.add(jspScrollBars, BorderLayout.CENTER);
		jpGeneral.add(jlStatus, BorderLayout.SOUTH);		
		this.add(jpGeneral);
	}


	private void assignListeners() {
		jbNew.addActionListener((ActionListener)controller);
	}


	private Icon loadIcon (String name) {
		URL url =  this.getClass().getResource("/res/" + name);
		return new ImageIcon(url);
	}
	
	private void initializeComponents() {
		Dimension buttonSize = new Dimension(26, 26);
		
		jbNew = new JButton(loadIcon("ic_insert_drive_file_black_24dp.png"));
		jbNew.setPreferredSize(buttonSize);
		jbNew.setToolTipText("New file");
		
		jbOpen = new JButton(loadIcon("ic_folder_open_black_24dp.png"));
		jbOpen.setPreferredSize(buttonSize);
		jbOpen.setToolTipText("Open file");
		
		jbSave = new JButton(loadIcon("ic_save_black_24dp.png"));
		jbSave.setPreferredSize(buttonSize);
		jbSave.setToolTipText("Save file");
		
		jbSaveAs = new JButton(loadIcon("ic_save_black_24dp.png"));
		jbSaveAs.setPreferredSize(buttonSize);
		jbSaveAs.setToolTipText("Save file as...");
		
		jbCopy = new JButton(loadIcon("ic_content_copy_black_24dp.png"));
		jbCopy.setPreferredSize(buttonSize);
		jbCopy.setToolTipText("Copy");
		
		jbCut = new JButton(loadIcon("ic_content_cut_black_24dp.png"));
		jbCut.setPreferredSize(buttonSize);
		jbCut.setToolTipText("Cut");
		
		jbPaste = new JButton(loadIcon("ic_content_paste_black_24dp.png"));
		jbPaste.setPreferredSize(buttonSize);
		jbPaste.setToolTipText("Paste");
		
		jbFind = new JButton(loadIcon("ic_find_in_page_black_24dp.png"));
		jbFind.setPreferredSize(buttonSize);
		jbFind.setToolTipText("Find");
		
		jbDateTime = new JButton(loadIcon("ic_insert_invitation_black_24dp.png"));
		jbDateTime.setPreferredSize(buttonSize);
		jbDateTime.setToolTipText("Insert date & time");
		
		jbInvertColors = new JButton(loadIcon("ic_invert_colors_on_black_24dp.png"));
		jbInvertColors.setPreferredSize(buttonSize);
		jbInvertColors.setToolTipText("Invert colors");
		
		jbFontBold = new JButton(loadIcon("ic_format_bold_black_24dp.png"));
		jbFontBold.setPreferredSize(buttonSize);
		jbFontBold.setToolTipText("Bold");
		
		jbFontItalics = new JButton(loadIcon("ic_format_italic_black_24dp.png"));
		jbFontItalics.setPreferredSize(buttonSize);
		jbFontItalics.setToolTipText("Italics");
		
		jbFontUnderline = new JButton(loadIcon("ic_format_underline_black_24dp.png"));
		jbFontUnderline.setPreferredSize(buttonSize);
		jbFontUnderline.setToolTipText("Underline");
		
		jcbFont = new JComboBox();
		jtfFontSize = new JTextField();
		
		jtaEditor = new JTextArea();
		jlStatus = new JLabel("");
		
		jmFile = new JMenu("File");
		jmEdit = new JMenu("Edit");
		jmView = new JMenu("View");
		jmHelp = new JMenu("Help");
		jmiNew = new JMenuItem("New");
		jmiOpen = new JMenuItem("Open...");
		jmiSave = new JMenuItem("Save");
		jmiSaveAs = new JMenuItem("Save as...");
		jmiExit = new JMenuItem("Exit");
		jmiCut = new JMenuItem("Cut");
		jmiCopy = new JMenuItem("Copy");
		jmiPaste = new JMenuItem("Paste");
		jmiFind = new JMenuItem("Find...");
		jmiDateTime = new JMenuItem("Insert date & time");
		jcbmiInvertColors = new JCheckBoxMenuItem("Invert colors");
		jcbmiShowStatusBar = new JCheckBoxMenuItem("Show status bar");
		jmiAbout = new JMenuItem("About...");		
	}

	@Override
	public void displayText(String text) {
		//TODO implementar

	}

	@Override
	public void deleteText() {
		// TODO Auto-generated method stub

	}

	@Override
	public void invertColors() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setStatus(String status) {
		jlStatus.setText(status);
	}

}
