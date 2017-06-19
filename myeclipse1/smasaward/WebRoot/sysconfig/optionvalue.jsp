<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:head/>
		<title>SMAS</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
	</head>

	<body>
		<div id="container">
			<div id="top">
				<span class="text_red">系统配置</span>
			</div>
			
			<div id="mid"></div>
			
			<div id="bottom">
				<table class="table_edit" cellspacing="0" cellpadding="0">
					<tr>
						<td class="wd12">请选择下拉框名称</td>
						<td class="wd13">
							<s:select theme="simple" value="%{optionname}" cssClass="select" list="#session.sysmain" headerKey="" headerValue="请选择"
								listKey="description" listValue="name" onchange="document.location.href='main/buildSelect.action?optionname='+this.value" />
						</td>
					</tr>
				</table>
				<br />
				<table class="table_optionvalue" cellspacing="0" cellpadding="0">
					<s:iterator value="sysoption" status="stat">
						<tr>
							<s:iterator value="sysoption[#stat.index]" status="stat1">
								<td>
									<s:property value="name" />
								</td>
								<td class="deletewidth">
									<a href="main/deleteOptionValue.action?id=<s:property value='id' />&optionname=${optionname}" onclick="return false;">
										<img title="删除" src="image/del_entry.gif" /></a>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>
				<br />
				<s:form action="addOptionValue" id="form_sysconfig" namespace="/main" theme="simple">
					<s:include value="/validateError.jsp"/>
					<table id="addoptionvalue" class="addoptionvalue" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wd_right">
								<s:hidden name="optionname" />
								选项名称&nbsp;
								<s:textfield name="option.name" />&nbsp;
								选项编码&nbsp;
								<s:textfield name="option.code" />&nbsp;
								<input type="submit" class ="input" value="添加" onclick="return false;"/>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
	</body>
</html>
