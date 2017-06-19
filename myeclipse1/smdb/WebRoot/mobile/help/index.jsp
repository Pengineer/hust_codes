<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<base href="<%=basePath%>" />
    <!-- Basic Page Needs–––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <meta charset="utf-8">
     <title>常见问题</title>
    <meta name="description" content="smdb客户端">
    <meta name="author" content="">
    <!-- Mobile Specific Metas –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <link rel="stylesheet" href="css/mobile/normalize.css">
    <link rel="stylesheet" href="css/mobile/skeleton.css">
    
    <link rel="stylesheet" href="css/mobile/help.css">
</head>
<body>
    <!-- Primary Page Layout –––––––––––––––––––––––––––––––––––––––––––––––––– -->
    <div id = "container">
        <nav>
          <span>常见问题</span>
        </nav>
        <ul id="Q-list">
          <li><a href="mobile/help/help0.jsp">切换子系统</a></li>
          <li><a href="mobile/help/help1.jsp">个人信息中心</a></li>
          <li><a href="mobile/help/help2.jsp">我要留言</a></li>
          <li><a href="mobile/help/help3.jsp">编辑留言</a></li>
          <li><a href="mobile/help/help4.jsp">通知中附件下载</a></li>
          <li><a href="mobile/help/help5.jsp">详情页面上下条翻页</a></li>
          <li><a href="mobile/help/help6.jsp">修改密码</a></li>
    <s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT, @csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 研究人员-->
          <li><a href="mobile/help/help7.jsp">修改头像</a></li>
          <li><a href="mobile/help/help8.jsp">修改个人信息</a></li>
    </s:if>
          <li><a href="mobile/help/help9.jsp">切换账号</a></li>
          <li><a href="mobile/help/help10.jsp">退出当前账号</a></li>
          <li><a href="mobile/help/help11.jsp">用户反馈</a></li>
        </ul>
    </div>
    <!-- End Document –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>

</html>
