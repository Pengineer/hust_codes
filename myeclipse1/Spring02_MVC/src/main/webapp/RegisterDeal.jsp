<%@ page language="java" import="java.util.*, java.sql.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.88.176:3306/sshDB","ssh","peng123");

String username=request.getParameter("username");
String password=request.getParameter("password");

String sqlQuery="select count(*) from t_user where c_username=?";
PreparedStatement ps = conn.prepareStatement(sqlQuery);
ps.setString(1, username);
ResultSet rs = ps.executeQuery();
rs.next();
if(rs.getInt(1) > 0) {
	rs.close();
	ps.close();
	response.sendRedirect("RegisterFail.jsp");
	return;
}

String sql="insert into t_user values(null,?,?)";
PreparedStatement ps1 = conn.prepareStatement(sql);
ps1.setString(1, username);
ps1.setString(2, password);
ps1.executeUpdate();
ps1.close();
response.sendRedirect("RegisterSuccess.jsp");
conn.close();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户注册</title>
  </head>
  
  <body>
  </body>
</html>
