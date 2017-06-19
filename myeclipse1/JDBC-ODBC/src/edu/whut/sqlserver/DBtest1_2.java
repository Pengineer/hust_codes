package edu.whut.sqlserver;

import java.sql.*;

public class DBtest1_2 {

	/**
	 * ʹ��preparedstatement�������ݿ���䡣���ͳһʹ��preparedstatement��������statement
	 * 
	 * preparedstatement�������ִ��Ч�ʣ���Ϊ����Ԥ����Ĺ��ܣ�
	 * preparedstatement���Է�ֹSQL��ע�룬����Ҫ��ʹ��?��ֵ�ķ�ʽ�ſ���
	 * 
	 * ���磺
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
			ps.setString(2, "Ǯʮ");
			ps.setInt(3, 20);
			ps.setString(4, "2");
			int i = ps.executeUpdate();
			System.out.println("���"+i+"����¼�ɹ�");
			
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
