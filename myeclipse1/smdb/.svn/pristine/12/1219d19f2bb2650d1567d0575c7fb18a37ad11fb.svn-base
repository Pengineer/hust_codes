<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!-- 项目相关 -->
<!-- 项目的申请、立项、中检、结项的数目 -->
<div id="view_project_num_container" style="display:none;"></div>
<textarea id="view_num_project" style="display:none;">
	<div class="p_box_body">
		<table id="list_project" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<tbody>
				<tr style = "text-align:center;">
					<td width="30%" style = "text-align:center;" class="first">申请数量：</td>
					<td class="even" style = "text-align:left;">
						${projectNumber[0]}
					</td>
				</tr>
				<tr style = "text-align:center;">
					<td width="30%" style = "text-align:center;" class="first">立项数量：</td>
					<td class="even" style = "text-align:left;">
						${projectNumber[1]}
					</td>
				</tr>
				<tr style = "text-align:center;">
					<td width="30%" style = "text-align:center;" class="first">中检数量：</td>
					<td class="even" style = "text-align:left;">
						${projectNumber[2]}
					</td>
				</tr>
				<tr style = "text-align:center;">
					<td width="30%" style = "text-align:center;" class="first">结项数量：</td>
					<td class="even" style = "text-align:left;">
						${projectNumber[3]}
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</textarea>
<div id="view_project_container" style="display:none;"></div>
<textarea id="view_project" style="display:none;">
	<div class="p_box_body">
		<table id="list_project" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="30%" style = "text-align:center;">项目名称</td>
					<td width="30%" style = "text-align:center;">申请人</td>
					<td width="20%" style = "text-align:center;">项目类型</td>
					<td style = "text-align:center;">项目状态</td>
				</tr>
			</thead>
			<tbody>
			{if appList != null || graList != null}
				{for item in appList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[3]}
						</td>
						<td>
							{if item[2]=='general'}一般项目
							{elseif item[2]=='key'}重大攻关项目
							{elseif item[2]=='instp'}基地项目
							{elseif item[2]=='post'}后期资助项目
							{elseif item[2]=='entrust'}委托应急课题
							{else}
							{/if}
						</td>
						<td>
							未立项
						</td>
					</tr>
				{/for}
				{for item in graList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[4]}
						</td>
						<td>
							{if item[2]=='general'}一般项目
							{elseif item[2]=='key'}重大攻关项目
							{elseif item[2]=='instp'}基地项目
							{elseif item[2]=='post'}后期资助项目
							{elseif item[2]=='entrust'}委托应急课题
							{else}
							{/if}
						</td>
						<td>
							{if item[3]==1}在研
							{elseif item[3]==2}结项
							{elseif item[3]==3}终止
							{elseif item[3]==4}撤项
							{else}
							{/if}
						</td>
					</tr>
				{/for}
			{else}
				<tr class="even">
					<td align="center" colspan="4">暂无符合条件的记录</td>
				</tr>
			{/if}
			</tbody>
		</table>
	</div>
</textarea>
