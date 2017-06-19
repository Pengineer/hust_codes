<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Unit">
		<head>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/pop-init.js', function(view) {
					
				});
			</script>
		</head>

		<body>
			<div style="width:490px;">
				<s:if test="#request.map.get('errorInfo') == null || #request.map.get('errorInfo') == ''">
					<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="92" align="right"><s:text name="i18n_Name" />：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('name')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="92" align="right"><s:text name="i18n_Subjection" />：</td>
								<td class="title_bar_td" width="116"><s:property value="#request.map.get('belongUnit')" /></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_SName" />：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('sname')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_Linkman" />：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('linkmanName')" /></td>
							</tr>
							<tr>
							    <td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_SEmail" />：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('semail')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_SPhone" />：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('sphone')" /></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_SFax" />：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('sfax')" /></td>
							</tr>
						</table>
					</div>
				</s:if>
				<s:else>
					<div style="text-align:center;"><s:property value="#request.map.get('errorInfo')" /></div>
				</s:else>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="<s:text name='i18n_Ok' />" />
				</div>
			</div>
		</body>
	</s:i18n>
</html>