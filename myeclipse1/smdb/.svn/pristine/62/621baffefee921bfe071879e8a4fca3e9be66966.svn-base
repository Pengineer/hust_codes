<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_base_template" style="display:none;">
	<div class="main_content">
		<div class="title_bar">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">清单名称：</td>
					<td class="title_bar_td" >${fundList.name}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">经办人：</td>
					<td class="title_bar_td" >${fundList.attn}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">生成时间：</td>
					<td class="title_bar_td" width="120">${fundList.createDate}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">拨款比率：</td>
					<td class="title_bar_td" width="120">${fundList.rate}%</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">清单状态：</td>
					<td class="title_bar_td" width="120">
					{if fundList.status ==0}未拨款
						<span>
							<input id="view_audit" type="button" class="btn1" value="拨款" />
						</span>
					{elseif fundList.status == 1}已拨款
					{/if}
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">清单备注：</td>
					<td class="title_bar_td" >${fundList.note}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">项目总数：</td>
					<td id ="projectNum" class="title_bar_td" width="120">${fundList.projectNumber}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">总金额：</td>
					<td class="title_bar_td" width="120">${fundList.total}(万元)</td>
				</tr>
			</table>
		</div>
	</div>
</textarea>
<div id="view_base" style="clear:both;"></div>