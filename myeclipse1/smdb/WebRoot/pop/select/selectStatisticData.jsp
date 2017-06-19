<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div style="width:450px;">
			<s:form id="search" theme="simple" action="list" namespace="/selectMinistry">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr">
						<td>人员</td>
						<td><s:radio list="#{'人员数量':'人员数量','人员数量同比增长量':'人员数量同比增长量','全时人员数量':'全时人员数量','非全时人员数量':'非全时人员数量','折合全时人员数量':'折合全时人员数量','人员投入强度':'人员投入强度'}" ></s:radio></td>
					</tr>
					<tr class="table_main_tr">
						<td>机构</td>
						<td><s:radio list="#{'1':'1','2':'2','3':'3'}" ></s:radio></td>
					</tr>
					<tr class="table_main_tr">
						<td>项目</td>
						<td><s:radio list="#{'申请项目数量':'申请项目数量','申请项目数量同比增加':'申请项目数量同比增加','立项项目数量':'立项项目数量','立项项目数量同比增加':'立项项目数量同比增加','立项率':'立项率','项目数量':'项目数量','人均项目数量':'人均项目数量','项目数量同比增加':'项目数量同比增加','拨入经费':'拨入经费','人均拨入经费':'人均拨入经费','拨入经费同比增加':'拨入经费同比增加','支出经费':'支出经费','人均支出经费':'人均支出经费','支出经费同比增加':'支出经费同比增加'}" ></s:radio></td>
					</tr>
					<tr class="table_main_tr">
						<td>成果</td>
						<td><s:radio list="#{'1':'1','2':'2','3':'3'}" ></s:radio></td>
					</tr>
					<tr class="table_main_tr">
						<td>奖励</td>
						<td><s:radio list="#{'1':'1','2':'2','3':'3'}" ></s:radio></td>
					</tr>
				</table>
			</s:form>
			<s:include value="/pop/select/radioBottom.jsp" />
		</div>
	</body>
	<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/lib/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/list_old.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
</html>