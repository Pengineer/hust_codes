package edu.whut;

/**
 * 使用JDBC-ODBC桥接方式操作数据库，步骤：
 * 1，配置数据源   控制面板->系统和安全->管理工具->数据源（ODBC）
 * 2，在程序中连接数据源（固定写法）
 */

import java.sql.*;

public class DBtest1_1 {

	public static void main(String[] args) {
		Connection con = null;
		Statement sm = null;
		
		try {
			//1，加载驱动：把需要的驱动程序加入内存
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			//2，得到连接：指定连接到哪个数据库（第二三个参数取决于数据源的配置）
			con = DriverManager.getConnection("jdbc:odbc:mytest", "sa", "pl");
			
			//3，创建statement或则preparedstatement:用于发送SQL语句到数据库
			sm = con.createStatement(); 
			
			//4，执行crud（创建数据库，备份数据库，删除数据库。。。。）
			
			//4.1 executeUpdate可进行的操作有cud:添加，删除和修改，并返回一个整型
			
			//int i = sm.executeUpdate("insert into student values('0121009330303','李九','18','1')");			
			//System.out.println("添加"+i+"条记录成功");  
 			
			//int i = sm.executeUpdate("delete from student where stuid = '0121009330303'");
			//System.out.println("删除"+i+"条记录成功");
			
			//int i = sm.executeUpdate("update student set stuage = '19' where stuid = '0121009330303'");
			//System.out.println("修改"+i+"条记录成功");
			
			//4.2 查询使用的是executeQuery，返回的是一个结果集ResultSet,并且是一个表行的结果集，可以理解为一个游标或则指定，指向第一行的上面一行
			//    每次执行rs.next()后，游标就会指向下一行
			ResultSet rs = sm.executeQuery("select * from student");
			while(rs.next()){
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getString(4));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//！！！必须关闭资源，否则连接的数据会越来越大，知道系统内存溢出:原则是先创建的后关闭！！！
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
