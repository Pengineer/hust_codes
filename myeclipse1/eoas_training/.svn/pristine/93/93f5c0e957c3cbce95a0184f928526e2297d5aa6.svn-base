<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<title>部门设置</title>
	<base href="<%=basePath%>" />
	<link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
<head>
	
	
</head>
<body>

<!-- 标题显示 --> 
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="style/images/title_arrow.gif"/> 部门信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>


<!--显示表单内容-->
<div id=MainArea>

    <s:form action="departmentAction_%{id==null ? 'add' : 'modify'}">
    	<s:hidden name="id"></s:hidden>
        
        <div class="ItemBlock_Title1"></div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr><td width="100">上级部门</td>
                        <td>
                        	<s:select name="parentId" cssClass="SelectStyle"
                        		list="#departmentList" listKey="id" listValue="name"
                        		headerKey="" headerValue="请选择部门"
                        	/>
                        </td>
                    </tr>
                    <tr><td>部门名称</td>
                        <td><s:textfield name="name" cssClass="InputStyle"/> *</td>
                    </tr>
                    <tr><td>职能说明</td>
                        <td><s:textarea name="description" cssClass="TextareaStyle"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="style/images/goBack.png"/></a>
        </div>
    </s:form>
</div>

<div class="Description">
	说明：<br />
	1，上级部门的列表是有层次结构的（树形）。<br/>
	2，如果是修改：上级部门列表中不能显示当前修改的部门及其子孙部门。因为不能选择自已或自已的子部门作为上级部门。<br />
</div>

</body>
</html>
