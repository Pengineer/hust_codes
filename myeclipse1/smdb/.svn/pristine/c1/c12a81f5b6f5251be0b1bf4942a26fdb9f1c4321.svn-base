<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我的职位修改</title>
    <s:include value="/innerBase.jsp" />
	<link rel="stylesheet" type="text/css" href="css/recruit.css">
	 <link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
  </head>
  
  <body>
    <div class="container">
      <s:include value="../nav.jsp" />
      <div class="myJobInfo_center">
        <s:include value="../links.jsp" />
        <div class="content_right">
            <div class="join_content_head">
   					<p><span>当前位置：我的职位信息 > 职位列表 > 修改信息</span></p>
   					<hr/>
   			</div>
            <form name="modifyApplicant" id = "modifyApplicant" action="portal/recruit/modifyApplicant.action" method="post">
                  <input style="display:none;" name="appId" id = "appId" type="text" value="${app.id}">
                  <table border="1">
                    <tr>
                      <td colspan="3" class="form_title">我的个人信息</td>
                    </tr>
                    <tr>
                      <td width="150">姓名：</td>
                      <td width="150"><input name="name" type="text" value="${app.name}"></td>
                      <td id="photo" rowspan="4" style="width:120px; padding-top:5px;">
                      <img width ="100%" src="/smdb/${app.photoFile}">
                      </td>
                    </tr>
                    <tr>
                      <td width="150">性别：</td>
                      <td width="150">
                      	<select id="gender" name="gender">
                      		<option selected="selected">${app.gender}<option>
                      	</select>
                      </td>
                    <tr>
                      <td width="150">民族：</td>
                      <td width="150">
                      	<select id="ethnic" name="ethnic">
                      		<option selected="selected">${app.ethnic}<option>
                      	</select>
                      </td>
                    </tr>
                    <tr>
                      <td width="150">籍贯：</td>
                      <td width="150"><input name="birthplace" type="text" value="${app.birthplace}"></td>
                    </tr> 
                    <tr>
                      <td width="150">生日：</td>
                      <td colspan = "4">
                      	<input type="text" id="birthday" name="birthday" readonly="readonly" value="<fmt:formatDate value="${app.birthday}" pattern="yyyy-MM-dd"/>">
                      </td>
                    </tr>
                    <tr>
                      <td width="150">政治面貌：</td>
                      <td width="150">
                      	<select id="membership" name="membership">
                      		<option selected="selected">${app.membership}<option>
                      	</select>
                      </td>
                    </tr>
                    <tr>
                      <td width="150">电话：</td>
                      <td colspan = "2"><input name="mobilePhone" type="text" value="${app.mobilePhone}"></td>
                    </tr>
                    <tr>
                      <td width="150">身份证号：</td>
                      <td colspan = "2"><input name="idCardNumber" type="text" value="${app.idCardNumber}"></td>
                    </tr>
                    <tr>
                      <td width="150">邮箱：</td>
                      <td colspan = "2"><input name="email" type="text" value="${app.email}"></td>
                    </tr>
                    <tr>
                      <td width="150">qq：</td>
                      <td colspan = "2"><input name="qq" type="text" value="${app.qq}"></td>
                    </tr>
                    <tr>
                      <td width="150">地址：</td>
                      <td colspan = "2"><input name="address" type="text" value="${app.address}"></td>
                    </tr>
                    <tr>
                      <td width="150">专业：</td>
                      <td colspan = "2"><input name="major" type="text" value="${app.major}"></td>
                    </tr>
                    <tr>
                      <td width="150">学历：</td>
                      <td width="150">
                      	<select id="education" name="education">
                      		<option selected="selected">${app.education}</option>
                      	</select>
                      </td>
                    </tr>
                     <tr>
                      <td width="150">照片：</td>
                      <td colspan = "2" style = "text-align:left"><input name="photo_modify" type="file" id = "photo_${app.id}"></td>
                    </tr>
                     <tr>
                      <td width="150">简历：</td>
                      <td colspan = "2" style = "text-align:left"><input name="resume_modify" type="file" id = "file_${app.id}"></td>
                    </tr>
                    <tr>
                      <td colspan="4" style="text-align:center;">
                      	<input type="submit" value="提&nbsp;&nbsp;交" />
                      	<input type="button" value="取&nbsp;&nbsp;消" onclick="window.history.go(-1);">
                      </td>
                    </tr>
                  </table>
            </form>
            </div>
        </div>
      </div>
      <s:include value="/innerFoot.jsp" />
   
    <script>
    	seajs.use("javascript/portal/recruit/modify",function(modify){
    		modify.init();
    	})
    </script>
  </body>
</html>
