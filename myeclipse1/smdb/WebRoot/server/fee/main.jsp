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
						<div class="sub_title_center" id="serverName" alt="fee">经费管理系统</div>
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
							<li><img src="image/top_ico01.png" /><span><a href="login/feeRight.action" target="main">首页</a></span></li>
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
							<%--<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@ADMINISTRATOR)">
							<li id="01" class="menu1"><a href="javascript:void(0);">研究项目经费</a></li>
								<li id="0101" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">一般项目</a></li>
									<li id="010101" class="menu3" style="display:none;"><a href="projectFund/general/toList.action?update=1" target="main">拨款概况</a></li>
									<li id="010102" class="menu3" style="display:none;"><a href="fundList/general/granted/toList.action?update=1" target="main">立项拨款</a></li>
									<li id="010103" class="menu3" style="display:none;"><a href="fundList/general/mid/toList.action?update=1" target="main">中检拨款</a></li>
									<li id="010104" class="menu3" style="display:none;"><a href="fundList/general/end/toList.action?update=1" target="main">结项拨款</a></li>
								<li id="0102" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">重大攻关项目</a></li>
									<li id="010201" class="menu3" style="display:none;"><a href="projectFund/key/toList.action?update=1" target="main">拨款概况</a></li>
									<li id="010202" class="menu3" style="display:none;"><a href="fundList/key/granted/toList.action?update=1" target="main">立项拨款</a></li>
									<li id="010203" class="menu3" style="display:none;"><a href="fundList/key/mid/toList.action?update=1" target="main">中检拨款</a></li>
									<li id="010204" class="menu3" style="display:none;"><a href="fundList/key/end/toList.action?update=1" target="main">结项拨款</a></li>
								<li id="0103" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">基地项目</a></li>
									<li id="010301" class="menu3" style="display:none;"><a href="projectFund/instp/toList.action?update=1" target="main">拨款概况</a></li>
									<li id="010302" class="menu3" style="display:none;"><a href="fundList/instp/granted/toList.action?update=1" target="main">立项拨款</a></li>
									<li id="010303" class="menu3" style="display:none;"><a href="fundList/instp/mid/toList.action?update=1" target="main">中检拨款</a></li>
									<li id="010304" class="menu3" style="display:none;"><a href="fundList/instp/end/toList.action?update=1" target="main">结项拨款</a></li>
								<li id="0104" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">后期资助项目</a></li>
									<li id="010401" class="menu3" style="display:none;"><a href="projectFund/post/toList.action?update=1" target="main">拨款概况</a></li>
									<li id="010402" class="menu3" style="display:none;"><a href="fundList/post/granted/toList.action?update=1" target="main">立项拨款</a></li>
									<li id="010404" class="menu3" style="display:none;"><a href="fundList/post/end/toList.action?update=1" target="main">结项拨款</a></li>
								<li id="0105" class="menu2" style="display:none;"><a href="javascript:void(0);" target="main">委托应急课题</a></li>
									<li id="010501" class="menu3" style="display:none;"><a href="projectFund/entrust/toList.action?update=1" target="main">拨款概况</a></li>
									<li id="010502" class="menu3" style="display:none;"><a href="fundList/entrust/granted/toList.action?update=1" target="main">立项拨款</a></li>
									<li id="010504" class="menu3" style="display:none;"><a href="fundList/entrust/end/toList.action?update=1" target="main">结项拨款</a></li>
							<li id="02" class="menu1"><a href="javascript:void(0);">非研究项目经费</a></li>
								<li id="0201" class="menu2_sub" style="display:none;"><a href="javascript:void(0);" target="main">人员经费</a></li>
								<li id="0202" class="menu2_sub" style="display:none;"><a href="javascript:void(0);" target="main">业务费</a></li>
								<li id="0203" class="menu2_sub" style="display:none;"><a href="javascript:void(0);" target="main">设备购置费</a></li>
							<li id="03" class="menu1"><a href="javascript:void(0);">工作经费</a></li>
								<li id="0301" class="menu2_sub" style="display:none;"><a href="javascript:void(0)" target="main">经费批次</a></li>
								<li id="0302" class="menu2_sub" style="display:none;"><a href="javascript:void(0)" target="main">奖励经费</a></li>
								<li id="0303" class="menu2_sub" style="display:none;"><a href="javascript:void(0)" target="main">中心建设经费</a></li>
								<li id="0304" class="menu2_sub" style="display:none;"><a href="javascript:void(0);" target="main">学部经费</a></li>
							</s:if>
								--%>
							<sec:authorize ifAnyGranted="ROLE_FUND_FUNDINGLIST_PROJECT_VIEW,ROLE_FUND_FUNDINGLIST_WORK_VIEW,ROLE_FUND_AGENCY_VIEW">
								<li id="04" class="menu1"><a href="javascript:void(0)" target="main">拨款清单</a></li>
									<sec:authorize ifAnyGranted="ROLE_FUND_FUNDINGLIST_PROJECT_VIEW,ROLE_FUND_FUNDINGLIST_WORK_VIEW">
										<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
											<li id="0401" class="menu2_sub" style="display:none;"><a href="funding/fundingList/toList.action" target="main">按类型拨款清单</a></li>
										</s:if>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_FUND_AGENCY_VIEW">
										<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级及以上-->
											<li id="0402" class="menu2_sub" style="display:none;"><a href="funding/agencyFunding/toList.action" target="main">按单位拨款清单</a></li>
										</s:if>
									</sec:authorize>
							</sec:authorize>
							<%--<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">
								<sec:authorize ifAnyGranted="ROLE_FUND_PROJECTFUND_VIEW,ROLE_FUND_FUNDINGLIST_PROJECT_VIEW">
									<li id="05" class="menu1"><a href="javascript:void(0)" target="main">研究项目经费</a></li>
									<sec:authorize ifAnyGranted="ROLE_FUND_PROJECTFUND_VIEW">
											<li id="0501" class="menu2_sub" style="display:none;"><a href="funding/projectFund/toList.action" target="main">项目经费概况</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_FUND_FUNDINGLIST_PROJECT_VIEW">
										<li id="0502" class="menu2_sub" style="display:none;"><a href="funding/fundingList/project/toList.action" target="main">项目经费清单</a></li>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_FUND_FUNDINGLIST_WORK_VIEW">
										<li id="06" class="menu1"><a href="javascript:void(0)" target="main">非研究项目经费</a></li>
										<sec:authorize ifAnyGranted="ROLE_FUND_FUNDINGLIST_WORK_VIEW">
											<li id="0601" class="menu2_sub" style="display:none;"><a href="funding/fundingList/work/toList.action" target="main">工作经费清单</a></li>
										</sec:authorize>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_FUND_FUNDINGBATCH_VIEW">
										<li id="07" class="menu1_sub"><a href="funding/fundingBatch/toList.action" target="main">拨款批次管理</a></li>
									</sec:authorize>
								</sec:authorize>
							</s:if>
						--%></ul>
					</div>
					<s:include value="/auxiliary/basicAuxiliary/historyAccess.jsp"></s:include>
				</div>
				<s:include value="/server/expandIco.jsp" />
				<s:include value="/server/loadIco.jsp" />
				<div class="right">
					<iframe id="main" width="100%" name="main" src="login/feeRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
				</div>
			</div>
			
			<s:include value="/innerFoot.jsp" />
		</div>
		<div style="display:none">
			<script src="http://s6.cnzz.com/stat.php?id=4360704&web_id=4360704&show=pic" language="JavaScript"></script>
		<div>
	</body>
</html>