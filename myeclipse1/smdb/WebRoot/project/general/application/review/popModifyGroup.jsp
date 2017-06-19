<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:450px;">
				<s:form id="app_review_group">
					<s:hidden id="entityId" name="entityId"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2">评审方式：</span></td>
							<td class="table_td12"><s:radio name="reviewWay" list="#{'1':'通讯评审','2':'会议评审'}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" style="width:100px"><span class="table_title2">评审结果：</span></td>
							<td class="table_td12"><s:radio name="reviewResult" list="#{'2':'同意','1':'不同意'}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title2">成果修改定性意见：</span></td>
							<td class="table_td12"><s:select cssClass="select" id="reviewOpinionQualitative" name="reviewOpinionQualitative" list="%{baseService.getSystemOptionMapAsName('reviewOpinionQualitative', null)}" headerKey="-1" headerValue="--请选择--" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>评审意见：<br/>（限2000字）</span></td>
							<td class="table_td12"><s:textarea name="reviewOpinion" rows="2" cssClass="textarea_css" /><br/><a class="j_groupOpinion" href="javascript:void(0)">参考本组专家所有意见</a></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_modifyGroupSave" type="button" value="保存" />
					<input id="submit" class="btn1 j_modifyGroupSubmit" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/application/review/edit.js','javascript/project/project_share/application/review/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>
