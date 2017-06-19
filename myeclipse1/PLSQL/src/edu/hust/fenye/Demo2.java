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
			
			//输入雇员编号
			cs.setString(1, "7788");
			//为所有的输出？注册返回类型
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
			
			cs.execute();//先执行，在取值
			
			String name = cs.getString(2);
			System.out.println("编号为7788的雇员是：" + name);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cs = null;
			ct = null;
		}

	}

}
