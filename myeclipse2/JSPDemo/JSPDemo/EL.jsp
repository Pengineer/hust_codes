<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="edu.hust.domain.Person"%>
<%@ page import="edu.hust.domain.Student"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>EL表达式</title>
  </head>
  
  <body>
    
    <%--1,数据以普通方式带给jsp：模拟servlet代码:标准的jsp中是不允许出现一行java代码的 --%>
	<%
		String data = "hello,EL";
		request.setAttribute("data", data);
	 %>
	 
	<%--通过EL表达式获取属性值 --%> 
	${data }  <%--等效于pageContext.findAttribute("data") 它会依次去查找四个域：page request session application --%>
  
  	<br><br>
  
  	<%--2,数据通过JavaBean带过来： --%>
  	<%
  		Person p = new Person();
  		p.setName("Json");
  		request.setAttribute("person", p);//将数据封装到request域中
  	 %>
  	 ${person.name }
  	 
  	 <br><br>
  	 
  	 <%--3,数据以list集合的方式带过来 :--%>
  	 <%
  	 	ArrayList<Student> list = new ArrayList<Student>();
  	 	list.add(new Student("aa"));
  	 	list.add(new Student("bb"));
  	 	list.add(new Student("cc"));
  	 	request.setAttribute("students", list);
  	 %>
  	 ${students[1].name }
  	 
  	 <br><br>
  	 
  	 <%--4,数据以map集合的方式带过来 --%>
  	 <%
  	 	Map<String,Student> map = new HashMap<String,Student>();
  	 	map.put("a", new Student("aaa"));
  	 	map.put("b", new Student("bbb"));
  	 	map.put("c", new Student("ccc"));
  	 	request.setAttribute("map", map);
  	  %>
  	  ${map.a.name }     <%--map.a表示获取到键为a的元素 --%>
  	  ${map['a'].name }  <%--EL表达式取数据一般用.号，如果取不出来（关键字为数字）时，也可换成[''] --%>
  	  
  	  <%--5,通过EL表达式获取工程应用路径，从而避免工程路径被本地写死 --%>
  	  <a href="${pageContext.request.contextPath }/index.jsp">回到首页</a>
  	  <br><br>
  	  ${pageContext.request.contextPath }<%--相当于pageContext.getRequest().contextPath(),先获取request域对象 --%> 
  	  
  </body>
</html>
