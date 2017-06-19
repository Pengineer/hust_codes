<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
	</head>
	<body>
		<div>
			<ul>
				<li>
					<s:text name = "申请审核"></s:text>
				</li>
			</ul>
		</div>
		<div>
			<table>
				<tr>
					<td>考勤分数</td>
					<td>
						${assess.ascores}
					</td>
				</tr>
				<tr>
					<td>工作表现分数</td>
					<td>
						${assess.pscores}
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td>
						${assess.note}
					</td>
				</tr>
				<tr>
					<td>考核人</td>
					<td>
						${auditorPerson.realName}
					</td>
				</tr>
				<tr>
					<td>被考核人</td>
					<td>
						${accountPerson.realName}
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>