<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>经费详情</title>
		<s:include value="/innerBase.jsp" />
		<style>
		#list{
		margin-bottom:10px;
		}
		</style>
	</head>
	
	<body style = "width:700px;">
		<div>
			<div>
				<s:form id="search" theme="simple" action="" namespace="/funding">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<tr class="table_main_tr">
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
									list="#{
										'0':'名称',
										'1':'收款人',
										'2':'所在单位',
										'3':'备注'			
									}"
								/>
							</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="list_pagesize" name="pageSize" />
							</td>
							<td width="66"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
						</tr>
					
					</table>
				</s:form>
				<textarea id="list_template" style="display:none;">
				<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<thead id="list_head">
						<tr class="table_title_tr">
							<td width="30">序号</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>							
							<td>名称</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="80">{if fundingType == 1}经费子类型 {else} 经费类型 {/if}</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="80">{if fundingType == 1}经费子子类型 {else} 经费子类型 {/if}</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="80"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按姓名排序">收款人</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td  width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按姓名排序">所在单位</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="80"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按原高校代码排序">金额(万元)</a></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td style = "min-width:50px"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按姓名排序">备注</td>
						</tr>
					</thead>
					<tbody style = "text-align:center">
						{for item in root}
						<tr>
							<td>${item.num}</td>
							<td></td>
							<td class = "table_txt_td">${item.laData[1]}</td>
							<td></td>
						{if item.laData[6] != ""}
							<td>${item.laData[6]}</td>
							<td></td>
						{/if}
							<td>${item.laData[7]}</td>
							<td></td>
							<td>${item.laData[2]}</td>
							<td></td>
							<td>${item.laData[3]}</td>
							<td></td>
							<td>${item.laData[4]}</td>
							<td></td>
							<td class = "table_txt_td">${item.laData[5]}</td>
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
				<s:form id="list" theme="simple" action="delete" namespace="/verify">
				<s:hidden id="pagenumber" name="pageNumber" />
				<s:hidden id="checkedIds" name="checkedIds" />
				<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/fund/popFundList.js', function(list) {
				$(function(){
						list.init();
				})
			});
		</script>
	</body>
</html>