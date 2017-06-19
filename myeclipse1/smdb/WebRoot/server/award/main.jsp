<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国高校社会科学管理数据库</title>
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
						<div class="sub_title_center" id="serverName" alt="award">奖励管理系统</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@EXPERT) || #session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER) || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/awardRight.action" target="main">首页</a></span></li>
						<sec:authorize ifAnyGranted="ROLE_SYSTEM_CONFIG">
							<li><img src="image/top_ico02.png" /><span><a href="system/config/toConfig.action" target="main">设置</a></span></li>
						</sec:authorize>
							<li><img src="image/top_ico03.png" /><span><a href="javascript:void(0);" target="main">帮助</a></span></li>
							<li><img src="image/top_ico04.png" /><span><a href="javascript:void(0);" target="main">关于</a></span></li>
							<li><img src="image/top_ico05.png" /><span><a href="logout">退出</a></span></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div id="center" class="center">
				<div class="left_menu">
						<div class="left_menu_bar">
						<ul>
							<li id="menu_bar" class="left_menu_curr1"><a href="#" onclick="menufunction(0);return false;">主菜单</a></li>
							<li id="func_bar" class="left_menu_curr2"><a href="#" onclick="menufunction(1);return false;">导航栏</a></li>
						</ul>
					</div>
					<div id="menu" class="menu_bar">
						<ul class="menu">
							<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITY_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_AWARDED_VIEW"> 
							<li id="01" class="menu1"><a href="javascript:void(0);">社科奖励数据</a></li>
								<li id="0101" class="menu2" style="display:none;"><a href="javascript:void(0);">人文社科奖</a></li>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_VIEW"> 
									<li id="010101" class="menu3" style="display:none;"><a href="award/moesocial/application/apply/toList.action?update=1&listflag=1" target="main">申请数据</a></li>
								</sec:authorize> 
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITY_VIEW">
									<li id="010102" class="menu3" style="display:none;"><a href="award/moesocial/application/publicity/toList.action?update=1&listflag=2" target="main">公示数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_AWARDED_VIEW">
									<li id="010103" class="menu3" style="display:none;"><a href="award/moesocial/application/awarded/toList.action?update=1&listflag=3" target="main">获奖数据</a></li>
								</sec:authorize>
							</sec:authorize>
						</ul>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
				<div class="right">
					<iframe id="main" width="100%" name="main" src="login/awardRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
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