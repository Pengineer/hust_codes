package edu.hust.fenye;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Demo3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection ct;
		CallableStatement cs;
		ResultSet rs;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl1","scott","");
			cs = ct.prepareCall("{call sp_getInfos(?,?)}");
			cs.setInt(1, 10);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			while(rs.next()){
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rs = null;
			cs = null;
			ct = null;
		}
		

	}

}
