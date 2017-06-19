<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>选择评审专家</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
		</head>
		
		<body>
			<div id="container">
				<div id="poj_info" class="base_info" onclick="toggle_view('poj_info')" style="margin-left: 10px;margin-right: 10px;">
					<div class="img_dis"></div>
					<div class="title_dis">项目基本信息</div>
				</div>	
				<div style="margin-left: 10px;margin-right: 10px;">
					<table class="table_pview" cellspacing="0" cellpadding="0">
						<tr style="background-color:#D3D9E2">
							<td style="border: solid #999;border-width: 0 1px 1px 1px;">项目名称</td>
							<td style="border: solid #999;border-width: 0 1px 1px 1px;">项目类别</td>
							<td style="border: solid #999;border-width: 0 1px 1px 1px;">负责人</td>
							<td style="border: solid #999;border-width: 0 1px 1px 1px;">高校名称</td>
							<td style="border: solid #999;border-width: 0 1px 1px 1px;">学科代码及名称</td>
						</tr>
						<s:iterator value="pojList" status="stat">
							<tr>
								<td><a href="project/general/toView.action?entityId=<s:property value="pojList[#stat.index][0]" />&isReviewable=1&listType=1&businessType=${businessType}"><s:property value="pojList[#stat.index][1]" /></a></td>
								<td><s:property value="pojList[#stat.index][2]" /></td>
								<td><s:property value="pojList[#stat.index][3]" /></td>
								<td><s:property value="pojList[#stat.index][4]" /></td>
								<td><s:property value="pojList[#stat.index][5]" /></td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<div style="width:100%;">
					<iframe id="expertTreeFrame" src="project/general/showExpertTree.action?entityId=${entityId}" scrolling="auto" frameborder="no" border="0" width="100%"></iframe>
				</div>
				<table  cellspacing="0" cellpadding="0" width="100%">
					<tr style="height:30px">
						<td colspan="2" align="center">
							<input class="btn" type="button" value="确定" onclick="doSelect();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="btn" type="button" value="取消" onclick="history.back();"/>
						</td>
					</tr>
				</table>
			</div>
			<s:form id="selectReviewer">
				<s:hidden id="entityId" name="entityId"/>
				<s:hidden id="selectedExpertIds" name="selectExp"/>
				<s:hidden name="isReviewable" value="1"/>
				<s:hidden id="businessType" name="businessType"></s:hidden>
			</s:form>
			<s:hidden id="GeneralMinistryExpertNumber" value="%{#application.GeneralMinistryExpertNumber}"/><!-- 隐藏字段，查询出"一般项目_每个项目_部属高校专家数" -->
			<s:hidden id="GeneralLocalExpertNumber" value="%{#application.GeneralLocalExpertNumber}"/><!-- 隐藏字段，查询出"一般项目_每个项目_地方高校专家数" -->
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/general/general.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>