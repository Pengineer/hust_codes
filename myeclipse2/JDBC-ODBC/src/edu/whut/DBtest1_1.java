package edu.whut;

/**
 * ʹ��JDBC-ODBC�Žӷ�ʽ�������ݿ⣬���裺
 * 1����������Դ   �������->ϵͳ�Ͱ�ȫ->������->����Դ��ODBC��
 * 2���ڳ�������������Դ���̶�д����
 */

import java.sql.*;

public class DBtest1_1 {

	public static void main(String[] args) {
		Connection con = null;
		Statement sm = null;
		
		try {
			//1����������������Ҫ��������������ڴ�
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			//2���õ����ӣ�ָ�����ӵ��ĸ����ݿ⣨�ڶ���������ȡ��������Դ�����ã�
			con = DriverManager.getConnection("jdbc:odbc:mytest", "sa", "pl");
			
			//3������statement����preparedstatement:���ڷ���SQL��䵽���ݿ�
			sm = con.createStatement(); 
			
			//4��ִ��crud���������ݿ⣬�������ݿ⣬ɾ�����ݿ⡣��������
			
			//4.1 executeUpdate�ɽ��еĲ�����cud:��ӣ�ɾ�����޸ģ�������һ������
			
			//int i = sm.executeUpdate("insert into student values('0121009330303','���','18','1')");			
			//System.out.println("���"+i+"����¼�ɹ�");  
 			
			//int i = sm.executeUpdate("delete from student where stuid = '0121009330303'");
			//System.out.println("ɾ��"+i+"����¼�ɹ�");
			
			//int i = sm.executeUpdate("update student set stuage = '19' where stuid = '0121009330303'");
			//System.out.println("�޸�"+i+"����¼�ɹ�");
			
			//4.2 ��ѯʹ�õ���executeQuery�����ص���һ�������ResultSet,������һ�����еĽ�������������Ϊһ���α����ָ����ָ���һ�е�����һ��
			//    ÿ��ִ��rs.next()���α�ͻ�ָ����һ��
			ResultSet rs = sm.executeQuery("select * from student");
			while(rs.next()){
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getString(4));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//����������ر���Դ���������ӵ����ݻ�Խ��Խ��֪��ϵͳ�ڴ����:ԭ�����ȴ����ĺ�رգ�����
			try{
				if(sm!=null){
					sm.close();
				}
				if(con!=null){
					con.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}

}
