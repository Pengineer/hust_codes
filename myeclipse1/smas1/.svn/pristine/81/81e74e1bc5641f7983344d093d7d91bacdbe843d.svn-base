<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>基地项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
		</head>

		<body>
			<div id="top">
				项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;
				<s:if test="isReviewable == 0">
					<span class="text_red">退评项目</span>
				</s:if>
				<s:else>
					<span class="text_red">参评项目</span>
				</s:else>
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/project/instp">
						<table class="table_bar">
							<tr height="28px">
								<s:if test="#session.visitor.user.issuperuser == 1">
									<td width="60px" align="center"><input type="button" id="popExport" value="导出" class="btn" /></td>
								</s:if>
								<s:if test="isReviewable == 1">
									<s:if test="#session.visitor.user.issuperuser == 1">
										<td width="85px" align="center"><input id="matchExpert" type="button" value="自动分配" class="btn isCurYear"  /></td>
									</s:if>
									<td width="85px" align="center"><input id="reMatchExpert" type="button" value="手动分配" class="btn isCurYear"/></td>
									<s:if test="#session.visitor.user.issuperuser == 1">
										<td width="85px" align="center"><input id="deleteMatches" type="button" value="清空匹配" class="btn isCurYear" /></td>
									</s:if>
								</s:if>
								<td align="right">
									<s:if test="isReviewable == 0">
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'负责人','3':'高校名称','6':'基地名称','7':'退评原因'}" />
									</s:if>
									<s:else>
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'负责人','3':'高校名称','4':'基地名称','5':'专家个数','6':'警告'}" />
									</s:else>
									
									<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
									<s:hidden id="isReviewable" name="isReviewable" />
									<s:hidden id="listType" name="listType" />
									<s:hidden id="businessType" name="businessType" />
								</td>
								<td width="50px" align="center"><input id="list_button_query" type="button" value="检索" class="btn" /></td>
							</tr>
						</table>
					</s:form>
			
					<textarea id="list_template" style="display:none;">
						<table id="list_table" class="table_list" cellspacing="0" cellpadding="0">
							<thead id="list_head">
								<tr class="tr_list1">
									<td class="wd0 border0"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
									<td class="wd1 border0">序号</td>
									<td class="border0" style="width:250px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td class="border0" style="width:55px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td class="border0" style="width:110px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
									<td class="border0" style="width:120px;"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按照基地名称排序">基地名称</a></td>
									<s:if test="isReviewable == 1">
										<td class="wd_auto border0">评审专家</td>
										<td class="border0" style="width:60px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按专家个数排序"><img src="image/reviewer.gif" /></a></td>
										<td class="border0" style="width:50px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按警告排序"><img src="image/warning_reviewer.gif" /></a></td>
									</s:if>
									<s:else>
										<td class="border0" style="width:70px;"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按退评原因排序">退评原因</a></td>
										<td class="border0" style="width:60px;"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托学科排序">依托学科</a></td>
									</s:else>
								</tr>
							</thead>
							
							<tbody>
							{for item in root}
								<tr class="tr_list2">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td>${item.num}</td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="1">${item.laData[1]}</a></td>
									<td>${item.laData[2]}</td>
									<td>${item.laData[3]}</td>
									<td>${item.laData[10]}</td>
									<s:if test="isReviewable == 1">
										<td class="table_txt_td">
											{for item2 in item.laData[8]}
												<a title="${item2.uname}&nbsp;${item2.title}" href="expert/toView.action?entityId=${item2.id}&listType=3">${item2.name}</a>&nbsp;
											{/for}
										</td>
										<td><a title="${item.laData[9]}" href="project/instp/toSelectExpert.action?entityId=${item.laData[0]}&isReviewable=1">共
										{if item.laData[8] == null}0{else}${item.laData[8].length}{/if}
										个</a></td>
										<td>{if item.laData[5] != null}<img src="image/flag_yellow.gif" title="${item.laData[5]}" />{/if}</td>
									</s:if>
									<s:else>
										<td>${item.laData[7]}</td>
										<td>${item.laData[6]}</td>
									</s:else>
								</tr>
							{forelse}
								<tr>
									<td align="center">暂无符合条件的记录</td>
								</tr>
							{/for}
							</tbody>
						</table>
						
						<table class="table_list">
							<tr class="tr_list1" style="border-top-width: 0px;">
								<td class="border1" style="width:40px;"><input id="list_add" type="button" value="添加" class="btn" /></td>
								<td class="border1" style="width:40px;"><input id="list_delete" type="button" value="删除" class="btn isCurYear" /></td>
								<s:if test="isReviewable == 0">
									<td class="border1" style="width:40px;"><input type="button" value="参评" onclick="setReview('entityIds', 'list', 1);" class="btn isCurYear" /></td>
								</s:if>
								<s:else>
									<td class="border1" style="width:40px;"><input type="button" value="退评" onclick="setReview('entityIds', 'list', 0);" class="btn isCurYear" /></td>
								</s:else>
							</tr>
						</table>
					</textarea>
					
					<s:form name="selectedProjects" action="groupDeleteProjects" theme="simple" namespace="/project">
						<input type="hidden" name="isReviewable" value="${isReviewable}" />
						<s:iterator value="pageList" status="stat">
							<tr class="tr_list2">
								<td class="wd0">
									<s:checkbox name="projectids" theme="simple"
										fieldValue="%{pageList[#stat.index][0]}" value="false" />
								</td>
								<td class="wd1">
									<s:property value="#stat.index+#session.projectPage.startRow+1" />
								</td>
								<td class="wd11">
									<s:if test="isReviewable == 1">
										<a href="project/viewProject.action?projectid=<s:property value='pageList[#stat.index][0]' />&isReviewable=${isReviewable}"
											title="<s:property value='projectdis.get(pageList[#stat.index][0])' />"><s:property value="pageList[#stat.index][1]" /></a>
									</s:if>
									<s:else>
										<a href="project/viewProject.action?projectid=<s:property value='pageList[#stat.index][0]' />&isReviewable=${isReviewable}">
											<s:property value="pageList[#stat.index][1]" /></a>
									</s:else>
								</td>
								<td class="wd16">
									<s:property value="pageList[#stat.index][2]" />
								</td>
								<td class="wd14">
									<s:property value="pageList[#stat.index][3]" />
								</td>
								<s:if test="isReviewable == 1">
									<td class="wd_auto">
										<s:iterator value="expertlink.get(pageList[#stat.index][0])">
											<a title="<s:property value='uname' />&nbsp;<s:property value='title' />" href="expert/viewExpert.action?expertid=<s:property value='id' />&listType=1"><s:property value="name" /></a>&nbsp;
										</s:iterator>
									</td>
									<td class="wd15" style="text-align:center; padding-left:0;">
										<a title="<s:property value='expertinfo.get(pageList[#stat.index][0])' />" href="project/toSelectExpert.action?projectid=<s:property value='pageList[#stat.index][0]' />&isReviewable=${isReviewable}">共<s:if test="expertlink.get(pageList[#stat.index][0]) == null">0</s:if><s:else><s:property value="expertlink.get(pageList[#stat.index][0]).size()" /></s:else>个</a>
									</td>
									<td class="wd15" style="text-align:center; padding-left:0;">
										<s:if test="pageList[#stat.index][5] != null ">
											<img src="image/flag_yellow.gif" title="<s:property value='pageList[#stat.index][5]' />" />
										</s:if>
									</td>
								</s:if>
								<s:else>
									<td class="wd14">
										<s:property value="pageList[#stat.index][0]" />
									</td>
									<td class="wd_auto">
										<s:property value="projectdis.get(pageList[#stat.index][0])" />
									</td>
								</s:else>
							</tr>
						</s:iterator>
					</s:form>
					
					<s:form id="list" theme="simple" action="delete" namespace="/project/instp">
						<div id="list_container" style="display:none;"></div>
						<s:hidden id="isReviewable" name="isReviewable" />
						<s:hidden id="listType" name="listType" />
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/application/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/instp.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>
