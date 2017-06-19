package edu.hust.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 * @author liangjian
 * JDBC��Java Data Base Connectivity,java���ݿ����ӣ�:��һ������ִ��SQL����Java API������Ϊ���ֹ�ϵ���ݿ��ṩͳһ���ʣ�����һ����
 *                     Java���Ա�д����ͽӿ���ɡ�JDBC�ṩ��һ�ֻ�׼���ݴ˿��Թ������߼��Ĺ��ߺͽӿڣ�ʹ���ݿ⿪����Ա�ܹ���д���ݿ�Ӧ�ó���.
 *                     JDBC��SUN��˾�ƶ�����Ϊ��java��������
 * JDBC����һ��API����Ҫ�ɽӿڹ��ɣ�ʵ������٣�������ΪJDBC�����ṩһЩ�ӿڣ���˲�����ִ�о��������Ҫ��������ݿ⣬�����ṩʵ���࣬��Щʵ�������Driver��
 * Ҳ�������ǳ�˵��������ÿһ�����ݿⶼ��һ��Driver�������Ŀ���������SUN��˾��������ֻ�ṩ�ӿڣ�����������ʵ���ɸ����ݿ⳧�̵��Լ�ʵ�֡�
 * 
 * ֱ��ʹ��JDBC�������ݿ��ǲ���Ҫ��������Դ�ģ�������ҪojdbcX.jar��
 */
public class DBtest1 {
	public static void main(String[] args){
		test();
		
	}
	 
	static void test(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			//����������OJDBC6.jar����ʵ����JDBC�ӿڵ���������jar���ŵ������º󣬾ͻ���classpath������һ��·�������ǽ�jar�������class�ļ����ص��ڴ棩
			Class.forName("oracle.jdbc.OracleDriver");//����дoracle.jdbc.driver.OracleDriver�����ԣ���jar���п��Բ鵽
			//�õ�����(��д����Զ�� ���ݿ�ĵ�ַ������ϵͳ�û�ʱ��username=sys as sysdba)(jdbc:oracle:thinָ���ݿ�ͨ��ʹ�õ�Э�飬jdbcָ����ʹ�õ�API,һ��JDBC����������֧�ֶ���Э��)
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.88.176:1521:orcl","scott","peng123");
			//�������Ͷ���
			ps = con.prepareStatement("select table_name ,tablespace_name from user_tables");
			
			//��ȡ�����(ÿһ���������һ�����ݣ������ݹ����Ƿ���һ��list������)
			rs = ps.executeQuery();
			
			while(rs.next()){  
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t");//�����֪����������ͣ�����getObject(�к�)
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
