<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>申报数据</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：评审数据&nbsp;&gt;&nbsp;评审奖励
		</div>
		
		<div class="main">
			<div class="main_content">
				<div class="table_main_tr_adv">
					<s:include value="/award/moesocial/application/review/search.jsp"/>
				</div>
				<s:hidden id="searchTypeValue" name="searchTypeValue" value="-1"/>
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按成果名称排序">成果名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="51"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请人姓名排序">申请人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科门类排序">学科门类</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按成果类型排序">成果类型</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请届次排序">申请届次</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn28" href="" class="{if sortColumn == 28}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审状态排序">评审状态</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn29" href="" class="{if sortColumn == 29}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审时间排序">评审时间</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="58"><a id="sortcolumn17" href="" class="{if sortColumn == 17}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按小组评审状态排序">小组评审状态</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="39">申请书</td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" >${item.laData[1]}</a></td>
								<td></td>
								<td><a id="${item.laData[8]}" class="viewapplicant" href="" title="点击查看详细信息" > ${item.laData[2]}</a></td>
								<td></td>
								<td><a id="${item.laData[9]}" class="viewuniversity" href="" title="点击查看详细信息">${item.laData[3]}</a></td>
								<td></td>
								<td>${item.laData[4]}</td>
								<td></td>
								<td>${item.laData[5]}</td>
								<td></td>
								<td>第<span class="session">${item.laData[6]}</span>届</td>
								<td></td>
								<td>
									{if item.laData[11] == 0}待审
	                  				{elseif item.laData[11] == 2}保存
	                  				{elseif item.laData[11] == 3}已提交
	                  				{/if}
	                  			</td>
	                  			<td></td>
								<td>${item.laData[12]}</td>
	                  			<td></td>
								<td>
									{if item.laData[13] == 0}待审
				                    {elseif item.laData[13] == 2&&item.laData[14]==1}不同意（暂存）
				                 	{elseif item.laData[13] == 2&&item.laData[14]==2}同意（暂存）
				                 	{elseif item.laData[13] == 3&&item.laData[14]==1}不同意
				                 	{elseif item.laData[13] == 3&&item.laData[14]==2}同意
				                    {/if}
								</td>
								<td></td>
								<td><a href="award/moesocial/application/apply/download.action?filePath=${item.laData[10]}"  class="downlaod" name="${item.laData[10]}" id="${item.laData[0]}"><img src="image/ico03.gif" width="11" height="12" title="下载" /></a></td>
							</tr>
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
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" name="selectedApplies" namespace="/award/moesocial/application/review" >
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="type" name="type" value="1" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/review/list.js', function(list) {
				list.init();
			});
		</script>
	</body>
</html>
