<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>高校参评专家数量</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/reviewstatic/reviewstatic.css" />
			<link rel="stylesheet" type="text/css" href="css/index.css" />
			<link type="text/css" rel="stylesheet" href="css/global.css">
			<style type="text/css">
				.staticwd1 {text-align: center;padding:0;}
				.staticwd2 {text-align: center;padding:0;}
			</style>
		</head>
		<body>
			<div id="top">
				<s:if test="listType == 1">
					统计分析&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">专家参与项目使用情况</span>
				</s:if>
				<s:else>
					统计分析&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">专家参与项目使用情况</span>
				</s:else>
			</div>
			<div class="static">
			
				<div id="expertHighchartHolder"></div>
				<div id="universityHighchartHolder"></div>
				<s:hidden id="chartData" name="#request.universityUseStatisticList"/>
				<s:hidden id="chartDataStd" name="#request.universityUseStatisticStd"/>
				<s:hidden id="chartDataAve" name="#request.universityUseStatisticAve"/>
				<s:hidden id="chartStaticYears" name="#request.chartStaticYears"/>
			
				<table class="static_table" cellpadding="0" style="width: 98%;">
					<th></th>
					<tr>
						<td class="staticwd0" align="center">高校名称</td>
						<td class="staticwd0" colspan="5" align="center">高校（专家配对情况）</td>
						<td class="staticwd0" colspan="5" align="center">高校专家使用情况</td>
					</tr>
					<tr>
						<td class="staticwd1">年度</td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 4"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 3"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 2"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 1"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 4"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 3"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 2"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear - 1"/></td>
						<td class="staticwd1"><s:property value="#request.statiCurrentYear"/></td>
					</tr>
					<tr>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[0]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[1]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[2]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[3]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[4]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[5]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[6]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[7]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[8]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[9]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticStd[10]"/></td>
					</tr>
					<tr>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[0]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[1]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[2]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[3]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[4]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[5]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[6]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[7]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[8]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[9]"/></td>
						<td class="staticwd1"><s:property value="#request.universityUseStatisticAve[10]"/></td>
					</tr>
					<s:iterator value="#request.universityUseStatisticList" status="stat1">
						<tr>
							<s:iterator value="#request.universityUseStatisticList[#stat1.index]" status="stat2">
								<s:if test="#stat2.index==0">
									<td class="staticwd1">
										<s:property value="#request.universityUseStatisticList[#stat1.index][#stat2.index]" />
									</td>
								</s:if>
								<s:else>
									<td class="staticwd2">
										<s:property value="#request.universityUseStatisticList[#stat1.index][#stat2.index]" />
									</td>
								</s:else>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/highcharts/highcharts.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/statistic/universityUseStatistic.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>