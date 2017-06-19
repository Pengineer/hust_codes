package csdc.tool.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;


/**
 * jdbc数据读取器
 * @author xuhan
 *
 */
public class JdbcReader implements TableDataReader {
	
	private String driverClassName;
	
	private String url;
	
	private String username;
	
	private String password;
	
	/**
	 * 已读取了多少行正式数据
	 */
	private int currentRowIndex;
	
	private int columnNumber;
	
	/**
	 * 当前结果集
	 */
	public ResultSet resultSet;

	
	public JdbcReader(String driverClassName, String url, String username, String password) {
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public void query(String sql) throws Exception {
		Properties prop = new Properties();
		prop.put("user", username);
		prop.put("password", password);

		Class.forName(driverClassName);
		Connection conn = DriverManager.getConnection(url, prop); 
		Statement stmt = (Statement)conn.createStatement();

		resultSet = stmt.executeQuery(sql);
		ResultSetMetaData data = resultSet.getMetaData();
		
		columnNumber = data.getColumnCount();
		
		currentRowIndex = 0;
	}

	public boolean hasNext() throws Exception {
		return resultSet.next();
	}

	public String[] next() throws Exception {
		String[] row = new String[columnNumber];
		for (int i = 1; i <= columnNumber; i++){
			String cellContent = resultSet.getString(i);
			row[i - 1] = cellContent == null ? "" : cellContent;
		}
		++currentRowIndex;
		return row;
	}

	public int getCurrentRowIndex() {
		return currentRowIndex;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public int getRowNumber() {
		throw new RuntimeException("Unsupported method");
	}

}
