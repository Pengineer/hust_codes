<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="i18n_CSDCSMDB" /></title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/server/project/right.js', function(right) {
				right.init();
			});
		</script>
	</head>
	
	<body>
		<div class="link_bar">当前位置：首页</div>
       	<div class="main">
       		<div class="main_bar">
           		<div class="main_home">
           			<span class="png"><span class="color1"><s:property value="#session.loginer.passport.name" /></span>，您好！您的账号类型为：<s:property value="#session.loginer.currentTypeName" />。<br />所在单位为：<s:property value="#session.loginer.currentBelongUnitName" />。</span>
           		</div>
 			</div>
 			<div class="main">
 				<s:if test="#request.reviewDeadlineBeanMap != null && !#request.reviewDeadlineBeanMap.isEmpty()">
	 				<img src="image/g09.png" />&nbsp;&nbsp;
	 				<s:iterator value="#request.reviewDeadlineBeanMap.keySet()" id="key" status="stat0">
						<s:iterator value="#request.reviewDeadlineBeanMap.get(#key)" status="stat"> 
							<span style="color:#FF883B">
								<s:if test="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][1] == -1 && #request.reviewDeadlineBeanMap.get(#key)[#stat.index][2] == -1"></s:if>
								<s:elseif test="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][1] != -1 && #request.reviewDeadlineBeanMap.get(#key)[#stat.index][2] != -1"><s:property value="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][1]"/>-<s:property value="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][2]"/>年度</s:elseif>
								<s:elseif test="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][1] == -1 && #request.reviewDeadlineBeanMap.get(#key)[#stat.index][2] != -1"><s:property value="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][2]"/>年度之前</s:elseif>
								<s:elseif test="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][1] != 1 && #request.reviewDeadlineBeanMap.get(#key)[#stat.index][2] == -1"><s:property value="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][1]"/>年度之后</s:elseif>
								<s:property value="key"/>专家评审截止时间为<s:property value="#request.reviewDeadlineBeanMap.get(#key)[#stat.index][0]"/>，请您在截止日期前完成评审！
							</span>
							<s:if test="!#stat.last">
								<br/><span style="visibility:hidden"><img src="image/g09.png" />&nbsp;&nbsp;</span>
							</s:if>  
						</s:iterator> 
						<s:if test="!#stat0.last">
							<br/><span style="visibility:hidden"><img src="image/g09.png" />&nbsp;&nbsp;</span>
						</s:if>
			 		</s:iterator>
	 			</s:if>
 			</div>
 			
 			
 			
			<div class="main_content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td width="49%" valign="top">
							<div class="m_title">
								<span>通知公告</span><a href="notice/inner/toList.action?update=1">更多</a>
							</div>
							<div class="m_content" style="height:140px;">
								<s:if test="homeNotice.isEmpty()">
									<div class="no_records"><s:text name="i18n_NoRecords" /></div>
								</s:if>
								<s:else>
									<ul>
										<s:iterator value="homeNotice" status="stat">
											<li><a title="<s:property value="homeNotice[#stat.index][1]" />" href="notice/inner/toView.action?viewFlag=2&entityId=<s:property value="homeNotice[#stat.index][0]" />" class="txt_left"><s:property value="homeNotice[#stat.index][1]" /></a><span class="txt_right">[<s:if test="#session.locale == 'zh'"><s:date name="homeNotice[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="homeNotice[#stat.index][2]" format="dd/MM/yyyy" /></s:else>]</span></li>
										</s:iterator>
									</ul>
								</s:else>
							</div>
						</td>
						<td width="2%">&nbsp;</td>
						<td width="49%" valign="top">
							<div class="m_title">
								<span>热点新闻</span><a href="news/inner/toList.action?update=1">更多</a>
							</div>
							<div class="m_content" style="height:140px;">
								<s:if test="homeNews.isEmpty()">
									<div class="no_records"><s:text name="i18n_NoRecords" /></div>
								</s:if>
								<s:else>
									<ul>
										<s:iterator value="homeNews" status="stat">
											<li><a title="<s:property value="homeNews[#stat.index][1]" />" href="news/inner/toView.action?viewFlag=2&entityId=<s:property value="homeNews[#stat.index][0]" />" class="txt_left"><s:property value="homeNews[#stat.index][1]" /></a><span class="txt_right">[<s:if test="#session.locale == 'zh'"><s:date name="homeNews[#stat.index][2]" format="yyyy-MM-dd" /></s:if><s:else><s:date name="homeNews[#stat.index][2]" format="dd/MM/yyyy" /></s:else>]</span></li>
										</s:iterator>
									</ul>
								</s:else>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@EXPERT) || #session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)"><!-- 专家或教师-->
				<div class="main_content">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="49%" valign="top">
								<div class="m_title">
									<span>待处理项目</span><a href="javascript:void(0);">更多</a>
								</div>
								<div class="m_content" style="height:155px;">
									<ul>
										<s:if test="#request.teacherProjectBeanMap == null || #request.teacherProjectBeanMap.isEmpty()">
		                  				<li><span class="txt_left2">暂无项目需要处理</span></li>
			                  			</s:if>
			                  			<s:else>
				             				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				             					<thead class="table_tr3">
				             						<tr>
				             							<td>项目类型</td>
				             							<td>年检待处理数量</td>
				             							<td>中检待处理数量</td>
				             							<td>结项待处理数量</td>
				             							<td>变更待处理数量</td>
				             						</tr>
				             					</thead>
				             					<tbody>
				             					<s:iterator value="#request.teacherProjectBeanMap.keySet()" id="key">
				                  				  	<tr class="table_tr4">    
														<td><s:property value="key"/></td>
														<s:iterator value="#request.teacherProjectBeanMap.get(#key)" status="stat">    
															<td><a class="e_btn" alt="<s:property value="#request.teacherProjectBeanMap.get(#key)[#stat.index][0]" />" type="deal" href="javascript:void(0);"><s:property value="#request.teacherProjectBeanMap.get(#key)[#stat.index][1]"/></a></td>
														</s:iterator>  
													</tr>  
				                  				 </s:iterator>
	             								</tbody>
				             				</table>
		             					</s:else>
									</ul>
								</div>
							</td>
							<td width="2%">&nbsp;</td>
							<td width="49%" valign="top">
								<div class="m_title">
									<span>待评审项目</span><a href="javascript:void(0);">更多</a>
								</div>
								<div class="m_content" style="height:155px;">
									<ul>
									<s:if test="#request.teacherReviewBeanMap == null || #request.teacherReviewBeanMap.isEmpty()">
										<li><span class="txt_left2">暂无项目需要评审</span></li>
									</s:if>
									<s:else>
										<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			             					<thead class="table_tr3">
			             						<tr>
			             							<td>项目类型</td>
			             							<td>申报待评审数量</td>
			             							<td>结项待评审数量</td>
			             						</tr>
			             					</thead>
			             					<tbody>
			             					<s:iterator value="#request.teacherReviewBeanMap.keySet()" id="key">
			                  				  	<tr class="table_tr4">    
													<td><s:property value="key"/></td>
													<s:iterator value="#request.teacherReviewBeanMap.get(#key)" status="stat">    
														<td><a class="d_btn" alt="<s:property value="#request.teacherReviewBeanMap.get(#key)[#stat.index][0]" />" type="review" href="javascript:void(0);"><s:property value="#request.teacherReviewBeanMap.get(#key)[#stat.index][1]"/></a></td>
													</s:iterator>  
												</tr>  
			                  				 </s:iterator>
	           								</tbody>
			             				</table>
									</s:else>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</s:if>
			
			<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@STUDENT)"><!-- 学生-->
				<div class="main_content">
		           	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		   				<tr>
		           			<td valign="top">
		   						<div class="m_title">
		   							<span>待处理项目</span><a href="javascript:void(0);">更多</a>
		   						</div>
		             			<div class="p_box_body">
		             				<ul>
			                  			<s:if test="#request.teacherProjectBeanMap == null || #request.teacherProjectBeanMap.isEmpty()">
			                  				<li><span class="txt_left2">暂无项目需要处理</span></li>
			                  			</s:if>
			                  			<s:else>
				             				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				             					<thead class="table_tr3">
				             						<tr>
				             							<td>项目类型</td>
				             							<td>年度待处理数量</td>				             							
				             							<td>中检待处理数量</td>
				             							<td>结项待处理数量</td>
				             							<td>变更待处理数量</td>
				             						</tr>
				             					</thead>
				             					<tbody>
				             					<s:iterator value="#request.teacherProjectBeanMap.keySet()" id="key">
				                  				  	<tr class="table_tr4">    
														<td><s:property value="key"/></td>
														<s:iterator value="#request.teacherProjectBeanMap.get(#key)" status="stat">    
															<td><a class="e_btn" alt="<s:property value="#request.teacherProjectBeanMap.get(#key)[#stat.index][0]" />" type="deal" href="javascript:void(0);"><s:property value="#request.teacherProjectBeanMap.get(#key)[#stat.index][1]"/></a></td>
														</s:iterator>  
													</tr>  
				                  				 </s:iterator>
	             								</tbody>
				             				</table>
		             					</s:else>
			                      	</ul>
		             			</div>
		           			</td>
		           		</tr>
		        	</table>
		       	</div>
       		</s:if>

       		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"> <!-- 管理人员-->
				<div class="main_content">
		           	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		   				<tr>
		           			<td valign="top">
		   						<div class="m_title">
		   							<span>待办事宜</span>
		   						</div>
		             			<div class="p_box_body">
		             				<ul>
			                  			<s:if test="#request.managerBusinessMap == null || #request.managerBusinessMap.isEmpty()">
			                  				<li><span class="txt_left2">暂无项目需要处理</span></li>
			                  			</s:if>
			                  			<s:else>
				             				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				             					<thead class="table_tr3">
				             						<tr>
				             							<td>项目类型</td>
				             							<td>申报待审核数量</td>
				             							<td>年度待审核数量</td>				             							
				             							<td>中检待审核数量</td>
				             							<td>结项待审核数量</td>
				             							<td>变更待审核数量</td>
				             						</tr>
				             					</thead>
				             					<tbody>
				             					<s:iterator value="#request.managerBusinessMap.keySet()" id="key">
				                  				  	<tr class="table_tr4">    
														<td><s:property value="key"/></td>
														<s:iterator value="#request.managerBusinessMap.get(#key)" status="stat">    
															<td><a class="b_btn" alt="<s:property value="#request.managerBusinessMap.get(#key)[#stat.index][0]" />" type="audit" href="javascript:void(0);"><s:property value="#request.managerBusinessMap.get(#key)[#stat.index][1]"/></a></td>
														</s:iterator>  
													</tr>  
				                  				 </s:iterator>
	             								</tbody>
				             				</table>
		             					</s:else>
			                      	</ul>
		             			</div>
		           			</td>
		           		</tr>
		        	</table>
		       	</div>
       		</s:if>
      		<div class="main_content">
	           	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   				<tr>
	           			<td valign="top">
	   						<div class="m_title">
	   							<span>业务日程</span><a href="business/toList.action?update=1">更多</a>
	   						</div>
	             			<div class="p_box_body">
	             				<ul>
		                  			<s:if test="businessManagement == null || businessManagement.isEmpty()">
		                  				<li><span class="txt_left2">暂无业务需要处理</span></li>
		                  			</s:if>
		                  			<s:else>
			             				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			             					<thead class="table_tr3">
			             						<tr>
			             							<td>业务类型</td>
			             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@STUDENT)">
			             								<td>申请截止</td>
			             							</s:if>
			             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@INSTITUTE)">
			             								<td>部门截止</td>
			             							</s:if>
			             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
			             								<td>高校截止</td>
			             							</s:if>
			             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@PROVINCE)">
			             								<td>省厅截止</td>
			             							</s:if>
			             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">
			             								<td>专家评审截止</td>
			             							</s:if>
			             							<td>业务起止年度</td>
			             							<td>业务设置</td>
			             						</tr>
			             					</thead>
			             					<tbody>
			             					<s:iterator value="businessManagement" status="stat">
												<s:if test="businessManagement[#stat.index] != null">
				             						<tr class="table_tr4">
				             							<td><s:property value="businessManagement[#stat.index][0]" /></td>
				             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@STUDENT)">
				             							<td><s:if test="businessManagement[#stat.index][1] == null"><span style="color:#7A5892">无期限</span></s:if>
				             								<s:else><s:date name="businessManagement[#stat.index][1]" format="yyyy-MM-dd" /></s:else>
				             							</td>
				             							</s:if>
				             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@INSTITUTE)">
				             							<td><s:if test="businessManagement[#stat.index][2] == null"><span style="color:#7A5892">无期限</span></s:if>
				             								<s:else><s:date name="businessManagement[#stat.index][2]" format="yyyy-MM-dd" /></s:else>
				             							</td>
				             							</s:if>
				             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
				             							<td><s:if test="businessManagement[#stat.index][3] == null"><span style="color:#7A5892">无期限</span></s:if>
				             								<s:else><s:date name="businessManagement[#stat.index][3]" format="yyyy-MM-dd" /></s:else>
				             							</td>
				             							</s:if>
				             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@PROVINCE)">
				             							<td><s:if test="businessManagement[#stat.index][4] == null"><span style="color:#7A5892">无期限</span></s:if>
				             								<s:else><s:date name="businessManagement[#stat.index][4]" format="yyyy-MM-dd" /></s:else>
				             							</td>
				             							</s:if>
				             							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">
				             							<td><s:if test="businessManagement[#stat.index][5] == null"><span style="color:#7A5892">无期限</span></s:if>
				             								<s:else><s:date name="businessManagement[#stat.index][5]" format="yyyy-MM-dd" /></s:else>
				             							</td>
				             							</s:if>
				             							<td><s:if test="businessManagement[#stat.index][6] == -1 && businessManagement[#stat.index][7] == -1"><span style="color:#7A5892">无期限</span></s:if>
				             								<s:elseif test= "businessManagement[#stat.index][6] == -1 && businessManagement[#stat.index][7] != -1"><s:property value="businessManagement[#stat.index][7]" />止</s:elseif>
				             								<s:elseif test= "businessManagement[#stat.index][6] != -1 && businessManagement[#stat.index][7] == -1"><s:property value="businessManagement[#stat.index][6]" />起</s:elseif>
				             								<s:elseif test= "businessManagement[#stat.index][6] != -1 && businessManagement[#stat.index][7] != -1"><s:property value="businessManagement[#stat.index][6]" />-<s:property value="businessManagement[#stat.index][7]" /></s:elseif>
				             							</td>
				             							<td><s:if test="businessManagement[#stat.index][8] == 1">业务激活中</s:if><s:else>业务停止</s:else></td>
				             						</tr>
				             					</s:if>
				             				</s:iterator>
             								</tbody>
			             				</table>
	             					</s:else>
		                      	</ul>
	             			</div>
	           			</td>
	           		</tr>
	        	</table>
	       	</div>
		</div>
	</body>
</html>