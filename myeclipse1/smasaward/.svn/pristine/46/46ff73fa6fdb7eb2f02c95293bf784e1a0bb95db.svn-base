<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
		</head>
		
		<body>
			<div id="top">
				<s:if test="isReviewable == 0">
					项目管理&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">退评项目</span>
				</s:if>
				<s:else>
					项目管理&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">参评项目</span>
				</s:else>
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/project/general">
						<table class="table_bar">
							<tr height="28px">
								<s:if test="#session.visitor.user.issuperuser == 1">
									<td width="60px" align="center"><input type="button" id="popExport" value="导出" class="btn" /></td>
								</s:if>
								<s:if test="isReviewable == 1">
									<s:if test="#session.visitor.user.issuperuser == 1">
										<td width="85px" align="center"><input id="matchExpert" type="button" value="自动匹配" class="btn isCurYear" /></td>
									</s:if>
									<td width="85px" align="center"><input id="reMatchSelected" type="button" value="手动匹配" class="btn isCurYear" /></td>
									<s:if test="#session.visitor.user.issuperuser == 1">
										<td width="85px" align="center"><input id="deleteMatches" type="button" value="清空匹配" class="btn isCurYear" /></td>
									</s:if>
									<td width="85px" align="center"><input id="grantedCheck" type="button" value="立项核算" class="btn isCurYear" /></td>
								</s:if>
								<td align="right">
									<s:if test="isReviewable == 0">
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'负责人','3':'高校名称','6':'院系名称','4':'学科代码','5':'学科名称','7':'退评原因'}" />
									</s:if>
									<s:else>
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'负责人','3':'高校名称','6':'院系名称','4':'学科代码','5':'学科名称'}" />
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
									<td class="border0" style="width:200px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td class="border0" style="width:55px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td class="border0" style="width:100px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
									<td class="border0" style="width:110px;"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按照院系排序">院系名称</a></td>
									<s:if test="isReviewable == 1">
										<td class="wd_auto border0">评审专家</td>
										<td class="border0" style="width:50px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按专家个数排序"><img src="image/reviewer.gif" /></a></td>
										<td class="border0" style="width:40px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按警告排序"><img src="image/warning_reviewer.gif" /></a></td>
										<td class="border0" style="width:75px;"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按票数/总分排序">票数/分数</a></td>
										<td class="border0" style="width:70px;"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按立项状态排序">申报状态</a></td>
										<td class="border0" style="width:70px;"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按立项状态排序">拟立项状态</a></td>
									</s:if>
									<s:else>
										<td class="border0" style="width:110px;"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按退评原因排序">退评原因</a></td>
										<td class="border0" style="width:100px;"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托学科排序">依托学科</a></td>
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
									<td>${item.laData[14]}</td>
									<s:if test="isReviewable == 1">
										<td class="table_txt_td">
											{for item2 in item.laData[11]}
												<a title="${item2.uname}&nbsp;${item2.title}" href="expert/toView.action?entityId=${item2.id}&listType=1&projectId=${item.laData[0]}">${item2.name}</a>&nbsp;
											{/for}
										</td>
										<td><a title="${item.laData[12]}" class="isCurYear" href="project/general/toSelectExpert.action?entityId=${item.laData[0]}&isReviewable=1">共
										{if item.laData[11] == null}0{else}${item.laData[11].length}{/if}
										个</a></td>
										<td>{if item.laData[5] != null}<img src="image/flag_yellow.gif" title="${item.laData[5]}" />{/if}</td>
										<td>${item.laData[8]}/${item.laData[9]}</td>
										<td>
											{if item.laData[15] == 0}待审
											{elseif item.laData[15] == 1}不同意
											{elseif item.laData[15] == 2}同意
											{/if}
										</td>
										<td><a class="isGranting" style="cursor:pointer;" title="点击修改拟立项状态">
											{if item.laData[7] == 0}不立项
											{elseif item.laData[7] == 1}拟立项
											{elseif item.laData[7] == 2}确定立项
											{/if}</a>
											<s:hidden value="${item.laData[7]}" />
											<s:hidden value="${item.laData[0]}" />
										</td>
									</s:if>
									<s:else>
										<td>${item.laData[13]}</td>
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
									<td class="border1" style="width:40px;"><input id="review" type="button" value="参评"  class="btn isCurYear" /></td>
								</s:if>
								<s:else>
									<td class="border1" style="width:40px;"><input id="unReview" type="button" value="退评"  class="btn isCurYear" /></td>
								</s:else>
							</tr>
						</table>
					</textarea>
					
					<s:form id="list" theme="simple" action="delete" namespace="/project/general">
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
			<script type="text/javascript" src="javascript/project/general/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/general/general.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>