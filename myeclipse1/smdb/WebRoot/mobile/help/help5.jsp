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
        
        <span>如何查看详情页面上下条？</span>
      </nav>
      <div class="answer">
        在基础数据库和统计分析与决策系统中，进入详情页面后，左右滑动页面，可以查看上一条和下一条的详情页面。
      </div>
    </div>
    <!-- End Document
    –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  </body>
</html>