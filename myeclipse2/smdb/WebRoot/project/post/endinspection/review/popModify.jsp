<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>后期资助项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:480px;">
				<s:form id="end_review">
					<s:hidden id="endId" name="endId"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td colspan="3"><span class="tip">是否立足学术前沿；是否具有原创性；是否取得突破；是否采用新的研究手段和方法 (重大：50-40分；较大：40－30分；一般：30-20分；不明显：20分以下)</span></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7">创新和突破得分：<br />（50分）</span></td>
							<td class="table_td6 score_class"><s:textfield name="innovationScore" cssClass="cal_score input_css"/></td>
							<td class="table_td7" width="70"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td colspan="3"><span class="tip">观点、方法是否正确；体例是否规范；资料是否翔实；学风是否严谨 (很强：25-20分；较强：20－15分；一般：15-10分；不强：10分以下)</span></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7">科学性和规范性得分：<br />（25分）</span></td>
							<td class="table_td6 score_class"><s:textfield name="scientificScore" cssClass="cal_score input_css"/></td>
							<td class="table_td7" width="70"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td colspan="3"><span class="tip">成果的学术价值、应用价值或影响如何；是否或可能取得良好的经济或社会效益 (重大：25-20分；较大：20－15分；一般：15-10分；不明显：10分以下)</span></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7">价值和效益得分：<br />（25分）</span></td>
							<td class="table_td6 score_class"><s:textfield name="benefitScore" cssClass="cal_score input_css"/></td>
							<td class="table_td7" width="70"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td5" style="width:100px"><span class="table_title7">总分：</span></td>
							<td class="table_td6"><s:textfield id="totalScore" name="totalScore"  readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
							<td class="table_td7" width="70"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title7">建议评价等级：</span></td>
							<td class="table_td6"><s:textfield id="grade" name="grade"  readonly="true" cssStyle="cursor:pointer;" cssClass="input_css"/></td>
							<td class="table_td7"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span class="table_title7">成果修改定性意见：</span></td>
							<td class="table_td6"><s:select cssClass="select" id="qualitativeOpinion" name="qualitativeOpinion" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--%{getText('请选择')}--" /></td>
							<td class="table_td7"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td5"><span>鉴定意见：<br/>（限2000字）</span></td>
							<td class="table_td6"><s:textarea name="opinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td7"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_submitOrSaveModifyEndReview" data=2 type="button" value="保存" />
					<input id="submit" class="btn1 j_submitOrSaveModifyEndReview" data=3 type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/post/endinspection/review/edit.js','javascript/project/project_share/endinspection/review/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>
