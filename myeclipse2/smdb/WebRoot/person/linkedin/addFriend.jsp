<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Person">
		<head>
			<title><s:text name="i18n_Add" /><s:text name="i18n_Teacher" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div style="width:340px;border: 1px solid #E1E1E1;padding-bottom: 10px;padding-right: 25px;">
				<div id="fri">
					<s:hidden id="entityId" name="entityId"/>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="table_tr2">
							<td width="22%"><span style="margin-left:10px;"><s:text name='验证信息' />：</span></td>
							<td width="78%" style = "line-height: 20px;padding: 15px 2px;"><s:textarea id="reason" name="reason"  style="width: 242px; height: 76px;"/></td>
						</tr>
					</table>
				</div>

				<div class="btn_div_view">
					<input id="submit" class="btn1" type="submit" value="<s:text name='i18n_Ok' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/person/link/edit.js', function(edit) {
					$(function(){
						edit.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>
