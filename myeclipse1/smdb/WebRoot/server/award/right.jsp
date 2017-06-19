<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国高校社会科学管理数据库</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/lib/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/list_old.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</head>
	
	<body>
		<div class="link_bar">当前位置：首页</div>
       	<div class="main">
       		<div class="main_bar">
           		<div class="main_home">
           			<span class="png"><span class="color1"><s:property value="#session.loginer.passport.name" /></span>，您好！您的账号类型为：<s:property value="#session.loginer.currentTypeName" />。<br />所在单位为：<s:property value="#session.loginer.currentBelongUnitName" />。</span>
           		</div>
 			</div>
			<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
				<div >
					<span align="center">
						<s:form id="toTotalSearch" action="toTotalSearch" namespace="/system/search">
							<s:textfield id="search_keyword_tabs"
								name="searchWord" type="input" cssClass="input_css2"/>
							<input id="isInit" name="isInit" type="hidden" value="1"/>
							<input id="searchAll" type="submit" class="btn1" value="搜索" />
						</s:form>
					<span>
				</div>
			</sec:authorize>
			<div class="main_content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td width="49%" valign="top">
							<div class="m_title">
								<span>通知公告</span><a href="notice/inner/toList.action?update=1">更多</a>
							</div>
							<div class="m_content" style="height:140px;">
								<s:if test="homeNotice.isEmpty()">
									<div class="no_records">暂无符合条件的记录</div>
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
									<div class="no_records">暂无符合条件的记录</div>
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
           	
           	<div class="main_content">
           		<table width="100%" border="0" cellspacing="0" cellpadding="0">
   					<tr>
           				<td width="49%" valign="top">
   							<div class="m_title">
   								<span>待办事宜</span><a href="javascript:void(0);">更多</a>
   							</div>
             				<div class="m_content" style="height:140px;">
		                  		<div class="no_records">暂无符合条件的记录</div>
		                  	</div>
           				</td>
           				<td width="2%">&nbsp;</td>
           				<td width="49%" valign="top">
		     	     	 	<div class="m_title">
   								<span>记事提醒</span><a href="javascript:void(0);">更多</a>
   							</div>
			              	<div class="m_content" style="height:140px;">
			                  	<div class="no_records">暂无符合条件的记录</div>
		                    </div>
             			</td>
           			</tr>
        		</table>
       		</div>
       		
		</div>
	</body>
</html>