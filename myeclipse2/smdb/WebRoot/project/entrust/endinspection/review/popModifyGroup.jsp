<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:450px;">
				<s:form id="end_review_group">
					<s:hidden id="endId" name="endId"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2"><s:text name="i18n_ReviewWay1" />：</span></td>
							<td class="table_td12"><s:radio name="reviewWay" list="#{'1':getText('i18n_ReviewComm1'),'2':getText('i18n_ReviewMeet1')}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2"><s:text name="i18n_ReviewResult1" />：</span></td>
							<td class="table_td12"><s:radio name="reviewResult" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title2"><s:text name="i18n_ReviewOpinionQualitative" />：</span></td>
							<td class="table_td12"><s:select cssClass="select" id="reviewOpinionQualitative" name="reviewOpinionQualitative" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_ReviewOpinion1" />：<br/><s:text name='i18n_LimitWordTwoThousand'/></span></td>
							<td class="table_td12"><s:textarea name="reviewOpinion" rows="2" cssClass="textarea_css" /><br/><a class="j_groupOpinion" href="javascript:void(0)"><s:text name="i18n_ReferAllExpertsOpinion" /></a></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_submitOrSaveModifyEndGroupReview" data="2" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_submitOrSaveModifyEndGroupReview" data="3" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/entrust/endinspection/review/edit.js','javascript/project/project_share/endinspection/review/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
					window.onload = edit.initGroupOpinion("entrust");
				});
			</script>
		</body>
	</s:i18n>
</html>
