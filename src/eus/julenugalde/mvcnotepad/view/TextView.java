package eus.julenugalde.mvcnotepad.view;

/** Interface that specifies the methods to be implemented for the MVC Notepad view */
public interface TextView {
	/** Replace the text currently in the editor by the one passed as an argument
	 * 
	 * @param text Text to be displayed
	 */
	public void displayText(String text);
	
	/** Delete all the text currently displayed in the editor*/
	public void deleteText();
	
	/** Set the font for displaying the text
	 * 
	 * @param fontName Font name
	 * @param fontSize Font size in points
	 */
	public void setTextFont(String fontName, int fontSize); 	
	
	/** Invert the colors for the text edition area. */
	public void toggleColors();
	
	/** Read the text currently in the text editor */
	public String getCurrentText();
	
	/** Show some text in the application's notification area */
	public void setStatus(String status);
	
	/** Append a string to the text currently in the editor
	 * 
	 * @param text String to be appended to the end of the text
	 */
	public void appendText(String text);
	
	/** Show or hide the application's notificaton area */
	public void toggleStatusBar();
	
	/** Show a custom message to the user using a modal window */
	public void showPopupMessage(String message);
	
	public boolean isTextBold();
	public boolean isTextItalic();
	public void setTextBold(boolean flag);
	public void setTextItalic(boolean flag);
}
