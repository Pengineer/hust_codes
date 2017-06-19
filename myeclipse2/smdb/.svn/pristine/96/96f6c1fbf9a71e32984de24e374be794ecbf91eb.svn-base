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
						<div class="sub_title_center" id="serverName" alt="ucenter">用户信息中心</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT , @csdc.tool.bean.AccountType@STUDENT) || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/ucenterRight.action" target="main"><s:text name="i18n_Index" /></a></span></li>
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
				<div class="left_menu">
					<div class="left_menu_bar">
						<ul>
							<li id="menu_bar" class="left_menu_curr1"><a href="#" onclick="menufunction(0);return false;"><s:text name="i18n_MainMenu" /></a></li>
							<li id="func_bar" class="left_menu_curr2"><a href="#" onclick="menufunction(1);return false;"><s:text name="i18n_NavigationBar" /></a></li>
						</ul>
					</div>
					<div id="menu" class="menu_bar">
						<ul class="menu">
							<sec:authorize ifAllGranted="ROLE_BASE_ACCOUNT_SELF_VIEW">
								<li id="10" class="menu1_sub"><a href="selfspace/viewSelfAccount.action" target="main"><s:text name="我的通行证" /></a></li>
								<li id="06" class="menu1_sub"><a href="selfspace/memo/toList.action?update=1" target="main">我的备忘录</a></li>
							</sec:authorize>
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)">
								<li id="07" class="menu1_sub"><a href="person/teacher/toList.action?update=1" target="main"><s:text name="我的资料" /></a></li>
							</s:if>
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@EXPERT)">
								<li id="07" class="menu1_sub"><a href="person/expert/toList.action?update=1" target="main"><s:text name="我的资料" /></a></li>
							</s:if>
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@STUDENT)">
								<li id="07" class="menu1_sub"><a href="person/student/toList.action?update=1" target="main"><s:text name="我的资料" /></a></li>
							</s:if>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY , @csdc.tool.bean.AccountType@INSTITUTE) && #session.loginer.isPrincipal ==1">
								<sec:authorize ifAnyGranted="ROLE_BASE_UNIT_SELF_VIEW">
									<li id="02" class="menu1_sub"><a href="unit/selfspace/toView.action" target="main"><s:text name="i18n_CurrentInfo" /></a></li>
								</sec:authorize>
							</s:if>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT , @csdc.tool.bean.AccountType@STUDENT)">
								<sec:authorize ifAnyGranted="ROLE_BASE_PROJECT_SELF_VIEW">
									<li id="03" class="menu1_sub"><a href="project/toSearchMyProject.action" target="main"><s:text name="i18n_MyProject" /></a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_BASE_PRODUCT_SELF_VIEW">
									<li id="04" class="menu1_sub"><a href="product/toSearchDirectProduct.action" target="main"><s:text name="i18n_CurrentProduct" /></a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_BASE_AWARD_SELF_VIEW">
									<li id="05" class="menu1_sub"><a href="award/toSearchMyAward.action?listflag=5" target="main"><s:text name="i18n_CurrentAward" /></a></li>
								</sec:authorize>
								<li id="08" class="menu1_sub"><a href="linkedin/toList.action?update=1" target="main"><s:text name="我的好友" /></a></li>
								<!---------------------------- 评审项目 ------------------->
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW,ROLE_PROJECT_KEY_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW,
															ROLE_PROJECT_INSTP_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW,ROLE_PROJECT_POST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_REVIEW_VIEW,
															ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
									<li id="01" class="menu1"><a href="javascript:void(0);"><s:text name="评审项目" /></a></li>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW">
										<li id="0101" class="menu2" style="display:none;"><a href="javascript:void(0);"><s:text name="i18n_GeneralProject" /></a></li>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW">
											<li id="010101" class="menu3" style="display:none;"><a href="project/general/application/review/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW">
											<li id="010102" class="menu3" style="display:none;"><a href="project/general/endinspection/review/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
										</sec:authorize>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW">
										<li id="0102" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main"><s:text name="i18n_KeyProject" /></a></li>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_REVIEW_VIEW">
											<li id="010201" class="menu3" style="display:none;"><a href="project/key/application/review/toList.action?update=1" target="main"><s:text name="i18n_TenderData" /></a></li>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW">
											<li id="010202" class="menu3" style="display:none;"><a href="project/key/endinspection/review/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
										</sec:authorize>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW">
										<li id="0103" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main"><s:text name="i18n_InstpProject" /></a></li>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_REVIEW_VIEW">
											<li id="010301" class="menu3" style="display:none;"><a href="project/instp/application/review/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW">
											<li id="010302" class="menu3" style="display:none;"><a href="project/instp/endinspection/review/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
										</sec:authorize>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_REVIEW_VIEW">
										<li id="0104" class="menu2" style="display:none;"><a href=javascript:void(0);" target="main"><s:text name="i18n_PostProject" /></a></li>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_REVIEW_VIEW">
											<li id="010401" class="menu3" style="display:none;"><a href="project/post/application/review/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ENDINSPECTION_REVIEW_VIEW">
											<li id="010402" class="menu3" style="display:none;"><a href="project/post/endinspection/review/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
										</sec:authorize>
									</sec:authorize>	
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
										<li id="0105" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main"><s:text name="i18n_EntrustSubProject" /></a></li>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
											<li id="010501" class="menu3" style="display:none;"><a href="project/entrust/application/review/toList.action?update=1" target="main"><s:text name="i18n_ApplyData" /></a></li>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
											<li id="010502" class="menu3" style="display:none;"><a href="project/entrust/endinspection/review/toList.action?update=1" target="main"><s:text name="i18n_EndinspectionData" /></a></li>
										</sec:authorize>
									</sec:authorize>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_VIEW">
										<li id="02" class="menu1_sub"><a href="award/moesocial/application/review/toList.action?update=1&listflag=4" target="main"><s:text name="i18n_ReviewAward" /></a></li>
									</sec:authorize>
							</s:if>
						</ul>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
				<div class="right">
					<iframe id="main" width="100%" name="main" src="login/ucenterRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
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