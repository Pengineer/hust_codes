<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
						<td class="title_bar_td" width="100"><s:property value="awardReview.reviewer.name" /></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">评审时间：</td>
						<td class="title_bar_td"><s:date name="awardReview.date" format="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">总分：</td>
						<td class="title_bar_td" ><s:property value="awardReview.score"/></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">建议等级：</td>
						<td class="title_bar_td"><s:property value="awardReview.grade.name" /></td>
					</tr>
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">分数明细：</td>
						<td class="title_bar_td"></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">	
					<tr>
						<td width="30" height="9"></td>
						<td width="64"></td>
		   				<td width="169">研究内容意义和前沿性：</td>
		    			<td><s:property value="awardReview.meaningScore"/></td>
		  			</tr>
		  			<tr>
						<td width="30" height="9"></td>
						<td width="64"></td>
		   				<td width="169">主要创新和学术价值：</td>
		    			<td><s:property value="awardReview.innovationScore"/></td>
		  			</tr>
		  			<tr>
						<td width="30" height="9"></td>
						<td width="64"></td>
		   				<td width="169">学术影响或效益方法：</td>
		    			<td><s:property value="awardReview.influenceScore"/></td>
		  			</tr>
		  			<tr>
						<td width="30" height="9"></td>
						<td width="64"></td>
		   				<td width="169">研究方法和学术规范：</td>
		    			<td><s:property value="awardReview.methodScore"/></td>
		  			</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">	
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">评审意见：</td>
						<td class="title_bar_td" >
							<pre><s:property value="awardReview.opinion"/></pre>
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