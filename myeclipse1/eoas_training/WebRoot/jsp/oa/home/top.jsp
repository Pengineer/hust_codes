<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title>Top</title>
	<s:include value="/jsp/innerBase.jsp" />
</head>
<body>
	<div class="g-header">
		<div class="g-logo"><span style="color:#fff;">E</span>OA<span style="color:#fff;">S</span></div>
<%-- 		<div class="g-navLeft">
			<ul>
				<li>
					<a class="g-navLink" target="desktop" href="javascript:void(0)">
						<button type="button" class="btn btn-sm btn-info" >
							<span class="glyphicon glyphicon-envelope"></span>
							邮件
						</button>
						<span class="badge u-bg-success">6</span>
					</a>
				</li>
				<li>
					<a class="g-navLink" target="desktop" href="javascript:void(0)">
						<button type="button" class="btn btn-sm btn-info" >
							<span class="glyphicon glyphicon-list-alt"></span>
							个人代办
						</button>
						<span class="badge u-bg-important">6</span>
					</a>
				</li>
			</ul>
		</div> --%>
		<div class="g-navRight" style = "color: #FFF;">
				<s:property value="#session.visitor.account.name" />&nbsp;&nbsp;&nbsp;<a href="account/logout.action?label=1" target="_top"><p style = "color: #FFF;">退出</p></a>

			
<%-- 			<ul>
				<li><a href="javascript:void(0)"><span class="glyphicon glyphicon-user"></span>&nbsp;admin&nbsp;</a></li>
				<li><a href="javascript:void(0)"><span class="glyphicon glyphicon-cog"></span>&nbsp;设置&nbsp;</a></li>
				<li><a href="jsp/oa/home/logout.jsp"><span class="glyphicon glyphicon-off"></span>&nbsp;注销&nbsp;</a></li>
			</ul> --%>
			
		</div>
	</div>
	<%--<div id="Head1">
		<div id="Logo">
			<a id="msgLink" href="javascript:void(0)"></a>
            <font color="#0000CC" style="color:#F1F9FE; font-size:28px; font-family:Arial Black, Arial">EOAS</font> 
			<!--<img border="0" src="style/blue/images/logo.png" />-->
        </div>
		
		<div id="Head1Right">
			<div id="Head1Right_UserName">
                <img border="0" width="13" height="14" src="style/images/top/user.gif" /> 您好，<b>${person.realName }</b>
			</div>
			<div id="Head1Right_UserDept"></div>
			<div id="Head1Right_UserSetup">
            	<a href="javascript:void(0)">
					<img border="0" width="13" height="14" src="style/images/top/user_setup.gif" /> 个人设置
				</a>
			</div>
			<div id="Head1Right_Time"></div>
		</div>
		
        <div id="Head1Right_SystemButton">
            <a target="_parent" href="jsp/oa/home/logout.jsp">
				<img width="78" height="20" alt="退出系统" src="style/blue/images/top/logout.gif" />
			</a>
        </div>
		
        <div id="Head1Right_Button">
            <a target="desktop" href="/desktop?method=show">
				<img width="65" height="20" alt="显示桌面" src="style/blue/images/top/desktop.gif" />
			</a>
        </div>
	</div>
    
    <div id="Head2">
        <div id="Head2_Awoke">
            <ul id="AwokeNum">
                <li class="Line"></li>
                <li><a target="desktop" href="javascript:void(0)">
						<img border="0" width="16" height="11" src="style/images/top/mail.gif" /> 邮件
						<span id="mail"></span>
					</a>
				</li>
                <li class="Line"></li>
				  <!-- 是否有待审批文档的提示1，数量 -->
                <li><a href="Flow_Formflow/myTaskList.html" target="desktop">
                		<img border="0" width="12" height="14" src="style/images/top/wait.gif" /> 
                		待办事项（<span id="wait" class="taskListSize">1</span>）
                	</a>
                </li>
				  
                <!-- 是否有待审批文档的提示2，提示审批 -->
                <li id="messageArea">您有 1 个待审批文档，请及时审批！★★★★★</li>
            </ul>
        </div>
        
		<div id="Head2_FunctionList">
			<marquee style="WIDTH: 100%;" onMouseOver="this.stop()" onMouseOut="this.start()" 
				scrollamount=1 scrolldelay=30 direction=left>
				<b>这是滚动的消息</b>
			</marquee>
		</div>
	</div>

--%></body>
</html>
