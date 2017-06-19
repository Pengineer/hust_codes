<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我的职位列表</title>
    <s:include value="/innerBase.jsp" />
    <link rel="stylesheet" type="text/css" href="css/recruit.css">
  </head>
  
  <body>
    <div class="container">
      <s:include value="../nav.jsp" /> 
      <div class="myJobInfo_center">
        <s:include value="../links.jsp" />
        <div style="margin:5px 0 10px 250px;border:1px solid #f4f4f4; width:740px; height:497px;background:#f4f4f4;">
       		<div class="join_content_head">
				<p><span>当前位置：我的职位信息 > 职位列表</span></p>
				<hr/>
			</div>
			<table class="job_table table_td_padding" cellspacing="0" cellpadding="0" border="0">
				<thead>
					<tr class="table_title_tr">
						<td width="100">职位</td>
						<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
						<td width="100">姓名</td>
						<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
						<td width="100">申请时间</td>
						<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
						<td width="100">审核状态</td>
						<td width="2"><img height="25" width="2" src="image/table_line.gif" ></td>
						<td width="50">操作</td>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="applicantList" status="stat">
						<tr>
							<td width="120"><s:property value="applicantList[#stat.index][0]" /></td>
							<td></td>
							<td width="120"><s:property value="applicantList[#stat.index][2]" /></td>
							<td></td>
							<td width="120"><s:date name="applicantList[#stat.index][3]" format="yyyy-MM-dd"/></td>
							<td></td>
							<td width="120" style="color:#f00;">
								<s:if test="applicantList[#stat.index][4]==0">已申请</s:if>
								<s:elseif test="applicantList[#stat.index][4]==1">审核通过</s:elseif>
								<s:elseif test="applicantList[#stat.index][4]==2">审核不通过</s:elseif>
								<s:else>审核不通过</s:else>
							</td>
							<td></td>
							<td width="120">
								<a href="portal/recruit/viewApplicant.action?appId=<s:property value="applicantList[#stat.index][1]" />">查看详情</a>
							</td>
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

