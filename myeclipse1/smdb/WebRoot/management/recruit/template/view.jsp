<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		
	</head>
	<body>
		<div class="link_bar">
			当前位置：招聘管理&nbsp;&gt;&nbsp;模板管理&nbsp;&gt;&nbsp;详情
		</div>
		<input type = "hidden" id = "entityId" name = "entityId" value = "${entityId}"/>
		<div class="main">
			<div class="choose_bar">
				<ul>
					<li id="view_back"><input class="btn1" type="button" value="返回"></li>
					<li id="view_next"><input class="btn1" type="button" value="下条"></li>
					<li id="view_prev"><input class="btn1" type="button" value="上条"></li>
					<li id="view_del"><input class="btn1" type="button" value="删除"></li>
					<li id="view_mod"><input class="btn1" type="button" value="修改"></li>
					<li id="view_add"><input class="btn1" type="button" value="添加"></li>
				</ul>
			</div>
			
			<div class="main_content">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
								<td class="title_bar_td" width="64" align="right">模版名称：</td>
								<td class="title_bar_td" colspan="" id = "templateName"></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
								<td class="title_bar_td" width="64" align="right">模版文件：</td>
								<td class="title_bar_td" colspan="" id = "templateDownload"></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
								<td class="title_bar_td" width="64" align="right">上传时间：</td>
								<td class="title_bar_td" colspan="" id = "uploadDate"></td>
							</tr>
							<tr>
								<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
								<td class="title_bar_td" width="64" align="right">模版描述：</td>
								<td class="title_bar_td" colspan="" id = "description"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div></div>
			<script type="text/javascript">
					seajs.use('javascript/management/recruit/template/view.js', function(view) {
						$(function(){
							view.init();
						})
					});
			</script>
		</body>
	</html>