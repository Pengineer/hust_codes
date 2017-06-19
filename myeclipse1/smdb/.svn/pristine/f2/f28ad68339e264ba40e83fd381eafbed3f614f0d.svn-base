<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>委托应急课题</title>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:480px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<s:if test="auditViewFlag == 1">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="85" align="right">审核人：</td>
							<td class="title_bar_td" ><s:property value="variation.deptInstAuditorName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">审核时间：</td>
							<td class="title_bar_td" width="120"><s:date name="variation.deptInstAuditDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">是否同意变更：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="variation.deptInstAuditStatus == 1">退回</s:if>
								<s:elseif test="variation.deptInstAuditStatus == 2 && variation.deptInstAuditResult == 1">不同意（暂存）</s:elseif>
								<s:elseif test="variation.deptInstAuditStatus == 2 && variation.deptInstAuditResult == 2">同意（暂存）</s:elseif>
								<s:elseif test="variation.deptInstAuditStatus == 3 && variation.deptInstAuditResult == 1">不同意</s:elseif>
								<s:elseif test="variation.deptInstAuditStatus == 3 && variation.deptInstAuditResult == 2">同意</s:elseif>
								<s:if test="variation.deptInstAuditResult == 2 && variation.deptInstAuditResultDetail != null && variation.deptInstAuditResultDetail != ''">
									<s:property value="variation.deptInstAuditResultDetail"/>
								</s:if>
							</td>
						</tr>
						<s:if test="variation.deptInstAuditOpinion != null && variation.deptInstAuditOpinion != ''">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">审核意见：</td>
							<td class="title_bar_td" colspan="4">
								<pre><s:property value="variation.deptInstAuditOpinion"/></pre>
							</td>
						</tr>
						</s:if>
					</s:if>	
					<s:elseif test="auditViewFlag == 2">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="85" align="right">审核人：</td>
							<td class="title_bar_td"><s:property value="variation.universityAuditorName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">审核时间：</td>
							<td class="title_bar_td" width="120"><s:date name="variation.universityAuditDate" format="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">是否同意变更：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="variation.universityAuditStatus == 1">退回</s:if>
								<s:elseif test="variation.universityAuditStatus == 2 && variation.universityAuditResult == 1">不同意（暂存）</s:elseif>
								<s:elseif test="variation.universityAuditStatus == 2 && variation.universityAuditResult == 2">同意（暂存）</s:elseif>
								<s:elseif test="variation.universityAuditStatus == 3 && variation.universityAuditResult == 1">不同意</s:elseif>
								<s:elseif test="variation.universityAuditStatus == 3 && variation.universityAuditResult == 2">同意</s:elseif>
								<s:if test="variation.universityAuditResult == 2 && variation.universityAuditResultDetail != null && variation.universityAuditResultDetail != ''">
									<s:property value="variation.universityAuditResultDetail"/>
								</s:if>
							</td>
						</tr>
						<s:if test="variation.universityAuditOpinion != null && variation.universityAuditOpinion != ''">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">审核意见：</td>
							<td class="title_bar_td" colspan="4">
								<pre><s:property value="variation.universityAuditOpinion"/></pre>
							</td>
						</tr>
						</s:if>
					</s:elseif>	
					<s:elseif test="auditViewFlag == 3">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="85" align="right">审核人：</td>
							<td class="title_bar_td"><s:property value="variation.provinceAuditorName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">审核时间：</td>
							<td class="title_bar_td" width="120"><s:date name="variation.provinceAuditDate" format="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">是否同意变更：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="variation.provinceAuditStatus == 1">退回</s:if>
								<s:elseif test="variation.provinceAuditStatus == 2 && variation.provinceAuditResult == 1">不同意（暂存）</s:elseif>
								<s:elseif test="variation.provinceAuditStatus == 2 && variation.provinceAuditResult == 2">同意（暂存）</s:elseif>
								<s:elseif test="variation.provinceAuditStatus == 3 && variation.provinceAuditResult == 1">不同意</s:elseif>
								<s:elseif test="variation.provinceAuditStatus == 3 && variation.provinceAuditResult == 2">同意</s:elseif>
								<s:if test="variation.provinceAuditResult == 2 && variation.provinceAuditResultDetail != null && variation.provinceAuditResultDetail != ''">
									<s:property value="variation.provinceAuditResultDetail"/>
								</s:if>
							</td>
						</tr>
						<s:if test="variation.provinceAuditOpinion != null && variation.provinceAuditOpinion != ''">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">审核意见：</td>
							<td class="title_bar_td" colspan="4">
								<pre><s:property value="variation.provinceAuditOpinion" /></pre>
							</td>
						</tr>
						</s:if>
					</s:elseif>	
					<s:elseif test="auditViewFlag == 4">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="85" align="right">审核人：</td>
							<td class="title_bar_td"><s:property value="variation.finalAuditorName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">审核时间：</td>
							<td class="title_bar_td" width="120"><s:date name="variation.finalAuditDate" format="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">是否同意变更：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="variation.finalAuditStatus == 1">退回</s:if>
								<s:elseif test="variation.finalAuditStatus == 2 && variation.finalAuditResult == 1">不同意（暂存）</s:elseif>
								<s:elseif test="variation.finalAuditStatus == 2 && variation.finalAuditResult == 2">同意（暂存）</s:elseif>
								<s:elseif test="variation.finalAuditStatus == 3 && variation.finalAuditResult == 1">不同意</s:elseif>
								<s:elseif test="variation.finalAuditStatus == 3 && variation.finalAuditResult == 2">同意</s:elseif>
								<s:if test="variation.finalAuditResult == 2 && variation.finalAuditResultDetail != null && variation.finalAuditResultDetail != ''">
									<s:property value="variation.finalAuditResultDetail"/>
								</s:if>
							</td>
						</tr>
						<s:if test="variation.finalAuditOpinion != null && variation.finalAuditOpinion != ''">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">审核意见：</td>
							<td class="title_bar_td" colspan="4">
								<pre><s:property value="variation.finalAuditOpinion" /></pre>
							</td>
						</tr>
						</s:if>
					</s:elseif>
					</table>
				</div>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="确定" /></li>
				</div>
			</div>
		<script type="text/javascript">
			seajs.use('javascript/pop/view/view.js', function(view) {
				
			});
		</script>
		</body>
	
</html>