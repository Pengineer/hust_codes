<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
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
						<div class="sub_title_center" id="serverName" alt="basis">基础数据库系统</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT) || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/basisRight.action" target="main">首页</a></span></li>
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
							<sec:authorize ifAnyGranted="ROLE_INFORMATION_NEWS_VIEW,ROLE_INFORMATION_NOTICE_VIEW,ROLE_INFORMATION_MESSAGE_VIEW,ROLE_INFORMATION_INBOX_VIEW">
								<li id="09" class="menu1"><a href="javascript:void(0);">社科信息发布</a></li>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_VIEW">
									<li id="0901" class="menu2_sub" style="display:none;"><a href="news/inner/toList.action?update=1" target="main">新闻</a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_NOTICE_VIEW">
									<li id="0902" class="menu2_sub" style="display:none;"><a href="notice/inner/toList.action?update=1" target="main">通知</a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_VIEW">
									<li id="0903" class="menu2_sub" style="display:none;"><a href="message/inner/toList.action?update=1" target="main">留言簿</a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_INBOX_VIEW">
									<li id="0904" class="menu2_sub" style="display:none;"><a href="inBox/toList.action?update=1" target="main">站内信</a></li>
								</sec:authorize>
							</sec:authorize>
							
							
							
							
					<!-------------------- 人员 --------------->
							<sec:authorize ifAnyGranted="ROLE_PERSON_OFFICER_MINISTRY_VIEW,ROLE_PERSON_OFFICER_PROVINCE_VIEW,ROLE_PERSON_OFFICER_UNIVERSITY_VIEW,ROLE_PERSON_OFFICER_DEPARTMENT_VIEW,ROLE_PERSON_OFFICER_INSTITUTE_VIEW,ROLE_PERSON_EXPERT_VIEW,ROLE_PERSON_TEACHER_VIEW,ROLE_PERSON_STUDENT_VIEW">
							<li id="03" class="menu1"><a href="javascript:void(0);">社科人员数据</a></li>
								<sec:authorize ifAnyGranted="ROLE_PERSON_OFFICER_MINISTRY_VIEW,ROLE_PERSON_OFFICER_PROVINCE_VIEW,ROLE_PERSON_OFFICER_UNIVERSITY_VIEW,ROLE_PERSON_OFFICER_DEPARTMENT_VIEW,ROLE_PERSON_OFFICER_INSTITUTE_VIEW">
								<li id="0301" class="menu2" style="display:none;"><a href="javascript:void(0);">社科管理人员</a></li>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_MINISTRY_VIEW">
									<li id="030101" class="menu3" style="display:none;"><a href="person/ministryOfficer/toList.action?update=1&unitType=0" target="main">部级管理人员</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_PROVINCE_VIEW">
									<li id="030102" class="menu3" style="display:none;"><a href="person/provinceOfficer/toList.action?update=1&unitType=1" target="main">省级管理人员</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_UNIVERSITY_VIEW">
									<li id="030103" class="menu3" style="display:none;"><a href="person/universityOfficer/toList.action?update=1&unitType=2" target="main">高校管理人员</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_DEPARTMENT_VIEW">
									<li id="030104" class="menu3" style="display:none;"><a href="person/departmentOfficer/toList.action?update=1&unitType=3" target="main">院系管理人员</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_INSTITUTE_VIEW">
									<li id="030105" class="menu3" style="display:none;"><a href="person/instituteOfficer/toList.action?update=1&unitType=4" target="main">基地管理人员</a></li>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PERSON_EXPERT_VIEW,ROLE_PERSON_TEACHER_VIEW,ROLE_PERSON_STUDENT_VIEW">
								<li id="0302" class="menu2" style="display:none;"><a href="javascript:void(0);">社科研究人员</a></li>
									<sec:authorize ifAllGranted="ROLE_PERSON_EXPERT_VIEW">
									<li id="030201" class="menu3" style="display:none;"><a href="person/expert/toList.action?update=1" target="main">外部专家</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_TEACHER_VIEW">
									<li id="030202" class="menu3" style="display:none;"><a href="person/teacher/toList.action?update=1" target="main">教师</a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted="ROLE_PERSON_STUDENT_VIEW">
									<li id="030203" class="menu3" style="display:none;"><a href="person/student/toList.action?update=1" target="main">学生</a></li>
									</sec:authorize>
								</sec:authorize>
							</sec:authorize>
					<!-------------------- 机构 --------------->
							<sec:authorize ifAnyGranted="ROLE_UNIT_MINISTRY_VIEW,ROLE_UNIT_PROVINCE_VIEW,ROLE_UNIT_UNIVERSITY_VIEW,ROLE_UNIT_DEPARTMENT_VIEW,ROLE_UNIT_INSTITUTE_VIEW">
								<li id="04" class="menu1"><a href="javascript:void(0);">社科机构数据</a></li>
									<li id="0401" class="menu2" style="display:none;"><a href="javascript:void(0);">社科管理机构</a></li>
									<sec:authorize ifAnyGranted="ROLE_UNIT_MINISTRY_VIEW">
										<li id="040101" class="menu3" style="display:none;"><a href="unit/agency/ministry/toList.action?update=1" target="main">部级管理机构</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_UNIT_PROVINCE_VIEW">
										<li id="040102" class="menu3" style="display:none;"><a href="unit/agency/province/toList.action?update=1" target="main">省级管理机构</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_UNIT_UNIVERSITY_VIEW">
										<li id="040103" class="menu3" style="display:none;"><a href="unit/agency/university/toList.action?update=1" target="main">校级管理机构</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_UNIT_DEPARTMENT_VIEW">
										<li id="040104" class="menu3" style="display:none;"><a href="unit/department/toList.action?update=1" target="main">院系管理机构</a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_UNIT_INSTITUTE_VIEW">
									<li id="0402" class="menu2" style="display:none;"><a href="javascript:void(0);">社科研究机构</a></li>
									<li id="040201" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=1" target="main">部级重点研究基地</a></li>
									<li id="040202" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=2" target="main">部省共建重点研究基地</a></li>
									<li id="040203" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=3" target="main">部部共建重点研究基地</a></li>
									<li id="040204" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=4" target="main">部级重点研究实验室</a></li>
									<li id="040205" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=5" target="main">省级重点研究基地</a></li>
									<li id="040206" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=6" target="main">校级重点研究基地</a></li>
									<li id="040207" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=7" target="main">校企合作研究基地</a></li>
									<li id="040208" class="menu3" style="display:none;"><a href="unit/institute/toList.action?update=1&instType=8" target="main">其他研究基地</a></li>
								</sec:authorize>
							</sec:authorize>
							
							
							
							
							
							
							<!---------------------------- 项目管理 ------------------->
							<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW,ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW,ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_VARIATION_APPLY_VIEW,
														 ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
								<li id="05" class="menu1"><a href="javascript:void(0);">社科项目数据</a></li>
							</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW,ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW">
									<li id="0501" class="menu2" style="display:none;"><a href="javascript:void(0);">一般项目</a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW">
										<li id="050101" class="menu3" style="display:none;"><a href="project/general/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_VIEW">
										<li id="050102" class="menu3" style="display:none;"><a href="project/general/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW">
										<li id="050103" class="menu3" style="display:none;"><a href="project/general/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLY_VIEW">
										<li id="050104" class="menu3" style="display:none;"><a href="project/general/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW">
										<li id="050105" class="menu3" style="display:none;"><a href="project/general/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_VARIATION_APPLY_VIEW">
										<li id="050106" class="menu3" style="display:none;"><a href="project/general/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW,ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW">
										<li id="0502" class="menu2" style="display:none;"><a href="javascript:void(0);">重大攻关项目</a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW">
										<li id="050201" class="menu3" style="display:none;"><a href="project/key/topicSelection/apply/toList.action?update=1" target="main">年度选题</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_VIEW">
										<li id="050202" class="menu3" style="display:none;"><a href="project/key/application/apply/toList.action?update=1" target="main">投标数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_APPLICATION_GRANTED_VIEW">
										<li id="050203" class="menu3" style="display:none;"><a href="project/key/application/granted/toList.action?update=1" target="main">中标数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ANNINSPECTION_APPLY_VIEW">
										<li id="050204" class="menu3" style="display:none;"><a href="project/key/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW">
										<li id="050205" class="menu3" style="display:none;"><a href="project/key/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_ENDINSPECTION_APPLY_VIEW">
										<li id="050206" class="menu3" style="display:none;"><a href="project/key/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_KEY_VARIATION_APPLY_VIEW">
										<li id="050207" class="menu3" style="display:none;"><a href="project/key/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW,ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW,ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW">
									<li id="0503" class="menu2" style="display:none;"><a href="javascript:void(0);">基地项目</a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_APPLY_VIEW">
										<li id="050301" class="menu3" style="display:none;"><a href="project/instp/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_APPLICATION_GRANTED_VIEW">
										<li id="050302" class="menu3" style="display:none;"><a href="project/instp/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize >
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW">
										<li id="050303" class="menu3" style="display:none;"><a href="project/instp/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW">
										<li id="050304" class="menu3" style="display:none;"><a href="project/instp/midinspection/apply/toList.action?update=1" target="main">中检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW">
										<li id="050305" class="menu3" style="display:none;"><a href="project/instp/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_INSTP_VARIATION_APPLY_VIEW">
										<li id="050306" class="menu3" style="display:none;"><a href="project/instp/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_POST_VARIATION_APPLY_VIEW">
										<li id="0504" class="menu2" style="display:none;"><a href="javascript:void(0);">后期资助项目</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_APPLY_VIEW">
										<li id="050401" class="menu3" style="display:none;"><a href="project/post/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_VIEW">
										<li id="050402" class="menu3" style="display:none;"><a href="project/post/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ANNINSPECTION_APPLY_VIEW">
										<li id="050403" class="menu3" style="display:none;"><a href="project/post/anninspection/apply/toList.action?update=1" target="main">年检数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_ENDINSPECTION_APPLY_VIEW">
										<li id="050404" class="menu3" style="display:none;"><a href="project/post/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_POST_VARIATION_APPLY_VIEW">
										<li id="050405" class="menu3" style="display:none;"><a href="project/post/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW,ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW,ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
									<li id="0505" class="menu2" style="display:none;"><a href="javascript:void(0);">委托应急课题</a></li>
								</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW">
										<li id="050501" class="menu3" style="display:none;"><a href="project/entrust/application/apply/toList.action?update=1" target="main">申请数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_VIEW">
										<li id="050502" class="menu3" style="display:none;"><a href="project/entrust/application/granted/toList.action?update=1" target="main">立项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW">	
										<li id="050503" class="menu3" style="display:none;"><a href="project/entrust/endinspection/apply/toList.action?update=1" target="main">结项数据</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_PROJECT_ENTRUST_VARIATION_APPLY_VIEW">
										<li id="050504" class="menu3" style="display:none;"><a href="project/entrust/variation/apply/toList.action?update=1" target="main">变更数据</a></li>
									</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_PRODUCT_VIEW">
								<li id="06" class="menu1"><a href="javascript:void(0);">社科成果数据</a></li>
								<li id="0601" class="menu2_sub" style="display:none;"><a href="product/paper/toList.action?update=1" target="main">论文</a></li>
								<li id="0602" class="menu2_sub" style="display:none;"><a href="product/book/toList.action?update=1" target="main">著作</a></li>
								<li id="0603" class="menu2_sub" style="display:none;"><a href="product/consultation/toList.action?update=1" target="main">研究咨询报告</a></li>
								<li id="0604" class="menu2_sub" style="display:none;"><a href="product/electronic/toList.action?update=1" target="main">电子出版物</a></li>
								<li id="0605" class="menu2_sub" style="display:none;"><a href="product/patent/toList.action?update=1" target="main">专利</a></li>
								<li id="0606" class="menu2_sub" style="display:none;"><a href="product/otherProduct/toList.action?update=1" target="main">其他成果</a></li>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITY_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_AWARDED_VIEW"> 
								<li id="07" class="menu1"><a href="javascript:void(0);">社科奖励数据</a></li>
								<li id="0701" class="menu2" style="display:none;"><a href="javascript:void(0);">人文社科奖</a></li>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_VIEW"> 
									<li id="070101" class="menu3" style="display:none;"><a href="award/moesocial/application/apply/toList.action?update=1&listflag=1" target="main">申请数据</a></li>
								</sec:authorize> 
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITY_VIEW">
									<li id="070102" class="menu3" style="display:none;"><a href="award/moesocial/application/publicity/toList.action?update=1&listflag=2" target="main">公示数据</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_AWARDED_VIEW">
									<li id="070103" class="menu3" style="display:none;"><a href="award/moesocial/application/awarded/toList.action?update=1&listflag=3" target="main">获奖数据</a></li>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_OTHER_NSFC_VIEW,ROLE_OTHER_NSSF_VIEW"> 
								<li id="08" class="menu1"><a href="javascript:void(0);">社科其他数据</a></li>
								<sec:authorize ifAnyGranted="ROLE_OTHER_NSFC_VIEW"> 
									<li id="0801" class="menu2_sub" style="display:none;"><a href="other/nsfc/granted/toList.action?update=1" target="main">国家自然科学基金项目</a></li>
								</sec:authorize> 
								<sec:authorize ifAnyGranted="ROLE_OTHER_NSSF_VIEW"> 
									<li id="0802" class="menu2" style="display:none;"><a href="javascript:void(0);">国家社会科学基金项目</a></li>
									<li id="080201" class="menu3" style="display:none;"><a href="other/nssf/application/toList.action?update=1" target="main">申请数据</a></li>
									<li id="080202" class="menu3" style="display:none;"><a href="other/nssf/granted/toList.action?update=1" target="main">立项数据</a></li>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_BUSINESS_VIEW">
								<li id="10" class="menu1_sub"><a href="business/toList.action?update=1" target="main">社科业务日程</a></li>
							</sec:authorize>
						</ul>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
				<div class="right">
					<iframe id="main" width="100%" name="main" src="login/basisRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no" ></iframe>
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
