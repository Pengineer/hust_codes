package edu.hust.fenye;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class Demo1 {

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
			cs=  ct.prepareCall("{call sp_procedure(?,?,?)}");
			//���ʺŸ�ֵ
			cs.setInt(1, 1);
			cs.setString(2, "oracle�����ŵ���ͨ");
			cs.setString(3, "��е��ҵ������");
			
			cs.execute();//�����
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cs=null;
			ct=null;
		}

	}

}

