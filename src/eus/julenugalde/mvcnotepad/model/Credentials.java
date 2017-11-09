package eus.julenugalde.mvcnotepad.model;

/** Simple class to store the user name and password for the connection to a database or
 * a network resource
 */
public class Credentials {
	private String userName;
	private String password;
	
	public Credentials (String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}
	
	public Credentials () {
		this("", "");
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
