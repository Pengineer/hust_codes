<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:430px;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="64" align="right">鉴定人：</td>
							<td class="title_bar_td" ><s:property value="endReview.reviewerName" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="90" align="right">鉴定时间：</td>
							<td class="title_bar_td" width="104"><s:date name="endReview.date" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">总分：</td>
							<td class="title_bar_td"><s:property value="endReview.score" /></td>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">建议评价等级：</td>
							<td class="title_bar_td"><s:property value="grade" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">分数详情：</td>
							<td class="title_bar_td" colspan='4'></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td colspan="3"><span>创新和突破得分</span><span>：</span></td>
							<td colspan="1"><s:property value="endReview.innovationScore" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td colspan="3"><span>科学性和规范性得分</span><span>：</span></td>
							<td colspan="1"><s:property value="endReview.scientificScore" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td colspan="3"><span>价值和效益得分</span><span>：</span></td>
							<td colspan="1"><s:property value="endReview.benefitScore" /></td>
						</tr>
						<tr>
							<td width="30" height="10" align="right" colspan='6'></td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">成果修改定性意见：</td>
							<td class="title_bar_td" colspan="4"><s:property value="endReview.qualitativeOpinion" /></td>
						</tr>
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">鉴定意见：</td>
							<td class="title_bar_td" colspan="4" >
								<pre><s:property value="endReview.opinion" /></pre>
							</td>
						</tr>
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