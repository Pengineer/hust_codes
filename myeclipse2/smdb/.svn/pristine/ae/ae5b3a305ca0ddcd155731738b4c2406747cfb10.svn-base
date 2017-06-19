<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:480px;">
				<s:form id="end_review">
					<s:hidden id="endId" name="endId"/>
				    <s:hidden id="deadline" name="deadline" />
				    <s:hidden id="endStatus" name="endStatus" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td colspan="3"><span class="tip"><s:text name="i18n_InnovationScoreDesciption" /></span></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7"><s:text name="i18n_InnovationScore" />：<br /><s:text name="i18n_MaxScore1" /></span></td>
							<td class="table_td6 score_class"><s:textfield name="innovationScore" cssClass="cal_score input_css"/></td>
							<td class="table_td7" width="70"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td colspan="3"><span class="tip"><s:text name="i18n_ScientificScoreDesciption" /></span></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7"><s:text name="i18n_ScientificScore" />：<br /><s:text name="i18n_MaxScore2" /></span></td>
							<td class="table_td6 score_class"><s:textfield name="scientificScore" cssClass="cal_score input_css"/></td>
							<td class="table_td7" width="70"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td colspan="3"><span class="tip"><s:text name="i18n_BenefitScoreDesciption" /></span></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7"><s:text name="i18n_BenefitScore" />：<br /><s:text name="i18n_MaxScore2" /></span></td>
							<td class="table_td6 score_class"><s:textfield name="benefitScore" cssClass="cal_score input_css"/></td>
							<td class="table_td7" width="70"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7"><s:text name="i18n_TotalScore" />：</span></td>
							<td class="table_td6"><s:textfield id="totalScore" name="totalScore" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
							<td class="table_td7" width="70"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title7"><s:text name="i18n_AdviceReviewGrade" />：</span></td>
							<td class="table_td6"><s:textfield id="grade" name="grade" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
							<td class="table_td7"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title7"><s:text name="i18n_ReviewOpinionQualitative" />：</span></td>
							<td class="table_td6"><s:select cssClass="select" id="qualitativeOpinion" name="qualitativeOpinion" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" /></td>
							<td class="table_td7"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span><s:text name="i18n_ReviewOpinion1" />：<br/><s:text name='i18n_LimitWordTwoThousand'/></span></td>
							<td class="table_td6"><s:textarea name="opinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td7"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_submitOrSaveAddEndReview" data="2" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_submitOrSaveAddEndReview" data="3" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/endinspection/review/edit.js','javascript/project/project_share/endinspection/review/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>
