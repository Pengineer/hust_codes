<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_publishStatus_template" style="display:none;">
	<div class="main_content">
		<div class="title_bar">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="100" align="right">申报审核结果：</td>
						<td class="title_bar_td" width="">{if applicationResult == 0} 未发布 {elseif applicationResult == 1} 发布失败 {elseif applicationResult == 2} 发布成功 {/if} </td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="100" align="right">中检审核结果：</td>
						<td class="title_bar_td" width="">{if midinspectionResult == 0} 未发布 {elseif midinspectionResult == 1} 发布失败 {elseif midinspectionResult == 2} 发布成功  {/if} </td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="100" align="right">结项审核结果：</td>
						<td class="title_bar_td" width="">{if endinspectionResult == 0} 未发布 {elseif endinspectionResult == 1} 发布失败 {elseif endinspectionResult == 2} 发布成功   {/if}</td>
					</tr>
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="120" align="right">项目需中检数据：</td>
						<td class="title_bar_td" width="">{if midinspectionRequired == 0} 未发布 {elseif midinspectionRequired == 1} 发布失败 {elseif midinspectionRequired == 2} 发布成功   {/if}</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="120" align="right">中检延期审核结果：</td>
						<td class="title_bar_td" width="">{if midinspecitonDefer == 0} 未发布 {elseif midinspecitonDefer == 1} 发布失败 {elseif midinspecitonDefer == 2} 发布成功   {/if}</td>
					</tr>
					{for item in variationResult}
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="120" align="right">第 ${parseInt(item_index) + 1} 变更审核结果： </td>
						<td class="title_bar_td" width="">{if item == 0} 未发布 {elseif item == 1} 发布失败 {elseif item == 2} 发布成功  {/if}</td>
					</tr>
					{/for}
					
			</table>
			
		</div>
	</div>
</textarea>
<div id="view_publishStatus" style="display:none; clear:both;"></div>
