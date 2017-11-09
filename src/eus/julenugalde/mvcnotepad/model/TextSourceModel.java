package eus.julenugalde.mvcnotepad.model;

/** Inteface for the data model, to be implemented by different sources, such as text files,
 * databases or network devices */
public interface TextSourceModel {
	/** Reads the contents of a source.
	 * @param source <b>Location</b> or path to the source, which can be a directory,
	 * a database schema or a network location, and <b>name</b> of the specific information 
	 * source, which can be a file, database structure(s) or a network resource.
	 * 
	 * @return Content of the source.
	 */
	public String readSource(Source source);
	
	/** Writes data into a source.
	 * @param source <b>Location</b> or path to the source, which can be a directory,
	 * a database schema or a network location, and <b>name</b> of the specific information 
	 * source, which can be a file, database structure(s) or a network resource.
	 * @param content {@link String} to be written. It will replace the original content
	 * 
	 * @return <code>true</code> if the operation was successful, <code>false</code> otherwise.
	 */
	public boolean writeSource(Source source, String content);
	
	/** Checks if a data source already exists.
	 * @param source <b>Location</b> or path to the source, which can be a directory,
	 * a database schema or a network location, and <b>name</b> of the specific information 
	 * source, which can be a file, database structure(s) or a network resource.
	 *  
	 * @return <code>true</code> if the location exists, <code>false</code> otherwise.
	 */
	public boolean existsSource(Source source);
	
	/** Creates a data source in the specified location
	 * @param source <b>Location</b> or path to the source, which can be a directory,
	 * a database schema or a network location, and <b>name</b> of the specific information 
	 * source, which can be a file, database structure(s) or a network resource.
	 * @return <code>true</code> if the source was successfully created, <code>false</code> if 
	 * there were errors or the source already existed.
	 */
	public boolean createSource(Source source);
	
	/** Opens the source location
	 * 
	 * @param sourceLocation {@link String} with the location to be opened. For database connections, 
	 * attempts to open the connection and checks if the schema is correct
	 * @return <code>true</code> if successful, <code>false</code> if there are any errors.
	 */
	public boolean openSource(String sourceLocation);
	
	/** Closes the data source
	 */
	public void closeSource();
	
	/** Returns a list of all the available data sources in a given location
	 * 
	 * @param sourceLocation Location to be searched. It will be directory for text files, the schema
	 * for database connections or a network location for network connections.
	 * @return {@link String} array with the list of sources. <code>null</code> if the sourceLocation
	 * parameter is not correct or does not correspond to a directory.
	 */
	public String[] listSources(String sourceLocation);
}
