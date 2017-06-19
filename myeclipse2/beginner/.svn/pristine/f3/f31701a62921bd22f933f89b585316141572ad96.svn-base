<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Main">
		<head>
			<base href="<%=basePath%>" />
		    <title></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/main/page.css" />
			<script type="text/javascript">
				function showLocale() {
					var dn, str;
					var objD;
					objD = new Date();
					var d = new initArray("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
			
					var hh = objD.getHours();
					var mm = objD.getMinutes();
					var ss = objD.getSeconds();
					str = objD.getFullYear() + "年" + (objD.getMonth() + 1) + "月"
							+ objD.getDate() + "日  " + d[objD.getDay() + 1];
					return str;
				}
			
				function initArray() {
					this.length = initArray.arguments.length
					for ( var i = 0; i < this.length; i++)
						this[i + 1] = initArray.arguments[i];
				}
			</script>
		</head>
  
		<body>
			<div id="container">
				<table class="table" cellspacing="0" cellpadding="0">
					<tr class="tr_tile">
						<td style="text-align:left;">
							&nbsp;&nbsp;<s:text name="i18n_Welcome" />!&nbsp;
							<s:property value="#session.visitor.user.chinesename" />
							&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale())</script>
						</td>
						<td>&nbsp;</td>
						<td>
  							<div>
  								<a href="main/page_right.action" target="right"><s:text name="i18n_Index" /></a>|
  								<a href="main/toConfig.action" target="right"><s:text name="i18n_Config" /></a>|
  								<a href="main/page_right.action" target="right"><s:text name="i18n_Help" /></a>|
  								<a href="main/page_right.action" target="right"><s:text name="i18n_About" /></a>|
  								<a href="user/logout.action"><s:text name="i18n_Exit" /></a>
  							</div>
  						</td>
  					</tr>
  					<tr class="tr_back">
						<td class="td_left">
							<div id="unit"><s:text name="i18n_SystemName" /></div>
						</td>
						<td class="td_mid"></td>
						<td class="td_right"></td>
					</tr>
  				</table>
  			</div>
		</body>
	</s:i18n>
</html>