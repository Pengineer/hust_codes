<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>查看基地项目信息</title>
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
								<s:if test="businessType == 1">
								 	<s:if test="listType==1">
										<s:if test="isFromExpert == 0">
											项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
										</s:if>
										<s:else>
											<span class="text_red">查看基地项目信息</span>
										</s:else>
									</s:if>
									<s:elseif test="listType==2">
										项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;公示项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
									</s:elseif>
									<s:elseif test="listType==3">
										项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;立项项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
									</s:elseif>
									<s:elseif test="listType==4">
										项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;初审结果&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
									</s:elseif>
								</s:if>
								<s:if test="businessType == 2">
									项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;立项项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
								</s:if>
								<s:if test="businessType == 3">
									项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;中检项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
								</s:if>
								<s:if test="businessType == 4">
									项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;变更项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
								</s:if>
								<s:if test="businessType == 5">
									项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;结项项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
								</s:if>
							</td>
							<td class="wd_right">
								<s:if test="isFromExpert == 1">
									<a href="" onclick="history.go(-1);return false;"><img title="返回" src="image/return.gif" /></a>&nbsp;
								</s:if>
								<s:else>
									<s:if test="listType==1">
									<a id="view_mod" href="javascript:void(0);"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
									<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
									</s:if>
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
					<s:hidden id="businessType" name="businessType" />
					
					<div class="main_content">
						<!-- 项目详情页面项目及负责人基本信息标签 -->
						<div id="tabs_top" class="p_box_bar" style="margin-top:10px;">
							<ul>
								<li><a href="#basicInfo">基本信息</a></li>
								<%--<li><a href="#directorInfo">负责人信息</a></li>--%>
							</ul>
						</div>
						<div class="p_box">
							<!-- 项目基本信息标签页 -->
							<s:include value="view_basicInfo.jsp"></s:include>
							<%--<!-- 负责人基本信息标签页 -->
							<s:include value="view_directorInfo.jsp"></s:include>--%>
						</div>
					
						<!-- 项目详情页面项目各业务流程标签 -->
						<div id="tabs_business" class="p_box_bar" style="margin-top:10px; display:none;">
							<ul>
								<li><a href="#application">申请信息</a></li>
								<li class="un_granted"><a href="#granted">立项信息</a></li>
								<s:if test="#session.visitor.user.issuperuser == 1">
									<li class="un_granted"><a href="#midinspection">中检信息</a></li>
									<li class="un_granted"><a href="#variation">变更信息</a></li>
									<li class="un_granted"><a href="#endinspection">结项信息</a></li>
								</s:if>
							</ul>
						</div>
						<div class="p_box">
							<!-- 项目申请业务标签页 -->
							<s:include value="application/view.jsp"></s:include>
							<!-- 项目立项业务标签页 -->
							<s:include value="granted/view.jsp"></s:include>
							<!-- 项目中检业务标签页 -->
							<s:include value="midinspection/view.jsp"></s:include>
							<!-- 项目变更业务标签页 -->
							<s:include value="variation/view.jsp"></s:include>
							<!-- 项目结项业务标签页 -->
							<s:include value="endinspection/view.jsp"></s:include>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-1.8.5.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>