<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<meta charset = "utf-8">
			<title>进度条</title>
			<s:include value="/innerBase.jsp" />
			<link type="text/css" href="tool/jquery.menus/menu.css" rel="stylesheet" />
				<style>
					.progress-bar {
						float: left;
						width: 0;
						height: 100%;
						font-size: 12px;
						line-height: 20px;
						color: #fff;
						text-align: center;
						background-color: #428bca;
						-webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
						box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
						-webkit-transition: width .6s ease;
						-o-transition: width .6s ease;
						transition: width .6s ease;
					}
					.progress-bar-striped {
						background-image: -webkit-linear-gradient(bottom left, rgba(255,255,255,.15) 25%, transparent 25%, transparent 50%, rgba(255,255,255,.15) 50%, rgba(255,255,255,.15) 75%, transparent 75%, transparent);
						background-image: -o-linear-gradient(bottom left, rgba(255,255,255,.15) 25%, transparent 25%, transparent 50%, rgba(255,255,255,.15) 50%, rgba(255,255,255,.15) 75%, transparent 75%, transparent);
						background-image: linear-gradient(to top right, rgba(255,255,255,.15) 25%, transparent 25%, transparent 50%, rgba(255,255,255,.15) 50%, rgba(255,255,255,.15) 75%, transparent 75%, transparent);
						-webkit-background-size: 40px 40px;
						background-size: 40px 40px;
					}
					.progress-bar-success {
						background-color: #9e8abb;
					}
					.progress {
						height: 20px;
						margin-bottom: 20px;
						overflow: hidden;
						background-color: #f5f5f5;
						border-radius: 4px;
						-webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
						box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
					}
					.container{
						max-width: 420px;
						margin: 16px auto;
						
					}
					#container{
						min-height:130px;
						max-width: 450px;
						margin: 16px auto;
					}
					.action{
						margin-top:15px;
					}
					#cancel{
						float:left;
						margin-left:16px;
					}
					#finished{
						float:right;
						margin-right:16px;
					}
					.btn1{
						margin-top:10px;
					}
					.loadings{
						position: relative;
						top: 20px;
						left: 112px;
						color:red;
					}
			</style>
		</head>
		<body>
		<div id = "container">
			 <span class = "loadings">后台程序正在初始化...请稍候<span></span></span>
			<div class="progress container" style = "opacity:0">
				
		 		<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="min-width: 1%;width:0;">
        			<span class="sr-only">已完成<span id = "counter"></span></span>
      			</div>
			</div>
				<button class = "btn1 " id = "cancel"> 取消</button>
				<button class = "btn1 disable" id = "finished"> 完成</button>			
		</div>
		<script >
		seajs.use('javascript/dataProcessing/fromMidToDB/progress.js', function(progress) {
			$(function(){
				progress.init();
			})
		});
		</script>
		</body>
	
</html>