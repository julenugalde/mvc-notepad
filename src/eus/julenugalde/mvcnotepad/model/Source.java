package eus.julenugalde.mvcnotepad.model;

/** Class that stores information about the source location and name */
public class Source {
	private String location;
	private String name;
	
	public Source(String location, String name) {
		this.setLocation(location);
		this.setName(name);
	}
	
	public Source() {
		this("", "");
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Separates a full path into the location and the resource name. The method will look for the
	 * directory-file separator defined in {@link java.io.File}.
	 * 
	 * @param fullPath Full path to be separated.
	 * @return {@link Source} instance with the location and resource name, <code>null</code> if 
	 * any errors are found.
	 */
	public static Source separatePath(String fullPath) {
		if (fullPath == null) {
			return null;
		}
		if (!fullPath.contains(java.io.File.separator)) {
			return null;
		}
		Source result = new Source();
		int separator = fullPath.lastIndexOf(java.io.File.separator);
		result.setLocation(fullPath.substring(0, separator));
		result.setName(fullPath.substring(separator+1, fullPath.length()));
		return result;
	}

}
