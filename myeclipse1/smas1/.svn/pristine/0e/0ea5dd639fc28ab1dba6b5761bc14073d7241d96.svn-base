<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看专家信息</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
	</head>
	<body>
	
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<s:if test="listType == 0">
							<td>
								专家管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看专家信息</span>
							</td>
						</s:if>
						<s:else>
							<td>
								专家管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">查看专家信息</span>
							</td>
						</s:else>
						<td class="wd_right">
							<s:if test="listType == 1 || listType == 3">
<%--									<a href="" onclick="history.go(-1);return false;"><img title="返回" src="image/return.gif" /></a>&nbsp;--%>
								<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>
							</s:if>
							<s:elseif test="listType == 2">
								<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
								<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
								<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
							</s:elseif> 
							<s:else>
								<a id="view_mod" href="javascript:void(0);"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
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
				<s:hidden id="listType" name="listType" />
				<s:hidden id="projectId" name="projectId" />
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="update" name="update" />
				<s:hidden id="isReviewer" name="isReviewer" />
				<s:hidden id="expertType" name="expertType" />
				<s:hidden id="levelOneDiscipline" name="levelOneDiscipline" />
				<s:hidden id="listTypeStatic" value="%{#session.listType}" />
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
					<table class="table_edit" cellspacing="0" cellpadding="0">
						<tr><td id="updateTimeTd" colspan=4 align="right" style="padding-right: 10px;">更新时间：${expert.importedDate}</td></tr>
						<tr><th class="thhead" colspan="4">基本信息</th></tr>
						<tr><td class="wd14">姓名</td>
							<td class="wd15">
								${expert.name}
								<s:if test="(#session.visitor.userRight.contains(\"重点信息管理\")) || (#session.visitor.user.issuperuser == 1)">
								<a class="toggleKey" href="javascript:void(0)" alt="${expert.id}" iskey="${expert.isKey}">
								{if expert.isKey == 1}<img src="image/error2.png" title="是重点人，点击可切换" />{else}<img src="image/highlight.png" title="非重点人，点击可切换" />{/if}
								</a>
								</s:if>
							</td>
							<td class="wd14">编号</td>
							<td class="wd15">${expert.number}</td>
						</tr>
						<tr><td class="wd14">性别</td>
							<td class="wd15">${expert.gender}</td>
							<td class="wd14">出生日期</td>
							<td class="wd15">${expert.birthday}</td>
						</tr>
						<tr><td class="wd14">身份证号</td>
							<td class="wd15">${expert.idCardNumber}</td>
							<td class="wd14">外语语种</td>
							<td class="wd15">${expert.language}</td>
						</tr>
						<tr><td class="wd14">高校代码</td>
							<td class="wd15">${expert.universityCode}</td>
							<td class="wd14">高校名称</td>
							<td class="wd15">${universityName}</td>
						</tr>
						<tr><td class="wd14">所在院系</td>
							<td class="wd15">${expert.department}</td>
							<td class="wd14">行政职务</td>
							<td class="wd15">${expert.job}</td>
						</tr>
						<tr><td class="wd14">岗位等级</td>
							<td class="wd15">${expert.positionLevel}</td>
							<td class="wd14">是否博导</td>
							<td class="wd15">${expert.isDoctorTutor}</td>
						</tr>
						<tr><td class="wd14">评价等级</td>
							<td class="wd15">${expert.rating}</td>
							<td class="wd14">办公传真</td>
							<td class="wd15">${expert.officeFax}</td>
						</tr>
						<tr><td class="wd14">专业职称</td>
							<td class="wd15">${expert.specialityTitle}</td>
							<td class="wd14">最后学位</td>
							<td class="wd15">${expert.degree}</td>
						</tr>
						<tr><td class="wd14">邮箱</td>
							<td class="wd15">${expert.email}</td>
							<td class="wd14">移动电话</td>
							<td class="wd15">${expert.mobilePhone}</td>
						</tr>
						<tr><td class="wd14">家庭电话</td>
							<td class="wd15">${expert.homePhone}</td>
							<td class="wd14">办公电话</td>
							<td class="wd15">${expert.officePhone}</td>
						</tr>
						<tr><td class="wd14">办公地址</td>
							<td class="wd15">${expert.officeAddress}</td>
							<td class="wd14">办公邮编</td>
							<td class="wd15">${expert.officePostcode}</td>
						</tr>
						
						<tr><th class="thhead" colspan="4">学术信息</th></tr>
						<tr><td class="wd14">研究方向</td>
							<td class="wd15">${expert.researchField}</td>
							<td class="wd14">学术兼职</td>
							<td class="wd15">${expert.partTime}</td>
						</tr>
						<tr><td class="wd14">参评年份</td>
							<td class="wd15">{if expert.generalReviewYears!=null}${expert.generalReviewYears}{else}无{/if}</td>
							<td class="wd14">是否参与评审</td>
							<td class="wd15">{if expert.isReviewer == 1}是{else}否{/if}</td>
						</tr>
						<tr><td class="wd14">一般项目申请年份</td>
							<td class="wd15">{if expert.generalApplyYears!=null}${expert.generalApplyYears}{else}无{/if}</td>
							<td class="wd14">基地项目申请年份</td>
							<td class="wd15">{if expert.instpApplyYears!=null}${expert.instpApplyYears}{else}无{/if}</td>
						</tr>
						<tr><td class="wd14">学科代码及名称</td>
							<td colspan="3" style="padding-left:10px;">${expert.discipline}</td>
						</tr>
						<tr><td class="wd14">人才奖励资助情况</td>
							<td colspan="3" style="padding-left:10px;">${expert.award}</td>
						</tr>
						<tr><th class="thhead" colspan="4">项目信息</th></tr>
						{if newMoeProjectInfos != null && newMoeProjectInfos.length != 0}
							<tr><td class="wd14">承担教育部项目情况</td>
								<td class="wd14 wd23">项目编号</td>
								<td class="wd14 wd23">项目名称</td>
								<td class="wd14 wd23">项目类型</td>
							</tr>
							{for item in newMoeProjectInfos}
								<tr><td class="wd23">${++item_index}</td>
									<td class="wd23">${item.projectNum}</td>
									<td class="wd23">${item.projectName}</td>
									<td class="wd23">${item.projectType}</td>
								</tr>
							{/for}
						{/if}
						{if newMoeProjectInfos == null || newMoeProjectInfos.length == 0}
							<tr><td class="wd14">承担教育部项目情况</td>
								<td colspan="3" style="padding-left:10px;">${expert.moeProject}</td>
							</tr>
						{/if}
						{if newNationalProjectInfos != null && newNationalProjectInfos.length != 0}
							<tr><td class="wd14">承担国家项目情况</td>
								<td class="wd14 wd23">项目编号</td>
								<td class="wd14 wd23">项目名称</td>
								<td class="wd14 wd23">项目类型</td>
							</tr>
							{for item in newNationalProjectInfos}
								<tr><td class="wd23">${++item_index}</td>
									<td class="wd23">${item.projectNum}</td>
									<td class="wd23">${item.projectName}</td>
									<td class="wd23">${item.projectType}</td>
								</tr>
							{/for}
						{/if}
						{if newNationalProjectInfos == null || newNationalProjectInfos.length == 0}
							<tr><td class="wd14">承担国家项目情况</td>
								<td colspan="3" style="padding-left:10px;">${expert.nationalProject}</td>
							</tr>
						{/if}
						<tr><th class="thhead" colspan="4">个人简介</th></tr>
						<tr>
							<td colspan="4" style="padding-left:10px;">${expert.introduction}</td>
						</tr>
						
						<tr><th class="thhead" colspan="4">备注</th></tr>
						<tr>
							<td colspan="4" style="padding-left:10px;">${expert.remark}</td>
						</tr>
						
						<tr><th class="thhead" colspan="4">警告</th></tr>
						<tr>
							<td colspan="4" style="padding-left:10px;">${expert.warning}</td>
						</tr>
						<%-- 一般项目 --%>
						{if generalList != null && generalList.length!=0}
						<tr>
							<td colspan="4" style="border:0 0 0 1px;">
								<table class="table_view" cellspacing="0" cellpadding="0">
									<tr><th class="thhead" colspan="5" style="border-width:0 0 1px 0;">评审一般项目</th>
										<th class="thhead" colspan="2" style="border-width:0;text-align:right">
											<input type="button" id="notReviewGeneral" class="btn isCurYear" value="退评项目" />
											<input type="button" id="general_replace_expert" class="btn isCurYear" value="替换专家" />
											<input type="button" id="general_transfer" class="btn isCurYear" value="指定移交" />
										</th>
									</tr>
									<tr>
										<td class="wd19" style="width:30px;"><input type="checkbox" name="check" title="全选/全不选" onclick="checkAll(this.checked, 'generalIds')" value="false" /></td>
										<td class="wd19" style="width:50px;">序号</td>
										<td class="wd19" style="width:100px;">项目名称</td>
										<td class="wd19" style="width:100px;">项目类型</td>
										<td class="wd19" style="width:100px;">负责人</td>
										<td class="wd19" style="width:100px;">高校名称</td>
										<td class="wd19" style="width:100px;border-width:1px 0 1px 1px;">学科代码及名称</td>
									</tr>
									<div style="display: none;">${num = 0}</div>
									{for item in generalList}
										{if item[6] < 6}<tr class="wd22">{else}<tr>{/if}
										<td style="border-bottom-width:0px;"><s:checkbox name="generalIds" fieldValue="${item[0]}" value="false" theme="simple" /></td>
										<td style="border-bottom-width:0px;">${num = num + 1}</td>
										<td style="border-bottom-width:0px;"><a href="project/general/toView.action?entityId=${item[0]}&isReviewable=1&isFromExpert=1&listType=1&businessType=1" title="点击查看详细信息">${item[1]}</a></td>
										<td style="border-bottom-width:0px;">${item[2]}</td>
										<td style="border-bottom-width:0px;">${item[3]}</td>
										<td style="border-bottom-width:0px;">${item[4]}</td>
										<td style="width:100px;border-width:1px 0 0 1px;">${item[5]}</td>
										</tr>
									{/for}
								</table>
							</td>
						</tr>
						{/if}
						<%-- 基地项目 --%>
						{if instituteList != null && instituteList.length!=0}
						<tr>
							<td colspan="4" style="border:0 0 0 1px;">
								<table class="table_view" cellspacing="0" cellpadding="0">
									<tr><th class="thhead" colspan="5" style="border-width:0 0 1px 0;">评审基地项目</th>
										<th class="thhead" colspan="2" style="border-width:0;text-align:right">
											<input type="button" id="notReviewInstp" class="btn isCurYear" value="退评项目" />
											<input type="button" id="institute_replace_expert" class="btn isCurYear" value="替换专家" />
											<input type="button" id="institute_transfer" class="btn isCurYear" value="指定移交" />
										</th>
									</tr>
									<tr>
										<td class="wd19" style="width:30px;"><input type="checkbox" name="check" title="全选/全不选" onclick="checkAll(this.checked, 'instpIds')" value="false" /></td>
										<td class="wd19" style="width:50px;">序号</td>
										<td class="wd19" style="width:100px;">项目名称</td>
										<td class="wd19" style="width:100px;">项目类型</td>
										<td class="wd19" style="width:100px;">负责人</td>
										<td class="wd19" style="width:100px;">高校名称</td>
										<td class="wd19" style="width:100px;border-width:1px 0 1px 1px;">学科代码及名称</td>
									</tr>
									<div style="display: none;">${num = 0}</div>
									{for item in instituteList}
										{if item[6] < 6}<tr class="wd22">{else}<tr>{/if}
										<td style="border-bottom-width:0px;"><s:checkbox name="instpIds" fieldValue="${item[0]}" value="false" theme="simple" /></td>
										<td style="border-bottom-width:0px;">${num = num + 1}</td>
										<td style="border-bottom-width:0px;"><a href="project/instp/toView.action?entityId=${item[0]}&isReviewable=1&isFromExpert=1&listType=1&businessType=1" title="点击查看详细信息">${item[1]}</a></td>
										<td style="border-bottom-width:0px;">${item[2]}</td>
										<td style="border-bottom-width:0px;">${item[3]}</td>
										<td style="border-bottom-width:0px;">${item[4]}</td>
										<td style="border-width:1px 0 0 1px;">${item[5]}</td>
										</tr>
									{/for}
								</table>
							</td>
						</tr>
						{/if}
					</table>
					<div></div>
					</div>
				</textarea>
				
				<div id="view_container" style="display:none; clear:both;"></div>
				
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/expert.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>