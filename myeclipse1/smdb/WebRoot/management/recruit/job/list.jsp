<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>职位管理</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<div class="link_bar">
			当前位置：招聘管理&nbsp;&gt;&nbsp;职位管理
		</div>
		<div class="main">
			<div class="main_content">					
				<s:form id="search" theme="simple" action="list" namespace="/management/recruit/job">
					 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr">
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
									list="#{
										'0':'职位',
										'1':'招聘人数',
										'2':'发布时间',
										'3':'截止时间'
									}"
								/>
							</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="list_pagesize" name="pageSize" />
							</td>
							<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
						</tr>
					</table> 
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20"><input id="check" name="check" type="checkbox" title="点击全选/不选本页所有人员" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="80"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按职位排序">职位</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按招聘人数排序">招聘人数</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布时间排序">发布时间</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按截止时间排序">截止时间</a></td>
							</tr>
						</thead>
						<tbody style = "text-align:center">
						{for item in root}
							<tr>
								<td><input type="checkbox" name="entityIds" value="${item.laData[4]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td ><a id="${item.laData[4]}" class="link1" href="javascript:void(0)" title="点击查看详细信息">${item.laData[0]}</a></td>
								<td></td>
								<td>${item.laData[1]}</td>
								<td></td>
								<td class= "date">${item.laData[2]}</td>
								<td></td>
								<td  class  = "date">${item.laData[3]}</td>
						{forelse}
							<tr>
								<td align="center">暂无符合条件的记录</td>
							</tr>
						{/for}
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
							<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/management/recruit/job">
					<s:hidden id="checkedIds" name="checkedIds" />
					<s:hidden id="pagenumber" name="pageNumber" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/management/recruit/job/list.js', function(list) {
				$(function(){
					list.init();	
				})
			});
		</script>
	</body>
</html>