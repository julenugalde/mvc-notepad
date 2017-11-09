package eus.julenugalde.mvcnotepad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.mysql.jdbc.AssertionFailedException;

/** Implementation of the model in a relational database. The MySQL connector is used, so the 
 * appropriate libraries must be included in the project. <br><br>
 * The database schema uses two simple tables to manage the text data: <br>
 * <ul>
 * <li><b>file</b>, with the following fields: file_id (INT), file_name (VARCHAR(255)), 
 * file_owner (VARCHAR(64))</li>
 * <li><b>line</b>, with the following fields: line_id (INT), file_id (INT), line_text (TEXT)</li>
 * </ul>
 * The text is structured in paragraphs, one of each corresponding to an element in the 'line' 
 * table.<br><br>
 * For the sake of simplicity, the application just checks if the 'file' and 'line' tables exist,
 * and exits if they are not found. The creation statements for the tables are the following:<br><br>
 * <b>'file' table:</b><BR>
 * <code>
 * CREATE TABLE IF NOT EXISTS file (<br>
 * &emsp;&emsp;file_id INT NOT NULL AUTO_INCREMENT, <br>
 * &emsp;&emsp;PRIMARY KEY(file_id),<br>
 * &emsp;&emsp;file_name VARCHAR(255) NOT NULL,<br>
 * &emsp;&emsp;file_owner VARCHAR(64)<br>
 * );</code><br><br>
 * <b>'line' table:</b><BR>
 * <code>
 * CREATE TABLE IF NOT EXISTS line (<br>
 * &emsp;&emsp;line_id INT NOT NULL AUTO_INCREMENT, <br>
 * &emsp;&emsp;PRIMARY KEY(line_id),<br>
 * &emsp;&emsp;file_id INT NOT NULL,<br>
 * &emsp;&emsp;line_text TEXT<br>
 * );</code><br><br>
 */
public class DataBaseSource implements TextSourceModel {
	/** User name and password for the database connection. 
	 * If a new file is created, the current user name value will be used
	 * as owner name in the database. */
	private Credentials credentials;
	private String schema;	
	private Connection connection;
	
	/** Initializes the database connection with a MySQL server in localhost:3306.
	 * 
	 * @param credentials User name and password for the database connection
	 * @param initialSchema Initial schema to be used.
	 */
	public DataBaseSource(Credentials credentials, String initialSchema) {
		this.credentials = credentials;
		this.schema = initialSchema;	
	}		
	
	@Override
	public String readSource(Source source) {
		try {
			if(!checkConnection(source.getLocation())) return null;
						
			String sql = 
					"SELECT line_text " + 
					"FROM line " + 
					"JOIN file ON file.file_id=line.file_id " +
					"WHERE file.file_name='" + source.getName() + "'";
			ResultSet result = executeSQLQuery(sql);
			
			StringBuffer sb = new StringBuffer();
			while (result.next()) {
				sb.append(result.getString(1));
				sb.append(System.getProperty("line.separator"));
			}
			return sb.toString();
			
		} catch (Exception e) {
			System.err.println("Error listSources: " + e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public boolean writeSource(Source source, String content) {
		try {
			assert existsSource(null);
			if (!checkConnection(source.getLocation())) return false;
			
			//Find the file id in the database
			String sql = 
					"SELECT file_id FROM file " + 
					"WHERE file.file_name='" + source.getName() + "'";
			ResultSet result = executeSQLQuery(sql);
			if (!result.next()) return false;
			int fileId = result.getInt(1);
			
			//Delete the lines currently stored in the database
			sql = "DELETE FROM line WHERE file_id=" + fileId;
			if (executeSQLUpdate(sql) < 0) return false;
			
			//Store the new text lines
			StringTokenizer st = new StringTokenizer(content, "\n", true);
			while (st.hasMoreTokens()) {
				sql = "INSERT INTO line (file_id, line_text) " + 
					"VALUES (" + fileId + ", '" + st.nextToken("") + "')";
				if (executeSQLUpdate(sql) < 1) return false;
			}
			return true;
		}
		catch (AssertionFailedException afe) {
			System.err.println("Assertion error: the resource had to exist but did not.");
			return false;
		} catch (SQLException sqle) {
			System.err.println("Database exception: " + sqle.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public boolean existsSource(Source source) {
		if ((source.getName() == null) | (source.getLocation() == null)) return false;
		if ((source.getName().equals("")) | (source.getLocation().equals(""))) return false;
		
		try {
			if (!checkConnection(source.getLocation())) return false;
			String sql = 
					"SELECT file_name " + 
					"FROM file " + 
					"WHERE file.file_name='" + source.getName() + "'";
			ResultSet result = executeSQLQuery(sql);
			return result.next();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean createSource(Source source) {
		//Check if the database entry already existed
		if (existsSource(null)) return false;
		
		try {
			if (!checkConnection(source.getLocation())) return false;
					
			String sql = 
					"INSERT INTO file (file_name, file_owner) " + 
					"VALUES ('" + source.getName() + "', '" + credentials.getUserName() + "')";
			int result = executeSQLUpdate(sql);
			return (result > 0);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void closeSource() {
		if (connection != null) {
			try {
				if (!connection.isClosed()) {
					connection.close();
					System.out.println("Closing DB connection...");
				}
			} catch (Exception e) {
				System.err.println("Error closing the database connection");
			}
		}
		
	}

	@Override
	public boolean openSource(String sourceLocation) {
		try {
			//Register the driver and establisch the connection
			Class.forName("com.mysql.jdbc.Driver");    
			String url = "jdbc:mysql://localhost:3306/" + schema + 
					"?verifyServerCertificate=false&useSSL=true"; //SSL w/o certificate verification
			connection = DriverManager.getConnection(url, 
					credentials.getUserName(), credentials.getPassword());
			
			//Checks if the tables file and line exist
			ResultSet result = executeSQLQuery("SHOW TABLES");						
			boolean fileFound = false;
			boolean lineFound = false;
			String tableName;
			while (result.next()) {
				tableName = result.getString(1);
				if (tableName.equals("file")) fileFound = true;
				if (tableName.equals("line")) lineFound = true;
			}
			return (fileFound & lineFound);
						
		} catch (Exception e) {
			System.err.println("Error: " + e.getLocalizedMessage());
			return false;
		}
			
	}
	
	/** Executes a database query.
	 * 
	 * @param sql {@link String} containing the SQL query (SELECT).
	 * @return {@link ResultSet} with the query result; <code>null</code> if there are errors.
	 */
	private ResultSet executeSQLQuery(String sql) {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);			
		}
		catch (SQLException sqlex) {
			return null;
		}
	}

	/** Executes an update operation in the database.
	 * 
	 * @param sql {@link String} with the SQL sentence (UPDATE, DELETE, INSERT).
	 * @return If successful, number of rows modified; -1 if there are errors.
	 */
	private int executeSQLUpdate(String sql) {
		try {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);			
		}
		catch (SQLException sqlex) {
			return -1;
		}
	}

	@Override
	public String[] listSources(String sourceLocation) {
		try {
			if (!checkConnection(sourceLocation)) return null;
						
			String sql = "SELECT file_name FROM file";
			ResultSet result = executeSQLQuery(sql);
			
			String[] sources = new String[1];
			ArrayList<String> alSources = new ArrayList<String>();
			while (result.next()) {
				alSources.add(result.getString("file_name"));
			}
			
			return (String[])alSources.toArray(sources);
			
		} catch (Exception e) {
			//System.err.println("Error listSources: " + e.getLocalizedMessage());
			return null;
		}
	}
	
	private boolean checkConnection(String sourceLocation) {
		try {
			//If the source location doesn't correspond to the current schema, the connection
			//will be closed and created again
			if (connection.isClosed() | (!connection.isValid(0))) {
				openSource(sourceLocation);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/* DEBUG ***********************************************************
	ResultSetMetaData rsmd = result.getMetaData();
	System.out.println("TYPE\tSIZE\tNAME");
	System.out.println("----------------------------------------------");
	for (int i=1; i<=rsmd.getColumnCount(); i++) {
		System.out.println(rsmd.getColumnTypeName(i) + "\t" + 
				rsmd.getColumnDisplaySize(i) + "\t" + rsmd.getColumnName(i));				
	}
	********************************************************* */
}
