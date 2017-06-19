package edu.whut.sqlserver;

import java.sql.*;

public class DBtest1 {

	/**
	 * JDBC��ʽ�������ݿ�:����Ҫ��������Դ��ֱ���������ݿ�
	 * �˷�ʽ��Ҫ��������jar��
	 */
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			//��ȡ����
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			//�õ�����
			con = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=school","sa","pl");
			//�������Ͷ���
			ps = con.prepareStatement("select * from student");
			
			//��ȡ�����
			rs = ps.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString(1)+" "+rs.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//�ر���Դ
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
