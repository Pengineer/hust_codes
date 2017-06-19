<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
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
				当前位置：社科信息发布&nbsp;&gt;&nbsp;通知&nbsp;&gt;&nbsp;查看
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
					<s:if test="viewFlag!=2">
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					</s:if>
					<sec:authorize ifAllGranted="ROLE_INFORMATION_NOTICE_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_INFORMATION_NOTICE_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_INFORMATION_NOTICE_ADD">
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</sec:authorize>
					</ul>
				</div>
		
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<div class="news_notice_title">${notice.title}</div>
						<div class="news_notice_bar">
							<span>通知类型：{if noticeType == null}未知类型{else}${noticeType}{/if}</span>
						<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR) == 0">
							<span>发布账号：${accountName}</span>
							<span>发布者：${notice.accountBelong}</span>
						</s:if>
						<s:else>
							<span>发布者：社科司</span>
						</s:else>
							<span>发布时间：${notice.createDate}</span>
							<span>查看数量：${notice.viewCount}</span>
							<span><s:text name="通知来源" />：{if notice.source == null}未知来源{else}${notice.source}{/if}</span>
							<span>有效期：{if notice.validDays == 0}永久有效{else}${notice.validDays}天{/if}</span>
							{if notice.isOpen == 0}<span class="text_red">内网通知</span>{/if}
						</div>
						<div class="news_notice_txt"><pre>${notice.content}</pre></div>
						{if notice.attachmentName != null}
						<div class="news_notice_attach">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="70" valign="top" align="right">附件：</td>
									<td>
										<ul>
											{for item in notice.attachmentName}
											<li id="${item_index}"><a href="javascript:void(0);" class="attach" id="${notice.id}">${item}</a>
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
				seajs.use('javascript/system/notice/inner/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>

</html>