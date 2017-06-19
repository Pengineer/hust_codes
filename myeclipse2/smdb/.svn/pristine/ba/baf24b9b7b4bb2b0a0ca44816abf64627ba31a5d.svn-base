<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_CSDCSMDB" /></title>
		<s:include value="/innerBase.jsp" />
		<link type="text/css" href="tool/jquery.xslider/xslider.css" rel="stylesheet" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/statistic/tool/flash_ver_detection.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/swf/swfobject.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/statistic/statistic.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</head>
	
	<body>
		<div class="link_bar">当前位置：首页</div>
       	<div class="main">
 			<div class="m_content1" style="height:353px; width:968px">
 				<div id="slide-wrapper">
				    <ul id="slide">
				      	<s:iterator value="homeStat" status="stat">
						  	<li><a href="statistic/common/toView.action?viewFlag=2&entityId=<s:property value="homeStat[#stat.index].mdxid"/>&statisticType=<s:property value="homeStat[#stat.index].type"/>" title="<s:text name='i18n_ViewDetails' />"><div class="caption"><s:property value="homeStat[#stat.index].title" /></div></a><div id="picture" style="width:600px; height:300px"></div></li>
					  	</s:iterator>
				    </ul>
				    <s:hidden id="homeStat" name="homeStat"/>
				</div>
           		<div class="main_home1">
           			<span class="png"><span class="color1"><s:property value="#session.loginer.passport.name" /></span>，您好！您的账号类型为：<s:property value="#session.loginer.currentTypeName" />。<br />
           				所在单位为：<s:property value="#session.loginer.currentBelongUnitName" />。
           				<sec:authorize ifAllGranted="ROLE_STATISTIC_DATAWAREHOUSE_UPDATE">
           					<input id="updateDW" type="button" value="更新数据仓库" />
           					<span id="updating" style="color: red; display: none;">正在更新...</span>
           				</sec:authorize>
           				<br /><span class="color2">上次更新时间：<s:property value="#application.DWUpdateTime" /></span>
           			</span>
           		</div>
 			</div>
 			<div class="main_content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
           				<td width="49%" valign="top">
   							<div class="m_title">
   								<span><s:text name="i18n_StaReport" /></span><a href="javascript:void(0);"><s:text name="i18n_More" /></a>
   							</div>
             				<div class="m_content" style="height:140px;">
             					<%--<div class="no_records"><s:text name="i18n_NoRecords" /></div>--%>
		                  		<ul>
		                      		<li><a class="txt_left" title="2011年教育部人文社会科学研究项目统计及比较分析报告" href="upload/statistic/report/2011年教育部人文社会科学研究项目统计及比较分析报告.pdf">2011年教育部人文社会科学研究项目统计及比较分析报告</a><span class="txt_right">[2012.05.28]</span></li>
		                        	<li><a class="txt_left" title="2009年－2010年教育部人文社会科学研究项目统计及比较分析报告" href="upload/statistic/report/2009年－2010年教育部人文社会科学研究项目统计及比较分析报告.pdf">2009年－2010年教育部人文社会科学研究项目统计及比较分析报告</a><span class="txt_right">[2010.11.10]</span></li>
		                      	</ul>
		                  	</div>
           				</td>
           				<td width="2%">&nbsp;</td>
           				<td width="49%" valign="top">
   							<div class="m_title">
   								<span><s:text name="i18n_PersonSta" /></span><a href="statistic/common/toList.action?statisticType=person&update=1"><s:text name="i18n_More" /></a>
   							</div>
             				<div class="m_content" style="height:140px;">
		                  		<ul>
		                      		<s:iterator value="personStat" status="stat">
										<li><a title="<s:property value="personStat[#stat.index][1]" />" href="statistic/common/toView.action?viewFlag=2&entityId=<s:property value="personStat[#stat.index][0]"/>&statisticType=<s:property value="personStat[#stat.index][3]"/>" class="txt_left"><s:property value="personStat[#stat.index][1]" /></a><span class="txt_right"><s:if test="#session.locale == 'zh'"><s:date name="personStat[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="personStat[#stat.index][2]" format="yyyy-MM-dd" /></s:else></span></li>
									</s:iterator>
		                      	</ul>
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
   								<span><s:text name="i18n_UnitSta" /></span><a href="statistic/common/toList.action?statisticType=unit&update=1"><s:text name="i18n_More" /></a>
   							</div>
			              	<div class="m_content" style="height:140px;">
			                  	<ul>
		                      		<s:iterator value="unitStat" status="stat">
										<li><a title="<s:property value="unitStat[#stat.index][1]" />" href="statistic/common/toView.action?viewFlag=2&entityId=<s:property value="unitStat[#stat.index][0]"/>&statisticType=<s:property value="unitStat[#stat.index][3]"/>" class="txt_left"><s:property value="unitStat[#stat.index][1]" /></a><span class="txt_right"><s:if test="#session.locale == 'zh'"><s:date name="unitStat[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="unitStat[#stat.index][2]" format="yyyy-MM-dd" /></s:else></span></li>
									</s:iterator>
		                        </ul>
		                    </div>
           				</td>
           				<td width="2%">&nbsp;</td>
           				<td width="49%" valign="top">
		     	     	 	<div class="m_title">
   								<span><s:text name="i18n_ProjectSta" /></span><a href="statistic/common/toList.action?statisticType=project_general&update=1"><s:text name="i18n_More" /></a>
   							</div>
             				<div class="m_content" style="height:140px;">
		                  		<ul>
		                  			<s:iterator value="projectStat" status="stat">
										<li><a title="<s:property value="projectStat[#stat.index][1]" />" href="statistic/common/toView.action?viewFlag=2&entityId=<s:property value="projectStat[#stat.index][0]"/>&statisticType=<s:property value="projectStat[#stat.index][3]"/>" class="txt_left"><s:property value="projectStat[#stat.index][1]" /></a><span class="txt_right"><s:if test="#session.locale == 'zh'"><s:date name="projectStat[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="projectStat[#stat.index][2]" format="yyyy-MM-dd" /></s:else></span></li>
									</s:iterator>
		                      	</ul>
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
   								<span><s:text name="i18n_ProductSta" /></span><a href="statistic/common/toList.action?statisticType=product&update=1"><s:text name="i18n_More" /></a>
   							</div>
			   
		                    <div class="m_content" style="height:140px;">
			                  	<ul>
			                  		<s:iterator value="productReport" status="stat">
										<li><a title="<s:property value="productReport[#stat.index][1]" />" href="statistic/common/toView.action?viewFlag=2&entityId=<s:property value="productReport[#stat.index][0]"/>&statisticType=<s:property value="productReport[#stat.index][3]"/>" class="txt_left"><s:property value="productReport[#stat.index][1]" /></a><span class="txt_right"><s:if test="#session.locale == 'zh'"><s:date name="productReport[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="productReport[#stat.index][2]" format="yyyy-MM-dd" /></s:else></span></li>
									</s:iterator>
		                        </ul>
		                    </div>
           				</td>
           				<td width="2%">&nbsp;</td>
           				<td width="49%" valign="top">
		     	     	 	<div class="m_title">
   								<span><s:text name="i18n_AwardSta" /></span><a href="statistic/common/toList.action?statisticType=award&update=1"><s:text name="i18n_More"/></a>
   							</div>
			              	<div class="m_content" style="height:140px;">
			                  	<ul>
			                  		<s:iterator value="awardStat" status="stat">
										<li><a title="<s:property value="awardStat[#stat.index][1]" />" href="statistic/common/toView.action?viewFlag=2&entityId=<s:property value="awardStat[#stat.index][0]"/>&statisticType=<s:property value="awardStat[#stat.index][3]"/>" class="txt_left"><s:property value="awardStat[#stat.index][1]" /></a><span class="txt_right"><s:if test="#session.locale == 'zh'"><s:date name="awardStat[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="awardStat[#stat.index][2]" format="yyyy-MM-dd" /></s:else></span></li>
									</s:iterator>
		                        </ul>
		                    </div>
             			</td>
           			</tr>
        		</table>
       		</div>
		</div>
	</body>
</html>