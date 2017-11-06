package eus.julenugalde.mvcnotepad.view;

import java.awt.Font;

public interface TextView {
	public void displayText(String text);
	public void deleteText();
	public void setFont(Font font); 	//TODO Ver si se pierde generalidad con esto
	public void invertColors();
	public String getText();
	public void setStatus(String status);
}
