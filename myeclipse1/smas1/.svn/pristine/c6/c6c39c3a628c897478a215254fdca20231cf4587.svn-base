<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>社科管理咨询服务中心</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/global.css" />
		<link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui-1.8.5.custom.css" />
		<link rel="stylesheet" type="text/css" href="css/project/project.css" />
	</head>
	
	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							奖励管理&nbsp;&gt;&gt;&nbsp;社科奖励&nbsp;&gt;&gt;&nbsp;奖励分组&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
						</td>
						<td class="wd_right">
							<s:if test="isFromExpert == 1">
								<a href="" onclick="history.go(-1);return false;"><img title="返回" src="image/return.gif" /></a>&nbsp;
							</s:if>
							<s:else>
								<!-- TODO -->
								<!-- <a id="view_mod" href="javascript:void(0);"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp; -->
								<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
								<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
								<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
								<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
							</s:else> 
						</td>
					</tr>
				</table>
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="update" name="update" />
				<s:hidden id="listType" name="listType" />
				<s:hidden id="isReviewable" name="isReviewable" />
				
				<div class="main-content">
					<!-- 分组详情页面项目 基本信息标签 -->
					<div id="tabs_top" class="p_box_bar" style="margin-top:10px;">
						<ul>
							<li><a href="#basicInfo">基本信息</a></li>
						</ul>
					</div>
					
					<div class="p_box">
						<!-- 分组匹配基本信息标签页 -->
						<div id="basicInfo">
							<textarea id="view_basicInfo_template" style="display:none;">
								<table class="table_edit" cellspacing="0" cellpadding="0">
									<tr>
										<td class="wd17">奖励名称</td>
										<td class="wd21" colspan="3">${awardApplication.productName}</td>
									</tr>
									<tr>
										<td class="wd17">主要作者（含申请人）</td>
										<td class="wd18">${awardApplication.authors}</td>
										<td class="wd17">成果类型</td>
										<td class="wd18">${awardApplication.productType}</td>
									</tr>
								</table>
							</textarea>
							<div id="view_basicInfo"></div>
						</div>
					</div>
					
					<!-- 奖励专家匹配信息 -->
					<div id="tabs_award" class="p_box_bar" style="margin-top:10px;display:none;">
						<ul>
							<li><a href="#application">申请信息</a></li>
						</ul>
					</div>
					<div class="p_box">
						<div id="view_application">
						</div>
						<s:include value="moeSocial/view.jsp"></s:include>
					</div>
				</div>
			</div>
		</div>
		
	<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery-ui-1.8.5.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/award/moeSocial/awardGroupMatch/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>	
	</body>
</html>
	
