package eus.julenugalde.mvcnotepad.model;

/** Inteface for the data model, to be implemented by different sources, such as text files,
 * databases or network devices */
public interface TextSourceModel {
	public boolean loadSource(String sourceName);
	public String readSource();
	public String listSources();
	public boolean writeSource(SourceModifier modifier);
	public boolean createSource(String sourceName);
}
