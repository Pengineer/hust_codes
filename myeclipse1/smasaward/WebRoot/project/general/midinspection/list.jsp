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
				项目管理&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">中检项目</span>
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/project/general/midinspection">
						<table class="table_bar">
							<tr height="28px">
								<td align="right">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{'请选择'}--" list="#{'1':'项目名称','2':'负责人', '3':'依托高校','4':'项目子类','5':'学科门类','6':'项目年度'}" />
									<s:textfield id="keyword" name="keyword" cssClass="input wd2" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
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
									<td class="border0" style="width:110px;"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td class="border0" style="width:75px;"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td class="border0" style="width:55px;"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
									<td class="border0" style="width:85px;"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目子类排序">项目子类</a></td>
									<td class="border0" style="width:70px;"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科门类排序">学科门类</a></td>
									<td class="border0" style="width:60px;"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目年度排序">项目年度</a></td>
									<td class="border0" style="width:60px;"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按中检状态排序">中检状态</a></td>
									<td class="border0" style="width:60px;"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按中检时间排序">中检时间</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr class="tr_list2">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td>${item.num}</td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="linkView" href="" title="点击查看详细信息" type="3">${item.laData[2]}</a></td>
									<td>${item.laData[3]}</td>
									<td>${item.laData[4]}</td>
									<td>${item.laData[5]}</td>
									<td>${item.laData[6]}</td>
									<td>${item.laData[7]}</td>
									<td>
										{if item.laData[9] == 1 }不同意
										{elseif item.laData[9] == 2 }同意
										{else}待审
										{/if}		
									</td>
									<td>${item.laData[10]}</td>
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
<!-- 								<td class="border1"><input type="button" value="删除" class="btn" /></td> -->
							</tr>
						</table>
					</textarea>
					
				<!-- 	<s:form id="list" theme="simple" action="delete" namespace="/project/general"> -->
						<div id="list_container" style="display:none;"></div>
			<!-- 		</s:form> -->
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/general/midinspection/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>