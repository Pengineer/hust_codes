<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:480px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name="i18n_AuditorName" />：</td>
							<td class="title_bar_td" ><s:property value="reviewAuditorName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right"><s:text name="i18n_AuditDate" />：</td>
							<td class="title_bar_td" width="120"><s:date name="reviewAuditDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name='i18n_EndinspectionResult' />：</td>
							<td class="title_bar_td" colspan='4'>
								<s:if test="reviewAuditStatus==2&&reviewAuditResultEnd==1"><s:text name="i18n_SaveNotApprove"/></s:if>
								<s:elseif test="reviewAuditStatus==2&&reviewAuditResultEnd==2"><s:text name="i18n_SaveApprove"/></s:elseif>
								<s:elseif test="reviewAuditStatus==3&&reviewAuditResultEnd==1"><s:text name="i18n_NotApprove"/></s:elseif>
								<s:elseif test="reviewAuditStatus==3&&reviewAuditResultEnd==2"><s:text name="i18n_Approve"/></s:elseif>
							</td>
						</tr>
						<s:if test="isApplyNoevaluation == 1 && reviewAuditResultNoevalu != 0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name='i18n_NoEvalResult' />：</td>
							<td class="title_bar_td" colspan='4'>
								<s:if test="reviewAuditStatus==2&&reviewAuditResultNoevalu==1"><s:text name="i18n_SaveNotApprove"/></s:if>
								<s:elseif test="reviewAuditStatus==2&&reviewAuditResultNoevalu==2"><s:text name="i18n_SaveApprove"/></s:elseif>
								<s:elseif test="reviewAuditStatus==3&&reviewAuditResultNoevalu==1"><s:text name="i18n_NotApprove"/></s:elseif>
								<s:elseif test="reviewAuditStatus==3&&reviewAuditResultNoevalu==2"><s:text name="i18n_Approve"/></s:elseif>
							</td>
						</tr>
						</s:if>
						<s:if test="isApplyExcellent == 1 && reviewAuditResultExcelle != 0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name='i18n_ExcellentResult' />：</td>
							<td class="title_bar_td" colspan='4'>
								<s:if test="reviewAuditStatus==2&&reviewAuditResultExcelle==1"><s:text name="i18n_SaveNotApprove"/></s:if>
								<s:elseif test="reviewAuditStatus==2&&reviewAuditResultExcelle==2"><s:text name="i18n_SaveApprove"/></s:elseif>
								<s:elseif test="reviewAuditStatus==3&&reviewAuditResultExcelle==1"><s:text name="i18n_NotApprove"/></s:elseif>
								<s:elseif test="reviewAuditStatus==3&&reviewAuditResultExcelle==2"><s:text name="i18n_Approve"/></s:elseif>
							</td>
						</tr>
						</s:if>
						<s:if test="reviewAuditResultEnd==2">
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name="i18n_EndNumber" />：</td>
							<td class="title_bar_td" colspan='4'><s:property value="certificate"/></td>
						</s:if>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_AuditOpinion" />：</td>
							<td class="title_bar_td" colspan="4" >
								<pre><s:property value="reviewAuditOpinion"/></pre>
							</td>
						</tr>
					</table>
				</div>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="<s:text name='i18n_Ok' />" />
				</div>
			</div>
		<script type="text/javascript">
			seajs.use('javascript/pop/view/view.js', function(view) {
				
			});
		</script>
		</body>
	</s:i18n>
</html>
