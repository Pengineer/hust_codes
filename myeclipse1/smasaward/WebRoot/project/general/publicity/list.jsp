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
				项目管理&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span class="text_red"><s:if test="#session.radioValue == 1">公示项目</s:if><s:else>未公示的项目</s:else></span>
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/project/general/publicity">
						<table class="table_bar">
							<tr height="28px">
<%--								<td width="50px" align="center"><input type="button" id="popPrint" value="打印" class="btn" /></td>--%>
								<td align="left"><span>公示项目：</span><s:radio list="#{'1':'是','2':'否'}" name="listFlag" value="#session.radioValue" /></td>
								<td align="right">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'负责人','3':'高校名称'}" />
									<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
									<s:hidden id="isReviewable" name="isReviewable" />
									<s:hidden id="listType" name="listType" />
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
									<td class="border0" style="width:110px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td class="border0" style="width:55px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td class="border0" style="width:85px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
									<td class="border0" style="width:70px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按票数/总分排序">票数/分数</a></td>
									<td class="border0" style="width:60px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按是否公示排序>">公示状态</a></td>
									<td class="border0" style="width:60px;"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按立项状态排序">立项状态</a></td>
									<td class="border0" style="width:60px;"><img src="image/warning_reviewer.gif" title="添加举报信息>"/></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr class="tr_list2">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td>${item.num}</td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="2">${item.laData[1]}</a></td>
									<td>${item.laData[2]}</td>
									<td>${item.laData[3]}</td>
									<td>${item.laData[5]}/${item.laData[6]}</td>
									<td><a class="isPublic" style="cursor:pointer;" title="点击修改公示状态">{if item.laData[7] == 0}否{else}是{/if}</a></td>
									<td>{if item.laData[8] == 0}未立项{else}<span style="color:green;">已立项</span>{/if}</td>
									<td style="text-align:center; padding-left:0;">
										<img src="image/modify_entry.gif" dir="${item.laData[0]}" class="addInform" style="cursor:pointer;" title="点击添加举报信息" />
										<s:hidden id="entityId" value="${item.laData[0]}" />
										<s:hidden id="publicStatus" value="${item.laData[7]}" />
										<s:hidden id="isGranted" value="${item.laData[8]}" />
									</td>
								</tr>
							{forelse}
								<tr>
									<td align="center">暂无符合条件的记录</td>
								</tr>
							{/for}
							</tbody>
						</table>
						
						<table class="table_list">
							<tr class="tr_list1">
								<s:if test="#session.radioValue == 1">
									<td class="border1" style="width:60px;"><input id="allGrant" type="button" value="全部立项" class="btn" style="width:80px;"/></td>
									<td class="border1" style="width:70px;"><input id="selectedGrant" type="button" value="选择立项" class="btn" style="width:80px;" /></td>
									<td class="border1" style="width:80px;"><input id="exportPubList" type="button" value="导出公示清单" class="btn" style="width:100px;" /></td>
								</s:if>
							</tr>
						</table>
						
					</textarea>
					
<%--					<s:form id="list" theme="simple" action="delete" namespace="/project/general/publicity">--%>
						<div id="list_container" style="display:none;"></div>
<%--					</s:form>--%>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/general/publicity/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>
