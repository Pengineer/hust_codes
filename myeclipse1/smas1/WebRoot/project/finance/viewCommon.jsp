<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="global_check" class="base_info" onclick="toggle_view('global_check');">
	<div class="img_dis"></div>
	<div class="title_dis">全局设置</div>
</div>
<div style="display: block; width: 100%;">
	<table class="table_edit5" cellspacing="0" cellpadding="0">
		<tr>
			<td class="rightbg fixed" style="width:85px;border-width: 0pt 1px 1px;" rowspan="2">一般立项比例</td>
			<td class="leftbg fixed" style="width:170px;border-width: 0pt 1px 1px;">规划基金项目: <input name="planRate" id="planRate" class="input clear" style="width:45px; text-align:right;"/> %</td>
			<td class="rightbg" style="width:75px;border-width: 0pt 1px 1px;" rowspan="2">西部立项数</td>
			<td class="leftbg" style="width:170px;border-width: 0pt 1px 1px;">规划基金项目: <input name="xbPlanCount" id="xbPlanCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
			<td class="rightbg" style="width:75px;border-width: 0pt 1px 1px;" rowspan="2">新疆立项数</td>
			<td class="leftbg" style="width:170px;border-width: 0pt 1px 1px;">规划基金项目: <input name="xjPlanCount" id="xjPlanCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
		</tr>
		<tr>
			<td class="leftbg fixed">青年基金项目: <input name="youthRate" id="youthRate" class="input clear" style="width:45px; text-align:right;"/> %</td>
			<td class="leftbg">青年基金项目: <input name="xbYouthCount" id="xbYouthCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
			<td class="leftbg">青年基金项目: <input name="xjYouthCount" id="xjYouthCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
		</tr>
		<tr>
			<td class="rightbg fixed" style="border-width: 0pt 1px 1px;" rowspan="2">经费额度上限</td>
			<td class="leftbg fixed" style="border-width: 0pt 1px 1px;">规划基金项目: <input name="planFee" id="planFee" class="input clear" style="width:45px; text-align:right;"/> 万</td>
			<td class="rightbg" rowspan="2">西藏立项数</td>
			<td class="leftbg">规划基金项目: <input name="xzPlanCount" id="xzPlanCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
			<td class="centerbg" style="border-width: 0pt 1px 1px;">自筹立项数:</td>
			<td class="leftbg"><input name="zcCount" id="zcCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
		</tr>
		<tr>
			<td class="leftbg fixed" style="border-width: 0pt 1px 1px;">青年基金项目: <input name="youthFee" id="youthFee" class="input clear" style="width:45px; text-align:right;"/> 万</td>
			<td class="leftbg">青年基金项目: <input name="xzYouthCount" id="xzYouthCount" class="input clear" style="width:45px; text-align:right;"/> 项</td>
			<td class="centerbg" colspan="2"><input class="btn" type="button" id="submit" value="开始核算" />&nbsp;&nbsp;&nbsp;&nbsp;<input class="btn" type="button" id="clear" value="清空核算" /></td>
		</tr>
		<tr>
			<td class="fixed" colspan="6">
				说明：<br/>1. 一般项目（含西部、新疆和西藏等特殊地区项目）按全局设置值实行分学科划线，但受特殊地区项目全局设置的影响，核算后的各学科总体立项率可能超过全局设置值。
				<br/>2. 因各类特殊地区项目的每个学科项目偏少，核算时该类项目实行所有学科统一划线，核算后的某些学科立项率可能超过全局设置值。
			</td>
		</tr>
	</table>
</div>
<br/>