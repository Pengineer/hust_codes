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
			<title>项目清单</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
		</head>

		<body>
			<div id="bottom" style="width:680px;">
				<s:hidden id="area" name="area" />
				<s:hidden id="disType" name="disType" />
				<s:hidden id="proType" name="proType" />
				<div id="list_container" style="height:320px; overflow-y:scroll; overflow-x:hidden;">
				<table class="table_list" cellspacing="0" cellpadding="0" style="margin-top:0px;">
					<tr class="table_title_tr3 tr_list1">
						<td class="wd1 border0">
							序号
						</td>
						<td class="wd11 border0">
							项目名称
						</td>
						<td class="wd15 border0">
							负责人
						</td>
						<td class="wd14 border0">
							高校名称
						</td>
						<td class="wd16 center border0">
							票数/分数
						</td>
						<td class="wd15 center border0">
							特殊地区
						</td>
						<td class="wd15 center border0">
							立项状态
						</td>
					</tr>
					<s:iterator value="pageList" status="stat">
						<tr class="tr_list2">
							<td class="wd1">
								<s:property value="#stat.index+1" />
							</td>
							<td class="wd11">
								<s:property value="pageList[#stat.index][1]" />
							</td>
							<td class="wd15">
								<s:property value="pageList[#stat.index][2]" />
							</td>
							<td class="wd14">
								<s:property value="pageList[#stat.index][3]" />
							</td>
							<td class="wd16 center">
								<s:property value="pageList[#stat.index][5]" />/<s:property value="pageList[#stat.index][6]" />
							</td>
							<td class="wd15 center">
								<s:if test="pageList[#stat.index][7] == '西部'">西部</s:if>
								<s:elseif test="pageList[#stat.index][7] == '新疆'">新疆</s:elseif>
								<s:elseif test="pageList[#stat.index][7] == '西藏'">西藏</s:elseif>
								<s:else>否</s:else>
							</td>
							<td class="wd15 center">
								<a class="isGranting" style="cursor:pointer;" title="点击可以修改立项状态">
									<s:if test="pageList[#stat.index][4] == 0">不立项</s:if>
									<s:elseif test="pageList[#stat.index][4] == 1">拟立项</s:elseif>
									<s:else>确定立项</s:else>
								</a>
								<s:hidden value="%{pageList[#stat.index][4]}" />
								<s:hidden value="%{pageList[#stat.index][0]}" />
							</td>
						</tr>
					</s:iterator>
				</table>
				</div>
				<table class="table_sub">
					<tr>
						<td>&nbsp;</td>
						<td class="td_sub">
							<input id="confirm" type="button" class="btn" value="确定" />
						</td>
						<td class="td_sub">
							<input id="cancel" type="button" class="btn" value="取消" />
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/finance/popCheck.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>