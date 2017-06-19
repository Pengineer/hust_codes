<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>模板管理</title>
		<s:include value="/innerBase.jsp" />
		<link href="tool/jquery.ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body style="">
		<div class="main" style="width:400px;padding-bottom:30px;">
			<div class="main_content">					
				<s:form id="search" theme="simple" action="list" namespace="/management/recruit/template">
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有人员" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="200"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按名称排序">名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按创建日期排序">创建日期</a></td>
							</tr>
						</thead>
						<tbody style = "text-align:center">
						{for item in root}
							<tr>
								<td><input class="checkbox_select" type="checkbox" name="entityIds" value="${item.laData[3]}" alt="${item.laData[0]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td >${item.laData[0]}</td>
								<td></td>
								<td class = "date">${item.laData[2]}</td>
						{forelse}
							<tr>
								<td align="center">暂无符合条件的记录</td>
							</tr>
						{/for}
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr3"><td></td></tr>
					</table>
				</textarea>
				
				<s:hidden id="checkedIds" name="checkedIds" />
				<div id="list_container" style="display:none;"></div>
				<div>
					当前选择:
					<label id="trimedName" name="checkboxPop" style="cursor:default;">
						
					</label>
				</div>
				<div class="btn_div_view">
					<input id="confirm" class="btn1" type="submit" value="确定" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/management/recruit/job/popSelectTemplate.js', function(list) {
				$(function(){
					list.init();	
				})
			});
		</script>
	</body>
</html>
