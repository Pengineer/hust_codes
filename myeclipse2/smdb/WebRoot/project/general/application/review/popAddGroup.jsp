<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:450px;">
				<textarea id="view_template" style="display:none;">
					<div class="p_box_body">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<thead>
							<tr class="head_title">
								<td colspan="6"><s:text name="各专家评审结果"/></td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td width="90"><s:text name='i18n_ExpertName' /></td>
								<td width="70"><s:text name='i18n_Score' /></td>
								<td width="70"><s:text name='i18n_Grade' /></td>
								<td><s:text name='评审意见'/></td>
							</tr>
							{for item in applicationReviews}
								{if item != null}
									<tr class="table_tr4">
										<td>${item[2]}</td>
										<td>${item[3]}</td>
										<td>${item[4]}</td>
										<td>${item[7]}</td>
									</tr>
								{/if}
							{/for}
						</tbody>
					</table>
				</div>
			</textarea>
			<div id="view_container" style="display:none; clear:both;"></div>
				<s:form id="app_review_group">
					<s:hidden id="entityId" name="entityId"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2"><s:text name="i18n_ReviewWay" />：</span></td>
							<td class="table_td12"><s:radio name="reviewWay" list="#{'1':getText('i18n_ReviewComm'),'2':getText('i18n_ReviewMeet')}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2"><s:text name="i18n_ReviewResult" />：</span></td>
							<td class="table_td12"><s:radio name="reviewResult" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title2"><s:text name="i18n_ReviewOpinionQualitative" />：</span></td>
							<td class="table_td12"><s:select cssClass="select" id="reviewOpinionQualitative" name="reviewOpinionQualitative" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_ReviewOpinion" />：<br/><s:text name='i18n_LimitWordTwoThousand'/></span></td>
							<td class="table_td12"><s:textarea name="reviewOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addGroupSave" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_addGroupSubmit" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/application/review/edit.js','javascript/project/project_share/application/review/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>
