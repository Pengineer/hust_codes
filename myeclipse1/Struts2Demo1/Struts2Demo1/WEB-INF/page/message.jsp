<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>ognl表达式用于获取属性值（ValueStack（里面存action对象）+parameters+request+session+application+attr）</title>
  </head>

  <body>
  
  <%
  	request.getSession.setAttribute("toOgnl", "ognlValue");
   %>
  
  
  <!-- 当Struts2接受一个请求时，会迅速创建ActionContext，ValueStack，action 。然后把action存放进ValueStack，所以action的实例
                     变量可以被OGNL访问。 （Context是上下文，说白了上下文就是一个MAP结构，在Struts2中它包含ValueStack（里面存对象）+parameters+request+session+application+attr）
       EL表达式${id }的底层执行顺序（该表达式被封装了）：首先通过getattribute(“id”)，查找其值，如果没找到，就会查找ValueStack里面的栈顶对象，看它
                                                                                                               有没有id属性，如果还是没有，就会再查找栈里面的下一个对象，直到最后一个，要是还没找到，就返回null。
                   这也是为什么我们在Action里面没有将methodInfo放到request里面，但是我们还是可以获取其值，因为methodInfo在action方法里面被直接赋值了。 
                   但是EL表达式只能访问request和ValueStack里面的属性值 ，parameters+session+application+attr里面的不能访问。                                                                                                                          
   --> 
     ${id }<br>   
     ${name }<br>   
     ${methodInfo }<br>
     
     <!-- 使用ognl访问属性值(#xxx) -->
     <s:property value="methodInfo"/>  <!-- 访问栈里面对象的属性值 -->
     <s:property value="#session.toOgnl"/> <!-- 访问非栈的属性值 -->
  </body>
</html>
