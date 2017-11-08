package eus.julenugalde.mvcnotepad.model;

/** Inteface for the data model, to be implemented by different sources, such as text files,
 * databases or network devices */
public interface TextSourceModel {
	/** Reads the contents of a source.
	 * 
	 * @param sourceLocation Location or path to the source, which can be a directory,
	 * a database schema or a network location.
	 * @param sourceName Name of the specific information source, which can be a file, database
	 * structure(s) or a network resource.
	 * @return Content of the source.
	 */
	public String readSource(String sourceLocation, String sourceName);
	
	/** Gives a list of the available data sources in a specific location.
	 * 
	 * @param sourceLocation Location or path to the source, which can be a directory,
	 * a database schema or a network location.
	 * @return {@link String} array with the available data sources in the specified location.
	 */
	//public String[] listSources(String sourceLocation);
	
	/** Writes data into a source.
	 * 
	 * @param sourceLocation Location or path to the source, which can be a directory,
	 * a database schema or a network location.
	 * @param sourceName Name of the specific information source, which can be a file, database
	 * structure(s) or a network resource.
	 * @param content {@link String} to be written. It will replace the original content
	 * @return <code>true</code> if the operation was successful, <code>false</code> otherwise.
	 */
	public boolean writeSource(String sourceLocation, String sourceName, String content);
	
	/** Checks if a data source already exists.
	 * 
	 * @param sourceLocation Location or path to the source, which can be a directory,
	 * a database schema or a network location.
	 * @param sourceName Name of the specific information source, which can be a file, database
	 * structure(s) or a network resource.
	 * @return <code>true</code> if the location exists, <code>false</code> otherwise.
	 */
	public boolean existsSource(String sourceLocation, String sourceName);
	
	/** Creates a data source in the specified location
	 * 
	 * @param sourceLocation Location or path to the source, which can be a directory,
	 * a database schema or a network location.
	 * @param sourceName Name of the specific information source, which can be a file, database
	 * structure(s) or a network resource.
	 * @return <code>true</code> if the source was successfully created, <code>false</code> if 
	 * there were errors or the source already existed.
	 */
	public boolean createSource(String sourceLocation, String sourceName);
}
