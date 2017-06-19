<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<div class="main_content">
		<div class="title_statistic">参数配置</div>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="80">接口开关：</td>
				<td class="table_td3">
					<input id="open" type="radio" name="finalAuditResultPublish" value="1" />
					<label for="open">开启</label>&nbsp;&nbsp;&nbsp;
					<input id="close" type="radio" name="finalAuditResultPublish" value="0" />
					<label for="close">关闭</label>
				</td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">项目年度：</td>
				<td class="table_td3">
					<s:select cssClass="select" id="startYear" name="startYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
					至
					<s:select cssClass="select" id="endYear" name="endYear" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
				</td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">申请时间：</td>
				<td class="table_td3">
					<s:textfield name="applyStartDate" readonly="true">
						<s:param name="value">
							<s:date name="applyStartDate" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
					至
					<s:textfield name="applyEndDate" readonly="true">
						<s:param name="value">
							<s:date name="applyEndDate" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">审核时间：</td>
				<td class="table_td3">
					<s:textfield name="auditStartDate" readonly="true">
						<s:param name="value">
							<s:date name="auditStartDate" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
					至
					<s:textfield name="auditEndDate" readonly="true">
						<s:param name="value">
							<s:date name="auditEndDate" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
			</tr>
		</table>
		<div class="btn_div_view">
			<input id="submit" class="btn2" type="submit" value="保存修改" />
		</div>
	</div>
</div>