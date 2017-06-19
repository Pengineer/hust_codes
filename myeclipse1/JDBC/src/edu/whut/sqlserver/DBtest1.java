package edu.whut.sqlserver;

import java.sql.*;

public class DBtest1 {

	/**
	 * JDBC方式操作数据库:不需要创建数据源，直接连接数据库
	 * 此方式需要导入三个jar包
	 */
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			//获取驱动
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			//得到连接
			con = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=school","sa","pl");
			//创建发送对象
			ps = con.prepareStatement("select * from student");
			
			//获取结果集
			rs = ps.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString(1)+" "+rs.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//关闭资源
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
