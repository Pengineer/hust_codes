<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>SMAS</title>
		<s:include value="/innerBase.jsp" />
		<link href="css/jquery/jquery-ui-1.8.5.custom.css" rel="stylesheet" type="text/css" />
		<style>
		.ui-tabs-vertical { width: 100%;}
		.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
		.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: .5em -1px .5em 0; }
		.ui-tabs-vertical .ui-tabs-nav li a { display:block;}
		.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px;}
		.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 30em;}
		.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-selected {margin: .5em -1px .5em 0; background: #fbd850;}
		.dataContent {width:161px; margin:10px auto;}
		#tabs h1 {font-size: 1.5em; text-align: center;}
		</style>
	</head>

	<body>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">在线同步数据操作</a></li>
				<li><a href="#tabs-2">数据修复同步操作</a></li>
			</ul>
			<div id="tabs-1" style="width:600px;margin:0 auto;">
				<div id="dataTabs">
					<ul>
						<li><a href="#dataTabs-1">申报数据同步</a></li>
						<li><a href="#dataTabs-2">立项数据同步</a></li>
						<li><a href="#dataTabs-3">中检数据同步</a></li>
						<li><a href="#dataTabs-4">变更数据同步</a></li>
						<li><a href="#dataTabs-5">结项数据同步</a></li>
					</ul>
					<div id="dataTabs-1">
						<h1>申报数据同步</h1>
						<div class="dataContent">
							项目类型：<s:select cssClass="select" name="projectTypeTag" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'一般项目','2':'基地项目'}" />
						</div>
						<div class="dataContent">
							是否加密：&nbsp;<input type="radio" name="isSecret" value="0" />是 &nbsp;<input type="radio" name="isSecret" value="1" />否
						</div>
						<div class="dataContent secret-type" style="display:none;">
							加密类型：<s:select cssClass="select" name="secretType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'DES','2':'AES','3':'DESede'}" />
						</div>
						<div class="btn_div">
							<input type="button" class="btn" value="建立连接">
							<%--<input type="submit" class="btn" value="确定" />&nbsp;&nbsp;
							<input type="button" class="btn" value="取消" onclick="history.back();" />
						--%></div>
					</div>
					<div id="dataTabs-2">
						<h1>立项数据同步</h1>
						<p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
					</div>
					<div id="dataTabs-3">
						<h1>中检数据同步</h1>
						<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
						<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
					</div>
					<div id="dataTabs-4">
						<h1>变更数据同步</h1>
						<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
						<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
					</div>
					<div id="dataTabs-5">
						<h1>结项数据同步</h1>
						<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
						<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
					</div>
				</div>
			</div>
			<div id="tabs-2">
				Proin elit arcu, rutrum c
			</div>
		</div>
	
	<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery-ui-1.8.5.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/sysconfig/dataManage.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>
