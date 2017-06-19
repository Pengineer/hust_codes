<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_InstpProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:480px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="90" align="right"><s:text name="i18n_Reviewer1" />：</td>
							<td class="title_bar_td" ><s:property value="endinspection.reviewerName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ReviewDate1" />：</td>
							<td class="title_bar_td" width="120"><s:date name="endinspection.reviewDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_ReviewWay1" />：</td>
							<td class="title_bar_td" >
								<s:if test="endinspection.reviewWay==1"><s:text name="i18n_ReviewComm1"/></s:if>
								<s:elseif test="endinspection.reviewWay==2"><s:text name="i18n_ReviewMeet1"/></s:elseif>
							</td>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_ReviewResult1" />：</td>
							<td class="title_bar_td" >
								<s:if test="endinspection.reviewResult==1"><s:text name="i18n_NotApprove"/></s:if>
								<s:elseif test="endinspection.reviewResult==2"><s:text name="i18n_Approve"/></s:elseif>
							</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_TotalScore" />：</td>
							<td class="title_bar_td" ><s:property value="endinspection.reviewTotalScore" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_AvgScore" />：</td>
							<td class="title_bar_td"><s:property value="endinspection.reviewAverageScore" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_ProductReviewGrade" />：</td>
							<td class="title_bar_td"><s:property value="reviewGrade" /></td>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_ReviewOpinionQualitative" />：</td>
							<td class="title_bar_td"><s:property value="endinspection.reviewOpinionQualitative" />	</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ReviewOpinion1" />：</td>
							<td class="title_bar_td" colspan="4" >
								<pre><s:property value="endinspection.reviewOpinion"/></pre>
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