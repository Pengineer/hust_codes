package edu.hust.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class ProcedureDemo {

	/**
	 * 在java程序中调用数据库的存储过程（任何语言都可以调用，只是接口不一样而已）
	 */
	public static void main(String[] args) {
		Connection ct;
		CallableStatement cs;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			ct = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1","scott","");
			
			//调用存储过程，固定格式
			cs=  ct.prepareCall("{call procedure3(?,?)}");
			//给问号赋值
			cs.setString(1, "zhangsan");
			cs.setInt(2, 100);
			
			cs.execute();//别掉了
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cs=null;
			ct=null;
		}

	}

}
