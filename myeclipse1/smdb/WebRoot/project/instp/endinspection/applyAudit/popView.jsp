<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>基地项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:480px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right">审核人：</td>
							<td class="title_bar_td"><s:property value="endAuditorName"/></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">审核时间：</td>
							<td class="title_bar_td" width="120"><s:date name="endAuditDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right">是否同意结项：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="endAuditResult==2">同意</s:if>
								<s:elseif test="endAuditResult==1">不同意</s:elseif>
							</td>
						</tr>
						<s:if test="isApplyNoevaluation == 1 && endNoauditResult != 0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right">免鉴定成果：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="endNoauditResult==2">同意</s:if>
								<s:elseif test="endNoauditResult==1">不同意</s:elseif>
							</td>
						</tr>
						</s:if>
						<s:if test="isApplyExcellent == 1 && endExcellentResult != 0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="110" align="right">优秀成果结果：</td>
							<td class="title_bar_td" colspan="4">
								<s:if test="endExcellentResult==2">同意</s:if>
								<s:elseif test="endExcellentResult==1">不同意</s:elseif>
							</td>
						</tr>
						</s:if>
						<s:if test="endAuditOpinion != null && endAuditOpinion != ''">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">审核意见：</td>
							<td class="title_bar_td" colspan="4" >
								<pre><s:property value="endAuditOpinion"/></pre>
							</td>
						</tr>
						</s:if>
					</table>
				</div>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="确定" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view.js', function(view) {
					
				});
			</script>
		</body>
	
</html>