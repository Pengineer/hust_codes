<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>社科业务表</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科业务日程
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update"/>
				
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					<sec:authorize ifAllGranted="ROLE_BUSINESS_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_BUSINESS_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_BUSINESS_ADD">
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</sec:authorize>
					</ul>
				</div>
				
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">业务类型：</td>
									<td class="title_bar_td" width="180" id="subType">${business.subType.name}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">业务设置：</td>
									<td class="title_bar_td">{if business.status == 1}业务激活中{else}业务停止{/if}</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">业务起始时间：</td>
									<td class="title_bar_td" width="180">${business.startDate}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">业务对象起止年份：</td>
									<td class="title_bar_td">{if business.startYear != -1 && business.endYear != -1}${business.startYear}-${business.endYear}
										{elseif business.startYear == -1 && business.endYear != -1}${business.endYear}止
										{elseif business.startYear != -1 && business.endYear == -1}${business.startYear}起
										{else}任意年份业务
										{/if}
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr id="businessYear">
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">业务年份：</td>
									<td class="title_bar_td" width="180">${business.businessYear}</td>
									<td class="title_bar_td" width="30" align="right"></td>
									<td class="title_bar_td" width="120" align="right"></td>
									<td class="title_bar_td"></td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@STUDENT)">
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">申请截止时间：</td>
									<td class="title_bar_td" width="180">{if business.applicantDeadline ==null}<span style="color:#7A5892">无期限</span>
										{else}${business.applicantDeadline}{/if}
									</td>
									</s:if>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
									<td class="title_bar_td" width="30" align="right"></td>
									<td class="title_bar_td" width="120" align="right"></td>
									<td class="title_bar_td"></td>
									</s:if>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">部门截止时间：</td>
									<td class="title_bar_td">{if business.deptInstDeadline ==null}<span style="color:#7A5892">无期限</span>
										{else}${business.deptInstDeadline}{/if}
									</td>
									</s:if>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">	
								<tr>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">高校截止时间：</td>
									<td class="title_bar_td" width="180">{if business.univDeadline ==null}<span style="color:#7A5892">无期限</span>
										{else}${business.univDeadline}{/if}
									</td>
									</s:if>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
									<td class="title_bar_td" width="30" align="right"></td>
									<td class="title_bar_td" width="120" align="right"></td>
									<td class="title_bar_td"></td>
									</s:if>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 省厅或教育部 -->
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">省厅截止时间：</td>
									<td class="title_bar_td" >{if business.provDeadline ==null}<span style="color:#7A5892">无期限</span>
										{else}${business.provDeadline}{/if}
									</td>
									</s:if>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr id="reviewDeadline">
								<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)">
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right">专家评审截止时间：</td>
									<td class="title_bar_td" width="180">{if business.reviewDeadline ==null}<span style="color:#7A5892">无期限</span>
										{else}${business.reviewDeadline}{/if}
									</td>
									<td class="title_bar_td" width="30" align="right"></td>
									<td class="title_bar_td" width="120" align="right"></td>
									<td class="title_bar_td"></td>
								</s:if>
								</tr>
							</table>
						</div>
					</div>
				</textarea>
				
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/business/view.js', function(view) {
					view.init();
				});
			</script>
		</body>
</html>
