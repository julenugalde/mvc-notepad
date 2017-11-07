package eus.julenugalde.mvcnotepad.controller;

public enum Action {
	FILE_NEW ("file_new"),
	FILE_OPEN ("file_open"),
	FILE_SAVE ("file_save"),
	FILE_SAVE_AS ("file_save_as"),
	FILE_EXIT ("file_exit"),
	CHANGE_FONT_NAME ("change_font_name"),
	CHANGE_FONT_SIZE ("change_font_size"),
	FONT_BOLD ("font_bold"),
	FONT_ITALIC ("font_italic"),
	EDIT_CUT ("edit_cut"),
	EDIT_COPY ("edit_copy"),
	EDIT_PASTE ("edit_paste"),
	EDIT_FIND ("edit_find"),
	EDIT_DATE_TIME ("edit_date_time"),
	EDIT_INVERT_COLORS ("edit_invert_colors"),
	VIEW_WORD_WRAP ("view_word_wrap"),
	VIEW_STATUS_BAR ("view_status_bar"),
	HELP_ABOUT ("help_about");
	
	private final String command;
	
	Action(String command) {
		this.command = command;
	}
	
	public String getCommand() {return command;}
}
