<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/pop/view/view.js', function(view) {
				
			});
		</script>
	</head>

	<body>
		<s:iterator value="#request.infoList" status="stat">
			<div style="width:450px;">
				<s:if test="#request.infoList[#stat.index].get('errorInfo') == null || #request.infoList[#stat.index].get('errorInfo') == ''">
					<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="64" align="right">姓名：</td>
								<td class="title_bar_td"><s:property value="#request.infoList[#stat.index].get('name')" /></td>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="64" align="right">性别：</td>
								<td class="title_bar_td" width="120"><s:property value="#request.infoList[#stat.index].get('gender')" /></td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">出生日期：</td>
								<td class="title_bar_td"><s:date name="#request.infoList[#stat.index].get('birthday')" format="yyyy-MM-dd" /></td>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">电子邮箱：</td>
								<td class="title_bar_td"><s:property value="#request.infoList[#stat.index].get('email')" /></td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">办公电话：</td>
								<td class="title_bar_td"><s:property value="#request.infoList[#stat.index].get('phone')" /></td>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">手机：</td>
								<td class="title_bar_td"><s:property value="#request.infoList[#stat.index].get('mobilePhone')" /></td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">所在单位：</td>
								<td class="title_bar_td"><s:property value="#request.infoList[#stat.index].get('belongUnit')" /></td>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right">所在部门：</td>
								<td class="title_bar_td"><s:property value="#request.infoList[#stat.index].get('belongDept')" /></td>
							</tr>
						</table>
					</div>
				</s:if>
				<s:else>
					<div style="text-align:center;"><s:property value="#request.infoList[#stat.index].get('errorInfo')" /></div>
				</s:else>
			</div>
		</s:iterator>
		<div class="btn_div_view">
			<input id="okclosebutton" class="btn1" type="button" value="确定" />
		</div>
	</body>
</html>