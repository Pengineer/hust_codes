<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>执行</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<br/>
			<span style="color:#818080;"><s:text name="1、执行 bean（事务控制），保证工作任务在一个事务内，为原子操作。" /></span>
			<s:form action="execute" namespace="/execution">
				<span>Executin Bean Name: </span>
				<input name="executionBeanName" size="100" />
				<input id="execute" type="submit" class="btn1" value="执行" />
			</s:form>
			<br/>
			<span style="color:#818080"><s:text name="2、执行 bean（无事务控制），可自己在bean中实现事务控制。" /></span>
			<s:form action="excuteNoTransaction" namespace="/execution">
				<span>Executin Bean Name: </span>
				<input name="executionBeanName" size="100" />
				<input id="executeNoTransaction" class="btn1" type="submit" value="执行" />
			</s:form>
		</body>
	
</html>
