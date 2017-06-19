<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看职位</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar" id="view_person_teacher">
			当前位置：招聘管理&nbsp;&gt;&nbsp;职位管理&nbsp;&gt;&nbsp;查看
		</div>

		<div class="main">
			<s:hidden id="entityId" name="entityId" />
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</ul>
				</div>
				<div class="main_content" style="display:none;" id="tabcontent">
					<div id="name_gender" class="title_bar">
						<textarea class="view_template" style="display:none;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="60" align="right" >名称：</td>
									<td class="title_bar_td" id="name" >${name}</td>
								</tr>
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="60" align="right">人数：</td>
									<td class="title_bar_td">${number}</td>
								</tr>
							</table>
						</textarea>
					</div>
				</div>
				
				<div class="main_content">
					<div class="p_box_t">
						<div class="p_box_t_t">基本信息</div>
					</div>
					<div id="basic">
						<textarea class="view_template" style="display:none;">
						<div class="p_box_body">
							<div class="p_box_body_t">
								<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
									<tr class="table_tr7">
										<td class="key">学位要求：</td>
										<td class="value">${degree}</td>
										<td class="key">年龄要求：</td>
										<td class="value">${age}</td>
									</tr>
									<tr class="table_tr7">
										<td class="key" width="100">发布时间：</td>
										<td id="publishDate_format" class="value">${publishDate}</td>
										<td class="key" width="100">截止时间：</td>
										<td id="endDate_format" class="value">${endDate}</td>
									</tr>
									<tr class="table_tr7">
										<td class="key">模板文件：</td>
										<td class="value" colspan="3">
											{for item in templateList}
											{if item.fileSize == null}<a href = 'javascript:void(0)' title = '文件不存在'>${item.name}（文件不存在！）</a>
											{else}<a href="management/recruit/job/download.action?entityId=${item.id}">${item.name}（${item.fileSize }）&nbsp;</a>
											{/if}
											{forelse}
												<span>暂无选择的模板</span>
											{/for}
										</td>
									</tr>
									<tr class="table_tr7">
										<td class="key">详细要求：</td>
										<td class="value" colspan="3">
											<s:textarea id="requirement" name="requirement" rows="20" cols="60" readonly="true" cssClass="textarea_css"  value="${requirement}" />
										</td>
									</tr>
								</table>
							</div>
						</div>
						</textarea>
					</div>
				</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/management/recruit/job/view.js', function(view) {
				$(function(){
					view.init();
				})
			});
		</script>
	</body>
</html>
