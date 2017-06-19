<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>社科管理咨询服务中心</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
		<link rel="stylesheet" type="text/css" href="tool/menu/menu.css" />
	</head>

	<body id="main_body">
		<div class="container">
			<div class="top">
				<div class="main_title">
					<span class="title_welcome">
						欢迎!&nbsp;<s:property value="#session.visitor.user.chinesename" />&nbsp;
						<s:if test="(#session.visitor.userRight.contains(\"切换系统年份\")) || (#session.visitor.user.issuperuser == 1)">
							年份：<s:select name="year" list="%{#application.allYears}" value="%{#session.defaultYear}" />
						</s:if>
						<span id="currentTime" class="currentTime"></span>
						<a href="user/logout.action">退出</a>
					</span>
					
				</div>
				<!-- <table class="table" cellspacing="0" cellpadding="0">
					<tr class="tr_tile">
						<td style="text-align:left;">
							&nbsp;&nbsp;欢迎!&nbsp;
							<s:property value="#session.visitor.user.chinesename" />
							&nbsp;&nbsp;<a href="user/logout.action">退出</a>
						</td>
						<td>&nbsp;</td>
						<td style="text-align: right;">
							<div>
								<span id="currentTime"></span>
							</div>
						</td>
					</tr>
					</table> -->
				<div class="top_pic" cellspacing="0" cellpadding="0">
					<p class="top_txt">社科管理咨询服务系统</p>
				</div>
					<!-- <table class="table" cellspacing="0" cellpadding="0">
						<tr class="tr_back">
							<td class="td_left">
								<div id="unit">
									社科管理咨询服务系统
								</div>
							</td>
							<td class="td_mid"></td>
							<td class="td_right"></td>
						</tr>
					</table> -->
			</div>
			
			<div id="menu">
			    <ul class="menu">
			    	<li><a href="main/right.action" target="right"><span>首页</span></a></li>
			    	<s:if test="(#session.visitor.userRight.contains(\"专家管理\")) || (#session.visitor.user.issuperuser == 1)">
				        <li>
				        	<a class="parent"><span>专家管理</span></a>
				            <div>
				            	<ul>
				                	<li><a class="parent"><span>参评专家</span></a>
				                    	<div>
				                    		<ul>
						                        <li><a href="expert/toList.action?update=1&expertType=0&isReviewer=1" target="right"><span>内部专家</span></a></li>
						                        <li><a href="expert/toList.action?update=1&expertType=1&isReviewer=1" target="right"><span>外部专家</span></a></li>
				                    		</ul>
				                    	</div>
				               		 </li>
									 <li><a class="parent"><span>退评专家</span></a>
	                            		<div>
	                            			<ul>
				                                <li><a href="expert/toList.action?update=1&expertType=0&isReviewer=0" target="right"><span>内部专家</span></a></li>
				                                <li><a href="expert/toList.action?update=1&expertType=1&isReviewer=0" target="right"><span>外部专家</span></a></li>
	                           				</ul>
	                           			</div>
	                        		 </li>
	                        		 <s:if test="(#session.visitor.user.issuperuser == 1)">
					               		 <li><a href="expert/toImportExpert.action" target="right"><span>导入专家</span></a></li>
					               		 <li><a href="expert/exportExpert.action?exportAll=1&countReviewer=1" target="right" onclick="return confirm('您确定要导出所有专家数据吗？');"><span>导出专家</span></a></li>
				               		 </s:if>
				            	</ul>
				           </div>
				        </li>
					</s:if>
					<s:if test="(#session.visitor.userRight.contains(\"一般项目管理\")) || (#session.visitor.userRight.contains(\"基地项目管理\")) || (#session.visitor.user.issuperuser == 1)">
						<li>
							<a class="parent"><span>项目管理</span></a>
							<div>
								<ul>
									<s:if test="(#session.visitor.userRight.contains(\"一般项目管理\")) || (#session.visitor.user.issuperuser == 1)">
										<li><a class="parent"><span>一般项目</span></a>
											<div>
												<ul>
													<li><a class="parent"><span>申报</span></a>
														<div>
															<ul>
																<li><a href="project/general/toList.action?update=1&listType=1&isReviewable=1&businessType=1" target="right"><span>参评项目</span></a></li>
																<li><a href="project/general/toList.action?update=1&listType=1&isReviewable=0&businessType=1" target="right"><span>退评项目</span></a></li>
																<li><a href="project/general/firstAudit/toList.action?update=1&listType=4&isReviewable=1&businessType=1" target="right"><span>初审结果</span></a></li>
																<li><a href="project/general/publicity/toList.action?update=1&listType=2&isReviewable=1&businessType=1" target="right"><span>公示项目</span></a></li>
																<s:if test="(#session.visitor.user.issuperuser == 1)">
																	<li><a href="javascript:void(0);" target="right" onclick="exportProject();"><span>导出项目</span></a></li>
																</s:if>
															</ul>
														</div>
													</li>
													<s:if test="#session.visitor.user.issuperuser == 1">
														<li><a href="project/general/granted/toList.action?update=1&businessType=2" target="right"><span>立项</span></a></li>
														<li><a href="project/general/midinspection/toList.action?update=1&businessType=3" target="right"><span>中检</span></a></li>
														<li><a href="project/general/variation/toList.action?update=1&businessType=4" target="right"><span>变更</span></a></li>
														<li><a href="project/general/endinspection/toList.action?update=1&businessType=5" target="right"><span>结项</span></a></li>
													</s:if>
												</ul>
											</div>
										</li>
									</s:if>
									<s:if test="(#session.visitor.userRight.contains(\"基地项目管理\")) || (#session.visitor.user.issuperuser == 1)">
										<li><a class="parent"><span>基地项目</span></a>
											<div>
												<ul>
													<li><a class="parent"><span>申报</span></a>
														<div>
															<ul>
																<li><a href="project/instp/toList.action?update=1&listType=1&isReviewable=1&businessType=1" target="right"><span>参评项目</span></a></li>
																<li><a href="project/instp/toList.action?update=1&listType=1&isReviewable=0&businessType=1" target="right"><span>退评项目</span></a></li>
																<li><a href="project/instp/firstAudit/toList.action?update=1&listType=4&isReviewable=1&businessType=1" target="right"><span>初审结果</span></a></li>
																<%--<li><a href="javascript:void(0);" target="right"><span>公示项目</span></a></li>
																<s:if test="(#session.visitor.user.issuperuser == 1)">
																	<li><a href="javascript:void(0);" target="right" onclick=""><span>导出项目</span></a></li>
																</s:if>
															--%></ul>
														</div>
													</li>
													<s:if test="#session.visitor.user.issuperuser == 1">
														<li><a href="project/instp/granted/toList.action?update=1&businessType=2" target="right"><span>立项</span></a></li>
														<li><a href="project/instp/midinspection/toList.action?update=1&businessType=3" target="right"><span>中检</span></a></li>
														<li><a href="project/instp/variation/toList.action?update=1&businessType=4" target="right"><span>变更</span></a></li>
														<li><a href="project/instp/endinspection/toList.action?update=1&businessType=5" target="right"><span>结项</span></a></li>
													</s:if>
												</ul>
											</div>
										</li>
									</s:if>
									<li><a class="parent" ><span>专项任务项目</span></a>
										<div>
											<ul>
												<li><a href="project/general/special/toList.action?update=1&listType=5&isReviewable=1" target="right"><span>参评项目</span></a></li>
												<li><a href="project/general/firstAudit/toList.action?update=1&listType=4" target="right"><span>初审结果</span></a></li>
											</ul>
										</div>
									</li>
								</ul>
							</div>
						</li>	
					</s:if>
					<s:if test="(#session.visitor.userRight.contains(\"专家管理\")) || (#session.visitor.user.issuperuser == 1)">
				        <li>
				        	<a class="parent"><span>奖励管理</span></a>
				            <div>
				            	<ul>
				                	<li><a href="award/moeSocial/toList.action" target="right"><span>人文社科奖</span></a></li>
				            	</ul>
				           </div>
				        </li>
					</s:if>
					<s:if test="(#session.visitor.userRight.contains(\"统计分析\")) || (#session.visitor.user.issuperuser == 1)">
						 <li>
						 	<a class="parent"><span>统计分析</span></a>
						 	<div>
						 		<ul>
						 			<s:if test="(#session.visitor.userRight.contains(\"一般项目管理\")) || (#session.visitor.user.issuperuser == 1)">
						 				<li><a class="parent"><span>一般项目</span></a>
						 					<div>
						 						<ul>
						 							<li><a href="statistic/review/university/toList.action?update=1&listType=1" target="right"><span>高校参评专家数</span></a></li>
						 							<li><a href="statistic/review/expert/toList.action?update=1&listType=1" target="right"><span>专家参评项目数</span></a></li>
						 							<li><a href="statistic/review/disciplineMatrix.action?listType=1" target="right"><span>学科退避矩阵</span></a></li>
						 							<li><a href="statistic/review/warnStatistic.action?listType=1" target="right"><span>匹配警告信息</span></a></li>
						 							<li><a href="statistic/review/universityUseStatistic.action?listType=1" target="right"><span>高校使用情况统计</span></a></li>
						 						</ul>
						 					</div>
						 				</li>
						 			</s:if>
						 			<s:if test="(#session.visitor.userRight.contains(\"基地项目管理\")) || (#session.visitor.user.issuperuser == 1)">
						 				<li><a class="parent"><span>基地项目</span></a>
						 					<div>
						 						<ul>
						 							<li><a href="statistic/review/university/toList.action?update=1&listType=2" target="right"><span>高校参评专家数</span></a></li>
						 							<li><a href="statistic/review/expert/toList.action?update=1&listType=2" target="right"><span>专家参评项目数</span></a></li>
						 							<li><a href="statistic/review/disciplineMatrix.action?listType=2" target="right"><span>学科退避矩阵</span></a></li>
						 							<li><a href="statistic/review/warnStatistic.action?listType=2" target="right"><span>匹配警告信息</span></a></li>
						 							<li><a href="statistic/review/universityUseStatistic.action?listType=2" target="right"><span>高校使用情况统计</span></a></li>
						 						</ul>
						 					</div>
						 				</li>
						 			</s:if>
						 			<s:if test="((#session.visitor.userRight.contains(\"一般项目管理\")) && (#session.visitor.userRight.contains(\"基地项目管理\"))) || (#session.visitor.user.issuperuser == 1)">
						 				<li><a class="parent"><span>评审专家</span></a>
						 					<div>
						 						<ul>
						 							<!-- 专家跨类信息统计分析页面 -->
						 							<li><a href="statistic/review/expertReviewer/toList.action?update=1" target="right"><span>专家评审项目</span></a></li>
						 							<li><a href="statistic/review/univReviewer/toList.action?update=1" target="right"><span>高校评审专家</span></a></li>
						 						</ul>
						 					</div>
						 				</li>
						 			</s:if>
						 		</ul>
						 	</div>
						 </li>
					</s:if>
					<s:if test="(#session.visitor.userRight.contains(\"业务设置\")) || (#session.visitor.user.issuperuser == 1)">
						<li>
							<a class="parent"><span>业务设置</span></a>
			            	<div>
			            		<ul>
			            			<li><a href="main/viewConfigGeneral.action" target="right"><span>一般项目</span></a></li>
			            			<li><a href="main/viewConfigInstp.action" target="right"><span>基地项目</span></a></li>
			            		</ul>	
			            	</div>
			       		</li>
					</s:if>
			        <s:if test="(#session.visitor.userRight.contains(\"基本权限\")) || (#session.visitor.user.issuperuser == 1)">
			        	<li><a class="parent"><span>个人空间</span></a>
			            	<div>
			            		<ul>
			            			<li><a href="selfspace/viewInfo.action" target="right"><span>个人信息</span></a></li>
			            			<li><a href="selfspace/toModifyPassword.action" target="right"><span>修改密码</span></a></li>
			            		</ul>	
			            	</div>
			       		</li>
			        </s:if>
			       	<s:if test="(#session.visitor.userRight.contains(\"用户管理\")) || (#session.visitor.userRight.contains(\"角色管理\")) || (#session.visitor.user.issuperuser == 1)">
			        	<li>
			       			<a class="parent"><span>系统管理</span></a>
			       			<div>
			       				<ul>
				       					<s:if test="(#session.visitor.userRight.contains(\"用户管理\")) || (#session.visitor.user.issuperuser == 1)">
				       						<li>
				       							<a class="parent"><span>用户管理</span></a>
				       							<div>
				       								<ul>
				       									<li><a href="user/toList.action?update=1&userstatus=1" target="right"><span>已启用用户</span></a></li>
				       									<li><a href="user/toList.action?update=1&userstatus=-1" target="right"><span>未启用用户</span></a></li>
				       									<li><a href="user/toList.action?update=1&userstatus=0" target="right"><span>未审批用户</span></a></li>
				       								</ul>
				       							</div>
				       						</li>
				       					</s:if>
				       					<s:if test="(#session.visitor.userRight.contains(\"角色管理\")) || (#session.visitor.user.issuperuser == 1)">
				       						<li><a class="parent"><span>角色权限</span></a>
				       							<div>
				       								<ul>
				       									<li><a href="role/toList.action?update=1" target="right"><span>角色列表</span></a></li>
				       									<s:if test="(#session.visitor.user.issuperuser == 1)">
				       										<li><a href="right/toList.action?update=1" target="right"><span>权限列表</span></a></li>
				       									</s:if>
				       								</ul>
				       							</div>
				       						</li>
				       					</s:if>
				       					<s:if test="(#session.visitor.user.issuperuser == 1)">
				       						<li><a href="mail/toList.action?update=1" target="right"><span>邮件管理</span></a></li>
			       							<li><a href="main/toConfig.action" target="right"><span>系统设置</span></a></li>
			       							<li><a href="log/toList.action?update=1" target="right"><span>系统日志</span></a></li>
				       					</s:if>
			       				</ul>
			       			</div>
			       		</li>
		       		</s:if>
		       		<li>
		       			<a class="parent"><span>系统帮助</span></a>
		       			<div>
		       				<ul>
				       			<li><a href="main/right.action" target="right"><span>帮助</span></a></li>
				       			<li><a href="main/right.action" target="right"><span>关于</span></a></li>
		       				</ul>
		       			</div>
		       		</li>
			    </ul>
			</div>
			<s:include value="/main/loadIco.jsp" />
			<div class="right">
		    	<iframe id="right" width="100%" name="right" src="main/right.action" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
			</div>
			
			<div class="footer">
				<div class="footer_r_txt">制作单位：中国高校社会科学数据中心
					<br/>Copyright&copy; 2009-2013 All Rights Reserved.&nbsp; 系统版本：1.0.<%=application.getAttribute("systemVersion")%>					
				</div>
			</div>
		</div>
		<div style="display:none;"><a href="http://apycom.com/">Apycom jQuery Menus</a></div>
		<input id="userName" type="hidden" value='<s:property value="#session.visitor.user.chinesename" />'/>
		
		<!-- 添加遮罩层（专家进行匹配的时候） -->
		<div id="mask"></div>
		<div class="mask_box" style="display:none">
			<div class="mask_background">
				<span>匹配/补配中，请稍候…</span>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="tool/menu/menu.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/main/main.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript">
				var timeDiff = <%= new Date().getTime()%> - new Date().valueOf();
				var updateTime = function() {
					var now = new Date(new Date().valueOf() + timeDiff);
					$("#currentTime").html(now.format("yyyy年MM月dd日 w hh:mm:ss"));
				};
				updateTime();
				setInterval(updateTime, 1000);
		</script>
	</body>
</html>