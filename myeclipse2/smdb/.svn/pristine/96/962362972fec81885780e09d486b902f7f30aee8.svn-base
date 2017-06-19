<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>院系管理机构</title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body>
		<div class="link_bar">
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科管理机构&nbsp;&gt;&nbsp;院系管理机构
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:hidden id="namespace" value="unit/department" />
				<s:hidden id="deleteType" name="department" value="5" />
				<div class="table_main_tr_adv">
					<s:include value="/unit/agency/department/search.jsp"/>
				</div>
				
				<textarea id="list_template" style="display:none;">
					<s:hidden id="entityId" name="entityId" />
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20"><input id="check" name="check" type="checkbox"  title="选择所有单位" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按单位名称排序">院系名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="140"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所在高校排序">所属高校</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按单位代码排序">院系代码</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="78"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">院系负责人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按电话排序">院系电话</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按传真排序">院系传真</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
								<td></td>
								<td><a id="${item.laData[8]}" class="linkA" href="">${item.laData[3]}</a></td>
								<td></td>
								<td><a id="${item.laData[0]}" class="linkD" href="">${item.laData[2]}</a></td>
								<td></td>
								<td><a id="${item.laData[7]}" class="link2" href="">${item.laData[4]}</a></td>
								<td></td>
								<td>${item.laData[5]}</td>
								<td></td>
								<td>${item.laData[6]}</td>
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
							<sec:authorize ifAllGranted="ROLE_UNIT_DEPARTMENT_ADD">
								<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_UNIT_DEPARTMENT_DELETE">
								<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_UNIT_DEPARTMENT_MERGE">
								<td width="58"><input id="list_merge" type="button" class="btn1" value="合并" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_INFORMATION_INBOX_ADD">
								<td width="58"><input id="list_sendInBox" type="button" class="btn1" value="<s:text name='发站内信' />" /></td>
							</sec:authorize>
							<%--<td width="58"><input id="list_mergeDept" type="button" class="btn1" value="合并" title="点击合并院系" /></td>
						--%></tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/unit/department">
					<s:hidden id="unitType" name="unitType" value="5" /> 
					<s:hidden id="pagenumber" name="pageNumber" />
					<s:hidden id="type" name="type" />
					<s:hidden id="checkedIds" name="checkedIds" />
					<s:hidden id="mainId" name="mainId" />
					<s:hidden id="name" name="name" />
					<s:hidden id="code" name="code" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/unit/agency/department/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
		</script>
	</body>
</html>