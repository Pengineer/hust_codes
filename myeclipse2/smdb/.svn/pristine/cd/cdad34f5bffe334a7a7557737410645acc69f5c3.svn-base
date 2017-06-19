<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
	</head>
 
	<body>
		<div style="width:450px;">
			<div class="title_bar">
				<s:if test="viewflag==1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核人：</td>
						<td class="title_bar_td"><s:property value="awardApplication.deptInstAuditorName" /></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核时间：</td>
						<td class="title_bar_td" width="120"><s:date name="awardApplication.deptInstAuditDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">审核结果：</td>
						<td class="title_bar_td">
							<s:if test="awardApplication.deptInstAuditStatus==0">待审</s:if>
							<s:elseif test="awardApplication.deptInstAuditStatus==1">被退回</s:elseif>
							<s:elseif test="awardApplication.deptInstAuditStatus==2&&awardApplication.deptInstAuditResult==1">不同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.deptInstAuditStatus==2&&awardApplication.deptInstAuditResult==2">同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.deptInstAuditStatus==3&&awardApplication.deptInstAuditResult==1">不同意</s:elseif>
							<s:elseif test="awardApplication.deptInstAuditStatus==3&&awardApplication.deptInstAuditResult==2">同意</s:elseif>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核意见：</td>
						<td class="title_bar_td">
							<pre><s:property value="awardApplication.deptInstAuditOpinion"/></pre>
						</td>
					</tr>
				</table>
				</s:if>	
				<s:elseif test="viewflag==2">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核人：</td>
						<td class="title_bar_td"><s:property value="awardApplication.universityAuditorName" />&nbsp;</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核时间：</td>
						<td class="title_bar_td" width="120"><s:date name="awardApplication.universityAuditDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">审核结果：</td>
						<td class="title_bar_td">
							<s:if test="awardApplication.universityAuditStatus==0">待审</s:if>
							<s:elseif test="awardApplication.universityAuditStatus==1">被退回</s:elseif>
							<s:elseif test="awardApplication.universityAuditStatus==2&&awardApplication.universityAuditResult==1">不同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.universityAuditStatus==2&&awardApplication.universityAuditResult==2">同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.universityAuditStatus==3&&awardApplication.universityAuditResult==1">不同意</s:elseif>
							<s:elseif test="awardApplication.universityAuditStatus==3&&awardApplication.universityAuditResult==2">同意</s:elseif>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核意见：</td>
						<td class="title_bar_td">
							<pre><s:property value="awardApplication.universityAuditOpinion" /></pre>
						</td>
					</tr>
				</table>
				</s:elseif>	
				<s:elseif test="viewflag==3">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核人：</td>
						<td class="title_bar_td"><s:property value="awardApplication.provinceAuditorName" />&nbsp;</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核时间：</td>
						<td class="title_bar_td" width="120"><s:date name="awardApplication.provinceAuditDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">审核结果：</td>
						<td class="title_bar_td">
							<s:if test="awardApplication.provinceAuditStatus==0">待审</s:if>
							<s:elseif test="awardApplication.provinceAuditStatus==1">被退回</s:elseif>
							<s:elseif test="awardApplication.provinceAuditStatus==2&&awardApplication.provinceAuditResult==1">不同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.provinceAuditStatus==2&&awardApplication.provinceAuditResult==2">同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.provinceAuditStatus==3&&awardApplication.provinceAuditResult==1">不同意</s:elseif>
							<s:elseif test="awardApplication.provinceAuditStatus==3&&awardApplication.provinceAuditResult==2">同意</s:elseif>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核意见：</td>
						<td class="title_bar_td">
							<pre><s:property value="awardApplication.provinceAuditOpinion"/></pre>
						</td>
					</tr>
				</table>
				</s:elseif>	
				<s:elseif test="viewflag==4">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核人：</td>
						<td class="title_bar_td"><s:property value="awardApplication.ministryAuditorName" />&nbsp;</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核时间：</td>
						<td class="title_bar_td" width="120"><s:date name="awardApplication.ministryAuditDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">审核结果：</td>
						<td class="title_bar_td">
							<s:if test="awardApplication.ministryAuditStatus==0">待审</s:if>
							<s:elseif test="awardApplication.ministryAuditStatus==1">被退回</s:elseif>
							<s:elseif test="awardApplication.ministryAuditStatus==2&&awardApplication.ministryAuditResult==1">不同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.ministryAuditStatus==2&&awardApplication.ministryAuditResult==2">同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.ministryAuditStatus==3&&awardApplication.ministryAuditResult==1">不同意</s:elseif>
							<s:elseif test="awardApplication.ministryAuditStatus==3&&awardApplication.ministryAuditResult==2">同意</s:elseif>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核意见：</td>
						<td class="title_bar_td">
							<pre><s:property value="awardApplication.ministryAuditOpinion"/></pre>
						</td>
					</tr>
				</table>
				</s:elseif>
				</table>
			</div>
			<div class="btn_div_view">
				<input id="okclosebutton" class="btn1" type="button" value="确定" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/pop/pop-init.js', function(view) {
				
			});
		</script>
	</body>
</html>