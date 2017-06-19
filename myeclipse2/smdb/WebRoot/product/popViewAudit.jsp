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
						<td class="title_bar_td" width="64" align="right">审核人：</td>
						<td class="title_bar_td"><s:property value="auditorName" /></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核时间：</td>
						<td class="title_bar_td" width="120"><s:date name="auditDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">审核结果：</td>
						<td class="title_bar_td" colspan="4">
							<s:if test="auditStatus==3 && auditResult==1">不同意</s:if>
							<s:elseif test="auditStatus==3 && auditResult==2">同意</s:elseif>
						</td>
					</tr>
				</table>
			</div>
			<div class="btn_div_view">
				<ul>
					<li><input id="okclosebutton" class="btn1" type="button" value="确定" /></li>
				</ul>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use(['javascript/product/view.js', ], function(view) {
				view.initLive();
			});
		</script>
	</body>
</html>
