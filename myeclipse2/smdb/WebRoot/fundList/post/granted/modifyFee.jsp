<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" >
				seajs.use('javascript/fundList/modifyFee.js', function(modify) {
					modify.init();
				});
			</script>
		</head>
		
		<body>
			<div style="width:30px;">
				<s:form id="form_modifyFee">
					<s:hidden id="entityId" name="entityId" value="%{entityId}" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td12"><s:textfield id="fee" name="projectFunding.fee" cssClass="input_css_other" /></td>
							<td><input id="submit" class="btn1" type="button" value="<s:text name='i18n_Submit' />" /></td>
							<td><input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" /></td>
						</tr>
					</table>
				</s:form>
<%--				<div class="btn_div_view">--%>
<%--					<input id="submit" class="btn1" type="button" value="<s:text name='i18n_Submit' />" />--%>
<%--					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />--%>
<%--				</div>--%>
			</div>
		</body>
	</s:i18n>
</html>