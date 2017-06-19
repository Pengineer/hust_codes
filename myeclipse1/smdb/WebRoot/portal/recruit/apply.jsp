<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>职位申请</title>
    <s:include value="/innerBase.jsp" />
    <link rel="stylesheet" type="text/css" href="css/recruit.css">
    <link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
  </head>
  
  <body>
    <div class="container">
      <s:include value="nav.jsp" />
      <div class="myJobInfo_center">
        <s:include value="links.jsp" />
        <div class="content_right">
            <form id="recruit_form" name="apply" action="portal/recruit/apply.action" method="post">
               <div id="recruit_apply_login" class="login_form">
	                <ul>
	                  <li style="display:none;"><input id="jobId" name="jobId" type="text" value="${jobId}"/></li>
	                  <li><span class="emphasize">*</span><span> 身份证号: </span><span><input id = "idCardNumber" name="idCardNumber" type="text" /></span><span></span></li>
	                  <li><span class="emphasize">*</span><span> 邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱: </span><span><input  id = "email" name="email" type="text" /></span><span></span></li>
	                </ul>
	                <div class="btn_group1">
	                 <input class="nextBtn btn1" type="button" value="下一步" />
	                 <input class="btn1" type="button" value="取&nbsp;&nbsp;消" onclick="window.location.href='portal/recruit/searchJob.action'" />
	               </div>
	               <div class="errorInfo">
	               	<p class="font_small"></p>
	               </div>
               </div>
               <div id="recruit_apply_selfInfo" class="recruit_input_area_self" style="display:none;">
                 <ul>
                   <li><span class="emphasize">*&nbsp;</span><span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</span><span><input id="name" name="name" type="text"></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</span><span><select id="gender" name="gender"><option id="genderData"></option></select></span><span></span></li>
                   <li><span class="emphasize">&nbsp;</span><span>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</span><span><select id="ethnic" name="ethnic"><option id="ethnicData"></option></select></span><span></span></li>
                   <li><span class="emphasize">&nbsp;</span><span>籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯:</span><span><input id="birthplace" name="birthplace" type="text"></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>出生日期:</span><span>
                   <input id="birthday" name="birthday" type="text" readonly="readonly" />
                   </span><span></span></li>
                   <li><span class="emphasize">&nbsp;</span><span>政治面貌:</span><span><select id="membership" name="membership"><option id="membershipData"></option></select></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>移动电话:</span><span><input id="mobilePhone" name="mobilePhone" type="text"></span><span></span></li>
                   <li><span class="emphasize">&nbsp;</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;q&nbsp;q:</span><span><input id="qq" name="qq" type="text"></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</span><span><input id="address" name="address" type="text"></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>所学专业:</span><span><input id="major" name="major" type="text"></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>最后学历:</span><span><select id="education" name="education"><option id="educationData"></option></select></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>上传照片:</span><span><input id="photo_applicant_add" type="file" ></span><span></span></li>
                   <li><span class="emphasize">*&nbsp;</span><span>上传简历:</span><span><input id="file_applicant_add" type="file" ></span><span></span></li>
                 </ul>
                <div class="recruit_btn_box_self">
                    <input class="returnSec btn1" type="button" value="上一步" />
	                <input class="btn1" type="submit" value="完&nbsp;&nbsp;成" />
	            </div>
               </div>
            </form>
        </div>
      </div>
      <s:include value="/innerFoot.jsp" />
    </div>
    
    <script type="text/javascript">
		seajs.use("javascript/portal/recruit/add.js", function(add) {
			add.init();
		});
	</script>
    </body>
</html>
