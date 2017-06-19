<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:750px;">
				<s:form id="review_appform" theme="simple">
					<s:hidden id="entityId" name="entityId"/>
					<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
					<s:hidden id="submitStatus" name="submitStatus"/>
					<div style="overflow-y:scroll;height:500px; *margin-right:20px; _margin-right:20px;">
						<div class="p_box_t">
							<div class="p_box_t_t"><s:text name='i18n_ReviewInfo' /></div>
							<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
						</div>
						<div>
							<div id="review">
								<s:iterator value="reviews" status="stat">
									<table class="table_valid" width="100%" border="0" cellspacing="2" cellpadding="0">
										<tr class="table_tr2">
											<td class="table_td8" width="200"><div class="sort_title"><b><span><s:text name='i18n_ReviewerExpert' /></span><span title="reviewSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
											<td class="table_td9">
												<input type="button" class="add_review btn1"  value="<s:text name="i18n_Add" />" />&nbsp;
												<input type="button" class="delete_row btn1" value="<s:text name="i18n_Delete" />" />&nbsp;
												<input type="button" class="up_row btn1" value="上移" />&nbsp;
												<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
												<span class="tip">可拖拽空白区域对成员排序</span>
											</td>
											<td class="table_td10" width="90"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span class="table_title11"><s:text name="i18n_PersonType" />：</span></td>
											<td class="table_td9">
												<s:select cssClass="select" name="%{'reviews['+#stat.index+'].reviewerType'}" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Teacher'),'2':getText('i18n_Expert')}" />
											</td>
											<td class="table_td10" width="90"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span class="table_title11"><s:text name="i18n_Name" />：</span></td>
											<td class="table_td9">
												<input name="selectReviewer" type="button" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
												<div name="reviewerNameDiv" class="choose_show"><s:property value="%{reviews[#stat.index].reviewerName}"/></div>
												<s:hidden name="%{'reviews['+#stat.index+'].reviewer.id'}" />
												<s:hidden name="%{'reviews['+#stat.index+'].reviewerName'}"/>
											</td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span><s:text name="i18n_LocalUnitAndDeptInst" />：</span></td>
											<td class="table_td9">
												<div name="reviewerUnitDiv" class="unit_show"><s:property value="%{reviews[#stat.index].agencyName}"/>&nbsp;<s:property value="%{reviews[#stat.index].divisionName}"/></div>
											</td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span class="table_title11"><s:text name="i18n_InnovationScore"/><s:text name="i18n_MaxScore1"/>：</span></td>
											<td class="table_td9"><s:textfield name="%{'reviews['+#stat.index+'].innovationScore'}" cssClass="cal_score input_css" /></td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span class="table_title11"><s:text name="i18n_ScientificScore"/><s:text name="i18n_MaxScore2"/>：</span></td>
											<td class="table_td9"><s:textfield name="%{'reviews['+#stat.index+'].scientificScore'}" cssClass="cal_score input_css" /></td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span class="table_title11"><s:text name="i18n_BenefitScore"/><s:text name="i18n_MaxScore2"/>：</span></td>
											<td class="table_td9"><s:textfield name="%{'reviews['+#stat.index+'].benefitScore'}" cssClass="cal_score input_css" /></td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span><s:text name="i18n_TotalScore" />：</span></td>
											<td class="table_td9"><s:textfield name="reviewScore" value="%{reviews[#stat.index].score}"  readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td8"><span><s:text name="i18n_AdviceReviewGrade" />：</span></td>
											<td class="table_td9"><s:textfield name="reviewGrade" value="%{reviews[#stat.index].grade.name}"  readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
											<td class="table_td10"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td5"><span class="table_title7"><s:text name="i18n_ReviewOpinionQualitative1" />：</span></td>
											<td class="table_td6"><s:select cssClass="select" name="%{'reviews['+#stat.index+'].qualitativeOpinion'}" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" /></td>
											<td class="table_td7"></td>
										</tr>
										<tr class="table_tr2">
											<td class="table_td5"><span><s:text name="i18n_ReviewOpinion" />：<br/><s:text name='i18n_LimitWordTwoThousand'/></span></td>
											<td class="table_td6"><s:textarea name="%{'reviews['+#stat.index+'].opinion'}" rows="2" cssClass="textarea_css" /></td>
											<td class="table_td7"></td>
										</tr>
									</table>
								</s:iterator>
							</div>
							<div id="last_add_div" style="display:none;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr class="table_tr2">
										<td class="table_td8" width="230"><div class="sort_title"><b><span><s:text name="i18n_ReviewerExpert"/></span><span title="reviewSpan"></span></b></div>&nbsp;</td>
										<td class="table_td9">
											<input type="button" class="add_last_table btn1" value="<s:text name="i18n_Add" />" />
										</td>
										<td class="table_td10" width="90"></td>
									</tr>
								</table>
							</div>
						</div>
						<div class="p_box_t">
							<div class="p_box_t_t"><s:text name='i18n_GroupReviewInfo' /></div>
							<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
						</div>
						<div>
							<table width="100%" border="0" cellspacing="2" cellpadding="0">
								<tr class="table_tr2">
									<td class="table_td8" width="200"><span class="table_title11"><s:text name="i18n_ReviewWay" />：</span></td>
									<td class="table_td9"><s:radio name="reviewWay" list="#{'1':getText('i18n_ReviewComm'),'2':getText('i18n_ReviewMeet')}"/></td>
									<td class="table_td10" width="90"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span><s:text name="i18n_TotalScore" />：<br /></span></td>
									<td class="table_td9"><s:textfield name="totalScore" value="%{#request.totalScore}" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span><s:text name="i18n_AvgScore" />：<br /></span></td>
									<td class="table_td9"><s:textfield name="avgScore" value="%{#request.avgScore}" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span><s:text name="i18n_ReviewGrade" />：</span></td>
									<td class="table_td9"><s:textfield name="groupGrade" value="%{#request.groupGrade}" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span class="table_title11"><s:text name="i18n_ReviewResult" />：</span></td>
									<td class="table_td9"><s:radio name="reviewResult" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span class="table_title11"><s:text name="i18n_ReviewOpinionQualitative1" />：</span></td>
									<td class="table_td9"><s:select cssClass="select" id="reviewOpinionQualitative" name="reviewOpinionQualitative" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" /></td>
									<td class="table_td10"></td>
								</tr>
								<tr class="table_tr2">
									<td class="table_td8"><span><s:text name="i18n_ReviewOpinion" />：<br/><s:text name='i18n_LimitWordTwoThousand'/></span></td>
									<td class="table_td9"><s:textarea name="reviewOpinion" rows="2" cssClass="textarea_css" /></td>
									<td class="table_td10"></td>
								</tr>
							</table>
						</div>
					</div>
				</s:form>
				<table id="table_review" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
					<tr class="table_tr2">
						<td class="table_td8" width="200"><div class="sort_title"><b><span><s:text name='i18n_ReviewerExpert' /></span><span title="reviewSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
						<td class="table_td9">
							<input type="button" class="add_review btn1"  value="<s:text name="i18n_Add" />" />&nbsp;
							<input type="button" class="delete_row btn1" value="<s:text name="i18n_Delete" />" />&nbsp;
							<input type="button" class="up_row btn1" value="上移" />&nbsp;
							<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
							<span class="tip">可拖拽空白区域对成员排序</span>
						</td>
						<td class="table_td10" width="90"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11"><s:text name="i18n_PersonType" />：</span></td>
						<td class="table_td9">
							<s:select cssClass="select" name="reviews[].reviewerType" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Teacher'),'2':getText('i18n_Expert')}" />
						</td>
						<td class="table_td10" width="90"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11"><s:text name="i18n_Name" />：</span></td>
						<td class="table_td9">
							<input name="selectReviewer" type="button" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
							<div name="reviewerNameDiv" class="choose_show"><s:property value="%{reviews[#stat.index].reviewerName}"/></div>
							<s:hidden name="reviews[].reviewer.id" />
							<s:hidden name="reviews[].reviewerName"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span><s:text name="i18n_LocalUnitAndDeptInst" />：</span></td>
						<td class="table_td9">
							<div name="reviewerUnitDiv" class="unit_show"></div>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11"><s:text name="i18n_InnovationScore"/><s:text name="i18n_MaxScore1"/>：</span></td>
						<td class="table_td9"><s:textfield name="reviews[].innovationScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11"><s:text name="i18n_ScientificScore"/><s:text name="i18n_MaxScore2"/>：</span></td>
						<td class="table_td9"><s:textfield name="reviews[].scientificScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11"><s:text name="i18n_BenefitScore"/><s:text name="i18n_MaxScore2"/>：</span></td>
						<td class="table_td9"><s:textfield name="reviews[].benefitScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span><s:text name="i18n_TotalScore" />：<br></span></td>
						<td class="table_td9"><s:textfield name="reviewScore" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span><s:text name="i18n_AdviceReviewGrade" />：</span></td>
						<td class="table_td9"><s:textfield name="reviewGrade" readonly="true"  cssStyle="cursor:pointer;" cssClass="input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11"><s:text name="i18n_ReviewOpinionQualitative1" />：</span></td>
						<td class="table_td9"><s:select cssClass="select" name="reviews[].qualitativeOpinion" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span><s:text name="i18n_ReviewOpinion" />：<br/><s:text name='i18n_LimitWordTwoThousand'/></span></td>
						<td class="table_td9"><s:textarea name="reviews[].opinion" rows="2" cssClass="textarea_css" /></td>
						<td class="table_td10"></td>
					</tr>
				</table>
			</div>
			<div class="btn_div_view">
				<input id="save" class="btn1 j_addResultSave" type="button" value="<s:text name='i18n_Save' />" />
				<input id="submit" class="btn1 j_addResultSubmit" type="button" value="<s:text name='i18n_Submit' />" />
				<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
			</div>
			<script type="text/javascript">
				<s:if test="null != revResultFlag && revResultFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
    					thisPopLayer.callBack(thisPopLayer);
    				})();
				</s:if>
				seajs.use(['javascript/project/general/application/review/edit_result.js','javascript/project/project_share/application/review/validate_result.js'], function(editResult, validate) {
					validate.valid();
					editResult.init();
				});
			</script>
		</body>
	</s:i18n>
</html>
