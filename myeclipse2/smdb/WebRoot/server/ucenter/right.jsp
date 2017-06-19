<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_CSDCSMDB" /></title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/lib/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/list_old.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop-self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/server/uCenter/right.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		
		<script type="text/javascript">
			var teacherBusinessUrl = {
				"012":"project/general/midinspection/apply",
				"022":"project/instp/midinspection/apply",
				"013":"project/general/endinspection/apply",
				"023":"project/instp/endinspection/apply",
				"033":"project/post/endinspection/apply",
				"014":"project/general/variation/apply",
				"024":"project/instp/variation/apply",
				"034":"project/post/variation/apply"
			};
			var selectedTab = {
				"012":"midinspection",
				"022":"midinspection",
				"013":"endinspection",
				"023":"endinspection",
				"033":"endinspection",
				"014":"variation",
				"024":"variation",
				"034":"variation"
			};
			$(document).ready(function() {
				$(".a_btn").bind("click", function() {
					window.location.href = basePath + teacherBusinessUrl[$(this).attr("alt")] + "/toView.action?entityId=" + $(this).parent().next().val() + "&listType=" + 9 + "&selectedTab=" + selectedTab[$(this).attr("alt")];
					return false;
				});
			});
		</script>
	</head>
	
	<body>
		<div class="link_bar">当前位置：首页</div>
       	<div class="main">
       		<div class="main_bar">
           		<div class="main_home">
           			<span class="png"><span class="color1"><s:property value="#session.loginer.passport.name" /></span>，您好！您的账号类型为：<s:property value="#session.loginer.currentTypeName" />。<br />所在单位为：<s:property value="#session.loginer.currentBelongUnitName" />。</span>
           		</div>
 			</div>
 			
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 专家或教师或学生-->
		       	<div class="main_content">
		       		<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="49%" valign="top">
								<div class="m_title">
									<span>未读消息</span>
								</div>
								<div class="m_content" style="height:140px;">
									<s:if test="homeChat.isEmpty()">
										<div class="no_records"><s:text name="i18n_NoRecords" /></div>
									</s:if>
									<s:else>
										<ul>
											<s:iterator value="homeChat" status="stat">
												<li><a title="<s:property value="回复" />" href="linkedin/toSend.action?flag=1&entityId=<s:property value="homeChat[#stat.index][2]" />" class="txt_left"><s:property value="homeChat[#stat.index][1]" /></a><span class="txt_right">[<s:property value="homeChat[#stat.index][0]" />]</span></li>
											</s:iterator>
										</ul>
									</s:else>
								</div>
							</td>
							<td width="2%">&nbsp;</td>
							<td width="49%" valign="top">
								<div class="m_title">
									<span>好友申请</span>
								</div>
								<div class="m_content" style="height:140px;overflow-y:auto;">
									<s:if test="homeApply.isEmpty()">
										<div class="no_records"><s:text name="i18n_NoRecords" /></div>
									</s:if>
									<s:else>
			             				<table width="100%" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			             					<tbody>
												<s:iterator value="homeApply" status="stat">
													<tr class="table_tr4">
														<td>
															<span class="txt_right"><s:property value="homeApply[#stat.index][1]" /></span>
														</td>
		             									<td>
															<span class="txt_right"><s:property value="homeApply[#stat.index][2]" /></span>
		             									</td>
		             									<td width="90"><input id="agree" name=<s:property value="homeApply[#stat.index][0]" /> type="button" class="btn1" value="<s:text name='同意' />" /></td>
		             									<td width="90"><input id="refuse" name=<s:property value="homeApply[#stat.index][0]" /> type="button" class="btn1" value="<s:text name='拒绝' />" /></td>
													</tr>
												</s:iterator>
             								</tbody>
			             				</table>
	             					</s:else>
								</div>
							</td>
						</tr>
					</table>
		       	</div>
	       	</s:if>
 			
 			<div class="main_content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td width="49%" valign="top">
							<div class="m_title">
								<span>通知公告</span><a href="notice/inner/toList.action?update=1">更多</a>
							</div>
							<div class="m_content" style="height:140px;">
								<s:if test="homeNotice.isEmpty()">
									<div class="no_records"><s:text name="i18n_NoRecords" /></div>
								</s:if>
								<s:else>
									<ul>
										<s:iterator value="homeNotice" status="stat">
											<li><a title="<s:property value="homeNotice[#stat.index][1]" />" href="notice/inner/toView.action?viewFlag=2&entityId=<s:property value="homeNotice[#stat.index][0]" />" class="txt_left"><s:property value="homeNotice[#stat.index][1]" /></a><span class="txt_right">[<s:if test="#session.locale == 'zh'"><s:date name="homeNotice[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="homeNotice[#stat.index][2]" format="dd/MM/yyyy" /></s:else>]</span></li>
										</s:iterator>
									</ul>
								</s:else>
							</div>
						</td>
						<td width="2%">&nbsp;</td>
						<td width="49%" valign="top">
							<div class="m_title">
								<span>热点新闻</span><a href="news/inner/toList.action?update=1">更多</a>
							</div>
							<div class="m_content" style="height:140px;">
								<s:if test="homeNews.isEmpty()">
									<div class="no_records"><s:text name="i18n_NoRecords" /></div>
								</s:if>
								<s:else>
									<ul>
										<s:iterator value="homeNews" status="stat">
											<li><a title="<s:property value="homeNews[#stat.index][1]" />" href="news/inner/toView.action?viewFlag=2&entityId=<s:property value="homeNews[#stat.index][0]" />" class="txt_left"><s:property value="homeNews[#stat.index][1]" /></a><span class="txt_right">[<s:if test="#session.locale == 'zh'"><s:date name="homeNews[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="homeNews[#stat.index][2]" format="dd/MM/yyyy" /></s:else>]</span></li>
										</s:iterator>
									</ul>
								</s:else>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>