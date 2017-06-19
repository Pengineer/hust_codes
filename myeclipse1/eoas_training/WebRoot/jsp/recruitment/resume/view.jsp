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

	</head>
	<body>	
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div class="content">
				<div class="kit" style="width:940px;margin-left:0px;padding-left:0px；float:right">
					<p style="font-size:14px;color:#000;font-weight:bold;text-align:left;">简历类型：社招简历名称:hello</p>
				</div>
				<div class="sub_bar">
				<td width="30"><a href="resume/toModifyResume.action?resumeId=${resume.id}">修改</a></td>
				</div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="个人基本信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="姓名" />：</td>
						<td class="txtpadding" width="179"><s:property value="resume.name" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="性别" />：</td>
						<td class="txtpadding" width="179"><s:property value="resume.gender" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="出生日期" />：</td>
						<td class="txtpadding" width="179"><s:property value="resume.birthday" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="证件类型" />：</td>
						<td class="txtpadding"><s:property value="resume.idcardType" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="证件号码" />：</td>
						<td class="txtpadding" width="179"><s:property value="resume.idcardNumber" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="手机号码" />：</td>
						<td class="txtpadding" width="179"><s:property value="resume.mobilephone" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="电子邮件" />：</td>
						<td class="txtpadding" width="179"><s:property value="account.email" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="籍贯" />：</td>
						<td class="txtpadding"><s:property value="resume.hometown" /></td>
					</tr>
				</table>
					
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="教育信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="毕业学校" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="开始时间" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="结束时间" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="学历" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="学位" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="专业" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="专业简介" /></td>
					</tr>
					<s:iterator value="edus" status="stat">
					<tr>
						<td class="txtcenter"><s:property value="edus[#stat.index].school" /></td>
						<td class="txtcenter"><s:date name="edus[#stat.index].stime" format="yyyy-MM-dd"/></td>
						<td class="txtcenter"><s:date name="edus[#stat.index].etime" format="yyyy-MM-dd"/></td>
						<td class="txtcenter"><s:property value="edus[#stat.index].education" /></td>
						<td class="txtcenter"><s:property value="edus[#stat.index].degree" /></td>
						<td class="txtcenter"><s:property value="edus[#stat.index].major" /></td>
						<td class="txtcenter"><s:property value="edus[#stat.index].description" /></td>
					</tr>
					</s:iterator>
				</table>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="工作信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="工作单位" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="工作开始时间" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="工作结束时间" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="职位名称" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="职位描述" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="项目名称" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="项目开始时间" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="项目结束时间" /></td>
						<td width="12.5%" class="txtcenter" bgcolor="#b3c6d9"><s:text name="职责描述"/></td>
					</tr>
					<s:iterator value="exps" status="stat">
					<tr>
						<td class="txtcenter"><s:property value="exps[#stat.index].company" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].companyStime" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].companyEtime" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].position" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].positionDescription" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].project" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].projectStime" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].projectEtime" /></td>
						<td class="txtcenter"><s:property value="exps[#stat.index].dutyDescription" /></td>	
					</tr>
					</s:iterator>
				</table>
				<td colspan="2" rowspan="6" align="center" style="padding-top:6px;">
					<img width="${photoWidth}" height="${photoHeight}" class="imgline"
						src="<s:property value='resume.photo'/>" />
				</td>
					
				<div id="production" style="float: left;padding-left: 20px;">
					附件：<a href=<s:property value="%{resume.eleresume}" />  title="点击下载附件"><s:property value="%{resume.eleresumeName}" /></a>
				</div>
				
				<div id="production" style="float: left;padding-left: 20px;">
					附件：<a href=<s:property value="%{resume.production}" />  title="点击下载附件"><s:property value="%{resume.productionName}" /></a>
				</div>
				<div id="production" style="float: left;padding-left: 20px;">
					附件：<a href=<s:property value="%{resume.otherAttachment}" />  title="点击下载附件"><s:property value="%{resume.otherAttachmentName}" /></a>
				</div>

<%-- 						<div class="news_notice_attach">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="70" valign="top" align="right"><s:text name="i18n_Attachment" />：</td>
									<td>
										<ul>
											{for item in resume.produtionName}
											<li id="${item_index}"><a href="javascript:void(0);" class="attach" id="${resume.id}">${item}</a>
											&nbsp;<span style="color: #A0A0A0;">(
											{if productionSizeList[item_index] != null}
												${productionSizeList[item_index]}
											{else}附件不存在
											{/if}
											)</span>
											</li>
											{/for}
										</ul>
									</td>
								</tr>
							</table>
						</div> --%>
				
				
		</div>
	</body>
</html>
