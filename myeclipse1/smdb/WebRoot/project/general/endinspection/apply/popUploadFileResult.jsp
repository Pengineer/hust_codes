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
				<s:form id="uploadEndFile_form">
					<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
					<s:hidden id="endId" name="endId" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="250"><span>上传结项申请书：</span></td>
							<td class="table_td3">
								<input type="file" id="file_add" />
							</td>
							<td class="table_td4" width="100"></td>
						</tr>
					</table>
					<div class="btn_div_view">
						<input id="submit" class="btn1" type="button" value="提交" onclick="uploadFileResult(3, 2, thisPopLayer);" />
						<input id="cancel" class="btn1" type="button" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/endinspection/apply/edit.js', function(edit) {
					edit.initUpload();
					window.uploadFileResult = function(data, type, layer){edit.uploadFileResult(data, type, layer)};
				});
			</script>
		</body>
	
</html>