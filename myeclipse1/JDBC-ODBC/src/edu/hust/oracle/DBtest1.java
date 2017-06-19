package edu.hust.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author liangjian
 * ʹ��JDBC-ODBC��������Ҫ��������Դ�ģ�����������ʱ���Բ���Ҫjar������ΪODBC��΢������һ�׷������ݿ��API��ODBC�������Ѿ�������ϵͳ�����ˣ�
 * ����ֻ��Ҫ���ء�������ͨ��java�������ݿ�ʱ�������Ƚ���Java�����c,Ȼ��ͨ��ODBC�������ǵ�����ȥ�������ݿ⣬�������ּ���ֻ����΢��Ĳ���ϵͳ��
 * ʵ�֣�����Ч�ʱ�JDBC������Ϊ�и��м䷭����̣�
 * 
 * �����˼�����ԭ����Щ���ݿ⳧�̵�����ֻ֧��ODBC������֧��JDBC��
 */
public class DBtest1 {
	public static void main(String[] args) {
		Connection con = null;
		Statement sm = null;
		
		try {
			//1������ODBC����������Ҫ��������������ڴ�
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			//2���õ����ӣ�ָ�����ӵ��ĸ����ݿ⣨�ڶ���������ȡ��������Դ�����ã�
			con = DriverManager.getConnection("jdbc:odbc:oracletest", "scott", "");
			
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
			ResultSet rs = sm.executeQuery("select table_name from user_tables");
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//����������ر���Դ���������ӵ����ݻ�Խ��Խ��ֱ��ϵͳ�ڴ����:ԭ�����ȴ����ĺ�رգ�����
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
