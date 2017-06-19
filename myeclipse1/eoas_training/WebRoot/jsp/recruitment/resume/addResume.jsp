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
	    <link rel="stylesheet" type="text/css" href="tool/uploadify/uploadify.css"/> 
	</head>
	<body>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div id="wrap" class="content">
			<s:form id = "resume_form"  name = "resume_form" action = "resume/addResume.action" method="post" enctype="multipart/form-data" theme="simple">
				<div class="panel panel-success">
					<div class="panel-heading">创建简历</div>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<div class="row">简历类型： 
							<label>
						        <input type="radio" name="resume.type" value="0">社会招聘 
						    </label>
						    <label> 
						        <input type="radio" name="resume.type" value="1">校园招聘 
						    </label>
						    <label> 
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
							<td><input id="form_account_account_birthday" name="resume.birthday" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*手机号</span>&nbsp;：</td>
							<td><input name="resume.mobilephone" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*籍贯</span>&nbsp;：</td>
							<td><input name="resume.hometown" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*证件类型</span>&nbsp;：</td>
							<td>
							<s:select name="resume.idcardType" headerKey="-1" headerValue="请选择"
											cssClass="inputcss" list="{'身份证','军官证','护照'}" />
							</td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*证件号码</span>&nbsp;：</td>
							<td><input name="resume.idcardNumber" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
					</table>						
					<div>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="上传照片" />：</td>
							<td><input type="file" id="file_add" /></td>
						</tr>
					</div>
					<table  id="table_education" width="100%" border="0" cellspacing="0" cellpadding="0"  class="education">
						<tr>
							<td width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>教育信息</span><span title="memberSpan1"></span></b></div>&nbsp;</td>
							<td>
								<input type="button" class="add_education" value="<s:text name="添加" />" />&nbsp;
								<input type="button" class="delete_education" value="<s:text name="删除" />" />&nbsp;
							</td>
						</tr>					
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="教育时间段" />：</td>
							<td>
								<input type="text" name="edus[0].stime" class="datepickCls" 
										value="<s:date name="edus[0].stime" format="yyyy-MM-dd" />" />至
								<input type="text" name="edus[0].etime" class="datepickCls"
										value="<s:date name="edus[0].etime" format="yyyy-MM-dd" />" />
							</td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="学历" />：</td>
							<td><s:select name="edus[0].education" list="{'本科','硕士研究生','博士研究生'}" headerKey="-1" headerValue="请选择您的学历"/></td>
						</tr>						
															
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="学位" />：</td>
							<td><s:select name="edus[0].degree" list="{'学士','硕士','博士'}" headerKey="-1" headerValue="请您选择您的学位"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="学校" />：</td>
							<td><s:textfield name="edus[0].school" class="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="专业" />：</td>
							<td><s:textfield name="edus[0].major" class="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="专业描述" />：</td>
							<td><s:textfield name="edus[0].description" class="inputcss" placeholder="输入文本"/></td>
						</tr>
					</table>				
					<table  id="table_experience" width="100%" border="0" cellspacing="0" cellpadding="0" class="experience">
						<tr>
							<td width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>工作经历</span><span title="memberSpan2"></span></b></div>&nbsp;</td>
							<td>
								<input type="button" class="add_experience" value="<s:text name="添加" />" />&nbsp;
								<input type="button" class="delete_experience" value="<s:text name="删除" />" />&nbsp;
							</td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="工作单位" />：</td>
							<td><input  name="exps[0].company" class="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="工作起止时间" />：</td>
							<td>
								<input type="text" name="exps[0].companyStime" class="datepickCls"
										value="<s:date name="exps[0].companyStime" format="yyyy-MM-dd" />" />至
								<input type="text" name="exps[0].companyEtime" class="datepickCls"
										value="<s:date name="exps[0].companyEtime" format="yyyy-MM-dd" />" />
							</td>
						</tr>										
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="职位名称" />：</td>
							<td><input id="username" name="exps[0].position" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="职位描述" />：</td>
							<td><input id="username" name="exps[0].positionDescription" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="项目名称" />：</td>
							<td><input id="username" name="exps[0].project" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="项目起止时间" />：</td>
							<td>
								<input type="text" name="exps[0].projectStime" class="datepickCls"
										value="<s:date name="exps[0].projectStime" format="yyyy-MM-dd" />" />至
								<input type="text" name="exps[0].projectEtime" class="datepickCls"
										value="<s:date name="exps[0].projectEtime" format="yyyy-MM-dd" />" />
							</td>
						</tr>						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="职责描述" />：</td>
							<td><input id="username" name="exps[0].dutyDescription" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
					</table>
						
						
					<table>
						<tr>
							<td width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>其他情况</span></b></div>&nbsp;</td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="奖学金" />：</td>
							<td><input name="resume.scholarship" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="竞赛获奖" />：</td>
							<td><input name="resume.award" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="担任领导经历" />：</td>
							<td><input name="resume.leaderExperience" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="备注" />：</td>
							<td><input name="resume.note" cssClass="inputcss" placeholder="输入文本"/></td>
						</tr>

						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="上传电子简历" />：</td>
							<td><input type="file" id="eleresume_add" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="上传作品" />：</td>
							<td><input type="file" id="production_add" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*</span>&nbsp;<s:text name="其他附件" />：</td>
							<td><input type="file" id="otherAttachment_add" /></td>
						</tr>
					</table>																				
					<div class="row">
					    <div class="center"><s:submit name="createResume" value="创建简历"/></div>
					</div>
				</div>
			</s:form>
		</div>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script> 
	<script type="text/javascript" src="javascript/recruitment/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="javascript/recruitment/resume/resume_validate.js"></script>
	<script type="text/javascript" src="javascript/recruitment/jquery/jquery.datepick.self.js"></script>
	<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
	<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
  	<script type="text/javascript" src="tool/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="javascript/recruitment/resume/resume.js"></script>
	<script type="text/javascript" src="javascript/recruitment/resume/resume_uploadify.js"></script>
	</body>
</html>
