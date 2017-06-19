<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<s:form id="form_common">
	<div class="p_box_t">
		<div class="p_box_t_t">可调变量</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<div style="padding:0 0 3px 0">
			<div id="validateInfo"></div>
			<div>限项申请年度：<s:textfield id="strictAppYear" name="strictAppYear" cssClass="input_css5" />年</div>
		</div>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<tbody>
				<tr class="table_tr7">
				    <td class="head_title1" rowspan="2">倾斜权重</td>
					<td class="key" width="89">部属高校倾斜：</td>
					<td class="value" width="80"><s:textfield id="moeTilt" name="moeTilt" cssClass="input_css4" />%</td>
					<td class="key" width="89">西部高校倾斜：</td>
					<td class="value" width="80"><s:textfield id="westTilt" name="westTilt" cssClass="input_css4" />%</td>
					<td class="key" width="89">高校限项下界：</td>
					<td class="value" width="80"><s:textfield id="univStrictLB" name="univStrictLB" cssClass="input_css4" />项</td>
					<td class="key" width="89">年度限项目标：</td>
					<td class="value" width="80"><s:textfield id="annStrictTarget" name="annStrictTarget" cssClass="input_css4" />项</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">立项率权重：</td>
					<td class="value"><s:textfield id="grweight" name="grweight" cssClass="input_css4" /></td>
					<td class="key">按期中检权重：</td>
					<td class="value"><s:textfield id="moweight" name="moweight" cssClass="input_css4" /></td>
					<td class="key">按期结项权重：</td>
					<td class="value"><s:textfield id="eoweight" name="eoweight" cssClass="input_css4" /></td>
					<td class="key">奖励得分权重：</td>
					<td class="value"><s:textfield id="asweight" name="asweight" cssClass="input_css4" /></td>
				</tr>
				<tr style="height:10px">
				</tr>
				<tr class="table_tr7">
				    <td class="head_title1" rowspan="3">年度设置</td>
					<td class="key" colspan="2">申请立项项目起止年度跨度：</td>
					<td class="value"><s:textfield id="graYearScope" name="graYearScope" cssClass="input_css4" />年</td>
					<td class="key" colspan="2">申请立项项目起止年度：</td>
					<td class="value" colspan="3">
						<s:select cssClass="select" id="graStartYear" name="graStartYear" list="%{generalService.getYearMapFrom2005()}" />
						至
						<s:select cssClass="select" id="graEndYear" name="graEndYear" list="%{generalService.getYearMapFrom2005()}" />年
					</td>
				</tr>
				<tr class="table_tr7">
					<td class="key" colspan="2">按期中检项目起止年度跨度：</td>
					<td class="value"><s:textfield id="midYearScope" name="midYearScope" cssClass="input_css4" />年</td>
					<td class="key" colspan="2">按期中检项目起止年度：</td>
					<td class="value" colspan="3">
						<s:select cssClass="select" id="midStartYear" name="midStartYear" list="%{generalService.getYearMapFrom2005()}" />
						至
						<s:select cssClass="select" id="midEndYear" name="midEndYear" list="%{generalService.getYearMapFrom2005()}" />年
					</td>
				</tr>
				<tr class="table_tr7">
					<td class="key" colspan="2">按期结项项目起止年度跨度：</td>
					<td class="value"><s:textfield id="endYearScope" name="endYearScope" cssClass="input_css4" />年</td>
					<td class="key" colspan="2">按期结项项目起止年度：</td>
					<td class="value" colspan="3">
						<s:select cssClass="select" id="endStartYear" name="endStartYear" list="%{generalService.getYearMapFrom2005()}" />
						至
						<s:select cssClass="select" id="endEndYear" name="endEndYear" list="%{generalService.getYearMapFrom2005()}" />年
					</td>
				</tr>
				<tr style="height:10px">
				</tr>
				<tr class="table_tr7">
					<td class="head_title1" rowspan="6">奖励分值</td>
					<td style="text-align:center;" rowspan="3">著作奖</td>
					<td class="key">一等奖：</td>
					<td class="value"><s:textfield id="bookAwardFir" name="bookAwardFir" cssClass="input_css4" /></td>
					<td style="text-align:center;" rowspan="3">论文奖</td>
					<td class="key">一等奖：</td>
					<td class="value" colspan="3"><s:textfield id="paperAwardFir" name="paperAwardFir" cssClass="input_css5" /></td>
				</tr>
				<tr class="table_tr7">
					<td class="key">二等奖：</td>
					<td class="value"><s:textfield id="bookAwardSec" name="bookAwardSec" cssClass="input_css4" /></td>
					<td class="key">二等奖：</td>
					<td class="value" colspan="3"><s:textfield id="paperAwardSec" name="paperAwardSec" cssClass="input_css5" /></td>
				</tr>
				<tr class="table_tr7">
					<td class="key">三等奖：</td>
					<td class="value"><s:textfield id="bookAwardThi" name="bookAwardThi" cssClass="input_css4" /></td>
					<td class="key">三等奖：</td>
					<td class="value" colspan="3"><s:textfield id="paperAwardThi" name="paperAwardThi" cssClass="input_css5" /></td>
				</tr>
				<tr class="table_tr7">
					<td style="text-align:center;" rowspan="3">研究报告奖（采纳/批示）</td>
					<td class="key">一等奖：</td>
					<td class="value"><s:textfield id="ResAdoAwardFir" name="ResAdoAwardFir" cssClass="input_css4" /></td>
					<td class="key" colspan="2" rowspan="3">成果普及奖：</td>
					<td class="value" colspan="3" rowspan="3"><s:textfield id="achPopuAward" name="achPopuAward" cssClass="input_css5" /></td>
				</tr>
				<tr class="table_tr7">
					<td class="key">二等奖：</td>
					<td class="value"><s:textfield id="ResAdoAwardSec" name="ResAdoAwardSec" cssClass="input_css4" /></td>
				</tr>
				<tr class="table_tr7">
					<td class="key">三等奖：</td>
					<td class="value"><s:textfield id="ResAdoAwardThi" name="ResAdoAwardThi" cssClass="input_css4" /></td>
				</tr>
			</tbody>
		</table>
		<div style="text-align:center; margin-top:10px;">
			<input id="setDefault" class="btn2" type="button" value="设为默认" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="default" class="btn2" type="button" value="恢复默认" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="submit" class="btn2" type="button" value="开始核算" />
		</div>
	</div>
</s:form>