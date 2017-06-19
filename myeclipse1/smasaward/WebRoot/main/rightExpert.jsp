<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div class="static">
	<table class="static_table" cellspaning="0" cellpadding="0">
		<tr>
			<td class="staticwd0" colspan="6">专家统计信息</td>
		</tr>
		<tr>
			<td class="staticwd1" rowspan="4">专家总数：</td>
			<td class="staticwd2" rowspan="4"><s:property value="#request.enall" />人</td>
			<td class="staticwd1" rowspan="2">参加评审的专家：</td>
			<td class="staticwd2" rowspan="2"><s:property value="#request.enReview" />人</td>
			<td class="staticwd1"><a href="expert/toList.action?update=1&expertType=0&isReviewer=1" title="点击进入参加评审的内部专家列表">内部专家</a>：</td>
			<td class="staticwd2"><s:property value="#request.enReviewI" />人</td>
		</tr>
		<tr>
			<td class="staticwd1"><a href="expert/toList.action?update=1&expertType=1&isReviewer=1" title="点击进入参加评审的外部专家列表">外部专家</a>：</td>
			<td class="staticwd2"><s:property value="#request.enReviewO" />人</td>
		</tr>
		<tr>
			<td class="staticwd1" rowspan="2">退出评审的专家：</td>
			<td class="staticwd2" rowspan="2"><s:property value="#request.ennReview" />人</td>
			<td class="staticwd1"><a href="expert/toList.action?update=1&expertType=0&isReviewer=0" title="点击进入退出评审的内部专家列表">内部专家</a>：</td>
			<td class="staticwd2"><s:property value="#request.ennReviewI" />人</td>
		</tr>
		<tr>
			<td class="staticwd1"><a href="expert/toList.action?update=1&expertType=1&isReviewer=0" title="点击进入退出评审的外部专家列表">外部专家</a>：</td>
			<td class="staticwd2"><s:property value="#request.ennReviewO" />人</td>
		</tr>
	</table>
</div>