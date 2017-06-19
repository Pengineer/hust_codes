<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Message">
		<head>
			<title><s:text name="i18n_Add" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<s:form id="form_chat" action="send" namespace="/chat" theme="simple">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="80"></td>
						<td class="table_td3">
							<s:textarea id="content" name="content" value="" style="width: 510px; height: 65px;"/>
						</td>
						<td style="padding:15px 30px;"><input id="submit" class="btn1" type="button" value="<s:text name='发送' />" /></td>
						<td><input id="cancel" class="btn1" type="button" value="<s:text name='取消' />" /></td>
					</tr>
				</table>
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
			</s:form>
			<s:include value="/system/chat/view.jsp"></s:include>
			<script type="text/javascript">
				seajs.use('javascript/system/chat/edit.js', function(edit) {
					$(function(){
						edit.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>