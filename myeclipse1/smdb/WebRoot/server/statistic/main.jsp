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
		<link type="text/css" href="tool/jquery.menus/menu.css" rel="stylesheet" />
		<script type="text/javascript" src="tool/jquery.menus/menu.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
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
						<div class="sub_title_center" id="serverName" alt="statistic">统计分析与决策系统</div>
						<div class="sub_title_right"><img src="image/sub_r.png"/></div>
						<s:include value="/server/switchServer.jsp" />
					</div>
					<div class="top_menu">
						<div class="txt">
							<span id="switch_account" class="passport" alt="<s:property value="#session.loginer.account.id" />"><span class="color1">登录为</span>&nbsp;<img src="image/ico08.gif"></span>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT , @csdc.tool.bean.AccountType@TEACHER) || #session.loginer.isPrincipal == 0">
								<s:property value="#session.loginer.currentPersonName" />
							</s:if>
							<s:else>
								<s:property value="#session.loginer.currentBelongUnitName" />
							</s:else>&nbsp;[<s:property value="#session.loginer.passport.name" />]&nbsp;&nbsp;<script type="text/javascript">document.write(showLocale());</script>
						</div>
						<ul>
							<li><img src="image/top_ico01.png" /><span><a href="login/statisticRight.action" target="main">首页</a></span></li>
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
			
			<div id="menu">
				<ul class="menu">
				<sec:authorize ifAnyGranted="ROLE_STATISTIC_COMMON_ADD,ROLE_STATISTIC_COMMON_DELETE,ROLE_STATISTIC_COMMON_MODIFY,ROLE_STATISTIC_COMMON_VIEW,ROLE_STATISTIC_COMMON_EXPORT,ROLE_STATISTIC_COMMON_HOMESHOW">
					<li><a href="javascript:void(0);" class="parent"><span>常规数据统计</span></a>
						<div><ul>
							<li><a href="statistic/common/toList.action?update=1&statisticType=person" target="main"><span>社科人员统计</span></a></li>
							<li><a href="statistic/common/toList.action?update=1&statisticType=unit" target="main"><span>社科机构统计</span></a></li>
							<li><a href="javascript:void(0);" class="parent"><span>社科项目统计</span></a>
								<div><ul>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project" target="main"><span>所有统计</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project_general" target="main"><span>一般项目</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project_key" target="main"><span>重大攻关项目</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project_instp" target="main"><span>基地项目</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project_post" target="main"><span>后期资助项目</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project_entrust" target="main"><span>委托应急课题</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=project_multiple" target="main"><span>综合统计</span></a></li>
								</ul></div>
							</li>
							<li><a href="javascript:void(0);" class="parent"><span>社科成果统计</span></a>
								<div><ul>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product" target="main"><span>所有统计</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_paper" target="main"><span>论文</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_book" target="main"><span>著作</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_consultation" target="main"><span>研究咨询报告</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_electronic" target="main"><span>电子出版物</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_patent" target="main"><span>专利</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_other" target="main"><span>其他成果</span></a></li>
									<li><a href="statistic/common/toList.action?update=1&statisticType=product_multiple" target="main"><span>综合统计</span></a></li>
								</ul></div>
							</li>
							<li><a href="statistic/common/toList.action?update=1&statisticType=award" target="main"><span>社科奖励统计</span></a></li>
							<li><a href="statistic/common/toList.action?update=1&statisticType=expert" target="main"><span>研修班学员统计</span></a></li>
						</ul></div>
					</li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_STATISTIC_CUSTOM_EXPORT,ROLE_STATISTIC_CUSTOM_TOCOMMON,ROLE_STATISTIC_CUSTOM_VIEW">
						<li><a href="javascript:void(0);" class="parent"><span>定制数据统计</span></a>
							<div><ul>
								<li><a href="statistic/custom/person/toCustomStatistic.action" target="main"><span>社科人员定制统计</span></a></li>
								<li><a href="statistic/custom/unit/toCustomStatistic.action" target="main"><span>社科机构定制统计</span></a></li>
								<li><a href="statistic/custom/project/toCustomStatistic.action" target="main"><span>社科项目定制统计</span></a></li>
								<li><a href="statistic/custom/product/toCustomStatistic.action" target="main"><span>社科成果定制统计</span></a></li>
								<li><a href="statistic/custom/award/toCustomStatistic.action" target="main"><span>社科奖励定制统计</span></a></li>
							</ul></div>
						</li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_STATISTIC_REPORT_VIEW">
					<li><a href="statistic/report/report.jsp" target="main"><span>统计分析报告</span></a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_DATAMINING,ROLE_DATAMINING_RESULT,ROLE_DATAMINING_RESULT_VIEW,ROLE_DATAMINING_RESULT_DELETE,ROLE_DATAMINING_HOTSPOT,ROLE_DATAMINING_HOTSPOT_UPDATEINDEX,
					ROLE_DATAMINING_HOTSPOT_ANALYZE,ROLE_DATAMINING_ASSOCIATION,ROLE_DATAMINING_ASSOCIATION_ANALYZE,ROLE_DATAMINING_ASSOCIATION_CUSTOM,	ROLE_DATAMINING_CLASSIFICATION,	ROLE_DATAMINING_CLASSIFICATION_TRAINMODEL,ROLE_DATAMINING_CLASSIFICATION_PREDICT">
						<li><a href="javascript:void(0);" class="parent"><span>数据分析与挖掘</span></a>
							<div><ul>
								<sec:authorize ifAnyGranted="ROLE_DATAMINING_RESULT_VIEW,ROLE_DATAMINING_RESULT_DELETE">
									<li><a href="dataMining/result/toList.action?update=1" target="main"><span>数据挖掘结果</span></a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_DATAMINING_HOTSPOT_UPDATEINDEX,ROLE_DATAMINING_HOTSPOT_ANALYZE">
									<li><a href="javascript:void(0);" class="parent"><span>领域热点分析</span></a>
										<div><ul>
											<li><a href="dataMining/hotspot/toHotspotConfig.action" target="main"><span>项目研究热点分析</span></a></li>
										</ul></div>
									</li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_DATAMINING_ASSOCIATION_ANALYZE,ROLE_DATAMINING_ASSOCIATION_CUSTOM">
									<li><a href="javascript:void(0);" class="parent"><span>关联规则挖掘</span></a>
										<div><ul>
										<sec:authorize ifAllGranted="ROLE_DATAMINING_ASSOCIATION_ANALYZE">
											<li><a href="dataMining/association/toAnalyzeConfig.action" target="main"><span>项目关联性分析</span></a></li>
										</sec:authorize>
										<sec:authorize ifAllGranted="ROLE_DATAMINING_ASSOCIATION_CUSTOM">
											<li><a href="dataMining/association/toCustom.action" target="main"><span>关联规则定制分析</span></a></li>
										</sec:authorize>
											<li><a href="statistic/analysis/project/toAnalysis.action" target="main"><span>社科项目关联性分析</span></a></li>
										</ul></div>
									</li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_DATAMINING_CLASSIFICATION_TRAINMODEL,ROLE_DATAMINING_CLASSIFICATION_PREDICT">
									<li><a href="javascript:void(0);" class="parent"><span>分类预测挖掘</span></a>
										<div><ul>
											<li><a href="dataMining/classification/toPredictConfig.action" target="main"><span>项目分类预测</span></a></li>
										</ul></div>
									</li>
								</sec:authorize>
								<li><a href="javascript:void(0);" class="mian"><span>聚类分析挖掘</span></a></li>
								<li><a href="javascript:void(0);" class="mian"><span>智能推荐挖掘</span></a></li>
							</ul></div>
						</li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_ASSIST_PERSON_QUERY, ROLE_ASSIST_UNIT_QUERY">
						<li><a href="javascript:void(0);" class="parent"><span>综合辅助信息</span></a>
							<div>
								<ul>
									<sec:authorize ifAllGranted="ROLE_ASSIST_PERSON_QUERY">
										<li><a href="auxiliary/person/toPersonQuery.action" class="mian" target="main"><span>社科人员查询</span></a></li>
									</sec:authorize>
									<sec:authorize ifAllGranted=" ROLE_ASSIST_UNIT_QUERY">
										<li><a href="auxiliary/unit/toUnitQuery.action" class="mian" target="main"><span>社科机构查询</span></a></li>
									</sec:authorize>
								</ul>
							</div>
						</li>
					</sec:authorize>
				</ul>
				<s:hidden id="queryName" />
			</div>
			
		    <div class="right1">
		    	<iframe id="main" width="100%" name="main" src="login/statisticRight.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
		    </div>
			
			<s:include value="/innerFoot.jsp" />
		</div>
		<div id="gotop"></div>
		<div style="display:none">
			<script src="http://s6.cnzz.com/stat.php?id=4360704&web_id=4360704&show=pic" language="JavaScript"></script>
		<div>
	</body>
</html>