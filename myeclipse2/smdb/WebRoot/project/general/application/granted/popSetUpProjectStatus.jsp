<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:480px;">
				<s:form id="granted_revoke">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="120"><span class="table_title4"><s:text name="i18n_ProjectStatus" />：</span></td>
							<td class="table_td3"><s:select cssClass="select" name="projectStatus" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'4':getText('i18n_Revoke')}" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title4"><s:text name="i18n_EndStopWithdrawDate" />：</span></td>
							<td class="table_td3">
								<s:textfield name="endStopWithdrawDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="endStopWithdrawDate" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="120" valign="top"><span class="table_title4"><s:text name='i18n_SetUpOpinion' />：<br/><s:text name="i18n_LimitWordTwo"/></span></td>
							<td class="table_td3"><s:textarea name="projectOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td4" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="120" valign="top"><span><s:text name='i18n_SetUpOpinion' />：<br /><s:text name='i18n_FeedbackToDirector'/><br /></span></td>
							<td class="table_td3"><s:textarea name="projectOpinionFeedback" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td4" width="100"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="submit" class="btn1 j_setUpProjectStatus" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript" >
				seajs.use('javascript/project/general/application/granted/popSetUpProjectStatus.js', function(setStatus) {
					setStatus.init();
				});
			</script>
		</body>
	</s:i18n>
</html>