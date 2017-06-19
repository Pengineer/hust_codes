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
			<s:include value="/person/linkedin/view.jsp"></s:include>
			<s:form id="form_chat" action="add" namespace="/inBox" theme="simple">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td3" style ="padding: 20px 0 50px 10px;">
							<s:textarea id="content" name="content" value="" style="width: 583px; height: 66px;"/>
						</td>
						<td style="padding: 20px 0 50px 20px;"><input id="submit" class="btn1" type="button" value="<s:text name='发送' />" /></td>
						<td style="padding: 20px 0 50px 20px;"><input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" onclick="history.back();" /></td>
					</tr>
				</table>
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="sendType" name="sendType" value="4" />
			</s:form>
			<script type="text/javascript">
				seajs.use('javascript/person/link/send.js', function(send) {
					$(function(){
						send.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>