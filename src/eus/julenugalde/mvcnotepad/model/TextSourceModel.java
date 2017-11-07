package eus.julenugalde.mvcnotepad.model;

/** Inteface for the data model, to be implemented by different sources, such as text files,
 * databases or network devices */
public interface TextSourceModel {
	public String readSource(String sourceName, String elementName);
	public String listSources();
	public boolean writeSource(String sourceName, String elementName);
	public boolean createSource(String sourceName, String elementName);
}
