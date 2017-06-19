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
			
			cs.setString(1,"emp2"); //���ñ���
			cs.setInt(2, 5);  //����ÿҳ��ʾ����
			cs.setInt(3, 3);  //���õ�ǰҳ����ʵ����������������JSPҳ���������֣�
			
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.INTEGER); //���ù��̵õ��ܼ�¼��
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.INTEGER); //���ù��̵õ���ҳ��
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);  //�õ������
			
			cs.execute();
			
			int totalRecord = cs.getInt(4);
			int totalPage = cs.getInt(5);
			System.out.println("�ܼ�¼����" + totalRecord + ",��ҳ����" + totalPage);
			rs = (ResultSet) cs.getObject(6);
			while(rs.next()){
				System.out.println("��ţ�"+ rs.getInt(1) +"  ������"+ rs.getString(2) +"  ����:"+ rs.getString(3) +"  ����:"+ rs.getDouble(6));
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
