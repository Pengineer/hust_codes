<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>社科管理咨询服务中心</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/global.css" />
		<link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui-1.8.5.custom.css" />
		<link rel="stylesheet" type="text/css" href="css/project/project.css" />
	</head>
	
	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							奖励管理&nbsp;&gt;&gt;&nbsp;社科奖励&nbsp;&gt;&gt;&nbsp;奖励分组&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看详情</span>
						</td>
						<td class="wd_right">
							<s:if test="isFromExpert == 1">
								<a href="" onclick="history.go(-1);return false;"><img title="返回" src="image/return.gif" /></a>&nbsp;
							</s:if>
							<s:else>
								<!-- TODO -->
								<!-- <a id="view_mod" href="javascript:void(0);"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp; -->
								<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
								<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
								<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
								<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
							</s:else> 
						</td>
					</tr>
				</table>
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="update" name="update" />
				<s:hidden id="listType" name="listType" />
				<s:hidden id="isReviewable" name="isReviewable" />
				
				<div class="main-content">
					<!-- 奖励详情页面项目 基本信息标签 -->
					<div id="tabs_top" class="p_box_bar" style="margin-top:10px;">
						<ul>
							<li><a href="#basicInfo">基本信息</a></li>
						</ul>
					</div>
					
					<div class="p_box">
						<!-- 奖励基本信息标签页 -->
						<div id="basicInfo">
							<textarea id="view_basicInfo_template" style="display:none;">
								<table class="table_edit" cellspacing="0" cellpadding="0">
									<tr>
										<td class="wd17">奖励名称</td>
										<td class="wd21" colspan="3">${awardApplication.productName}</td>
									</tr>
									<tr>
										<td class="wd17">主要作者（含申请人）</td>
										<td class="wd18">${awardApplication.authors}</td>
										<td class="wd17">成果类型</td>
										<td class="wd18">${awardApplication.productType}</td>
									</tr>
									<tr>
										<td class="wd17">上架号</td>
										<td class="wd18">${awardApplication.shelfNumber}</td>
										<td class="wd17">奖励类型</td>
										<td class="wd18">${awardApplication.awardCatalog}</td>
									</tr>
									<!--<tr>
										<td class="wd17">分组名称</td>
										<td class="wd18">${awardApplication.group}</td>
										<td class="wd17">组内编号</td>
										<td class="wd18">${awardApplication.number}</td>
									</tr>
									-->
									<tr>
										<td class="wd17">成果语种</td>
										<td class="wd18">${awardApplication.productLanguage}</td>
										<td class="wd17">高校代码</td>
										<td class="wd18">${awardApplication.unitCode}</td>
									</tr>
									<tr>
										<td class="wd17">学科门类</td>
										<td class="wd18">${awardApplication.disciplinePrimitive}</td>
										<td class="wd17">原著语言</td>
										<td class="wd18">${awardApplication.originalLanguage}</td>
									</tr>
									<tr>
										<td class="wd17">二级学科门类</td>
										<td class="wd18">${awardApplication.discipline}</td>
										<td class="wd17">学科代码</td>
										<td class="wd18">${awardApplication.disciplineCode}</td>
									</tr>
									<tr>
										<td class="wd17">申请类型</td>
										<td class="wd18">${awardApplication.applicationType}</td>
										<td class="wd17">奖励字数</td>
										<td class="wd18">${awardApplication.wordNumber}</td>
									</tr>
									<tr>
										<td class="wd17">著作类型</td>
										<td class="wd18">${awardApplication.productType}</td>
										<td class="wd17">出版、发表或使用单位</td>
										<td class="wd18">${awardApplication.publishUnit}</td>
									</tr>
									<tr>
										<td class="wd17">出版年度-期刊号</td>
										<td class="wd18">${awardApplication.journalNumber}</td>
										<td class="wd17">出版、发表或使用时间</td>
										<td class="wd18">${awardApplication.publishDate}</td>
									</tr>
									<tr>
										<th class="thhead" colspan="4">申请人信息</th>
									</tr>
									<tr>
										<td class="wd17">申请者</td>
										<td class="wd21">${awardApplication.applicantName}</td>
										<td class="wd17">第一作者</td>
										<td class="wd18">${awardApplication.firstAuthor}</td>
									</tr>
									<tr>
										<td class="wd17">性别</td>
										<td class="wd18">${awardApplication.gender}</td>
										<td class="wd17">出生日期</td>
										<td class="wd18">${awardApplication.birthday}</td>
									</tr>
									<tr>
										<td class="wd17">行政职务</td>
										<td class="wd18">${awardApplication.position}</td>
										<td class="wd17">职称</td>
										<td class="wd18">${awardApplication.specialistTitle}</td>
									</tr>
									<tr>
										<td class="wd17">国籍</td>
										<td class="wd18">${awardApplication.nationality}</td>
										<td class="wd17">人事关系</td>
										<td class="wd18">${awardApplication.humanRelationship}</td>
									</tr>
									<tr>
										<td class="wd17">是否校领导</td>
										<td class="wd18">
											{if awardApplication.isUnitLeader==1}
											是
											{else}
											否
											{/if}
										</td>
										<td class="wd17">是否是第一作者</td>
										<td class="wd18">
											{if awardApplication.isFirstAuthor==1}
											是
											{else}
											否
											{/if}
										</td>
									</tr>
									<tr>
										<td class="wd17">证件类型</td>
										<td class="wd18">${awardApplication.idcardType}</td>
										<td class="wd17">证件号码</td>
										<td class="wd18">${awardApplication.idcardNumber}</td>
									</tr>
									<tr>
										<td class="wd17">工作单位</td>
										<td class="wd18">${awardApplication.divisionName}</td>
										<td class="wd17">办公电话</td>
										<td class="wd18">${awardApplication.officePhone}</td>
									</tr>
									<tr>
										<td class="wd17">手机</td>
										<td class="wd18">${awardApplication.mobilePhone}</td>
										<td class="wd17">邮箱</td>
										<td class="wd18">${awardApplication.email}</td>
									</tr>
									<tr>
										<td class="wd17">是否合格</td>
										<td class="wd18">
											{if awardApplication.isPassed==1}
											是
											{else}
											否
											{/if}
										</td>
										<td class="wd17">是否需要跟踪</td>
										<td class="wd18">
											{if awardApplication.isTracked==1}
											是
											{else}
											否
											{/if}
										</td>
									</tr>
									<tr>
										<td class="wd17">通信地址</td>
										<td class="wd21" colspan="3">${awardApplication.address}</td>
									</tr>
									<tr>
										<td class="wd17">备注</td>
										<td class="wd21" colspan="3">${awardApplication.note}</td>
									</tr>
								</table>
							</textarea>
							<div id="view_basicInfo"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery-ui-1.8.5.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/award/moeSocial/awardGroup/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>	
	</body>
</html>
	
