package eus.julenugalde.mvcnotepad.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import eus.julenugalde.mvcnotepad.controller.Action;
import eus.julenugalde.mvcnotepad.controller.Controller;

@SuppressWarnings("serial")
public class SwingView extends JFrame implements TextView {
	public static final int LOOK_FEEL_METAL = 0;
	public static final int LOOK_FEEL_NIMBUS = 1;
	public static final int LOOK_FEEL_CDE_MOTIF = 2;
	public static final int LOOK_FEEL_WINDOWS = 3;
	public static final int LOOK_FEEL_WINDOWS_CLASSIC = 4;
	public static final int LOOK_FEEL_WIN_VISTA = 5;
	public static final int LOOK_FEEL_MACINTOSH = 6;
	
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
	private JButton jbFontBold;
	private JButton jbFontItalic;
	private JComboBox<String> jcbFontName;
	private JComboBox<String> jcbFontSize;
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
	private JCheckBoxMenuItem jcbmiWordWrap;
	private JCheckBoxMenuItem jcbmiShowStatusBar;
	private JMenuItem jmiAbout;
	
	private String[] fontNames;
	private String[] fontSizes;
	private int currentFontNameIndex;
	private int currentFontSizeIndex;
	private boolean fontBold;
	private boolean fontItalic;
	private int buttonIconSize;

	public SwingView(Controller controller) {
		this.controller = controller;
		
		initializeComponents();
		assignListeners();
		setLayout();
		setUIDefaultValues();
		initializeWindow();
	}
	
	
	private void setUIDefaultValues() {
		//Default background white
		jcbmiInvertColors.setSelected(false);
		toggleColors();
		
		//Status bar visible
		jcbmiShowStatusBar.setSelected(true);
		toggleStatusBar();
		
		//Plain text
		setTextBold(false);
		setTextItalic(false);
		
		//Word wrap feature on
		jtaEditor.setWrapStyleWord(true);
		jcbmiWordWrap.setSelected(true);
		toggleWordWrap();
	}


	private void initializeWindow() {
		//Set window's look&feel
		UIManager.LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
		try {
			UIManager.setLookAndFeel(laf[LOOK_FEEL_WINDOWS].getClassName());			
		} catch (Exception e) {
			showError("Look & Feel error", e.getLocalizedMessage());
		}

		setTitle("MVC Notepad");
		setSize(800, 600);
		setLocation(400, 50);
		setEnabled(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setVisible(true);	
		setStatus("Application started");
	}

	private void setLayout() {
		//Buttons toolbar
		JPanel jpGeneral = new JPanel(new BorderLayout(0, 0));
		JPanel jpButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		jpButtons.add(jbNew);
		jpButtons.add(jbOpen);
		jpButtons.add(jbSave);
		jpButtons.add(jbSaveAs);
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(jcbFontName);
		jpButtons.add(jcbFontSize);
		jpButtons.add(jbFontBold);
		jpButtons.add(jbFontItalic);
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(jbCopy);
		jpButtons.add(jbCut);
		jpButtons.add(jbPaste);
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(new JSeparator(SwingConstants.VERTICAL));
		jpButtons.add(jbFind);
		jpButtons.add(jbDateTime);
		
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
		jmEdit.addSeparator();
		jmEdit.add(jmiFind);
		jmEdit.addSeparator();
		jmEdit.add(jmiDateTime);
		jmEdit.addSeparator();
		jmEdit.add(jcbmiInvertColors);
		jmb.add(jmEdit);
		jmView.add(jcbmiWordWrap);
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
		//Toolbar buttons
		jbNew.addActionListener((ActionListener)controller);
		jbOpen.addActionListener((ActionListener)controller);
		jbSave.addActionListener((ActionListener)controller);
		jbSaveAs.addActionListener((ActionListener)controller);
		jbCopy.addActionListener((ActionListener)controller);
		jbCut.addActionListener((ActionListener)controller);
		jbPaste.addActionListener((ActionListener)controller);
		jbFontBold.addActionListener((ActionListener)controller);
		jbFontItalic.addActionListener((ActionListener)controller);
		jbDateTime.addActionListener((ActionListener)controller);
		jbFind.addActionListener((ActionListener)controller);
		
		//Combo boxes
		jcbFontName.addActionListener((ActionListener)controller);
		jcbFontSize.addActionListener((ActionListener)controller);
		
		//Menu items
		jmiAbout.addActionListener((ActionListener)controller);
		jmiCopy.addActionListener((ActionListener)controller);
		jmiCut.addActionListener((ActionListener)controller);
		jmiDateTime.addActionListener((ActionListener)controller);
		jmiExit.addActionListener((ActionListener)controller);
		jmiFind.addActionListener((ActionListener)controller);
		jmiNew.addActionListener((ActionListener)controller);
		jmiOpen.addActionListener((ActionListener)controller);
		jmiPaste.addActionListener((ActionListener)controller);
		jmiSave.addActionListener((ActionListener)controller);
		jmiSaveAs.addActionListener((ActionListener)controller);
		jcbmiWordWrap.addActionListener((ActionListener)controller);
		jcbmiInvertColors.addActionListener((ActionListener)controller);
		jcbmiShowStatusBar.addActionListener((ActionListener)controller);
		
		//Text area
		jtaEditor.addKeyListener((KeyListener)controller);
		
		//Window
		this.addWindowListener((WindowListener)controller);
	}

	private Icon loadIcon (String name, int size) {
		URL url =  this.getClass().getResource("/res/" + name);
		Image image = new ImageIcon(url).getImage();
		return new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
	}
	
	private void initializeComponents() {
		initializeFonts();

		//Button sized
		buttonIconSize = 20;
		int menuIconSize = 16;
		Dimension buttonSize = new Dimension(buttonIconSize+4, buttonIconSize+4);
				
		//Elements from the toolbar		
		jbNew = new JButton(loadIcon("ic_insert_drive_file_black_24dp.png", buttonIconSize));
		jbNew.setPreferredSize(buttonSize);
		jbNew.setToolTipText("New file");
		jbNew.setActionCommand(Action.FILE_NEW.getCommand());
		
		jbOpen = new JButton(loadIcon("ic_folder_open_black_24dp.png", buttonIconSize));
		jbOpen.setPreferredSize(buttonSize);
		jbOpen.setToolTipText("Open file");
		jbOpen.setActionCommand(Action.FILE_OPEN.getCommand());
		
		jbSave = new JButton(loadIcon("ic_save_black_24dp.png", buttonIconSize));
		jbSave.setPreferredSize(buttonSize);
		jbSave.setToolTipText("Save file");
		jbSave.setActionCommand(Action.FILE_SAVE.getCommand());
		
		jbSaveAs = new JButton(loadIcon("ic_save_black_24dp.png", buttonIconSize));
		jbSaveAs.setPreferredSize(buttonSize);
		jbSaveAs.setToolTipText("Save file as...");
		jbSaveAs.setActionCommand(Action.FILE_SAVE_AS.getCommand());
		
		jbCopy = new JButton(loadIcon("ic_content_copy_black_24dp.png", buttonIconSize));
		jbCopy.setPreferredSize(buttonSize);
		jbCopy.setToolTipText("Copy");
		jbCopy.setActionCommand(Action.EDIT_COPY.getCommand());
		
		jbCut = new JButton(loadIcon("ic_content_cut_black_24dp.png", buttonIconSize));
		jbCut.setPreferredSize(buttonSize);
		jbCut.setToolTipText("Cut");
		jbCut.setActionCommand(Action.EDIT_CUT.getCommand());
		
		jbPaste = new JButton(loadIcon("ic_content_paste_black_24dp.png", buttonIconSize));
		jbPaste.setPreferredSize(buttonSize);
		jbPaste.setToolTipText("Paste");
		jbPaste.setActionCommand(Action.EDIT_PASTE.getCommand());
		
		jbFind = new JButton(loadIcon("ic_find_in_page_black_24dp.png", buttonIconSize));
		jbFind.setPreferredSize(buttonSize);
		jbFind.setToolTipText("Find");
		jbFind.setActionCommand(Action.EDIT_FIND.getCommand());
		
		jbDateTime = new JButton(loadIcon("ic_insert_invitation_black_24dp.png", buttonIconSize));
		jbDateTime.setPreferredSize(buttonSize);
		jbDateTime.setToolTipText("Insert date & time");
		jbDateTime.setActionCommand(Action.EDIT_DATE_TIME.getCommand());
		
		jbFontBold = new JButton(loadIcon("ic_format_bold_black_24dp.png", buttonIconSize));
		jbFontBold.setPreferredSize(buttonSize);
		jbFontBold.setToolTipText("Bold");
		jbFontBold.setActionCommand(Action.FONT_BOLD.getCommand());
		
		jbFontItalic = new JButton(loadIcon("ic_format_italic_black_24dp.png", buttonIconSize));
		jbFontItalic.setPreferredSize(buttonSize);
		jbFontItalic.setToolTipText("Italic");
		jbFontItalic.setActionCommand(Action.FONT_ITALIC.getCommand());
		jbFontItalic.setPressedIcon(loadIcon("ic_format_italic_grey600_24dp.png", buttonIconSize));
		
		jcbFontName = new JComboBox<String>(fontNames);
		jcbFontName.setSelectedIndex(currentFontNameIndex);
		jcbFontName.setActionCommand(Action.CHANGE_FONT_NAME.getCommand());
		
		jcbFontSize = new JComboBox<String>(fontSizes);
		jcbFontSize.setSelectedItem(fontSizes[currentFontSizeIndex]);
		jcbFontSize.setActionCommand(Action.CHANGE_FONT_SIZE.getCommand());
		
		//Text area and status
		jtaEditor = new JTextArea();
		setTextFont(fontNames[currentFontNameIndex], 
				(Integer.valueOf(fontSizes[currentFontSizeIndex])).intValue());
		jlStatus = new JLabel("");
		
		//Menus
		jmFile = new JMenu("File");
		jmFile.setMnemonic(KeyEvent.VK_F);
		jmEdit = new JMenu("Edit");
		jmEdit.setMnemonic(KeyEvent.VK_E);
		jmView = new JMenu("View");
		jmView.setMnemonic(KeyEvent.VK_V);
		jmHelp = new JMenu("Help");
		jmHelp.setMnemonic(KeyEvent.VK_H);
		
		jmiNew = new JMenuItem("New");
		jmiNew.setIcon(loadIcon("ic_insert_drive_file_black_24dp.png", menuIconSize));
		jmiNew.setMnemonic(KeyEvent.VK_N);
		jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		jmiNew.setActionCommand(Action.FILE_NEW.getCommand());
		
		jmiOpen = new JMenuItem("Open...");
		jmiOpen.setIcon(loadIcon("ic_folder_open_black_24dp.png", menuIconSize));
		jmiOpen.setMnemonic(KeyEvent.VK_O);
		jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		jmiOpen.setActionCommand(Action.FILE_OPEN.getCommand());
		
		jmiSave = new JMenuItem("Save");
		jmiSave.setIcon(loadIcon("ic_save_black_24dp.png", menuIconSize));
		jmiSave.setMnemonic(KeyEvent.VK_S);
		jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		jmiSave.setActionCommand(Action.FILE_SAVE.getCommand());
		
		jmiSaveAs = new JMenuItem("Save as...");
		jmiSaveAs.setIcon(loadIcon("ic_save_black_24dp.png", menuIconSize));
		jmiSaveAs.setMnemonic(KeyEvent.VK_A);
		jmiSaveAs.setActionCommand(Action.FILE_SAVE_AS.getCommand());
		
		jmiExit = new JMenuItem("Exit");
		jmiExit.setIcon(loadIcon("ic_highlight_remove_black_24dp.png", menuIconSize));
		jmiExit.setMnemonic(KeyEvent.VK_X);
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		jmiExit.setActionCommand(Action.FILE_EXIT.getCommand());
		
		jmiCut = new JMenuItem("Cut");
		jmiCut.setIcon(loadIcon("ic_content_cut_black_24dp.png", menuIconSize));
		jmiCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		jmiCut.setActionCommand(Action.EDIT_CUT.getCommand());
		
		jmiCopy = new JMenuItem("Copy");
		jmiCopy.setIcon(loadIcon("ic_content_copy_black_24dp.png", menuIconSize));
		jmiCopy.setMnemonic(KeyEvent.VK_C);
		jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
		jmiCopy.setActionCommand(Action.EDIT_COPY.getCommand());
		
		jmiPaste = new JMenuItem("Paste");
		jmiPaste.setIcon(loadIcon("ic_content_paste_black_24dp.png", menuIconSize));
		jmiPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		jmiPaste.setActionCommand(Action.EDIT_PASTE.getCommand());
		
		jmiFind = new JMenuItem("Find...");
		jmiFind.setIcon(loadIcon("ic_find_in_page_black_24dp.png", menuIconSize));
		jmiFind.setMnemonic(KeyEvent.VK_F);
		jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
		jmiFind.setActionCommand(Action.EDIT_FIND.getCommand());
		
		jmiDateTime = new JMenuItem("Insert date & time");
		jmiDateTime.setIcon(loadIcon("ic_insert_invitation_black_24dp.png", menuIconSize));
		jmiDateTime.setMnemonic(KeyEvent.VK_D);
		jmiDateTime.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		jmiDateTime.setActionCommand(Action.EDIT_DATE_TIME.getCommand());
		
		jcbmiInvertColors = new JCheckBoxMenuItem("Invert colors");
		jcbmiInvertColors.setIcon(loadIcon("ic_invert_colors_on_black_24dp.png", menuIconSize));
		jcbmiInvertColors.setMnemonic(KeyEvent.VK_I);
		jcbmiInvertColors.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
		jcbmiInvertColors.setActionCommand(Action.EDIT_INVERT_COLORS.getCommand());
		
		jcbmiWordWrap = new JCheckBoxMenuItem("Word wrap");
		jcbmiWordWrap.setIcon(loadIcon("ic_wrap_text_black_24dp.png", menuIconSize));
		jcbmiWordWrap.setMnemonic(KeyEvent.VK_W);
		jcbmiWordWrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
		jcbmiWordWrap.setActionCommand(Action.VIEW_WORD_WRAP.getCommand());
		
		jcbmiShowStatusBar = new JCheckBoxMenuItem("Show status bar");
		jcbmiShowStatusBar.setIcon(loadIcon("ic_info_outline_black_24dp.png", menuIconSize));
		jcbmiShowStatusBar.setMnemonic(KeyEvent.VK_T);
		jcbmiShowStatusBar.setActionCommand(Action.VIEW_STATUS_BAR.getCommand());
		
		jmiAbout = new JMenuItem("About...");	
		jmiAbout.setIcon(loadIcon("ic_info_black_24dp.png", menuIconSize));
		jmiAbout.setMnemonic(KeyEvent.VK_B);
		jmiAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
		jmiAbout.setActionCommand(Action.HELP_ABOUT.getCommand());
				
	}

	private void initializeFonts() {
		fontSizes = new String[] {"8", "9", "10", "11", "12", "14", "16", "18", "20", 
				"22", "24", "26", "28", "36", "48", "72"};
		fontNames = getFontsArray();
		fontBold = false;
		fontItalic = false;
		currentFontSizeIndex = 4;	//12 points

		String preferredFont = "Arial";
		currentFontNameIndex = 0;
		fontNames = getFontsArray();
		for (int i=0; i<fontNames.length; i++) {
			if (fontNames[i].equals(preferredFont)) {
				currentFontNameIndex = i;
			}
		}
	}

	@Override
	public void displayText(String text) {
		jtaEditor.setText(text);
	}

	@Override
	public void deleteText() {
		jtaEditor.setText("");
	}

	@Override
	public void toggleColors() {
		if (jcbmiInvertColors.isSelected()) {	//Black background, white text
			jtaEditor.setBackground(Color.BLACK);
			jtaEditor.setForeground(Color.WHITE);
		}
		else {	//White background black text
			jtaEditor.setBackground(Color.WHITE);
			jtaEditor.setForeground(Color.BLACK);
		}
	}

	@Override
	public String getCurrentText() {
		return jtaEditor.getText();
	}


	@Override
	public void setStatus(String status) {
		jlStatus.setText(status);
	}
	
	private String[] getFontsArray () {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	}
	
	@Override
	public void setTextFont(String fontName, int fontSize) {
		//Modifiers are not defined in the view interface definition, but we include them
		int modifiers = Font.PLAIN;
		if (fontBold) {
			modifiers = modifiers | Font.BOLD;
		}
		if (fontItalic) {
			modifiers = modifiers | Font.ITALIC;
		}

		jtaEditor.setFont(new Font(fontName, modifiers, fontSize));
	}
	
	@Override
	public void setTextBold(boolean flag) {
		fontBold = flag;
		int style = jtaEditor.getFont().getStyle();
		if (flag == true) {
			jbFontBold.setIcon(loadIcon("ic_format_bold_black_24dp.png", buttonIconSize));
			style |= Font.BOLD;
		}
		else {
			jbFontBold.setIcon(loadIcon("ic_format_bold_grey600_24dp.png", buttonIconSize));
			style &= (~Font.BOLD);			
		}
		jtaEditor.setFont(jtaEditor.getFont().deriveFont(style));
	}
	
	@Override
	public boolean isTextBold() {
		return fontBold;
	}
	
	@Override
	public void setTextItalic(boolean flag) {
		fontItalic = flag;
		int style = jtaEditor.getFont().getStyle();
		if (flag == true) {
			jbFontItalic.setIcon(loadIcon("ic_format_italic_black_24dp.png", buttonIconSize));
			style |= Font.ITALIC;			
		}
		else {
			jbFontItalic.setIcon(loadIcon("ic_format_italic_grey600_24dp.png", buttonIconSize));
			style &= (~Font.ITALIC);			
		}
		jtaEditor.setFont(jtaEditor.getFont().deriveFont(style));
			
	}
	
	@Override
	public boolean isTextItalic() {
		return fontItalic;
	}
	
	@Override
	public void toggleWordWrap() {
		jtaEditor.setLineWrap(jcbmiWordWrap.isSelected());		
	}
	
	@Override
	public void appendText(String text) {
		//The text will be inserted at the end of the text area
		StringBuilder sb = new StringBuilder (jtaEditor.getText());
		jtaEditor.setText(sb.append(text).toString());			
		jtaEditor.requestFocus();
	}

	public String cutText() {
		String text = jtaEditor.getSelectedText();
		StringBuilder sb = new StringBuilder(jtaEditor.getText());
		int position = sb.indexOf(text);
		if (position > 0) {
			sb.delete(position, position+text.length());
			jtaEditor.setText(sb.toString());
		}
		else {
			showError("Error cutting text", "Text not found");
		}
		return text;		
	}
	
	public String copyText() {
		return jtaEditor.getSelectedText();
	}
	
	public void pasteText(String text) {
		//The text will be inserted in the current caret position
		int caretPosition = jtaEditor.getCaretPosition();
		StringBuilder sb = new StringBuilder (jtaEditor.getText());
		
		//Store the characters behind the caret before trimming the StringBuilder content
		String tail = sb.substring(caretPosition, sb.length());
		sb.delete(caretPosition, sb.length());
		jtaEditor.setText(sb.append(text).append(tail).toString());
		jtaEditor.setCaretPosition(caretPosition + text.length());
		jtaEditor.requestFocus();
	}
	
	@Override
	public void toggleStatusBar() {
		if (jcbmiShowStatusBar.isSelected()) {	//Status bar enabled
			jlStatus.setVisible(true);
		}
		else {	//Status bar disabled
			jlStatus.setVisible(false);
		}
	}
	
	@Override
	public void showPopupMessage (String title, String message) {
		JOptionPane.showMessageDialog(this, message, title,	JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void showError(String title, String error) {
		JOptionPane.showMessageDialog(this, error, title, JOptionPane.ERROR_MESSAGE);
	}

	

	@Override
	public String showTextInputDialog(String title, String message, String defaultText) {
		Object obj = JOptionPane.showInputDialog(
				this, message, title, JOptionPane.QUESTION_MESSAGE, null, null, defaultText);
		if (obj == null) {
			return null;
		}
		else {	
			return (String)obj;
		}
	}


	@Override
	public void updateFontName() {
		currentFontNameIndex = jcbFontName.getSelectedIndex();
		int style = jtaEditor.getFont().getStyle();
		int size = jtaEditor.getFont().getSize();
		String fontName = fontNames[currentFontNameIndex];
		jtaEditor.setFont(new Font(fontName, style, size));
		
	}

	@Override
	public void updateFontSize() {
		currentFontSizeIndex = jcbFontSize.getSelectedIndex();
		float size = Float.valueOf(fontSizes[currentFontSizeIndex]).floatValue();
		setStatus("New font size: " + size);
		jtaEditor.setFont(jtaEditor.getFont().deriveFont(size));		
	}

	@Override
	public void setCaretPosition(int position) {
		if (position >= (jtaEditor.getText().length())) {
			position = jtaEditor.getText().length() - 1;
		}
		jtaEditor.setCaretPosition(position);
		jtaEditor.requestFocus();
		
	}


	@Override
	public void closeView() {
		this.dispose();
	}


	@Override
	public int showPrompt(String title, String message) {
		int exitCode = JOptionPane.showConfirmDialog(this, message, title,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (exitCode) {
		case JOptionPane.YES_OPTION:
			return TextView.YES_OPTION;
		case JOptionPane.NO_OPTION:
			return TextView.NO_OPTION;
		case JOptionPane.CANCEL_OPTION:
			return TextView.CANCEL_OPTION;
		case JOptionPane.CLOSED_OPTION:
			return TextView.CANCEL_OPTION;
		default:
			return TextView.CANCEL_OPTION;
		}
	}

	@Override
	public String chooseDataSource(String defaultDataSource, int modifiers) {
		JFileChooser jfc = new JFileChooser(defaultDataSource);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		switch (modifiers) {
		case TextView.READ:
			jfc.setDialogTitle("Choose a file to read");
			jfc.setDialogType(JFileChooser.OPEN_DIALOG);
			break;
		case TextView.WRITE:
			jfc.setDialogTitle("Choose a file to save the data");
			jfc.setDialogType(JFileChooser.SAVE_DIALOG);
			break;
		default:
			return null;	
		}
		
		jfc.showOpenDialog(this);
		File selectedFile = jfc.getSelectedFile();
		if (selectedFile == null) {
			return null;
		}
		else {
			return selectedFile.getAbsolutePath();
		}
	}
	
	@Override
	public String chooseDataSource(String[] availableSources, int modifier) {
		switch (modifier) {
		case TextView.READ:
			Object objRespuesta = JOptionPane.showInputDialog(this, 
					"Please select a source to be read", "Open location", 
					JOptionPane.QUESTION_MESSAGE, null, availableSources, 0);
			if (objRespuesta == null) return null;
			return (objRespuesta.toString());	
			
		case TextView.WRITE:
			Object objResult = JOptionPane.showInputDialog(this, 
					"Please select the name of the data source", "Save data",
					JOptionPane.QUESTION_MESSAGE);
			if (objResult == null) return null;
			else { 
				return objResult.toString();
			}           

		default:
			return null;
		}
	}
}
