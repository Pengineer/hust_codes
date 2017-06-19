<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'struts2form.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  <!-- 
                     使用struts的form标签时，action只需要填写struts配置文件里面action的name，namespace是action所在的包的名称空间，但是有时我们也用
                     传统标签，struts对自己的标签有处理（复杂性） 
       <s:token/>翻译成简单标签：<input type="hidden" name="struts.token" value="一串值" /> 
                                                      每产生一次请求，value的值就会变化，Action中struts.token的属性值就会变化，这样就会根据配置文件跳转到指定的JSP              
  -->
    <s:form action="login" namespace="/test" method="post"> 
    	用户名：<s:textfield name="name" label="用户名"/>
    	    <s:token/>
    	    <input type="submit" value="发送"/>
    </s:form>
    
    <s:property value="name"/><br>
    ${attri }
  </body>
</html>
