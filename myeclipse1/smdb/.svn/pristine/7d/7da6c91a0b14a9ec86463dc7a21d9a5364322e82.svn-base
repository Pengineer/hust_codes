<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
			<script type="text/javascript" src="javascript/project/project_share/handlers_variation.js"></script>
		</head>
		
		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;添加变更申请
			</div>
			<div class="main">
				<s:include value="/validateError.jsp" />
				<s:include value="/project/general/variation/apply/editBasic.jsp" />
				<div class="main_content">
					<s:hidden name = "flag"></s:hidden>
					<s:form id="form_apply" action="add" namespace="/project/general/variation/apply" method="post" theme="simple" enctype="multipart/form-data">
						<s:include value="/project/general/variation/apply/editTable.jsp" />
						<s:hidden id="deadline" name="deadline" />
						<s:hidden id="varStatus" name="varStatus" />
						<s:hidden id="editFlag" name="editFlag" value="1"/>
					</s:form>
					<div class="btn_bar2">
						<input id="test_save" type="button" class="btn1 j_editSave" value="保存" />
						<input id="submit" type="button" class="btn1 j_editSubmit" value="提交" />
						<input id="cancel" type="button" class="btn1" value="取消"  onclick="history.back();"/>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/variation/edit.js','javascript/project/project_share/variation/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>