<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>体验SUN公司强大的c:Foreach标签(在于它的属性)</title>
  </head>
  
  <body>
  <h1>体验SUN公司强大的c:Foreach标签(在于它的属性)</h1>
  <style>
  	.even{background-color: #FF99FF;}
  	.odd{background-color: #FF6633;}
  	tr:HOVER {background-color: #0000FF;}  
  </style>       <%--tr:HOVER的作用是用于提示用户鼠标在哪一行，那一行就显示不同的颜色 --%>
    <%
    	List list = new ArrayList();
    	list.add("aaa");
    	list.add("bbb");
    	list.add("ccc");
    	list.add("ddd");
    	list.add("eee");
    	list.add("fff");
    	request.setAttribute("list", list);
     %>
     <table border="1" width="20%" align="center" >
     	<c:forEach var="col" items="${list }" varStatus="status"><%--varStatus对象的count属性用于记住当前迭代的是第几次 --%>
     		<tr class="${status.count%2==0?'even':'odd'}"> <%--通过count来设定每一行的样式 --%>
     			<th>${col }</td>
     		</tr>
     	</c:forEach>
     </table>
  </body>
</html>
