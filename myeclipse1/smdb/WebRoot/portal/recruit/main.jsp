<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
			<div id="joinUs_pic" style="margin:5px 0 5px 250px; width: 740px; height: 142px;">
				<img class="ico" src="image/recruit_joinUs.gif" style="width: 100%; height: 100%;">
			</div> 
			<div id="join_content" style="background:#f4f4f4; margin:5px 0 10px 250px;border:1px solid #f4f4f4; width:738px; height:350px;">
				<div class="join_content_head">
					<p><span>当前位置：招聘列表</span></p>
					<hr/>
				</div>
				<table class="job_table table_td_padding" cellspacing="0" cellpadding="0" border="0">
					<thead>
						<tr class="table_title_tr">
							<td width="100">职位</td>
							<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
							<td width="100">招聘人数</td>
							<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
							<td width="100">发布时间</td>
							<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
							<td width="100">截止时间</td>
							<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
							<td width="50">操作</td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="jobList" status="stat">
							<tr>
								<td><a href="portal/recruit/viewJob.action?jobId=<s:property value="jobList[#stat.index][0]" />"><s:property value="jobList[#stat.index][1]" /></a></td>
								<td></td>
								<td><s:property value="jobList[#stat.index][2]" /></td>
								<td></td>
								<td><s:date name="jobList[#stat.index][3]" format="yyyy-MM-dd" /></td>
								<td></td>
								<td><s:date name="jobList[#stat.index][4]" format="yyyy-MM-dd" /></td>
								<td></td>
								<td width="50"><a href="portal/recruit/toApply.action?jobId=<s:property value="jobList[#stat.index][0]" />">申请</a></td>
							</tr>
						</s:iterator>
					</tbody>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<s:include value="/innerFoot.jsp" />
    </div>
  </body>
</html>
