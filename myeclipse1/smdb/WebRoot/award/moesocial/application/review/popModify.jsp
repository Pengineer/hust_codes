<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div style="width:450px;">
			<s:form id="award_review">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11">研究内容意义和前沿性（满分20分）：</span></td>
						<td class="table_td9"><s:textfield name="meaningScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11">主要创新和学术价值（满分30分）：</span></td>
						<td class="table_td9"><s:textfield name="innovationScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11">学术影响或效益方法（满分30分）：</span></td>
						<td class="table_td9"><s:textfield name="influenceScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span class="table_title11">研究方法和学术规范（满分20分）：</span></td>
						<td class="table_td9"><s:textfield name="methodScore" cssClass="cal_score input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td8"><span>总分：</span></td>
						<td class="table_td9"><s:textfield name="totalScore" value="%{score}" readonly="true" cssStyle="cursor:pointer;" cssClass="input_css" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title11">建议等级：</span></td>
						<td class="table_td3"><s:select cssClass="select" id="awardGradeid" name="awardGradeid" list="%{baseService.getSystemOptionMap('awardGrade', null)}" headerKey="-1" headerValue="--%{'请选择'}--"/></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">评审意见：</td>
						<td class="table_td3"><s:textarea name="auditOpinion" rows="2" cssClass="textarea_css" /></td>
						<td class="table_td4"></td>
					</tr>
				</table>
			</s:form>
			<div class="btn_div_view">
				<input id="save" class="btn1 j_saveModifyReview" type="button" value="保存" />
				<input id="submit" class="btn1 j_submitModifyReview" type="button" value="提交" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/review/view.js', function(view) {
				view.init();
				view.initClick();
			});
		</script>
	</body>
</html>