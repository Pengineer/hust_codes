<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  <%--需要导入jar包的URI地址--%>
<%@ page import="edu.hust.domain.Person"%>
<%@ page import="edu.hust.domain.Student"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <title>使用JSTL+EL完成集合迭代</title>
  </head>
  
  <body>
  	<%--1,遍历list集合:--%>
    <%
  	 	ArrayList<Student> list = new ArrayList<Student>();
  	 	list.add(new Student("aa"));
  	 	list.add(new Student("bb"));
  	 	list.add(new Student("cc"));
  	 	request.setAttribute("list", list);
  	 %>
  	 <c:forEach var="student" items="${list }">  <%--从list集合中获取元素并赋给student --%>
  	 	${student.name }
  	 </c:forEach><br/>
  	 
  	 <%--2,遍历map集合:--%>
  	 <%
  	 	Map<String,Student> map = new HashMap<String,Student>();
  	 	map.put("a", new Student("aaa"));
  	 	map.put("b", new Student("bbb"));
  	 	map.put("c", new Student("ccc"));
  	 	request.setAttribute("map", map);
  	  %>
  	 <c:forEach var="entrySet" items="${map }">
  	 	${entrySet.key } : ${entrySet.value.name }<br/>   <%--key和value是entrySet的两个属性：有get方法 --%>
  	 </c:forEach>
  	 
  	 <%--3,还有一种应用场景就是判断用户登录：使用if标签 --%>
  	 <c:if test="${user!=null }">
  	 	欢迎您：${user.username }
  	 </c:if>
  	 <c:if test="${user==null }">
  	 	用户名：<input type="text" name="username"/>
  	 	密码：<input type="password" name="password"/>
  	 </c:if>
  	 
  </body>
</html>
