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
				系统管理&nbsp;&gt;&gt;&nbsp;系统配置&nbsp;&gt;&gt;&nbsp;<span class="text_red">同步数据</span>
			</div>
			
			<div id="bottom">
				<s:form action="syncDataOnline" id="form_synData" namespace="/system/smdbClient" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit table_edit2" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2" class="text_red">在线同步数据操作</td>
						</tr>
						<tr>
							<td class="wd12">参数配置</td>
							<td class="wd13">
								数据类型：<s:select cssClass="select" name="dataTypeTag" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'申请数据','2':'立项数据','3':'变更数据','4':'中检数据','5':'结项数据', '6':'smas高校表更新', '7':'部级审核信息更新至smdb'}" />
							</td>
						</tr>
						<tr>
							<td class="wd12"></td>
							<td class="wd13">
								项目类型：<s:select cssClass="select" name="projectTypeTag" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'一般项目','2':'基地项目'}" />
							</td>
						</tr>
						<tr>
							<td class="wd12"></td>
							<td class="wd13">
								同步年份： <s:textarea  cssClass="select"  name="projectYear" label="年份" ></s:textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="td_sub"><input type="submit" class ="btn" value = "同步数据" /></td>
						</tr>
					</table>
				</s:form>
				<s:form action="syncDataFix" id="form_enterData" namespace="/system/smdbClient" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit table_edit2" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2" class="text_red">数据修复同步操作</td>
						</tr>
						<tr>
							<td class="wd12">参数配置</td>
							<td class="wd13">
								数据类型：<s:select cssClass="select" name="dataTypeTag" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'申请数据','2':'立项数据','3':'变更数据','4':'中检数据','5':'结项数据','6':'fixSmasProgram'  }" />
							</td>
						</tr>
						<tr>
							<td class="wd12"></td>
							<td class="wd13">
								项目类型：<s:select cssClass="select" name="projectTypeTag" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'一般项目','2':'基地项目'}" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="td_sub"><input type="submit" class ="btn" value = "  run  " /></td>
						</tr>
					</table>	
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/sysconfig/dataManage.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>
