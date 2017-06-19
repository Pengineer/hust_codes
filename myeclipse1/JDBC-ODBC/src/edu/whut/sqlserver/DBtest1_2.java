package edu.whut.sqlserver;

import java.sql.*;

public class DBtest1_2 {

	/**
	 * 使用preparedstatement发送数据库语句。今后统一使用preparedstatement，而不用statement
	 * 
	 * preparedstatement可以提高执行效率（因为它有预编译的功能）
	 * preparedstatement可以防止SQL的注入，但是要求使用?赋值的方式才可以
	 * 
	 * 比如：
	 */
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:mytest","sa","pl");
			
			//ps = con.prepareStatement("select * from student where stuid = '0121009330303'");
			//rs = ps.executeQuery();
			//while(rs.next()){
			//	System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getString(4));
			//}
			
			ps = con.prepareStatement("insert into student values(?,?,?,?)");
			ps.setString(1, "0121009320303");
			ps.setString(2, "钱十");
			ps.setInt(3, 20);
			ps.setString(4, "2");
			int i = ps.executeUpdate();
			System.out.println("添加"+i+"条记录成功");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(con!=null){
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}

}
