<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>同步重点人</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
		<link rel="stylesheet" type="text/css" href="css/autocomplete/jquery.autocomplete.css" />
	</head>
	<body>
		<div id="top">
			专家管理&nbsp;&gt;&gt;&nbsp;同步重点人
		</div>
		<div class="main">
			<s:form id="addKeyPersonForm" action="addKeyPerson" method="post" namespace="/expert" theme="simple">
				<table class="table_list" cellpadding="0" style="width: 98%;margin-top: 10px;margin-left:10px;">
					<thead id="list_head">
						<tr class="tr_list1">
							<td class="wd0 border0"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有专家" /></td>
							<td class="wd1 border0">序号</td>
							<td class="border0">姓名</td>
							<td class="border0">电话</td>
							<td class="border0">固话</td>
							<td class="border0">传真</td>
							<td class="border0">邮箱</td>
							<td class="border0">移动电话</td>
							<td class="border0">身份类型</td>
							<td class="border0">身份证号</td>
							<td class="border0">生日</td>
							<td class="border0">单位</td>
							<td class="border0">原因</td>
						</tr>
					</thead>
					<s:iterator value="personToDeal" status="stat1">
						<tr class="tr_list2">
							<td><input type="checkbox" class="personToDeal"/></td>
							<td>
								<s:property value="#stat1.index"/>
							</td>
							<s:iterator value="personToDeal[#stat1.index]" status="stat2">
								<td>
									<s:property value="personToDeal[#stat1.index][#stat2.index]" />
								</td>
							</s:iterator>
							<s:hidden name="personToDeal[#stat1.index]" class="personToDealParam"></s:hidden>
						</tr>
					</s:iterator>
				</table>
				<div style="margin-top: 10px;margin-left: 10px;">
					<table style="width: 100%;">
						<tr>
							<td style="text-align:right;width: 20%;">是否考虑当前年份参评项目：</td><td><input type="checkbox" name="isConsiderCurrentYearProject"/></td>
						</tr>
						<tr><td style="text-align:right;">退评原因：</td><td><s:textarea name="reason" cssStyle="height:50px;width:98.5%;"/></td></tr>
					</table>
				</div>
				<div class="btn_div">
					<input type="button" id="submit1" value="提交" class="btn"/>
				</div>
			</s:form>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/addKeyPerson.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>