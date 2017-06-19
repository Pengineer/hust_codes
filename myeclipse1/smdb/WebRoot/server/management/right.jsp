<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国高校社会科学管理数据库</title>
		<s:include value="/innerBase.jsp" />
		<!-- <script type="text/javascript">
			seajs.use('javascript/server/management/right.js', function(right) {
				right.init();
			});
		</script> -->
	</head>
	
	<body>
		<div class="link_bar">当前位置：首页</div>
       	<div class="main">
       		<div class="main_bar">
           		<div class="main_home">
           			<span class="png"><span class="color1"><s:property value="#session.loginer.passport.name" /></span>，您好！您的账号类型为：<s:property value="#session.loginer.currentTypeName" />。<br />所在单位为：<s:property value="#session.loginer.currentBelongUnitName" />。</span>
           		</div>
 			</div>
			
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
	           			<td valign="top">
	   						<div class="m_title">
	   							<span>职位申请</span>
	   							<sec:authorize ifAllGranted="ROLE_RECRUIT_VIEW">
	   								<a href="management/recruit/applicant/toList.action?listType=1&update=1">更多</a>
	   							</sec:authorize>
	   						</div>
	             			<div class="p_box_body">
	             				<ul>
		                  			<s:if test="homeApply.isEmpty()">
		                  				<li><span class="txt_left2">暂无业务需要处理</span></li>
		                  			</s:if>
		                  			<s:else>
			             				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			             					<thead>
			             						<tr class="table_tr3">
			             							<td>职位</td>
			             							<td>姓名</td>
			             							<td>申请时间</td>
			             							<td>审核状态</td>
			             						</tr>
			             					</thead>
			             					<tbody>
			             						<s:iterator value="homeApply" status="stat">
			             						<tr class="table_tr4">
													<td><s:property value="homeApply[#stat.index][2]" /></td>
													<td><s:property value="homeApply[#stat.index][1]" /></td>
													<td><s:date name="homeApply[#stat.index][3]" format="yyyy-MM-dd"/></td>
													<td>
														<s:if test="homeApply[#stat.index][4]==0">已申请</s:if>
														<s:elseif test="homeApply[#stat.index][4]==1">审核中</s:elseif>
														<s:elseif test="homeApply[#stat.index][4]==2">审核通过</s:elseif>
														<s:else>审核不通过</s:else>
													</td>
												</tr>
												</s:iterator>
             								</tbody>
			             				</table>
	             					</s:else>
		                      	</ul>
	             			</div>
	           			</td>
	           		</tr>
	        	</table>
	       	</div>
		</div>
	</body>
</html>