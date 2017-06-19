<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="main">
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
					</ul>
				</div>

				<div class="main_content">
					<div class="news_notice_title"><s:property value="notice.title" /></div>
					<div class="news_notice_bar">
						<s:hidden id="entityId" value="%{notice.id}" />
						<s:hidden id="update" name="update" />
						<span>通知类型：<s:property value="notice.type.name" /></span>
						<span>发布者：社科司</span>
						<span>发布时间：<s:date name="notice.createDate" format="yyyy-MM-dd HH:mm:ss" /></span>
						<span>通知来源：<s:property value="notice.source" /></span>
					</div>
					<div class="news_notice_txt">${notice.content}</div>
					<s:if test="fileIds != null">
						<div class="news_notice_attach">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="70" align="right">附件：</td>
									<td>
										<ul>
											<s:iterator value="fileIds" status="stat">
												<li id="<s:property value='#stat.index' />" class="attach"><a href="javascript:void(0);"><s:property value="fileIds[#stat.index]" /></a></li>
											</s:iterator>
										</ul>
									</td>
								</tr>
							</table>
						</div>
					</s:if>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/notice/outer/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>