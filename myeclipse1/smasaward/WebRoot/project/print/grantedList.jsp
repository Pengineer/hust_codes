<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>立项清单</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/print/print.css" />
	</head>
	
	<body>
		<div class="main_content" style="width: 1225px;">
		<div id="printList" style="margin:0 auto;width: 280mm;">
			<s:iterator value="#request.dataList" status="stat">
				<s:if test="#request.dataList[#stat.index].size() != 0">
					<div class="content" style="padding-top:15px;margin-left:5px;margin-right:5px;page-break-after:always;">
						<div style="text-align:center;font-family:宋体; font-size:18pt;font-weight:bold;margin-bottom:10px;"><s:property value="#request.grantYear"/>年度教育部人文社会科学研究一般项目立项及拨款情况一览表</div>
						<table width="100%" cellspacing="0" cellpadding="2" border="0" bordercolor="#000" style="border-collapse:collapse;">
							<tr  style="font-size: 13px;">
								<td style="width:50%;" align="left">
									<span style="font-weight:bold;">高校名称：</span><s:property value="#request.dataList[#stat.index][0][1]"/>&nbsp;&nbsp;
									<span style="font-weight:bold;">项目数量：</span><s:property value="#request.dataList[#stat.index].size()"/>
								</td>
								<td align="right"><span style="font-weight:bold;">项目年度：</span><s:property value="#request.grantYear"/></td>
							</tr>
						</table>
						<table width="100%" cellspacing="0" cellpadding="2" border="1" bordercolor="#000" style="border-collapse:collapse;">
				  			<tr style="font-size: 13px;">
								<th style="width:35px; text-align:center;">序号</th>
								<th style="width:100px; text-align:center;">高校名称</th>
								<th style="width:200px; text-align:center;">项目名称</th>
								<th style="width:80px; text-align:center;">申请人</th>
				  				<th style="width:100px; text-align:center;">项目类别</th>
				  				<th style="width:100px; text-align:center;">学科门类</th>
				  				<th style="width:100px; text-align:center;">计划完成时间</th>
				  				<th style="width:80px; text-align:center;">批准经费（万元）</th>
				  				<th style="width:80px; text-align:center;">本次拨款（万元）</th>
				  				<th style="width:100px; text-align:center;">最终成果形式</th>
				  				<th style="width:100px; text-align:center;">项目批准号</th>
				  				<th style="width:150px; text-align:center;">备注</th>
				  			</tr>
						
							<s:iterator value="#request.dataList[#stat.index]" status="stat2">
								<tr style="font-size: 13px;font-family: Arial;">
									<td style="width:35px; text-align:center;"><s:property value="#stat2.index+1"/></td>
									<td style="width:100px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][1]"/></td>
									<td style="width:200px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][2]"/></td>
									<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][3]"/></td>
									<td style="width:100px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][4]"/></td>
									<td style="width:100px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][5]"/></td>
									<td style="width:100px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][6]"/></td>
									<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][7]"/></td>
									<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][8]"/></td>
									<td style="width:100px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][9]"/></td>
									<td style="width:100px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][10]"/></td>
									<td style="width:150px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][11]"/></td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</s:if>
	  		</s:iterator>
			</div>
  		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/project/print/print.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>