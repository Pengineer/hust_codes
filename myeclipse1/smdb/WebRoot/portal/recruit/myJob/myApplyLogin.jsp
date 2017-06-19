<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我的申请</title>
    <s:include value="/innerBase.jsp" />
    <link rel="stylesheet" type="text/css" href="css/recruit.css">
	<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/lib/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
    <script type="text/javascript">
		$(document).ready(function() {
			$.validator.addMethod("idCard", function(value, element) {
				var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
				return this.optional(element) || reg.test(value);
			},"身份证输入不合法");
			$.validator.addMethod("emailAddress", function(value, element) {
				var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				return this.optional(element) || reg.test(value);
			},"邮箱输入不合法");
			$("#Applicant_form").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"idCardNumber":{
						required:true,
						idCard: true	
					},
					"email":{
						required:true,
						emailAddress: true	
					}
				},
				messages: {
					idCardNumber: {
						required: "身份证号不能为空",
						idCard: "身份证输入不合法"
					},
					email: {
						required: "邮箱不能为空",
						emailAddress: "邮箱输入不合法"
					},
				},
				errorLabelContainer: $("#recruit_form div.error"),
				errorPlacement: function(error, element) {
					error.appendTo( element.parent().parent() ); 
				}
			});
		});
    </script>
  </head>
  
  <body>
    <div class="container">
      <s:include value="../nav.jsp" />
      <div class="myJobInfo_center">
        <s:include value="../links.jsp" />
        <div class="content_right">
            <form id="Applicant_form" action="portal/recruit/searchApplicant.action" method="post">
              <div class="login_form">
                  <ul>
                    <li><span>身份证号: </span><span><input id="idCardNumber" name="idCardNumber" type="text" /></span></li>
                    <li><span>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱: </span><span><input id="email" name="email" type="text" /></span></li>
                  </ul>
                  <div class="btn_group1">
                    <input class="btn1" type="submit" value="确&nbsp;&nbsp;定" />
                    <input class="btn1" type="button" value="取&nbsp;&nbsp;消" onclick="window.location.href=basePath+'portal/recruit/searchJob.action'" />
                  </div>
                  <div id="errorInfo" class="errorInfo">
                  	<p class="font_small"><s:property value="#request.errorInfo" /><s:fielderror/></p>
                  </div>
              </div>
            </form> 
        </div>
      </div>
      <s:include value="/innerFoot.jsp" />
    </div>
</body>
</html>
