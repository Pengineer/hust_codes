<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看我的职位信息</title>
    <s:include value="/innerBase.jsp" />
    <link rel="stylesheet" type="text/css" href="tool/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="tool/datatable/css/jquery.dataTables.min.css">
	<link rel="stylesheet" type="text/css" href="tool/datatable/css/dataTables.bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/recruit.css">
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
  </head>
  
  <body>
    <div class="container">
      <s:include value="../nav.jsp" />
      <div class="myJobInfo_center">
        <s:include value="../links.jsp" />
        <div class="content_right">
            <div class="join_content_head">
   					<p><span>当前位置：我的职位信息 > 职位列表 > 职位： <span class="emphasize">${jobName}</span></p>
   					<hr/>
   					<c:if test="${app.status==0}">  
						<input type="button" value=" 修&nbsp;改 " style="position:absolute;margin:-30px 0 0 680px" onclick="window.location.href=basePath+'portal/recruit/toModifyApplicant.action?appId=${app.id}'"/>  
					</c:if>
   			</div>
   			<div id="self_message" class="message_box">
   			<input id = "appId" name = "appId" value = 'portal/recruit/fileDownload.action?appId=${app.id}' type = "hidden">
   			<input id="appFile" type="hidden" value="${app.file}" />
   			<input id="fileSize" type="hidden" value="${fileSize}">
            <form id="recruit_login" name="login" action="login" method="post">
                  <table border="1">
                    <tr>
                      <td colspan="3" class="form_title">我的个人信息</td>
                    </tr>
                    <tr>
                      <td width="150">姓名：</td>
                      <td width="">${app.name}</td>
                      <td id="photo" rowspan="4" style="width:120px; padding-top:5px;">
                      <img  width = "100%" src = "${app.photoFile }">
						</td>
                    </tr>
                    <tr>
                      <td width="150">性别：</td>
                      <td width="150">${app.gender}</td>
                    </tr>
                    <tr>
                      <td width="150">民族：</td>
                      <td width="150">${app.ethnic}</td>
                    </tr>
                    <tr>
                      <td width="150">籍贯：</td>
                      <td width="150">${app.birthplace}</td>
                    </tr>
                    <tr>
                      <td width="150">出生日期：</td>
                      <td colspan = "2"><fmt:formatDate value="${app.birthday}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                      <td width="150">政治面貌：</td>
                      <td colspan = "2">${app.membership}</td>
                    </tr>
                    <tr>
                      <td width="150">电话：</td>
                      <td colspan = "2">${app.mobilePhone}</td>
                    </tr>
                    <tr>
                      <td width="150">身份证号：</td>
                      <td colspan = "2">${app.idCardNumber}</td>
                    </tr>
                    <tr>
                      <td width="150">邮箱：</td>
                      <td colspan = "2">${app.email}</td>
                    </tr>
                    <tr>
                      <td width="150">qq：</td>
                      <td colspan = "2">${app.qq}</td>
                    </tr>
                    <tr>
                      <td width="150">地址：</td>
                      <td colspan = "2">${app.address}</td>
                    </tr>
                    <tr>
                      <td width="150">专业：</td>
                      <td colspan = "2">${app.major}</td>
                    </tr>
                     <tr>
                      <td width="150">学历：</td>
                      <td colspan = "2">${app.education}</td>
                    </tr>
                    <tr>
                      <td width="150">简历：</td>
                      <td colspan = "2" id = "resume"></td>
                    </tr>
                  </table>
            </form>
            </div>
        </div>
      </div>
      <s:include value="/innerFoot.jsp" />
    </div>
    <script>
    document.onready = function(){
    	  var link = document.getElementById("appId").value;
    	  var appFile = document.getElementById("appFile").value;
    	  var fileSize = document.getElementById("fileSize").value;
    	    if(appFile){
    	    	if(fileSize){
    	    		document.getElementById("resume").innerHTML = "<a href ="+ link + ">下载附件("+fileSize+")</a>";
    	    	}else {
    	    		document.getElementById("resume").innerHTML = "<a href = 'javascript:void(0)'>下载附件（文件不存在）" +　"</a>";
    	    	}
    	    } else {
    	    	document.getElementById("resume").innerHTML = "<a href = 'javascript:void(0)'>附件不存在！</a>";
    	    }
    }
    </script>
  </body>
</html>
