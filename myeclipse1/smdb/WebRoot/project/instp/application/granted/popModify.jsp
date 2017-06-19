<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>
		
		<body>
			<div style="width:480px;">
				<s:include value="/validateError.jsp" />
				<s:form id="midFile">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="100"><span class="table_title4">上传立项计划书：</span></td>
							<td class="table_td12">
								<input type="file" id="file_${granted.id}" />
								<s:hidden id = "grantedId" name = "granted.id"></s:hidden>
							</td>
							<td class="table_td13" width="80"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">备注：<br/>（限200字）</td>
							<td class="table_td12"><s:textarea name="note" id="granote" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<s:hidden id="mid_flag" value="1"/>
					<s:hidden id="graId" name="projectid"/>
					<s:hidden id="midApplicantSubmitStatus" name="midApplicantSubmitStatus"/>
					<s:hidden id="deadline" name="deadline"/>
					<s:hidden id="timeFlag" name="timeFlag" />
					<s:hidden id="graStatus" name="graStatus" />
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_modifySave" type="button" value="保存"/>
					<input id="submit" class="btn1 j_modifySubmit" type="button" value="提交"/>
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				<s:if test="timeFlag == 0">
					(function(){
						alert("当前时间已经超过个人申请截止时间！");
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.destroy();
					})();
				</s:if>
				<s:if test="null != graFlag && graFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					})();
				</s:if>
				seajs.use(['javascript/project/instp/application/granted/edit.js','javascript/project/project_share/application/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>