<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div class="static">
	<table class="static_table" cellspaning="0" cellpadding="0" style="width: 99%;">
		<tr>
			<td class="staticwd0" colspan="4">一般项目统计信息</td>
		</tr>
		<tr>
			<td class="staticwd1" rowspan="2">申请项目总数：</td>
			<td class="staticwd2" rowspan="2"><s:property value="#request.pnall" />个</td>
			<td class="staticwd1"><a href="project/general/toList.action?update=1&listType=1&isReviewable=1" title="点击进入参加评审的项目列表">参加评审的项目</a>：</td>
			<td class="staticwd2"><s:property value="#request.pnReview" />个</td>
		</tr>
		<tr>
			<td class="staticwd1"><a href="project/general/toList.action?update=1&listType=1&isReviewable=0" title="点击进入退出评审的项目列表">退出评审的项目</a>：</td>
			<td class="staticwd2"><s:property value="#request.pnnReview" />个</td>
		</tr>
	</table>
	
	<table class="static_table" cellspaning="0" cellpadding="0" style="margin-top:10px;width: 99%;">
		<tr>
			<td class="staticwd0" colspan="9">一般项目专家分配情况</td>
		</tr>
		<tr>
			<td class="staticwd1">分配的专家数：</td>
			<s:bean name="org.apache.struts2.util.Counter" id="counter">
				<s:param name="first" value="0" />
				<s:param name="last" value="#request.maxCols" />
				<s:iterator><td class="staticwd4"><s:property /></td></s:iterator>
			</s:bean>
		</tr>
		<tr>
			<td class="staticwd1">对应的项目数：</td>
			<s:iterator value="#request.penum"><td class="staticwd4"><s:property /></td></s:iterator>
		</tr>
	</table>
<!-- 	<table class="static_table" cellspaning="0" cellpadding="0" style="width: 99%;">
		<tr>
			<td class="staticwd0" colspan="4" style="border-width:0 1px 1px 1px;">警告（<s:property value="#request.wnall" />条）</td>
		</tr>
		<s:iterator value="#request.num" status="stat">
			<tr>
				<td colspan="4" style="padding-left:10px;">
					<span class="text_red">“<s:property value="#request.num[#stat.index][0]" />”：</span><s:property value="#request.num[#stat.index][1]" />条
				</td>
			</tr>
		</s:iterator>
	</table> -->
	
	<table class="static_table" cellspaning="0" cellpadding="0" style="margin-top:10px;width: 99%;">
		<tr><td class="staticwd0" colspan="4">一般项目参评统计信息</td></tr>
		<tr>
			<td class="staticwd1" rowspan="2">参评高校数</td>
			<td class="staticwd2"  rowspan="2">
				<s:property value="#request.runum" />所
			</td>
			<td class="staticwd5">参评专家数小于<s:property value="%{#application.GeneralUniversityExpertMin}" />的高校数</td>
			<td class="staticwd2" style="width:190px;"><s:property value="#request.uecnum[0]" />所</td>
		</tr>
		<tr>
			<td class="staticwd5">参评专家数达到<s:property value="%{#application.GeneralUniversityExpertMin}" />的高校数</td>
			<td class="staticwd2" style="width:190px;"><s:property value="#request.uecnum[1]" />所</td>
		</tr>
		<tr>
			<td class="staticwd1" rowspan="3">参评专家数</td>
			<td class="staticwd2" rowspan="3" style="width:190px;">
				<s:property value="#request.renum" />人
			</td>
			<td class="staticwd5">
				
				参评项目数小于<s:property value="%{#application.GeneralExpertProjectMin}" />的专家数
			</td>
			<td class="staticwd2" style="width:190px;">
				<s:property value="#request.epcnum[0]" />人
			</td>
		</tr>
		<tr>
			<td class="staticwd5">
				参评项目数<s:property value="%{#application.GeneralExpertProjectMin}" />至<s:property value="%{#application.GeneralExpertProjectMax}" />的专家数
			</td>
			<td class="staticwd2" style="width:190px;">
				<s:property value="#request.epcnum[1]" />人
			</td>
		</tr>
		<tr>
			<td class="staticwd5">
				参评项目数大于<s:property value="%{#application.GeneralExpertProjectMax}" />的专家数
			</td>
			<td class="staticwd2" style="width:190px;">
				<s:property value="#request.epcnum[2]" />人
			</td>
		</tr>
	</table>
	<div style="height:5px;"></div>
</div>