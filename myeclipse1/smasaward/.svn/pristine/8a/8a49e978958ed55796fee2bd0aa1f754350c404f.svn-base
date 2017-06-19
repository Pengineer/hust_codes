<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>项目清单</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
	</head>

	<body>
		<div id="container" style="width:680px;height:300px; overflow-y:scroll; overflow-x:hidden;">
			<div class="main">
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="update" name="update" />
				<s:hidden id="listType" name="listType" />
				
				<textarea id="view_template" style="display:none;">
					<div class="main_content" style="width:100%;margin:0;">
						<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0;">
							<tr><th class="thhead" colspan="4">专家信息</th></tr>
							<tr>
								<td class="wd14">姓名</td>
								<td class="wd16">${expert.name}</td>
								<td class="wd14">出生日期</td>
								<td class="wd15">${expert.birthday}</td>
							</tr>
							<tr><td class="wd14">高校名称</td>
								<td class="wd15">${universityName}</td>
								<td class="wd14">移动电话</td>
								<td class="wd15">${expert.mobilePhone}</td>
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
							<tr>
								<td class="wd14">专业职称</td>
								<td class="wd15">${expert.specialityTitle}</td>
								<td class="wd14">最后学位</td>
								<td class="wd15">${expert.degree}</td>
							</tr>
							<tr>
								<td class="wd14">研究方向</td>
								<td class="wd15">${expert.researchField}</td>
								<td class="wd14">参评年份</td>
								<td class="wd15">{if expert.generalReviewYears != null}${expert.generalReviewYears}{else}无{/if}</td>
							</tr>
							<tr>
								<td class="wd14">学科代码及名称</td>
								<td class="wd15">${expert.discipline}</td>
								<td class="wd14">申报年份</td>
								<td class="wd15">{if expert.generalApplyYears != null}${expert.generalApplyYears}{else}无{/if}</td>
							</tr>
							<%-- 一般项目 --%>
							{if generalList != null && generalList.length!=0}
							<tr>
								<td colspan="4" style="border:0 0 0 1px;">
									<table class="table_view" cellspacing="0" cellpadding="0">
										<tr><th class="thhead" colspan="6" style="border-width:0 0 1px 0;">评审一般项目</th>
											<th class="thhead" style="border-width:0;text-align:right">
												<input type="button" id="notReviewGeneral" class="btn" value="退评一般项目" />
											</th>
										</tr>
										<tr>
											<td class="wd19" style="width:30px;"><input type="checkbox" name="check" title="全选/全不选" onclick="checkAll(this.checked, 'generalIds')" value="false" /></td>
											<td class="wd19" style="width:50px;">序号</td>
											<td class="wd19" style="">项目名称</td>
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
											<td style="border-bottom-width:0px;">${item[1]}</td>
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
							
							<%-- 基地项目 --%>
							{if instituteList != null && instituteList.length!=0}
							<tr>
								<td colspan="4" style="border:0;">
									<table class="table_view" cellspacing="0" cellpadding="0">
										<tr><th class="thhead" colspan="6" style="border-width:0 0px 1px 1px;">评审基地项目</th>
											<th class="thhead" style="border-width:0;text-align:right">
												<input type="button" id="notReviewInstp" class="btn" value="退评基地项目" />
											</th>
										</tr>
										<tr>
											<td class="wd19" style="width:30px;"><input type="checkbox" name="check" title="全选/全不选" onclick="checkAll(this.checked, 'instpIds')" value="false" /></td>
											<td class="wd19" style="width:50px;">序号</td>
											<td class="wd19" style="">项目名称</td>
											<td class="wd19" style="width:100px;">项目类型</td>
											<td class="wd19" style="width:100px;">负责人</td>
											<td class="wd19" style="width:100px;">高校名称</td>
											<td class="wd19" style="width:100px;">学科代码及名称</td>
										</tr>
										<div style="display: none;">${num = 0}</div>
										{for item in instituteList}
											{if item[6] < 6}<tr class="wd22">{else}<tr>{/if}
											<td><s:checkbox name="instpIds" fieldValue="${item[0]}" value="false" theme="simple" /></td>
											<td>${num = num + 1}</td>
											<td>${item[1]}</td>
											<td>${item[2]}</td>
											<td>${item[3]}</td>
											<td>${item[4]}</td>
											<td>${item[5]}</td>
											</tr>
										{/for}
									</table>
								</td>
							</tr>
							{/if}
						</table>
					</div>
					
					<div class="btn_div">
						<input type="button" class="cancel btn" value="关闭" />
					</div>
				</textarea>
			</div>
			
			<div id="view_container" style="display:none; clear:both;"></div>
			
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<%--			<script type="text/javascript" src="javascript/expert/popView.js?ver=<%=application.getAttribute("systemVersion")%>"></script>--%>
		<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>