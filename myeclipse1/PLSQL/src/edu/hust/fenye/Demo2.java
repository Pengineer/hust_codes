package edu.hust.fenye;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class Demo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection ct;
		CallableStatement cs;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl1","scott","");
			cs = ct.prepareCall("{call sp_getName(?,?)}");
			
			//�����Ա���
			cs.setString(1, "7788");
			//Ϊ���е������ע�᷵������
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
			
			cs.execute();//��ִ�У���ȡֵ
			
			String name = cs.getString(2);
			System.out.println("���Ϊ7788�Ĺ�Ա�ǣ�" + name);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cs = null;
			ct = null;
		}

	}

}
