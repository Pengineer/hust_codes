<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>高校参评专家跨类项目数</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/reviewstatic/reviewstatic.css" />
		</head>
		<body>
			<div id="top">
				统计分析&nbsp;&gt;&gt;&nbsp;专家评审&nbsp;&gt;&gt;&nbsp;<span class="text_red">高校参评专家跨类项目数</span>
			</div>			
			<div class="main">
				<div class="main_content">
					<div id="simple_search">
						<s:form id="search" theme="simple" action="list" namespace="/statistic/review/univReviewer">
							<table class="table_bar">
								<tr height="28px">
								
									<%--<td width="50px" align="center"><input type="button" id="export" value="导出" class="btn"/></td>
									--%>
									<td align="right" style="vertical-align:middle;">
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'高校名称','2':'高校代码'}" />
										<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
	<%--								包含备评专家--%>
	<%--								<s:radio theme="simple" name="isContainP" value="%{isContainP}" list="#{'1':'是','0':'否'}" />--%>
										<s:hidden id="list_pagenumber" name="pageNumber" />
										<s:hidden id="list_sortcolumn" name="sortColumn" />
										<s:hidden id="list_pagesize" name="pageSize" />
										<s:hidden id="listType" name="listType" />
									</td>
									<td width="50px" align="center"><input id="list_button_query" type="button" value="检索" class="btn" /></td>
									<td width="50px" align="center"><input id="list_search_more" type="button" value="更多条件" class="btn" /></td>
								</tr>
							</table>
						</s:form>
					</div>
					<div id="adv_search" style="display:none;">
						<s:form id="advSearch" action="advSearch" theme="simple" namespace="/statistic/review/univReviewer">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style="margin-top:10px;">
								<tr style="height:30px;font-size:12px;">
									<td width="100"></td>
									<td width="200"><s:checkbox name="keyword1" tooltip="选择过滤条件">一般项目条件&nbsp;&nbsp;</s:checkbox></td>
									<td width="200"><s:checkbox name="keyword2" tooltip="选择过滤条件">基地项目条件&nbsp;&nbsp;</s:checkbox></td>
								</tr>
							</table>
							<table  width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr height="30px">
									<td align="right"></td>
									<td align="right" width="60"><input id="list_button_advsearch" type="button" value="检索" class="btn" /></td>
									<td align="right" width="80"><input id="list_search_hide" type="button" value="隐藏条件" class="btn" /></td>
								</tr>
							</table>
						</s:form>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" class="table_list" cellspacing="0" cellpadding="0"> 
							<thead id="list_head">
								<tr class="tr_list1">
									<td class="wd1 border0">序号</td>
									<td class="border0" style="width:300px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
									<td class="border0" style="width:300px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校代码排序">高校代码</a></td>
									<td class="border0" style="width:100px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击安是否参评排序">是否参评</a></td>
									<td class="border0"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按参评专家数排序">参评专家总数</a></td>
									<td class="border0">参评一般项目专家数</td>
									<td class="border0">参评基地项目专家数</td>
								</tr>
							</thead>
							<tbody>    
							{for item in root}
								<tr class="tr_list2">
									<td class="wd1">${item.num}</td>
									<td class="wd11"><a id="${item.laData[1]}" class="linkUniv" href="" title="点击查看详细信息">${item.laData[0]}</a></td>
									<td class="wd11">${item.laData[1]}</td>
									<td class="wd11">${item.laData[2]}</td>
									<td class="wd_auto">${item.laData[3]}</td>
									<td class="wd_auto">${item.laData[4]}</td>
									<td class="wd_auto">${item.laData[5]}</td>
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
							</tr>
						</table>
					</textarea>
					
<%--					<s:form id="list" theme="simple" action="" namespace="/statistic/review/university">--%>
						<div id="list_container" style="display:none;"></div>
<%--					</s:form>--%>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/statistic/listUnivReviewer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>