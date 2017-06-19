package csdc.tool.execution.importer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import csdc.tool.reader.ExcelReader;

public class TestImporter extends Importer {
	
	private ExcelReader reader;
	
	public void work() throws Exception {
		Connection connection = DriverManager.getConnection("Provider=mondrian; Jdbc=jdbc:mysql://192.168.88.220:3306/experts; JdbcUser=experts; JdbcPassword=experts123; Catalog=file:///C:/Program Files/Tomcat 5.0/webapps/mondrian/WEB-INF/queries/s.xml; JdbcDriver=com.mysql.jdbc.Driver", null);

		if(!connection.isClosed()) {
    		System.out.println("Succeeded connecting to the Database!");     //验证是否连接成功
    	}
		
		String driver = "com.mysql.jdbc.Driver";         // 驱动程序名
        String url = "jdbc:mysql://192.168.88.220:3306/experts";     // URL指向要访问的数据库名scutcs          
        String user = "experts";       // MySQL配置时的用户名
        String password = "experts123";      // MySQL配置时的密码

        try { 
         
        	Class.forName(driver);    // 加载驱动程序

        
        	Connection conn = DriverManager.getConnection(url, user, password);      // 连续数据库

        	if(!conn.isClosed()) {
        		System.out.println("Succeeded connecting to the Database!");     //验证是否连接成功
        	}
         
	         Statement statement = conn.createStatement();               // statement用来执行SQL语句
	         String sql = "select * from newexpert";                  // 要执行的SQL语句
	         ResultSet rs = statement.executeQuery(sql);       // 结果集
	
	         System.out.println("-----------------------------------------");
	         System.out.println("执行结果如下所示:");
	         System.out.println("-----------------------------------------");
	         System.out.println("姓名" + "\t" + "生日" + "\t\t" + "性别");
	         System.out.println("-----------------------------------------");
	
	         String name = null;
	
	         while(rs.next()) {
	        	 name = rs.getString("name");                            									// 选择name这列数据
	        	 System.out.println(name + "\t" + rs.getString("birthday") + "\t" + rs.getString("sex_id"));        // 输出结果
	         }
         	rs.close();
         	conn.close();
        } catch(ClassNotFoundException e) {
        	System.out.println("Sorry,can`t find the Driver!"); 
         	e.printStackTrace();
        } catch(SQLException e) {
        	e.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        } 
		
		
//		reader.readSheet(0);
//		
//		while (next(reader)) {
//			System.out.println(N);
//		}
	}
	
	public TestImporter(){
	}
	
	public TestImporter(String file) {
		reader = new ExcelReader(file);
	}

}
