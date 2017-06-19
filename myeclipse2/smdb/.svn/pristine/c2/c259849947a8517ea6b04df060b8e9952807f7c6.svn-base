<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>接口管理</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div class="link_bar">
			当前位置：接口管理&nbsp;&gt;&nbsp;社科网服务端&nbsp;&gt;&nbsp;中检数据接口
		</div>
	    <s:form action="midinspectionRequiredConfig" namespace="/system/interfaces/sinossServer">
		<div class="title_statistic">参数配置</div>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="80"><s:text name="接口开关" />：</td>
						<td class="table_td3">
							<input id="open" type="radio" name="isPublished" value="1" />
							<label for="open">开启</label>&nbsp;&nbsp;&nbsp;
							<input id="close" type="radio" name="isPublished" value="0" />
							<label for="close">关闭</label>
						</td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><s:text name="项目年度" />：</td>
						<td class="table_td3">
							<s:select cssClass="select" id="startYear" name="startYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
							<s:text name="i18n_To"/>
							<s:select cssClass="select" id="endYear" name="endYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
						</td>
					</tr>
				</table>
			<div class="btn_div_view">
				<input id="submit" class="btn2" type="submit" value="<s:text name='保存修改' />" />
			</div>
		</s:form>
		<s:include value="/system/interfaces/sinossServer/bottom.jsp" />
	</body>
</html>