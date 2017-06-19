package edu.whut.sqlserver;

import java.sql.*;

public class DBtest2 {

	/**
	 * JDBC方式操作数据库:使用DDL语句，例如：创建数据库,备份数据库
	 */
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;

		try{
			//获取驱动
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			//得到连接
			con = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=school","sa","pl");
			//创建发送对象
		//	ps = con.prepareStatement("create database test");
			ps = con.prepareStatement("backup database test to disk='E:\\programming exercises/MyEclipse/JDBC/test.bak'");
			
			boolean b = ps.execute();
			if(!b){
				System.out.println("operate success");
			}else{
				System.out.println("operate fail");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//关闭资源
			try{
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
