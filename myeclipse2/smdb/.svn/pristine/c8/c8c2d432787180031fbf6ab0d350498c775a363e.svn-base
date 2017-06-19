<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_CSDCSMDB" /></title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/poplayer/js/pop-self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/server/main.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</head>

	<body>
		<div class="container">
			<div class="top">
				<div class="top_bar">
					<div class="top_logo">
						<img class="ico" src="image/logo_ico.png" />
						<img class="txt" src="image/logo_txt.png" />
					</div>
					<div id="switch_database" class="sub_title">
						<div class="sub_title_left"><img src="image/sub_l.png"/></div>
						<div class="sub_title_center" id="serverName" alt="expert">专家数据库系统</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@EXPERT) == 0 || #session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@TEACHER) == 0 || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/expertRight.action" target="main"><s:text name="i18n_Index" /></a></span></li>
						<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
							<li><img src="image/top_ico02.png" /><span><a href="system/config/toConfig.action" target="main"><s:text name="i18n_Config" /></a></span></li>
						</sec:authorize>
							<li><img src="image/top_ico03.png" /><span><a href="javascript:void(0);" target="main"><s:text name="i18n_Help" /></a></span></li>
							<li><img src="image/top_ico04.png" /><span><a href="javascript:void(0);" target="main"><s:text name="i18n_About" /></a></span></li>
							<li><img src="image/top_ico05.png" /><span><a href="logout"><s:text name="i18n_Exit" /></a></span></li>
						</ul>
					</div>
				</div>
			</div>
			<div id="center" class="center">
				<div class="left_menu" id="mondrian">
					<div class="left_menu_bar">
		                <ul>
		                    <li class="left_menu_curr1"><a href="javascript:void(0);"><s:text name="i18n_MainMenu" /></a></li>
		                    <li class="left_menu_curr2"><a href="javascript:void(0);"><s:text name="i18n_NavigationBar" /></a></li>
		                </ul>
		            </div>
					<div class="menu_bar">
						<ul class="menu">
							<li id="21" class="menu1"><a href="javascript:void(0);" ><s:text name="i18n_SSExpert" /></a></li>
								<li id="2101" class="menu2_sub" style="display:none;"><a href="statistic/common/toList.action?update=1?statisticType=person" target="main"><s:text name="i18n_InnerExpert" /></a></li>
								<li id="2102" class="menu2_sub" style="display:none;"><a href="statistic/common/toList.action?update=1?statisticType=unit" target="main"><s:text name="i18n_OuterExpert" /></a></li>
							<li id="22" class="menu1"><a href="statistic/report/report.jsp" target="main"><s:text name="i18n_EvaluateExpert" /></a></li>
						</ul>
						<s:hidden id="queryName" />
					</div>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
			    <div class="right">
			    	<iframe id="main" width="100%" name="main" src="login/expertRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
			    </div>
			</div>
			
			<s:include value="/innerFoot.jsp" />
		</div>
		<div id="gotop"></div>
		<div style="display:none">
			<script src="http://s6.cnzz.com/stat.php?id=4360704&web_id=4360704&show=pic" language="JavaScript"></script>
		<div>
	</body>
</html>