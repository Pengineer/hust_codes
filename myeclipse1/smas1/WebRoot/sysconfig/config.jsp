<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:head/>
		<title>SMAS</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
	</head>

	<body>
		<div id="container">
			<div id="top">
				系统管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">系统配置</span>
			</div>
			
			<div id="bottom">
				<div align="center" style="line-height:30px;">
					<ul>
<%--							<li><a href="main/toConfigUpload.action">配置文件上传路径</a></li>--%>
						<li><a href="main/toConfigPageSize.action">配置每页显示记录数</a></li>
<%--							<li><a href="main/toConfigSelect.action">配置下拉框</a></li>--%>
						<li><a href="main/toConfigYear.action">配置年份</a></li>
						<li><a href="main/toConfigMail.action">邮件配置</a></li>
						<s:if test="#session.visitor.user.issuperuser == 1">
							<li><a href="main/toConfigSystemStatus.action">配置系统状态</a></li>
							<li><a href="execution/toExecute.action">execution</a></li>
							<li><a href="expert/exportExcel.action" onclick="return confirm('您确定要导出最新MOEExpert数据吗？');">专家导出</a></li>
							<li><a href="main/dataManage.action">数据管理</a></li>
						</s:if>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>
