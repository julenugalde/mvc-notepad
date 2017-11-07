package eus.julenugalde.mvcnotepad.view;

import java.awt.Font;

/** Interface that specifies the methods to be implemented for the MVC Notepad view */
public interface TextView {
	public static final int YES_OPTION = 1;
	public static final int NO_OPTION = 2;
	public static final int CANCEL_OPTION = 0;
	
	/** Shows a file choose prompt to select a file to be read 
	 * 
	 * @param source Initial directory
	 * @return {@link String} with the file name
	 */
	public String chooseFile(String source);
	
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
	 * @param fontName {@link Font} name
	 * @param fontSize {@link Font} size in points
	 */
	public void setTextFont(String fontName, int fontSize); 	
	
	/** Invert the colors for the text edition area. */
	public void toggleColors();
	
	/** Read the text currently in the text editor
	 * 
	 * @return {@link String} with the content of the text area
	 */
	public String getCurrentText();
	
	/** Show some text in the application's notification area */
	public void setStatus(String status);
	
	/** Append a string to the text currently in the editor
	 * 
	 * @param text {@link String} to be appended to the end of the text
	 */
	public void appendText(String text);
	
	/** Show or hide the application's notificaton area */
	public void toggleStatusBar();
	
	/** Displays a prompt to find the text passed as parameter, allowing the user to
	 * change it before performing the search
	 * @param text {@link String} with the default text to search
	 * @return {@link String} that will be searched
	 */
	public String showTextSearchDialog(String text);
	
	/** Show a custom message to the user
	 * 
	 * @param title {@link String} with the message title
	 * @param message {@link String} with the message to be displayed
	 * @return An int array with the locations of the text within the text area. Otherwise, 
	 * <code>null</code> if the user cancelled the operation or an array with a single -1
	 * value if the text was not found.
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
