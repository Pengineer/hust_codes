<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>省级管理机构</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<div class="link_bar">
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科管理机构&nbsp;&gt;&nbsp;省级管理机构
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:hidden id="namespace" value="unit/agency/province" />
				<s:hidden id="deleteType" name="province" value="2" />
				<div id="simple_search"><!-- 初级检索 -->	
					<s:form theme="simple" id="search" action="list" namespace="/unit/agency/province">
						<s:include value="/unit/agency/simpleSearch.jsp"/>
					</s:form>
				</div>
				
				<div id="adv_search" style="display:none;"><!-- 高级检索 -->
					<s:form id="advSearch" action="advSearch" namespace="/unit/agency/province" theme="simple">
						<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>	
						<s:include value="/unit/agency/advSearch.jsp"/>
					</s:form>
				</div>
				
				<textarea id="list_template" style="display:none;">
					<s:hidden id="provinceid" name="provinceid" />
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20"><input id="check" name="check" type="checkbox"  title="选择所有单位" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按单位名称排序">单位名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="64"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按单位代码排序">单位代码</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="78"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">单位负责人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按社科管理部门排序">社科管理部门</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="78"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按部门负责人排序">部门负责人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按电话排序">电话</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按传真排序">传真</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[2]}</a></td>
								<td></td>
								<td><a id="${item.laData[0]}" class="linkA" href="">${item.laData[1]}</a></td>
								<td></td>
								<td><a id="${item.laData[8]}" class="link2" href="">${item.laData[3]}</a></td>
								<td></td>
								<td>${item.laData[4]}</td>
								<td></td>
								<td><a id="${item.laData[9]}" class="link2" href="">${item.laData[5]}</a></td>
								<td></td>
								<td>${item.laData[6]}</td>
								<td></td>
								<td>${item.laData[7]}</td>
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
							<sec:authorize ifAllGranted="ROLE_UNIT_PROVINCE_ADD">
								<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_UNIT_PROVINCE_DELETE">
								<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_INFORMATION_INBOX_ADD">
								<td width="58"><input id="list_sendInBox" type="button" class="btn1" value="发站内信" /></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/unit/agency/province">
					<s:hidden id="unitType" name="unitType" value="2" /> 
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="type" name="type" value="1" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/unit/agency/province/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
		</script>
		<script type="text/javascript">
			seajs.use('javascript/unit/validate.js', function(validate) {
				$(function(){
					validate.validAgency();
				})
			});
		</script>
	</body>
</html>