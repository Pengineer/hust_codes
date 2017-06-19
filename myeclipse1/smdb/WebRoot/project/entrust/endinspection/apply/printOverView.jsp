<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>委托应急课题</title>
			<s:include value="/innerBase.jsp" />
			<style type="text/css">
				@media print{div .content {page-break-after:always;}}
				th, td {font-size: 13px;}
			</style>
		</head>

		<body>
		
			<div class="main_content">
				<s:iterator value="#request.dataList" status="stat">
					<s:if test="#request.dataList[#stat.index].size() != 0">
						<div class="content" style="padding-top:15px;margin-left:5px;margin-right:5px;">
							<div style="text-align:center;font-size:20px;font-weight:bold;margin-bottom:10px;">教育部人文社会科学研究一般项目结项情况一览表</div>
							<table width="100%" cellspacing="0" cellpadding="2" border="0" bordercolor="#000" style="border-collapse:collapse;">
								<tr>
									<td style="width:50%;" align="left">
										<s:if test="#request.printType != 1">
											<span style="font-weight:bold;">学校名称：</span><s:property value="#request.dataList[#stat.index][0][1]"/>&nbsp;&nbsp;
										</s:if>
										<span style="font-weight:bold;">项目数量：</span><s:property value="#request.dataList[#stat.index].size()"/>
									</td>
									<td align="right"><span style="font-weight:bold;">审核时间：</span><s:date name="startDate" format="yyyy年MM月dd日"/>至<s:date name="endDate" format="yyyy年MM月dd日"/></td>
								</tr>
							</table>
							<table width="100%" cellspacing="0" cellpadding="2" border="1" bordercolor="#000" style="border-collapse:collapse;">
					  			<tr>
									<th>序号</th>
									<th>项目名称</th>
									<th>项目负责人</th>
									<s:if test="#request.printType == 1">
									<th>依托高校</th>
									</s:if>
					  				<th>项目类别</th>
					  				<th>项目批准号</th>
					  				<s:if test="#request.printType != 2">
					  				<th>批准经费（万元）</th>
					  				<th>成果形式</th>
					  				<th>是否申请优秀成果</th>
					  				<th>CSSCI等论文数量</th>
					  				<th>结项方式</th>
					  				</s:if>
					  				<th>是否同意</th>
					  				<th>结项时间</th>
					  				<s:if test="#request.printType == 1">
									<th>结项证书编号</th>
									</s:if>
					  				<th>备注</th>
					  			</tr>
							
								<s:iterator value="#request.dataList[#stat.index]" status="stat2">
									<tr>
										<td style="width:35px; text-align:center;"><s:property value="#stat2.index+1"/></td>
										<td><s:property value="#request.dataList[#stat.index][#stat2.index][3]"/></td>
										<td style="width:55px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][2]"/></td>
										<s:if test="#request.printType == 1">
											<td style="width:55px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][1]"/></td>
										</s:if>
										<s:if test="#request.printType != 2">
											<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][4]"/></td>
											<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][5]"/></td>
											<td style="width:60px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][6]"/></td>
											<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][7]"/></td>
											<td style="width:60px; text-align:center;">
												<s:if test="#request.dataList[#stat.index][#stat2.index][8] == 1">是</s:if>
												<s:else>否</s:else>
											</td>
											<td style="width:60px; text-align:center;">
												<s:if test="#request.dataList[#stat.index][#stat2.index][13] != 0">
													<s:property value="#request.dataList[#stat.index][#stat2.index][13]"/>
												</s:if>
											</td>
											<td style="width:60px; text-align:center;">
												<s:if test="#request.dataList[#stat.index][#stat2.index][9] == 2">免于鉴定</s:if>
												<s:else>鉴定结项</s:else>
											</td>
											<td style="width:70px; text-align:center;">
												<s:if test="#request.dataList[#stat.index][#stat2.index][10] == 1">不同意结项</s:if>
												<s:elseif test="#request.dataList[#stat.index][#stat2.index][10] == 2">同意结项</s:elseif>
											</td>
											<td style="width:60px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][11]"/></td>
											<s:if test="#request.printType == 1">
												<td style="width:55px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][15]"/></td>
											</s:if>
											<td style="width:80px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][12]"/></td>
										</s:if>
										<s:else>
											<td style="width:150px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][4]"/></td>
											<td style="width:150px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][5]"/></td>
											<td style="width:150px; text-align:center;">
												<s:if test="#request.dataList[#stat.index][#stat2.index][10] == 1">不同意结项</s:if>
												<s:elseif test="#request.dataList[#stat.index][#stat2.index][10] == 2">同意结项</s:elseif>
											</td>
											<td style="width:150px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][11]"/></td>
											<td style="width:150px; text-align:center;"><s:property value="#request.dataList[#stat.index][#stat2.index][12]"/></td>
										</s:else>
									</tr>
								</s:iterator>
							</table>
						</div>
					</s:if>
		  		</s:iterator>
	  		</div>
		
		</body>
	
</html>