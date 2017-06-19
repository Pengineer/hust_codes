<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<base href="<%=basePath%>" />
			<title>立项核算</title>
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui-1.8.5.custom.css" />
		</head>

		<body>
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							项目管理&nbsp;&gt;&gt;
							<span class="text_red">立项核算</span>
						</td>
					</tr>
				</table>
			</div>
				
			<div class="main">
				<div class="main_content" style="display:none;" id="tabcontent">
					<s:include value="/project/finance/viewCommon.jsp" />
					
					<div class="main_content1">
						<div id="tabs" class="p_box_bar">
							<ul>
								<li style=""><a href="#main">一般项目</a></li>
								<li><a href="#west">西部项目</a></li>
								<li><a href="#xinjiang">新疆项目</a></li>
								<li><a href="#tibet">西藏项目</a></li>
								<li><a href="#self">自筹项目</a></li>
								<li style="width:360px;"><input class="btn" style="height: 23px;margin-left:490px;" type="button" id="finish" value="保存结果" /></li>
							</ul>
						</div>

						<div class="p_box">
							<s:include value="/project/finance/viewMain.jsp" />
							<s:include value="/project/finance/viewWest.jsp" />
							<s:include value="/project/finance/viewXinjiang.jsp" />
							<s:include value="/project/finance/viewTibet.jsp" />
							<s:include value="/project/finance/viewSelf.jsp" />
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.cookie.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-1.8.5.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/template_tool.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/project.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/finance/finance.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>