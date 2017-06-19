package edu.hust.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class ProcedureDemo {

	/**
	 * ��java�����е������ݿ�Ĵ洢���̣��κ����Զ����Ե��ã�ֻ�ǽӿڲ�һ�����ѣ�
	 */
	public static void main(String[] args) {
		Connection ct;
		CallableStatement cs;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			ct = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1","scott","");
			
			//���ô洢���̣��̶���ʽ
			cs=  ct.prepareCall("{call procedure3(?,?)}");
			//���ʺŸ�ֵ
			cs.setString(1, "zhangsan");
			cs.setInt(2, 100);
			
			cs.execute();//�����
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cs=null;
			ct=null;
		}

	}

}
