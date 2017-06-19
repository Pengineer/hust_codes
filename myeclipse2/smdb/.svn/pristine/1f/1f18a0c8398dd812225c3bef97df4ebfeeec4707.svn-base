<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Product">
		<head>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					
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
								<td class="title_bar_td" width="80" align="right"><s:text name="i18n_OrganizationName"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('name')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="92" align="right"><s:text name="i18n_DirectorName"/>：</td>
								<td class="title_bar_td" width="120"><s:property value="#request.map.get('directorName')"/></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_BelongUnit"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('agencyName')"/></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_BelongDeptInst"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('divisionName')"/></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_Dtype"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('discipline')"/></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_Email"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('email')"/></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_Phone"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('officePhone')"/></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_PostCode"/>：</td>
								<td class="title_bar_td"><s:property value="#request.map.get('officePostcode')"/></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_MemberName"/>：</td>
								<td class="title_bar_td" colspan="4"><s:property value="#request.map.get('member')"/></td>
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