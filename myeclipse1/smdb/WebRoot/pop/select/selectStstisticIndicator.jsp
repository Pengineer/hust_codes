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
						<td><s:checkbox value="{'人员性别':'人员性别','人员年龄段':'人员年龄段','人员职称':'人员职称','人员最后学历':'人员最后学历','人员最后学位':'人员最后学位','人员所在省、市、自治区':'人员所在省、市、自治区','人员所在高校类型':'人员所在高校类型','人员所在机构':'人员所在机构','人员类型':'人员类型','人员学科门类':'人员学科门类','是否参与项目':'是否参与项目','参与项目类别':'参与项目类别','参与项目主题':'参与项目主题','参与项目状态':'参与项目状态','参与项目研究类别':'参与项目研究类别','参与项目研究门类':'参与项目研究门类','年度':'年度'}" /></td>
					</tr>
					<tr class="table_main_tr">
						<td>机构</td>
						<td><s:checkbox value="{'1':'1','2':'2','3':'3'}"></s:checkbox></td>
					</tr>
					<tr class="table_main_tr">
						<td>项目</td>
						<td><s:checkbox value="{'项目所属省、市、自治区':'项目所属省、市、自治区','项目依托高校类型':'项目依托高校类型','项目依托高校':'项目依托高校','项目类别':'项目类别','项目状态':'项目状态','项目研究类别':'项目研究类别','项目学科门类':'项目学科门类','负责人性别':'负责人性别','负责人职称':'负责人职称','负责人最后学历':'负责人最后学历','负责人最后学位':'负责人最后学位','负责人学科门类':'负责人学科门类'}"></s:checkbox></td>
					</tr>
					<tr class="table_main_tr">
						<td>成果</td>
						<td><s:checkbox value="{'1':'1','2':'2','3':'3'}"></s:checkbox></td>
					</tr>
					<tr class="table_main_tr">
						<td>奖励</td>
						<td><s:checkbox value="{'1':'1','2':'2','3':'3'}"></s:checkbox></td>
					</tr>
				</table>
			</s:form>
			<s:include value="/pop/select/checkboxBottom.jsp" />
		</div>
	</body>
	<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/lib/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/list_old.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
</html>