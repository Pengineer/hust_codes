<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<base href="<%=basePath%>" />
    <!-- Basic Page Needs
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <meta charset="utf-8">
    <title>问题详情</title>
    <meta name="description" content="smdb客户端">
    <meta name="author" content="">
    <!-- Mobile Specific Metas
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <link rel="stylesheet" href="css/mobile/normalize.css">
    <link rel="stylesheet" href="css/mobile/skeleton.css">
    
    <link rel="stylesheet" href="css/mobile/help.css">
</head>
<body>
 <body>
    <!-- Primary Page Layout
    –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <div id = "container">
      <nav>
        
        <span>如何修改密码？</span>
      </nav>
      <div class="answer">
        登录->点击标题栏右上角的人员图标进入个人信息中心->设置->修改密码->进入修改密码页面，设置新密码，点击确定即可。
      </div>
    </div>
    <!-- End Document
    –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  </body>
</html>