<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/iteratortag" prefix="ihust" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>创建自定义的迭代标签（系统为foreach）</title>
  </head>
  
  <body>&nbsp; 
  	<!-- 1,使用SUN公司的标签 -->
    <%
    	ArrayList list = new ArrayList();
    	list.add("aaa");
    	list.add("bbb");
    	list.add("ccc");
    	list.add("ddd");
    	request.setAttribute("list", list);
     %>
     <c:forEach var="data" items="${list }">
     	${data }<br>
     </c:forEach>    
         
   <!-- 2,使用自定义标签迭代list集合 -->
     <ihust:foreach var="mydata" items="${list }">
     	${mydata }<br>
     </ihust:foreach>
     
     <ihust:ForEachAll items="${list }" var="alllist">
     	${alllist }<br>
     </ihust:ForEachAll>
     
     <!-- 3,自定义标签迭代map集合 -->
     <%
     	Map map = new HashMap();
     	map.put("aaa", "AAA");
     	map.put("bbb", "BBB");
     	map.put("ccc", "CCC");
     	map.put("ddd", "DDD");
     	request.setAttribute("map", map);
      %>
     <ihust:ForEachAll items="${map }" var="allmap">
     	${allmap.key } : ${allmap.value }<br>
     </ihust:ForEachAll>
     
     <!-- 4,自定义标签迭代int数组 -->
     <%
     	int arr[] = {1,2,3,4};
     	request.setAttribute("arr", arr);
      %>
      <ihust:ForEachAll items="${arr }" var="inta">
      	${inta }<br>
      </ihust:ForEachAll>
      
      <!-- 5,自定义标签迭代boolean数组 -->
      <%
      	boolean b[] = {true,false,true,true};
      	request.setAttribute("bo", b);
       %>
       <ihust:ForEachAll items="${bo }" var="i">
       	${i }<br>
       </ihust:ForEachAll>
     
  </body>
</html>
