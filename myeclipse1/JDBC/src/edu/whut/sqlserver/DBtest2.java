package edu.whut.sqlserver;

import java.sql.*;

public class DBtest2 {

	/**
	 * JDBC��ʽ�������ݿ�:ʹ��DDL��䣬���磺�������ݿ�,�������ݿ�
	 */
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;

		try{
			//��ȡ����
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			//�õ�����
			con = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=school","sa","pl");
			//�������Ͷ���
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
			//�ر���Դ
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
