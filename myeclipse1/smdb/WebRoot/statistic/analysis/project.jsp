<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<style type="text/css" media="screen">
			table td,th {text-align:center;padding:5px;}
			.title {color: #6C6C96; line-height: 120%; padding: 8px 10px;}
		</style>
		<script type="text/javascript">
			seajs.use('javascript/statistic/analysis/project.js', function(project) {
				$(function(){
					project.init();
				})
			});
		</script>
	</head>

	<body>
		<div class="link_bar">
			当前位置：关联性分析&nbsp;&gt;&nbsp;社科项目关联性分析 
		</div>
		
		<div class="main" style="width:100%">
			<ul style="margin-left:33px;">
				<li class="title">某年（例如：2012）结项人员在某年（例如：2013）的项目申请情况</li>
				<li class="config">
					<input id="firstYear" class="keyword" type="text" size="4" name="firstYear">年结项人员在
					<input id="secondYear" class="keyword" type="text" size="4" name="secondYear">年的项目申请情况
					<input id="exportApplyInEnd" class="btn1" type="button" value="导出">
				</li>
			</ul>
		</div>
	</body>
</html>