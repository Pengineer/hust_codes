<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>

		<body>
			<div style="width:400px;">
				<s:form id="graFile">
					<s:hidden id = "graId" name="graId"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title4">上传立项计划书：</span></td>
							<td class="table_td12">
								<input type="file" id="file_${graId}" />
								<s:hidden name="gra_file"/>
								<s:hidden id = "grantedId" name = "graId"></s:hidden>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" width="120"><span class="table_title4">审核结果：</span></td>
							<td class="table_td12"><s:radio name="graResult" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span  class="table_title4">审核时间：</span></td>
							<td class="table_td12">
								<s:textfield name="graDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="graDate" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">审核意见：</td>
							<td class="table_td12"><s:textarea name='graImportedOpinion' rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">审核意见：<br /></span><span>（反馈给负责人）</span></td>
							<td class="table_td12"><s:textarea name="graOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_modifyResultSave" type="button" value="保存" />
					<input id="submit" class="btn1 j_modifyResultSubmit" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/instp/application/granted/edit.js','javascript/project/project_share/application/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>
