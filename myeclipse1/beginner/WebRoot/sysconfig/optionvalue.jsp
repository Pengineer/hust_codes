<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Main">
		<head>
			<base href="<%=basePath%>" />
		    <title><s:text name="i18n_SystemName" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/main/page.css" />
  		</head>
  		
  		<body>
  			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<span class="text_red"><s:text name="i18n_SysConfig" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<table class="table_edit" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wd12">
								<s:text name="i18n_MakeYourChoice" />
							</td>
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
		  									<img title="<s:text name='i18n_Delete' />" src="image/deleteEntry.gif" /></a>
		  							</td>
		  						</s:iterator>
		  					</tr>
		  				</s:iterator>
		  			</table>
		  			<br />
		  			<s:form action="addOptionValue" id="form_sysconfig" namespace="/main" theme="simple">
			  			<div id="inputtip">
							<s:property value="tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
			  			<table id="addoptionvalue" class="addoptionvalue" cellspacing="0" cellpadding="0">
			  				<tr>
			  					<td class="wd_right">
			  						<input type="hidden" name="optionname" value="${optionname}" />
			  						<s:text name="i18n_OptionValueName" />&nbsp;
			  						<s:textfield name="option.name" />&nbsp;
			  						<s:text name="i18n_OptionCode" />&nbsp;
			  						<s:textfield name="option.code" />&nbsp;
			  						<input type="submit" class ="input" value="<s:text name='i18n_Add' />" onclick="return false;"/>
			  					</td>
			  				</tr>
			  			</table>
		  			</s:form>
				</div>
			</div>
  		</body>
  	</s:i18n>
</html>
