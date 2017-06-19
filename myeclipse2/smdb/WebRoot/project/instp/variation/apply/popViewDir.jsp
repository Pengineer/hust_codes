<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:450px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name='i18n_IdcardType' />：</td>
							<td class="title_bar_td" width="64"><s:property value="members[0].idcardType"/></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="80" align="right"><s:text name='i18n_IdcardNumber' />：</td>
							<td class="title_bar_td"><s:property value="members[0].idcardNumber"/></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right"><s:text name="i18n_Name" />：</td>
							<td class="title_bar_td" width="64"><s:property value="members[0].memberName"/></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="80" align="right"><s:text name="i18n_MemberType" />：</td>
							<td class="title_bar_td">
								<s:if test="members[0].memberType == 1"><s:text name="i18n_Teacher"/></s:if>
								<s:elseif test="members[0].memberType == 2"><s:text name="i18n_Expert"/></s:elseif>
								<s:elseif test="members[0].memberType == 3"><s:text name="i18n_Student"/></s:elseif>
							</td>
						</tr>
						<tr>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right"><s:text name="i18n_Gender" />：</td>
							<td class="title_bar_td"><s:property value="members[0].gender" /></td>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right"><s:text name="i18n_LocalUnit" />：</td>
							<td class="title_bar_td" ><s:property value="members[0].agencyName" /></td>
						</tr>
						<tr>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right"><s:text name="i18n_DeptInstFlag" />：</td>
							<td class="title_bar_td">
								<s:if test="members[0].memberType == 1"><s:text name="i18n_Institute2"/></s:if>
								<s:elseif test="members[0].memberType == 2"><s:text name="i18n_Department2"/></s:elseif>
								<s:elseif test="members[0].memberType == 3"><s:text name="i18n_OtherDeptInst"/></s:elseif>
							</td>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right"><s:text name="i18n_LocalDeptInst" />：</td>
							<td class="title_bar_td" ><s:property value="members[0].divisionName" /></td>
						</tr>
						<tr>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right"><s:text name="i18n_SpecialistTitle" />：</td>
							<td class="title_bar_td"><s:property value="members[0].specialistTitle" /></td>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right"><s:text name="i18n_Major" />：</td>
							<td class="title_bar_td" ><s:property value="members[0].major" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_WorkMonthPerYear" />：</td>
							<td class="title_bar_td" ><s:property value="members[0].workMonthPerYear" /></td>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_WorkDivision" />：</td>
							<td class="title_bar_td" ><s:property value="members[0].workDivision" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_IsDirector" />：</td>
							<td class="title_bar_td" >
								<s:if test="members[0].isDirector == 1"><s:text name="i18n_Yes"/></s:if>
								<s:else><s:text name="i18n_No"/></s:else>
							</td>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right"><s:text name="i18n_IsSubprojectDirector" />：</td>
							<td class="title_bar_td">
								<s:if test="members[0].isSubprojectDirector == 1"><s:text name="i18n_Yes" /></s:if>
								<s:else><s:text name="i18n_No" /></s:else>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="btn_div_view">
				<input id="okclosebutton" class="btn1" type="button" value="<s:text name='i18n_Ok' />" />
			</div>
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					
				});
			</script>
		</body>
	</s:i18n>
</html>
