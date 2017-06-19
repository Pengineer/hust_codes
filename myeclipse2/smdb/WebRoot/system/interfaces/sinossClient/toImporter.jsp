<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>接口管理</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div class="link_bar">
			当前位置：接口管理&nbsp;&gt;&nbsp;社科网客户端&nbsp;&gt;&nbsp;<s:text name="数据入库" />
		</div>
	    <s:form action="importer" namespace="/system/interfaces/sinossClient">
			<div class="main">
				<div class="main_content">
					<div class="title_statistic">参数配置</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="" style="_padding-top:5px;font-weight:bold;"><s:text name="项目类型"/>：</span></td>
							<td class="table_td3"><s:select cssClass="select" id="search_projectType" name="projectType" list="#{'gener':'一般项目', 'base':'基地项目', 'special':'专项任务'}"/></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="" style="_padding-top:5px;font-weight:bold;"><s:text name="数据类型"/>：</span></td>
							<td class="table_td3"><s:select id="search_methodName" cssClass="select" name="methodName" list="#{'getApplyProject':'申报数据', 'getModifyRecord':'变更数据'}"/></td>
							<td class="table_td4"></td>
						</tr>
					</table>
					<div class="btn_div_view">
						<input id="submit" class="btn2" type="button" value="<s:text name='数据入库' />" />
					</div>
				</div>
			</div>
		</s:form>
		<textarea id="view_template" class="view_template" style="display:none;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" width="85" align="right"><span>当前入库的文件数为：</span></td>
					<td class="title_bar_td" width="120">${fileCount}</td>
				</tr>
				<tr>
					<td class="title_bar_td" align="right"><span>当前文件中已入库的数据条数为：</span></td>
					<td class="title_bar_td">${recordCount}</td>
				</tr>
			</table>
		</textarea>
		<script type="text/javascript">
			seajs.use('javascript/system/interfaces/sinossClient/importer.js', function(edit) {
				edit.init();
			});
		</script>
	</body>
</html>