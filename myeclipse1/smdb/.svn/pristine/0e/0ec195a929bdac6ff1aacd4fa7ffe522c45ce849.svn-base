<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
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
						<div class="sub_title_center" id="serverName" alt="project">项目管理系统</div>
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
							<li><img src="image/top_ico01.png" /><span><a href="login/projectRight.action" target="main">首页</a></span></li>
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
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@EXPERT) || #session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)"><!-- 专家或教师-->
							<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW,ROLE_PROJECT_KEY_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW,
														ROLE_PROJECT_INSTP_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW,ROLE_PROJECT_POST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_REVIEW_VIEW,
														ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
								<li id="01" class="menu1"><a href="javascript:void(0);">评审项目</a></li>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW">
									<li id="0101" class="menu2" style="display:none;"><a href="javascript:void(0);">一般项目</a></li>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW">
										<li id="010101" class="menu3" style="display:none;"><a href="project/general/application/review/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW">
										<li id="010102" class="menu3" style="display:none;"><a href="project/general/endinspection/review/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW">
									<li id="0102" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">重大攻关项目</a></li>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_REVIEW_VIEW">
										<li id="010201" class="menu3" style="display:none;"><a href="project/key/application/review/toList.action?update=1" target="main">投标数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW">
										<li id="010202" class="menu3" style="display:none;"><a href="project/key/endinspection/review/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW">
									<li id="0103" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">基地项目</a></li>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_REVIEW_VIEW">
										<li id="010301" class="menu3" style="display:none;"><a href="project/instp/application/review/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW">
										<li id="010302" class="menu3" style="display:none;"><a href="project/instp/endinspection/review/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_REVIEW_VIEW">
									<li id="0104" class="menu2" style="display:none;"><a href=javascript:void(0);" target="main">后期资助项目</a></li>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_REVIEW_VIEW">
										<li id="010401" class="menu3" style="display:none;"><a href="project/post/application/review/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ENDINSPECTION_REVIEW_VIEW">
										<li id="010402" class="menu3" style="display:none;"><a href="project/post/endinspection/review/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
								</sec:authorize>	
								<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
									<li id="0105" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">委托应急课题</a></li>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
										<li id="010501" class="menu3" style="display:none;"><a href="project/entrust/application/review/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW">
										<li id="010502" class="menu3" style="display:none;"><a href="project/entrust/endinspection/review/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
								</sec:authorize>	
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_VIEW">
								<li id="02" class="menu1_sub"><a href="award/moesocial/application/review/toList.action?update=1&listflag=4" target="main">评审奖励</a></li>
							</sec:authorize>
							</s:if>
							</s:if>
							<s:else>
							<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW,ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW">
								<li id="01" class="menu1"><a href="javascript:void(0);">一般项目</a></li>
							</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW">
									<li id="0101" class="menu2_sub" style="display:none;"><a href="project/general/application/apply/toList.action?update=1" target="main">申请数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW">
									<li id="0102" class="menu2_sub" style="display:none;"><a href="project/general/application/granted/toList.action?update=1" target="main">立项数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW">
									<li id="0103" class="menu2_sub" style="display:none;"><a href="project/general/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW">
									<li id="0104" class="menu2_sub" style="display:none;"><a href="project/general/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW">
									<li id="0105" class="menu2_sub" style="display:none;"><a href="project/general/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW">
									<li id="0106" class="menu2_sub" style="display:none;"><a href="project/general/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
								</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW">
								<li id="02" class="menu1"><a href="javascript:void(0);">重大攻关项目</a></li>
							</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW">
									<li id="0201" class="menu2_sub" style="display:none;"><a href="project/key/topicSelection/apply/toList.action?update=1" target="main">年度选题</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW">
									<li id="0202" class="menu2_sub" style="display:none;"><a href="project/key/application/apply/toList.action?update=1" target="main">投标数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW">
									<li id="0203" class="menu2_sub" style="display:none;"><a href="project/key/application/granted/toList.action?update=1" target="main">中标数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW">
									<li id="0204" class="menu2_sub" style="display:none;"><a href="project/key/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW">
									<li id="0205" class="menu2_sub" style="display:none;"><a href="project/key/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW">
									<li id="0206" class="menu2_sub" style="display:none;"><a href="project/key/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW">	
									<li id="0207" class="menu2_sub" style="display:none;"><a href="project/key/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
								</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW,ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW">
								<li id="03" class="menu1"><a href="javascript:void(0);">基地项目</a></li>
							</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW">
										<li id="0301" class="menu2_sub" style="display:none;"><a href="project/instp/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW">
										<li id="0302" class="menu2_sub" style="display:none;"><a href="project/instp/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW">
										<li id="0303" class="menu2_sub" style="display:none;"><a href="project/instp/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW">
										<li id="0304" class="menu2_sub" style="display:none;"><a href="project/instp/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW">
										<li id="0305" class="menu2_sub" style="display:none;"><a href="project/instp/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW">
										<li id="0306" class="menu2_sub" style="display:none;"><a href="project/instp/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_VARIATION_APPLY_VIEW">		
									<li id="04" class="menu1"><a href="javascript:void(0);">后期资助项目</a></li>
							</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW">
										<li id="0401" class="menu2_sub" style="display:none;"><a href="project/post/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW">
										<li id="0402" class="menu2_sub" style="display:none;"><a href="project/post/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW">
										<li id="0403" class="menu2_sub" style="display:none;"><a href="project/post/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW">
										<li id="0404" class="menu2_sub" style="display:none;"><a href="project/post/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_VARIATION_APPLY_VIEW">
										<li id="0405" class="menu2_sub" style="display:none;"><a href="project/post/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
								<li id="05" class="menu1"><a href="javascript:void(0);">委托应急课题</a></li>
							</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW">
										<li id="0501" class="menu2_sub" style="display:none;"><a href="project/entrust/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW">
										<li id="0502" class="menu2_sub" style="display:none;"><a href="project/entrust/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW">
										<li id="0503" class="menu2_sub" style="display:none;"><a href="project/entrust/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
										<li id="0504" class="menu2_sub" style="display:none;"><a href="project/entrust/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_APPLICATION_APPLY_VIEW,ROLE_PROJECT_SPECIAL_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_SPECIAL_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_SPECIAL_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_SPECIAL_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_SPECIAL_VARIATION_APPLY_VIEW">
										<li id="06" class="menu1"><a href="javascript:void(0);">专项任务项目</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_APPLICATION_APPLY_VIEW">
										<li id="0601" class="menu2_sub" style="display:none;"><a href="project/special/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_APPLICATION_GRANTED_VIEW">
										<li id="0602" class="menu2_sub" style="display:none;"><a href="project/special/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_ANNINSPECTION_APPLY_VIEW">
										<li id="0603" class="menu2_sub" style="display:none;"><a href="project/special/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_MIDINSPECTION_APPLY_VIEW">
										<li id="0604" class="menu2_sub" style="display:none;"><a href="project/special/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_ENDINSPECTION_APPLY_VIEW">
										<li id="0605" class="menu2_sub" style="display:none;"><a href="project/special/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_SPECIAL_VARIATION_APPLY_VIEW">
										<li id="0606" class="menu2_sub" style="display:none;"><a href="project/special/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
							</s:else>
							<sec:authorize ifAnyGranted="ROLE_BUSINESS_VIEW">
								<li id="07" class="menu1_sub"><a href="business/toList.action?update=1" target="main">社科业务日程</a></li>
							</sec:authorize>
						</ul>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
			    <div class="right">
			    	<iframe id="main" width="100%" name="main" src="login/projectRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
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