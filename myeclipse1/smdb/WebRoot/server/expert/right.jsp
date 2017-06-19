<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国高校社会科学管理数据库</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/server/expert/right.js', function(right) {
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
			
			<div class="main_content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td width="49%" valign="top">
							<div class="m_title">
								<span>通知公告</span><a href="notice/inner/toList.action?update=1">更多</a>
							</div>
							<div class="m_content" style="height:140px;">
								<s:if test="homeNotice.isEmpty()">
									<div class="no_records">暂无符合条件的记录</div>
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
									<div class="no_records">暂无符合条件的记录</div>
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
           	
			<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@EXPERT) == 0 || #session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@TEACHER) == 0">
				<div class="main_content">
		           	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		   				<tr>
		           			<td valign="top">
		   						<div class="m_title">
		   							<span>待处理项目</span>
		   						</div>
		             			<div class="m_content">
			                  		<ul>
			                  			<s:if test="teacherProjectBean == null || teacherProjectBean.isEmpty()">
			                  				<li><span class="txt_left2">暂无项目需要处理</span></li>
			                  			</s:if>
			                  			<s:else>
					                  		<s:iterator value="teacherProjectBean" id="teacherBusiness">
					                  			<li>
					                  				<span class="txt_left2" style="width:400px;"><s:property value="projectName" /></span>
					                  				<span class="txt_left2" style="width:100px;"></span>
					                  				<span class="txt_right">
					                  					<s:iterator value="#teacherBusiness.projectBusiness" status="stat">
					                  						<a alt="<s:property value='projectBusiness[#stat.index][0]' />" class="a_btn" href="javascript:void(0);"><s:property value="projectBusiness[#stat.index][1]" /></a>
					                  					</s:iterator>
					                  				</span>
					                  				<s:hidden value="%{projectApplicationId}" />
					                  			</li>
											</s:iterator>
										</s:else>
			                      	</ul>
			                  	</div>
		           			</td>
		           		</tr>
		        	</table>
		       	</div>
		       	<div class="main_content">
		           	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		   				<tr>
		           			<td valign="top">
		   						<div class="m_title">
		   							<span>待评审项目</span>
		   						</div>
		             			<div class="m_content">
			                  		<ul>
			                  			<s:if test="teacherReviewBean == null || teacherReviewBean[0] == null || teacherReviewBean.isEmpty()">
			                  				<li><span class="txt_left2">暂无项目需要评审</span></li>
			                  			</s:if>
			                  			<s:else>
					                  		<s:iterator value="teacherReviewBean" status="stat" id="teacherReview">
					                  		<s:if test="teacherReviewBean[#stat.index] != null">
					                  			<li>
					                  				<span class="txt_left2" style="width:400px;"><s:property value="teacherReviewBean[#stat.index][0][0]" /></span>
					                  				<span class="txt_left2" style="width:100px;"></span>
					                  				<span class="txt_right">
					                  					<a class="d_btn" alt="<s:property value="teacherReviewBean[#stat.index][0][1]" />" type="review" href="javascript:void(0);">待评审<s:property value="teacherReviewBean[#stat.index][2]" />条</a>
					                  				</span>
					                  			</li>
					                  		</s:if>
											</s:iterator>
										</s:else>
			                      	</ul>
				                  </div>
		           			</td>
		           		</tr>
		        	</table>
		       	</div>
		       	
       		</s:if>

       		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY , @csdc.tool.bean.AccountType@INSTITUTE)">
				<div class="main_content">
		           	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		   				<tr>
		           			<td valign="top">
		   						<div class="m_title">
		   							<span>待处理业务</span>
		   						</div>
		             			<div class="m_content">
			                  		<ul>
			                  			<s:if test="managerProjectBean == null || managerProjectBean.isEmpty()">
			                  				<li><span class="txt_left2">暂无项目需要处理</span></li>
			                  			</s:if>
			                  			<s:else>
					                  		<s:iterator value="managerProjectBean" status="stat" id="managerBusiness">
					                  		<s:if test="managerProjectBean[#stat.index] != null">
					                  			<li>
					                  				<span class="txt_left2" style="width:400px;"><s:property value="managerProjectBean[#stat.index][0][0]" /></span>
					                  				<span class="txt_left2" style="width:100px;"></span>
					                  				<span class="txt_right">
					                  					<a class="c_btn" alt="<s:property value="managerProjectBean[#stat.index][0][1]" />" type="total" href="javascript:void(0);">应处理<s:property value="managerProjectBean[#stat.index][1]" />条</a>
					                  					<a class="b_btn" alt="<s:property value="managerProjectBean[#stat.index][0][1]" />" type="submit" href="javascript:void(0);">已提交<s:property value="managerProjectBean[#stat.index][2]" />条</a>
					                  					<a class="b_btn" alt="<s:property value="managerProjectBean[#stat.index][0][1]" />" type="audit" href="javascript:void(0);">待处理<s:property value="managerProjectBean[#stat.index][3]" />条</a>
					                  				</span>
					                  			</li>
					                  		</s:if>
											</s:iterator>
										</s:else>
			                      	</ul>
				                  </div>
		           			</td>
		           		</tr>
		        	</table>
		       	</div>
       		</s:if>
       		
		</div>
	</body>
</html>