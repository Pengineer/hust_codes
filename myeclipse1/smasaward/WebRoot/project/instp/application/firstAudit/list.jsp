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
				项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">初审结果</span>
			</div>
			
			<div id="waiting" style="display:none; text-align:center"><span style='color: red;'>初审更新中，请稍后……</span></div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/project/instp/firstAudit">
						<table class="table_bar">
							<tr height="28px">
								<td align="right">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'项目子类', '3':'负责人','4':'高校名称','5':'初审结果'}" />
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
									<td class="border0" style="width:160px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td class="border0" style="width:80px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目类型排序">项目类型</a></td>
									<td class="border0" style="width:55px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td class="border0" style="width:100px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
									<td class="border0" >初审结果</td>
									<td class="border0" style="width:80px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按初审时间排序">初审时间</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr class="tr_list2">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td>${item.num}</td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="4">${item.laData[1]}</a></td>
									<td>${item.laData[2]}</td>
									<td>${item.laData[3]}</td>
									<td>${item.laData[4]}</td>
									<td>${item.laData[5]}</td>
									<td>${item.laData[6]}</td>
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
								<s:if test="(#session.visitor.user.issuperuser == 1)">
									<td class="border1" style="width:40px;"><input id="exportCheck" type="button" value="导出" class="btn" /></td>
									<td class="border1" style="width:40px;"><input id="updateCheck" type="button" value="更新" class="btn isCurYear" /></td>
								</s:if>
							</tr>
						</table>
					</textarea>
					
					<div id="list_container" style="display:none;"></div>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/application/firstAudit/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>