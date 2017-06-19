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
						<td class="title_bar_td"><s:property value="awardApplication.finalAuditorName" /></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核时间：</td>
						<td class="title_bar_td" width="120"><s:date name="awardApplication.finalAuditDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">审核结果：</td>
						<td class="title_bar_td" colspan="4">
							<s:if test="awardApplication.finalAuditStatus == 2 && awardApplication.finalAuditResult == 1">不同意（暂存）</s:if>
							<s:elseif test="awardApplication.finalAuditStatus == 2 && awardApplication.finalAuditResult == 2">同意（暂存）</s:elseif>
							<s:elseif test="awardApplication.finalAuditStatus == 3 && awardApplication.finalAuditResult == 1">不同意</s:elseif>
							<s:elseif test="awardApplication.finalAuditStatus == 3 && awardApplication.finalAuditResult == 2">同意</s:elseif>
						</td>
					</tr>
					<s:if test="awardApplication.finalAuditResult == 2">	
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">获奖年度：</td>
						<td class="title_bar_td"><s:property value="awardApplication.year" /></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">证书编号：</td>
						<td class="title_bar_td" width="80"><s:property value="awardApplication.number" /></td>
					</tr>
					</s:if>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">审核意见：</td>
						<td class="title_bar_td" >
							<pre><s:property value="awardApplication.finalAuditOpinion"/></pre>
						</td>
					</tr>
				</table>
			</div>
			<div class="btn_div_view">
				<input id="okclosebutton" class="btn1" type="button" value="确定" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/pop/pop-init.js', function(view) {
				
			});
		</script>
	</body>
</html>
