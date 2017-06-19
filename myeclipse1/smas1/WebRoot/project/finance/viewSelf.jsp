<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="self">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr>
					<td class="rightbg fixed" rowspan="2" style="">学科门类</td>
					<td class="centerbg" colspan="3">自筹项目</td>
				</tr>
				<tr>
					<td class="centerbg" style="width:191px;">自筹申请数</td>
					<td class="centerbg" style="width:190px;">立项比例</td>
					<td class="centerbg" style="width:190px;">立项数</td>
				</tr>
				<tr>
					<td class="rightbg fixed">总计</td>
					<td class="rightbg">${自筹项目.总计.自筹申请数}</td>
					<td class="right">${自筹项目.总计.立项比例}</td>
					<td class="right">${自筹项目.总计.立项数}</td>
				</tr>
				{for item in 自筹项目.学科门类}
					<tr>
						<td class="rightbg fixed">${item_index}</td>
						<td class="rightbg">${item.自筹申请数}</td>
						<td class="right">${item.立项比例}</td>
						<td class="right">${item.立项数}</td>
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
