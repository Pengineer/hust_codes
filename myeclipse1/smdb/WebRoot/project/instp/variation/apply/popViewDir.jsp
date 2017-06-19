<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:450px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right">证件类型：</td>
							<td class="title_bar_td" width="64"><s:property value="members[0].idcardType"/></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="80" align="right">证件号：</td>
							<td class="title_bar_td"><s:property value="members[0].idcardNumber"/></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right">姓名：</td>
							<td class="title_bar_td" width="64"><s:property value="members[0].memberName"/></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="80" align="right">成员类型：</td>
							<td class="title_bar_td">
								<s:if test="members[0].memberType == 1">教师</s:if>
								<s:elseif test="members[0].memberType == 2">外部专家</s:elseif>
								<s:elseif test="members[0].memberType == 3">学生</s:elseif>
							</td>
						</tr>
						<tr>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right">性别：</td>
							<td class="title_bar_td"><s:property value="members[0].gender" /></td>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right">所在单位：</td>
							<td class="title_bar_td" ><s:property value="members[0].agencyName" /></td>
						</tr>
						<tr>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right">部门类别：</td>
							<td class="title_bar_td">
								<s:if test="members[0].memberType == 1">研究基地</s:if>
								<s:elseif test="members[0].memberType == 2">院系</s:elseif>
								<s:elseif test="members[0].memberType == 3">外部部门</s:elseif>
							</td>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right">所在部门：</td>
							<td class="title_bar_td" ><s:property value="members[0].divisionName" /></td>
						</tr>
						<tr>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right">职称：</td>
							<td class="title_bar_td"><s:property value="members[0].specialistTitle" /></td>
							<td class="title_bar_td"  align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td"  align="right">专业：</td>
							<td class="title_bar_td" ><s:property value="members[0].major" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">每年工作时间（月）：</td>
							<td class="title_bar_td" ><s:property value="members[0].workMonthPerYear" /></td>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">分工情况：</td>
							<td class="title_bar_td" ><s:property value="members[0].workDivision" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">是否负责人：</td>
							<td class="title_bar_td" >
								<s:if test="members[0].isDirector == 1">是</s:if>
								<s:else>否</s:else>
							</td>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">是否子项目负责人：</td>
							<td class="title_bar_td">
								<s:if test="members[0].isSubprojectDirector == 1">是</s:if>
								<s:else>否</s:else>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="btn_div_view">
				<input id="okclosebutton" class="btn1" type="button" value="确定" />
			</div>
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					
				});
			</script>
		</body>
	
</html>
