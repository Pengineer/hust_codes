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
        
        <span>如何编辑留言？</span>
      </nav>
      
      <div class="answer"> 点击列表项显示弹出窗，在弹出窗显示的条目中选择其中的一个操作，对留言进行增删改查。</div>
    </div>
    <!-- End Document
    –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  </body>
</html>