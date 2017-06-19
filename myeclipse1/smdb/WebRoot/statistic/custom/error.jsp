<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>常规数据统计</title>
		<s:include value="/innerBase.jsp"/>
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
					<table class="table_statistic" width="100%" cellspacing="0" cellpadding="2">
						<tr class="first">
							<s:iterator value="#session.headList" status="stat">
									<td><s:property value="#session.headList[#stat.index]"/></td>
							</s:iterator>
						</tr>
						<tr>
							<td style="text-align:center;"><span>暂无符合条件的记录</span></td>
						</tr>
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
		<script>
			$(function(){
				$(".table_statistic tr:last td").attr("colspan", $(".table_statistic tr:first td").length)
			});
		</script>
	</body>
</html>