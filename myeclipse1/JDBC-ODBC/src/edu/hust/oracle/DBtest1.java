package edu.hust.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author liangjian
 * 使用JDBC-ODBC技术是需要配置数据源的，但是我们这时可以不需要jar包，因为ODBC是微软开发的一套访问数据库的API，ODBC的驱动已经被操作系统集成了，
 * 我们只需要加载。当我们通过java访问数据库时，会首先将将Java翻译成c,然后通过ODBC根据我们的配置去访问数据库，但是这种技术只能在微软的操作系统上
 * 实现（而且效率比JDBC慢，因为有个中间翻译过程）
 * 
 * 开发此技术的原因：有些数据库厂商的驱动只支持ODBC，但不支持JDBC。
 */
public class DBtest1 {
	public static void main(String[] args) {
		Connection con = null;
		Statement sm = null;
		
		try {
			//1，加载ODBC驱动：把需要的驱动程序加入内存
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			//2，得到连接：指定连接到哪个数据库（第二三个参数取决于数据源的配置）
			con = DriverManager.getConnection("jdbc:odbc:oracletest", "scott", "");
			
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
			ResultSet rs = sm.executeQuery("select table_name from user_tables");
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//！！！必须关闭资源，否则连接的数据会越来越大，直到系统内存溢出:原则是先创建的后关闭！！！
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
