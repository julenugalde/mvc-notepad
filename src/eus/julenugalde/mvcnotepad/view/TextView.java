package eus.julenugalde.mvcnotepad.view;

/** Interface that specifies the methods to be implemented for the MVC Notepad view */
public interface TextView {
	public static final int YES_OPTION = 1;
	public static final int NO_OPTION = 2;
	public static final int CANCEL_OPTION = 0;
	
	public static final int READ = 1;
	public static final int WRITE = 2;
		
	/** Shows a choose prompt to select a source to be read or writen. To be used when the 
	 * view implementation allows listing the data sources from a location, e.g. list the 
	 * files in a directory 
	 * 
	 * @param defaultDataSource Initial location of the source for reading operations, full path
	 * for writing 
	 * @param modifier <code>TextView.READ</code> indicates that the data source will be read; 
	 * <code>TextView.WRITE</code> means that the source will be written.
	 * @return {@link String} with the full path of the chosen source, <code>null</code> 
	 * if the operation had errors or was cancelled by the user.
	 */
	public String chooseDataSource(String defaultDataSource, int modifier);

	/** Shows a choose prompt to select a source to be read or writen. To be used when the 
	 * view implementation cannot list the data sources from a location, e.g. access a database
	 * 
	 * @param availableSources List of sources for reading operations, name of the source to be
	 * writen for writing operation 
	 * @param modifier <code>TextView.READ</code> indicates that the data source will be read; 
	 * <code>TextView.WRITE</code> means that the source will be written.
	 * @return {@link String} with the name of the chosen source, <code>null</code> 
	 * if the operation had errors or was cancelled by the user.
	 */
	public String chooseDataSource(String[] availableSources, int modifier);
	
	
	/** Shows a prompt to the user. The possible answers are yes, no and cancel.
	 * 
	 * @param title Title of the prompt
	 * @param message Question asked to the user
	 * @return int value with the answer to the question. Can be <code>TextView.YES_OPTION</code>
	 * if the answer was yes, <code>TextView.NO_OPTION</code> if the answer was no, or 
	 * <code>TextView.CANCEL_OPTION</code> if the user selected cancel or closed the prompt.
	 */
	public int showPrompt(String title, String message);
	
	/** Replace the text currently in the editor by the one passed as an argument
	 * 
	 * @param text {@link String} to be displayed
	 */
	public void displayText(String text);
	
	/** Delete all the text currently displayed in the editor*/
	public void deleteText();
	
	/** Set the font for displaying the text
	 * 
	 * @param fontName {@link String} font name
	 * @param fontSize font size in points
	 */
	public void setTextFont(String fontName, int fontSize); 	
	
	/** Invert the colors for the text edition area. */
	public void toggleColors();
	
	/** Read the text currently in the text editor
	 * 
	 * @return {@link String} with the content of the text area
	 */
	public String getCurrentText();
	
	/** Show some text in the application's notification area 
	 * 
	 * @param status Text to be displayed
	 */
	public void setStatus(String status);
	
	/** Append a string to the text currently in the editor
	 * 
	 * @param text {@link String} to be appended to the end of the text
	 */
	public void appendText(String text);
	
	/** Show or hide the application's notificaton area */
	public void toggleStatusBar();
	
	/** Displays a prompt to ask the user for a text.
	 * @param title Title of the prompt to be displayed.
	 * @param message Message for the prompt.
	 * @param defaultText {@link String} with the default text.
	 * @return {@link String} entered by the user. <code>null</code> if the operation is cancelled.
	 */
	public String showTextInputDialog(String title, String message, String defaultText);
	
	/** Show a custom message to the user
	 * 
	 * @param title {@link String} with the message title
	 * @param message {@link String} with the message to be displayed
	 */
	public void showPopupMessage(String title, String message);
	
	/** Show anerror message to the user
	 * 
	 * @param title {@link String} with the error title
	 * @param error {@link String} with the error message to be displayed
	 */
	public void showError(String title, String error);
	
	/** Remove the selected text from the text area and return it
	 * 
	 * @return {@link String} corresponding to the text area selection
	 */
	public String cutText();
	
	/** Copy the selected text from the text area (without removing it) and return it
	 * 
	 * @return {@link String} corresponding to the text area selection
	 */
	public String copyText();
	
	/** Insert the text at the caret location 
	 * 
	 * @param text {@link String} to be inserted at the caret position
	 */
	public void pasteText(String text);
	
	/** Flag indicating if the text is currently bold
	 * 
	 * @return <code>true</code> if the text is bold, <code>false</code> otherwise
	 */
	public boolean isTextBold();
	
	/** Flag indicating if the text is currently in italics
	 * 
	 * @return <code>true</code> if the text is in italics, <code>false</code> otherwise
	 */
	public boolean isTextItalic();
	
	/** Sets the current text style in bold or removes this style
	 * 
	 * @param flag <code>true</code> to set the text bold, <code>false</code> to remove the
	 * bold style
	 */
	public void setTextBold(boolean flag);
	
	/** Sets the current text style in italics or removes this style
	 * 
	 * @param flag <code>true</code> to set the text in italics, <code>false</code> to remove the
	 * italics style
	 */
	public void setTextItalic(boolean flag);
	
	/** Alternates the word wrap feature between on and off
	 */
	public void toggleWordWrap();
	
	/** Updates the font family used in the text area */
	public void updateFontName();
	
	/** Updates the font sized used in the text area */
	public void updateFontSize();
	
	/** Places the caret in the specified location
	 * 
	 * @param position New position of the caret. If it exceeds the length of the 
	 * text area content, it will be placed at the end.
	 */
	public void setCaretPosition(int position);
	
	/** Closes the view */
	public void closeView();
}
