<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="xinjiang">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr>
					<td class="rightbg fixed" rowspan="3" style="">学科门类</td>
					<td class="centerbg fixed" rowspan="3" style="width:60px;">立项合计</td>
					<td class="centerbg fixed" rowspan="3" style="width:80px;">立项总经费（万元）</td>
					<td class="centerbg" colspan="8" style="width:430px;">新疆项目</td>
				</tr>
				<tr>
					<td class="centerbg" colspan="4" style="width:215px;">规划基金项目</td>
					<td class="centerbg" colspan="4" style="width:215px;">青年基金项目</td>
				</tr>
				<tr>
					<td class="centerbg" style="width:45px;">申报数</td>
					<td class="centerbg" style="width:52px;">立项比例</td>
					<td class="centerbg" style="width:45px;">立项数</td>
					<td class="centerbg" style="width:55px;">立项经费</td>
					<td class="centerbg" style="width:45px;">申报数</td>
					<td class="centerbg" style="width:52px;">立项比例</td>
					<td class="centerbg" style="width:45px;">立项数</td>
					<td class="centerbg" style="width:55px;">立项经费</td>
				<tr>
					<td class="rightbg fixed">总计</td>
					<td class="right fixed">${总计.合计数.立项合计 }</td>
					<td class="right fixed">${总计.合计数.立项总经费 }</td>
					<td class="rightbg">${总计.项目类型.新疆维吾尔自治区.规划基金项目.申报数 }</td>
					<td class="right">${总计.项目类型.新疆维吾尔自治区.规划基金项目.立项比例 }</td>
					<td class="right">${总计.项目类型.新疆维吾尔自治区.规划基金项目.立项数 }</td>
					<td class="right">${总计.项目类型.新疆维吾尔自治区.规划基金项目.立项经费 }</td>
					<td class="rightbg">${总计.项目类型.新疆维吾尔自治区.青年基金项目.申报数 }</td>
					<td class="right">${总计.项目类型.新疆维吾尔自治区.青年基金项目.立项比例 }</td>
					<td class="right">${总计.项目类型.新疆维吾尔自治区.青年基金项目.立项数 }</td>
					<td class="right">${总计.项目类型.新疆维吾尔自治区.青年基金项目.立项经费 }</td>
				</tr>
				{for item in 学科门类}
					<tr>
						<td class="rightbg fixed">${item_index}</td>
						<td class="right fixed">${item.合计数.立项合计 }</td>
						<td class="right fixed">${item.合计数.立项总经费 }</td>
						<td class="rightbg">${item.项目类型.新疆维吾尔自治区.规划基金项目.申报数 }</td>
						<td class="right">${item.项目类型.新疆维吾尔自治区.规划基金项目.立项比例 }</td>
						<td><input class="granted02"  style="width:90%;text-align:right;cursor:pointer;" value="${item.项目类型.新疆维吾尔自治区.规划基金项目.立项数 }" readonly="true" /></td>
						<td class="right">${item.项目类型.新疆维吾尔自治区.规划基金项目.立项经费 }</td>
						<td class="rightbg">${item.项目类型.新疆维吾尔自治区.青年基金项目.申报数 }</td>
						<td class="right">${item.项目类型.新疆维吾尔自治区.青年基金项目.立项比例 }</td>
						<td><input class="granted12"  style="width:90%;text-align:right;cursor:pointer;" value="${item.项目类型.新疆维吾尔自治区.青年基金项目.立项数 }" readonly="true" /></td>
						<td class="right">${item.项目类型.新疆维吾尔自治区.青年基金项目.立项经费 }</td>
					</tr>
				{forelse}
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>
				{/for}
			</table>
		</div>
	</div>
	</textarea>
</div>
