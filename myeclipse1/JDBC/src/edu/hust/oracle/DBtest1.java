package edu.hust.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 * @author liangjian
 * JDBC（Java Data Base Connectivity,java数据库连接）:是一种用于执行SQL语句的Java API，可以为多种关系数据库提供统一访问，它由一组用
 *                     Java语言编写的类和接口组成。JDBC提供了一种基准，据此可以构建更高级的工具和接口，使数据库开发人员能够编写数据库应用程序.
 *                     JDBC由SUN公司制定（因为用java来连）。
 * JDBC就是一个API，主要由接口构成，实现类很少，正是因为JDBC仅仅提供一些接口，因此并不能执行具体操作，要想访问数据库，必须提供实现类，这些实现类就是Driver，
 * 也就是我们常说的驱动。每一个数据库都有一个Driver，驱动的开发并不是SUN公司来做，它只提供接口，具体驱动的实现由各数据库厂商的自己实现。
 * 
 * 直接使用JDBC操作数据库是不需要配置数据源的，但是需要ojdbcX.jar包
 */
public class DBtest1 {
	public static void main(String[] args){
		test();
		
	}
	 
	static void test(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			//加载驱动（OJDBC6.jar就是实现了JDBC接口的驱动。将jar包放到工程下后，就会在classpath下生成一个路径，就是将jar包里面的class文件加载到内存）
			Class.forName("oracle.jdbc.OracleDriver");//或则写oracle.jdbc.driver.OracleDriver都可以，在jar包中可以查到
			//得到连接(填写完整远程 数据库的地址，当是系统用户时，username=sys as sysdba)(jdbc:oracle:thin指数据库通信使用的协议，jdbc指的是使用的API,一个JDBC驱动器可能支持多种协议)
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.88.176:1521:orcl","scott","peng123");
			//创建发送对象
			ps = con.prepareStatement("select table_name ,tablespace_name from user_tables");
			
			//获取结果集(每一条结果都是一行数据，行数据估计是放在一个list集合中)
			rs = ps.executeQuery();
			
			while(rs.next()){  
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t");//如果不知道具体的类型，就用getObject(列号)
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//关闭资源
			try{
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(con!=null){
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
