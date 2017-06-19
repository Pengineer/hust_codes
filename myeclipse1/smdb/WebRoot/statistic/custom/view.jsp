<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>常规数据统计</title>
		<s:include value="/innerBase.jsp"/>
		<script type="text/javascript">
			seajs.use('javascript/statistic/view.js', function(view) {
				$(document).ready(function(){
					view.init();
				})
			});
		</script>
	</head>

	<body>
		<s:hidden id="entityId" name="entityId"/>
		<s:hidden id="entityIds" name="entityIds"/>
		<s:hidden id="update" name="update"/>
		<%--<div class="link_bar">
			当前位置：定制数据统计&nbsp;&gt;&nbsp;<s:property value="#session.statisticListType"/>&nbsp;&gt;&nbsp;查看
		</div>
		--%>
		<div class="main">
			<div class="main_content">
				<div class="title_statistic">
					<s:property value="#session.statisticTitle"/>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_CUSTOM_EXPORT">
						<input id="custom_export" type="button" class="btn1" value="导出" />
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_STATISTIC_CUSTOM_TOCOMMON">
						<input id="view_common" type="button" class="btn2" value="导至常规" />
					</sec:authorize>
				</div>
				<br/>
				<div class="title_bar">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<s:iterator value="#session.statistic_parm" status="stat">
							<tbody>
								<tr>
									<td class="title_bar_td" width="30" align="right">
										<img width="5" height="9" src="image/ico08.gif"/>
									</td>
									<td width="30"></td>
									<td class="title_bar_td" align="left"><s:property value="#session.statistic_parm[#stat.index]"/></td>
								</tr>
							</tbody>
						</s:iterator>
					</table>
				</div>
				<br/>
				<div style="margin-top:4px;">
					<table class="table_statistic" width="70%" cellspacing="0" cellpadding="2">
						<s:iterator value="#session.dataList" status="stat" var="list1">
							<tr class="<s:if test='#stat.first == true'>first</s:if><s:elseif test='#stat.even == true'>even</s:elseif>">
								<s:iterator value="#session.dataList[#stat.index]" status="stat2">
									<td><s:property value="#session.dataList[#stat.index][#stat2.index]"/></td>
								</s:iterator>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
			<br/>
			<div id="chart"></div>
		</div>
		<s:hidden name="encryptedMdxQueryString" id="encryptedMdxQueryString"/>
		<s:hidden name="statisticType" id="statisticType"/>
		<s:hidden name="resultLines" id="resultLines"/>
		<s:hidden name="chartConfig" id="chartConfig"/>
	</body>
</html>