package edu.hust.fenye;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FenYe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection ct = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			ct = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1","scott","");
			cs = ct.prepareCall("{call fenye(?,?,?,?,?,?)}");
			
			cs.setString(1,"emp2"); //设置表名
			cs.setInt(2, 5);  //设置每页显示个数
			cs.setInt(3, 3);  //设置当前页（真实情况下是我们鼠标在JSP页面点击的数字）
			
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.INTEGER); //调用过程得到总记录数
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.INTEGER); //调用过程得到总页数
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);  //得到结果集
			
			cs.execute();
			
			int totalRecord = cs.getInt(4);
			int totalPage = cs.getInt(5);
			System.out.println("总记录数：" + totalRecord + ",总页数：" + totalPage);
			rs = (ResultSet) cs.getObject(6);
			while(rs.next()){
				System.out.println("编号："+ rs.getInt(1) +"  姓名："+ rs.getString(2) +"  工作:"+ rs.getString(3) +"  工资:"+ rs.getDouble(6));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				ct.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			rs = null;
			cs = null;
			ct = null;
		}

	}

}
