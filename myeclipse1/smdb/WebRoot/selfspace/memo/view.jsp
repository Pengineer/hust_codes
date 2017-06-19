<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" src="javascript/lib/jquery/jquery.js"></script>
			<script type="text/javascript" src="javascript/lib/template.js"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js"></script>
			<script type="text/javascript" src="javascript/common.js"></script>
			<script type="text/javascript" src="javascript/selfspace/memo/view.js"></script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：我的备忘录&nbsp;&gt;&nbsp;查看
			</div>

			<div class="main">
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="update" name="update" />
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
						<li id="view_del"><input class="btn1" type="button" value="删除"/></li>
						<li id="view_mod"><input class="btn1" type="button" value="修改"/></li>
						<li id="view_add"><input class="btn1" type="button" value="添加"/></li>
					</ul>
				</div> 

				<textarea id="view_template" style="display:none;">
					<div class="main_content">
							<table class="txtlineheight" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="tdbg1">${memo.title}</td>
									<td align="right" class="tdbg1 buttonpadding">
										<span>是否提醒：{if memo.isRemind == "1"}是{else}否{/if}</span>
									</td>
								</tr>
							</table>
							<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse; bordercolor:#253d56; background:#f7f7f7;">
								<tr>
									<td width="200" rowspan="2" valign="top" bgcolor="#e3cfeb">
										<table width="100%" border="0" cellspacing="2" cellpadding="2">
											<tr>
												<td width="36%" align="right" valign="top" style="padding-top:2px">
												</td>
												<td width="64%" style="font-weight:bold;">
													<br />创建者：<br />[<span style="color:#FF7400;">${accountName}</span>]
												</td>
											</tr>
										</table>
									</td>
									<td height="60px" class="txtfckpadding">
										${memo.content}
									</td>
								</tr>
								<tr>
									<td class="txtpadding">
										<span style="float:left;">更新于：
											[${memo.updateTime}]
											&nbsp;&nbsp;&nbsp;
										</span>
										<span  style="float:left;">提醒方式：
											[<span id="alert_type">{if memo.remindWay == 1}指定日期
											{elseif memo.remindWay == 2}按天
											{elseif memo.remindWay == 3}按周
											{elseif memo.remindWay == 4}按月
											{else}无
											{/if}</span>]&nbsp;&nbsp;&nbsp;
										</span>
										<span style="display:none;" id="alert_value1">
											提醒时间：[${memo.remindTime}]</span>
										<span style="display:none;" id="alert_value2">
										    <br />
											开始时间：&nbsp;${memo.startDateDay}&nbsp;&nbsp;&nbsp;
											结束时间：&nbsp;${memo.endDateDay}&nbsp;&nbsp;&nbsp;
											<br />
											排除时间： &nbsp;<span id="excludeDate">{if memo.excludeDate != null}${memo.excludeDate}{else}无{/if}</span></span>
										<span style="display:none;" id="alert_value3">
										    <br />
											开始时间：&nbsp;${memo.startDateWeek}&nbsp;&nbsp;&nbsp;
											结束时间：&nbsp;${memo.endDateWeek}&nbsp;&nbsp;&nbsp;
											每周提醒时间：&nbsp;周<span id="week">${memo.week}</span></span>
										<span style="display:none;" id="alert_value4">
										    <br />
											每月提醒日期：&nbsp;<span id="month">${memo.month}</span></span>
									</td>
								</tr>
							</table>
							<br />
					</div>
				</textarea>

				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
		</body>
	
</html>