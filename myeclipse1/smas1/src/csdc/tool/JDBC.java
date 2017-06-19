package csdc.tool;

import java.sql.*;


/**
 * 此类使用JDBC连接数据库
 * @author 雷达
 * @version 1.0
 *
 */
public class JDBC {
	
	/**
	 * 驱动名
	 */
	private String sDBDriver = "oracle.jdbc.xa.client.OracleXADataSource";
	/**
	 * 连接地址
	 */
	private String sConnStr = "jdbc:oracle:thin:@192.168.88.220:1521:orcl";
	/**
	 * 连接类
	 */
	private Connection conn = null;
	
	/**
	 * 构造类
	 */
	public JDBC() {
		try {
			Class.forName(sDBDriver).newInstance();
		}
		catch (Exception e) {
			//System.out.println(e);
		}
	}
	
	/**
	 * 创建JDBC连接
	 * @return 布尔值表示是否建立连接成功
	 */
	public boolean createConn() {
		try {
			conn = DriverManager.getConnection(sConnStr, "csdc", "csdc123");
		}
		catch (Exception e) {
			//System.out.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * 执行sql语句并返回结果
	 * @param sql sql查询语句
	 * @return sql查询结构
	 */
	public ResultSet executeQuery(String sql) {
		ResultSet rs;
		if(conn == null) {
			createConn();
		}
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e) {
			//System.out.println(e);
			return null;
		}
		return rs;
	}
	
	/**
	 * 执行sql语句
	 * @param sql sql执行语句
	 * @return 是否执行成功
	 */
	public boolean executeStatement(String sql) {
		if(conn == null) {
			createConn();
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			//System.out.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * 关闭连接
	 */
	public void closeConn() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch (Exception e) {
				//System.out.println(e);
			}
			finally {
				conn = null;
			}
		}
	}
}
