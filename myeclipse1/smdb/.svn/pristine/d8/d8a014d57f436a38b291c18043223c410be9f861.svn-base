<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>招聘</title>
    <s:include value="/innerBase.jsp" />
	<link rel="stylesheet" type="text/css" href="css/recruit.css">
  </head>
  
  <body>
    <div class="container"> 
		<s:include value="nav.jsp" />
		<div class="join_center">
			<s:include value="links.jsp" />
			<div class="job_detail" style="margin:5px 0 10px 250px;border:1px solid #f4f4f4; width:740px; height:497px;background:#f4f4f4;">
				<div class="join_content_head">
					<p><span>当前位置：招聘职位  > <a href="portal/recruit/searchJobs.action">招聘列表</a> > 职位详情 </span></p>
					<hr/>
				</div>
				<div class="job_require">
					<h2>${job.name}</h2>
					<div>
						<table>
							<tr>
								<td width="100" class="job_label">招聘人数：</td>
								<td width="100">${job.number}</td>
								<td width="100" class="job_label">学位要求：</td>
								<td width="100">${job.degree} </td>
								<td width="100" class="job_label">年龄要求：</td>
								<td width="100">${job.age}</td>
							</tr>
							<tr>
								<td width="100" class="job_label">发布时间：</td>
								<td width="100"><fmt:formatDate value="${job.publishDate}" pattern="yyyy-MM-dd" /></td>
								<td width="100" class="job_label">截止时间：</td>
								<td width="100"><fmt:formatDate value="${job.endDate}" pattern="yyyy-MM-dd" /></td>
							</tr>
							<tr height="40">
								<td class="job_label">详细要求：</td>
								<td colspan="5">${requirement}</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="job_attachment">
					附件下载：
					<c:if test="${templateList[0]==undefined||templateList[0]==null}">
						<a href="javascript:void(0);">没有可供下载的模板</a>
					</c:if>
					<c:forEach var="temp" items="${templateList}">
						<c:choose>
							<c:when test="${temp.fileSize==null}">
								<a href="javascript:void(0)">${temp.name}(文件不存在);</a>
							</c:when>
							<c:otherwise>
								<a href="portal/recruit/templateDownload.action?templateId=${temp.id}">${temp.name}(${temp.fileSize});</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
		<s:include value="/innerFoot.jsp" />
    </div>
  </body>
</html>

