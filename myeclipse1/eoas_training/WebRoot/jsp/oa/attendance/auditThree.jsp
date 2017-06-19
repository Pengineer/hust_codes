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
		<title><s:text name="审核" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />	 
		
	    <script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>

	    <script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script> 
           <script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<s:if test="#session.locale.equals(\"zh_CN\")">
			<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		</s:if>
	</head>
	
	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="考勤管理" /></li>
				<li class="text_red"><s:text name="审核请假申请" /></li>
			</ul>
		</div>
		<div class="div_table">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="28" class="tdbg1"><s:text name="申请信息" /></td>
				</tr>
			</table>
			<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="请假类型" />：</td>
					<td class="txtpadding">
						<s:if test="attendance.type==1">事假</s:if>
						<s:elseif test="attendance.type==2">病假</s:elseif>
						<s:elseif test="attendance.type==3">婚假</s:elseif>
						<s:elseif test="attendance.type==4">丧家</s:elseif>
						<s:elseif test="attendance.type==5">产假</s:elseif>
						<s:elseif test="attendance.type==6">年假</s:elseif>
						<s:elseif test="attendance.type==7">调休</s:elseif>
						<s:else>其他申请</s:else>
					</td>
				</tr>
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请人" />：</td>
					<td class="txtpadding" width="300"><s:property value="person.realName" /></td>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="申请时间" />：</td>
					<td class="txtpadding"><s:date name="attendance.applyTime" format="yyyy-MM-dd" /></td>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
					<td class="txtpadding" width="300"><s:property value="attendance.note" /></td>
				</tr>
			</table>
		</div>
		<div class="div_table">
			<s:form action="audit" id="form_application" namespace="/attendance" theme="simple">
				<div class="errorInfo">
					<s:property value="tip" />
					<s:fielderror />
				</div>
				<input type="hidden" name="attendanceId" value="${attendanceId}" />
				<input type="hidden" name="tag" value="3" />
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="审核信息" /></td>
					</tr>
				</table>
				<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="i18n_AuditOpinion" />：</td>
						<td>
							<div class="input0" style="float:left;">
								<input type="radio" name="attendance.auditResult" value="1" />同意
								<input type="radio" name="attendance.auditResult" value="0" />不同意
							</div>
							<div style="float:left;"></div>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" valign="top" bgcolor="#b3c6d9"><s:text name="意见" />：</td>
						<td>
							<div class="input0" style="float:left;height:77px;">
								<s:textarea cssStyle="font-size:12px; width:505px; height:73px;" name="attendance.auditOpinion" cssClass="inputcss" />
							</div>
							<div></div>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#b3c6d9">&nbsp;</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="90"><input type="submit" class ="btn1" value="<s:text name='确定' />" /></td>
									<td><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
								</tr>
							</table>
						</td>
					</tr>
					</table>
			</s:form>
		</div>
	</body>
</html>