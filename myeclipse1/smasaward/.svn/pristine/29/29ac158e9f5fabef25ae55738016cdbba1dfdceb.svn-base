<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="granted">
	<!-- 项目立项业务标签页 -->
	<textarea id="view_granted_template" style="display:none;">
		<div>
			
			{if granted == null}
				<div style="text-align:center;">暂无符合条件的记录</div>
			{else}
				<div>
					<!-- 立项基本情况 -->
					<table class="table_edit" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wd17">项目批准号</td>
							<td class="wd18">${granted.number}</td>
							<td class="wd17">批准经费（万）</td>
							<td class="wd18">${granted.approveFee}</td>
						</tr>
						<tr>
							<td class="wd17">项目批准时间</td>
							<td class="wd18">${granted.approveDate}</td>
							<td class="wd17">计划完成时间</td>
							<td class="wd18">${granted.planEndDate}</td>
						</tr>
						<tr>
							<td class="wd17">最终成果形式</td>
							<td class="wd18" colspan="3">${granted.productType}</td>
						</tr>
					</table>
				</div>
			{/if}
			
		</div>
	</textarea>

	<div id="view_granted" style="display:none"></div>
</div>