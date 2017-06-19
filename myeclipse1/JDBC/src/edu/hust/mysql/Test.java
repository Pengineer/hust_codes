package edu.hust.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	
	private final static String url = "jdbc:mysql://192.168.88.176:3306/test_1";//jdbc:子协议:子名称://主机名：端口/数据库名?属性名=属性值
	private final static String user = "u_test";
	private final static String password = "peng123";
	
	public static void main(String[] args) {
		operator();
	}
	
	public static void operator() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement("select * from t_table");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println("数据读取失败");
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("资源释放失败");
			}
		}
	}
	
	
}
