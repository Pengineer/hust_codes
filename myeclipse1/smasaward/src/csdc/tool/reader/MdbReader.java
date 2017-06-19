package csdc.tool.reader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;


/**
 * MDB(MS Access)数据读取器
 * @author xuhan
 *
 */
public class MdbReader implements TableDataReader {
	
	/**
	 * mdb文件
	 */
	private File file;

	/**
	 * 已读取了多少行正式数据
	 */
	private int currentRowIndex;
	
	private int columnNumber;
	
	/**
	 * 当前结果集
	 */
	public ResultSet resultSet;

	
	public MdbReader(File file) {
		this.file = file;
	}
	
	public MdbReader(String filePath) {
		this.file = new File(filePath);
	}
	
	public void query(String sql) throws Exception {
		Properties prop = new Properties();
		prop.put("charSet", "gb2312");	//这里是解决中文乱码
		prop.put("user", "");
		prop.put("password", "");
		String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + file.getAbsolutePath();   //文件地址

		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
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
