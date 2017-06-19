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
						<td class="title_bar_td" width="64" align="right">评审人：</td>
						<td class="title_bar_td" ><s:property value="awardApplication.reviewerName" /></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">评审时间：</td>
						<td class="title_bar_td" width="120"><s:date name="awardApplication.reviewDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">评审方式：</td>
						<td class="title_bar_td"><s:property value="awardApplication.reviewWay" /></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">总分：</td>
							<td class="title_bar_td"><s:property value="awardApplication.reviewTotalScore" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">平均分：</td>
							<td class="title_bar_td"><s:property value="awardApplication.reviewAverageScore" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">评审结果：</td>
							<td class="title_bar_td">
								<s:if test=" awardApplication.reviewStatus==2&&awardApplication.reviewResult==1">不同意（暂存）</s:if>
								<s:elseif test="awardApplication.reviewStatus==2&&awardApplication.reviewResult==2">同意（暂存）</s:elseif>
								<s:elseif test="awardApplication.reviewStatus==3&&awardApplication.reviewResult==1">不同意</s:elseif>
								<s:elseif test="awardApplication.reviewStatus==3&&awardApplication.reviewResult==2">同意</s:elseif>
							</td>
						<s:if test="awardApplication.reviewResult == 2">
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">建议等级：</td>
							<td class="title_bar_td"><s:property value="awardApplication.reviewGrade.name" /></td>
						</s:if>
						<else>
							<td/>
							<td/>
							<td/>
						</else>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">评审意见：</td>
							<td class="title_bar_td">
								<pre><s:property value="awardApplication.reviewOpinion" /></pre>
							</td>
						</tr>
					</table>
				</div>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="确定" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/award/moesocial/application/review/view.js', function(view) {
					view.init();
					view.initClick();
				});
			</script>
		</body>
</html>