<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>项目清单</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
		</head>

		<body>
			<div>
				<div id="list_container" style="height:350px;">
					<s:hidden id="isReviewable" name="isReviewable" />
					<s:if test="#session.visitor.user.issuperuser == 1">
						<div class="expand base_info" style="border:0;cursor:pointer;margin-top: 0px;">
							<div class="title_dis">导出项目一览表</div>
							<s:url id="match" action="matchExpert" namespace="project" />
						</div>
						<div class="hide" style="display:none">
							<div class="btn_div">
								<input id="exportNotReviewProject" type="button" class="confirm btn" value="导出" />
								<input type="button" class="cancel btn" value="取消" />
							</div>
						</div>
					</s:if>
					<s:if test="isReviewable == 1">
						<div class="expand base_info" style="border:0;cursor:pointer;">
							<div class="title_dis">导出项目专家匹配表</div>
						</div>
						<div class="hide" style="display:none">
							<div class="btn_div">
								<input id="exportReviewProject" type="button" class="confirm btn" value="导出" />
								<input type="button" class="cancel btn" value="取消" />
							</div>
						</div>
						<div class="expand base_info" style="border:0;cursor:pointer;">
							<div class="title_dis">导出专家匹配更新表</div>
						</div>
						<div class="hide" style="display:none">
							<table style="margin-top: 10px;">
								<s:hidden name="startTime" id="startTimeMM"></s:hidden>
								<s:hidden name="endTime" id="endTimeMM"></s:hidden>
								<tr>
									<td style="width:110px;text-align:right;">起始时间：</td>
									<td><input type="text" id="startTime" class="text ui-widget-content ui-corner-all" /></td>
									<td></td>
								</tr>
								<tr>
									<td style="width:110px;text-align:right;">截止时间：</td>
									<td><input type="text" id="endTime" class="text ui-widget-content ui-corner-all" /></td>
									<td></td>
								</tr>
							</table>
							<div class="btn_div">
								<input id="exportMatchUpdate" type="button" class="confirm btn" value="导出" />
								<input type="button" class="cancel btn" value="取消" />
							</div>
						</div>
					</s:if>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/popExport.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>