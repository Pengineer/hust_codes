<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司招聘</title>   
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="css/recruitment/common.css">
		
	    <link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css"/> 
		
		
<!-- 		<link rel="stylesheet" type="text/css" href="tool/swfupload/plugins/default.css" />
		<script type="text/javascript" src="tool/swfupload/plugins/swfupload.js"></script>
		<script type="text/javascript" src="tool/swfupload/plugins/swfupload.swfobject.js"></script>
		<script type="text/javascript" src="tool/swfupload/plugins/swfupload.queue.js"></script>
		<script type="text/javascript" src="tool/swfupload/plugins/fileprogress.js"></script>
		<script type="text/javascript" src="tool/swfupload/plugins/handlers.js"></script>  -->
	</head>
	<body>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div id="wrap" class="content">
			<s:form id = "form_resume"  name = "form_resume" action = "resume/add.action"  theme="simple">
				<div class="panel panel-success">
					<div class="panel-heading">创建简历</div>
					
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<div class="row">简历类型： 
							<label> 
						        <input type="radio" name="resume.type" value="0">社会招聘 
						    </label><label> 
						        <input type="radio" name="resume.type" value="1">校园招聘 
						    </label><label> 
						        <input type="radio" name="resume.type" value="2">实习生招聘 
						    </label>
						    
						    
						</div>
						
						<tr>
							<td width="10" align="right" bgcolor="#b3c6d9"><span class="text_red">*简历名称</span>&nbsp;：</td>
							<td><input id="resume.resumeName"  name="resume.resumeName" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*姓名</span>&nbsp;：</td>
							<td><input name="resume.name" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<div class="row" >性别： 
							<label> 
						        <input type="radio" name="resume.gender" value="0">男 
						    </label>
						    <label> 
						        <input type="radio" name="resume.gender" value="1">女 
						    </label> 
						</div>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*出生日期</span>&nbsp;：</td>
							<td><input id="form_account_account_birthday" name="form_account_account_birthday" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*手机号</span>&nbsp;：</td>
							<td><input name="resume.mobilephone" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*籍贯</span>&nbsp;：</td>
							<td><input id="username" name="resume.hometown" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*证件类型</span>&nbsp;：</td>
							<td><input id="username" name="resume.idcardType" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*证件号码</span>&nbsp;：</td>
							<td><input id="username" name="resume.idcardNumber" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						
					<!-- 	<tr>
							<td width="100" align="right" bgcolor="#b3c6d9">上传照片：</td>
							<td>
								<div>
									<span id="spanButtonPlaceHolder"></span><input type="button" id="btnCancel" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 20px;">
									
								</div>
								<div class="fieldset flash" id="fsUploadProgress">
								</div>
								<div id="divStatus" style="display:none;">0 个文件已上传</div>
								<s:hidden id="uploadKey" name="uploadKey" />
							</td>
						</tr> -->
						
						<!-- <tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="上传照片" />：</td>
							<td><input type='file' id="imgInp" /><img id="blah" src="#" alt="resume.photo" width="100px" height="100px"/></td>
						</tr> -->						
					</table>						

					<table  id="table_education" width="100%" border="0" cellspacing="0" cellpadding="0"  class="education">
						<tr>
							<td width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>教育信息</span><span title="memberSpan1"></span></b></div>&nbsp;</td>
							<td>
								<input type="button" class="add_education" value="<s:text name="添加" />" />&nbsp;
							</td>
						</tr>
						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="教育时间段" />：</td>
							<td>
								<input type="text" name="edustime" class="datepickCls" 
										value="<s:date name="edus[0].stime" format="yyyy-MM-dd" />" />至
								<input type="text" name="eduetime" class="datepickCls"
										value="<s:date name="edus[0].etime" format="yyyy-MM-dd" />" />
							</td>
						</tr>
						
															
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="学位" />：</td>
							<td><s:textfield name="edus[0].degree" cssClass="inputcss" placeholder="输入文本" theme="simple"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="学校" />：</td>
							<td><input name="edus[0].school" class="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="专业" />：</td>
							<td><input name="edus[0].specialty" class="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="专业描述" />：</td>
							<td><input name="edus[0].description" class="inputcss" placeholder="输入文本"/></td>
						</tr>
					</table>
					
					<table  id="table_experience" width="100%" border="0" cellspacing="0" cellpadding="0" class="experience">
						<tr>
							<td width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>工作经历</span><span title="memberSpan2"></span></b></div>&nbsp;</td>
							<td>
								<input type="button" class="add_experience" value="<s:text name="添加" />" />&nbsp;
							</td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="工作单位" />：</td>
							<td><input id="username" name="experience.company" class="inputcss" placeholder="输入文本"/></td>
						</tr>
						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="工作起止时间" />：</td>
							<td>
								<input type="text" name="experience.companyStime" class="datepickCls"
										value="<s:date name="experience.companyStime" format="yyyy-MM-dd" />" />至
								<input type="text" name="experience.companyEtime" class="datepickCls"
										value="<s:date name="experience.companyEtime" format="yyyy-MM-dd" />" />
							</td>
						</tr>				
			
						
						
						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="工作开始时间" />：</td>
							<td><input id="form_account_account_birthday" name="experience.companyStime" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="工作结束时间" />：</td>
							<td><input id="form_account_account_birthday" name="experience.companyEtime" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="职位名称" />：</td>
							<td><input id="username" name="experience.position" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="职位描述" />：</td>
							<td><input id="username" name="experience.positionDescription" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="项目名称" />：</td>
							<td><input id="username" name="experience.project" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="项目开始时间" />：</td>
							<td><input id="username" name="experience.projectStime" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="项目结束时间" />：</td>
							<td><input id="username" name="experience.projectEtime" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="职责描述" />：</td>
							<td><input id="username" name="experience.dutyDescription" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
					</table>
						
						
					<table>
						<tr>
							<td width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>其他情况</span><span title="memberSpan2"></span></b></div>&nbsp;</td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="奖学金" />：</td>
							<td><input id="username" name="resume.scholarship" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="竞赛获奖" />：</td>
							<td><input id="username" name="resume.award" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="担任领导经历" />：</td>
							<td><input id="username" name="resume.leaderExperience" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="备注" />：</td>
							<td><input id="username" name="resume.note" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						
												
<!-- 						<tr class="table_tr2">
							<td class="table_td2"><s:text name="上传" />：</td>
							<td class="table_td3">
								<input type="file" id="file_upload" name = "file_upload"/>
							</td>
							<s:hidden name="file"/> 
							<td class="table_td4"></td>
						</tr> -->
						
						
						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="上传电子简历" />：</td>
							<td><input id="username" name="resume.eleresume" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="上传作品" />：</td>
							<td><input id="username" name="resume.production" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="其他附件" />：</td>
							<td><input id="username" name="resume.otherAttachment" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
					</table>																				
								   
					
					<div class="row">
					    <div class="center"><s:submit name="createResume" value="创建简历"/></div>
					</div>
				</div>
			</s:form>
		</div>
	</body>
	

 	<script type="text/javascript" src="javascript/recruitment/jquery/jquery-1.7.min.js"></script> 
	<script type="text/javascript" src="javascript/recruitment/jquery/jquery.validate.js"></script>
    <script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script> 
	<script type="text/javascript" src="javascript/recruitment/resume/resume_validate.js"></script>
	
	<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
	<script type="text/javascript" src="javascript/recruitment/jquery/jquery.datepick.self.js"></script>
	<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script> 
	
  	<!-- 文件上传 -->
<!--   	<script type="text/javascript" src="tool/uploadify/uploadify.swf"></script> 
  	<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.js"></script>
  	<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.min.js"></script> 
 	<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify-ext.js"></script>
	<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify-photo.js"></script> 
	<script type="text/javascript" src="tool/uploadify/js/jquery.cookie.js"></script> -->
	
	

	<!-- <script type="text/javascript" src="tool/jquery/jquery.validate.js"></script> -->
	<script type="text/javascript" src="javascript//recruitment/resume/resume.js"></script>

	<!-- <script type="text/javascript" src="javascript/recruitment/resume/resume_photo_Swfupload.js"></script>
	<script type="text/javascript">window.onload = initSwf;</script> -->
	
</html>
