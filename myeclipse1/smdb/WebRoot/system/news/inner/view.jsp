<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布&nbsp;&gt;&nbsp;新闻&nbsp;&gt;&nbsp;查看
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				
				<div class="choose_bar">
					<ul>
						<s:if test="viewFlag!=2">
							<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
							<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
							<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
						</s:if>
						<s:else>
							<li id="view_back2">
								<s:if test='#session.serverPath=="/server/basis/right.jsp"'>
									<a href="login/basisRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:if>
								<s:elseif test='#session.serverPath=="/server/expert/right.jsp"'>
									<a href="login/expertRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/project/right.jsp"'>
									<a href="login/projectRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/statistic/right.jsp"'>
									<a href="login/statisticRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/ucenter/right.jsp"'>
									<a href="login/ucenterRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/scenter/right.jsp"'>
									<a href="login/scenterRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/award/right.jsp"'>
									<a href="login/awardRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/fee/right.jsp"'>
									<a href="login/feeRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
								<s:elseif test='#session.serverPath=="/server/management/right.jsp"'>
									<a href="login/managementRight.action" target="main"><input class="btn1" type="button" value="返回" /></a>
								</s:elseif>
							</li>
						</s:else>
					<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_INFORMATION_NEWS_ADD">
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</sec:authorize>
					</ul>
				</div>
		
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<div class="news_notice_title">${news.title}</div>
						<div class="news_notice_bar">
							<span>新闻类型：{if newsType == null}未知类型{else}${newsType}{/if}</span>
						<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR) == 0">
							<span>发布账号：${accountName}</span>
							<span>发布者：${news.accountBelong}</span>
						</s:if>
						<s:else>
							<span>发布者：社科司</span>
						</s:else>
							<span>发布时间：${news.createDate}</span>
							<span>查看数量：${news.viewCount}</span>
							<span>新闻来源：{if news.source == null}未知来源{else}${news.source}{/if}</span>
							{if news.isOpen == 0}<span class="text_red">内网新闻</span>{/if}
						</div>
						<div class="news_notice_txt">${news.content}</div>
						{if news.attachmentName != null}
						<div class="news_notice_attach">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="70" valign="top" align="right">附件：</td>
									<td>
										<ul>
											{for item in news.attachmentName}
											<li id="${item_index}"><a href="javascript:void(0);" class="attach" id="${news.id}">${item}</a>
											&nbsp;<span style="color: #A0A0A0;">(
											{if attachmentSizeList[item_index] != null}
												${attachmentSizeList[item_index]}
											{else}附件不存在
											{/if}
											)</span>
											</li>
											{/for}
										</ul>
									</td>
								</tr>
							</table>
						</div>
						{/if}
					</div>
				</textarea>
					
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/news/inner/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>