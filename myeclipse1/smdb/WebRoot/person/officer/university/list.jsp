<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>校级管理人员</title>
		<s:include value="/innerBase.jsp" />			
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科人员数据&nbsp;&gt;&nbsp;社科管理人员&nbsp;&gt;&nbsp;校级管理人员
		</div>
		<s:hidden id="canMerge" name="canMerge" value="%{ personService.isPersonTablesExpired() }"/>	
		<div class="main">
			<div class="main_content">
				<div class="table_main_tr_adv">
					<s:include value="/person/officer/university/search.jsp"/>
				</div>

				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有人员" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="30">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="100"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按姓名排序">姓名</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按性别排序">性别</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按所在高校排序">所在高校</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="140"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按所在部门排序">所在部门</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按职务排序">职务</a></td>
								<sec:authorize ifAnyGranted="ROLE_SYSTEM_KEY,ROLE_SYSTEM_KEY_VIEW,ROLE_SYSTEM_KEY_MODIFY">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="50"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按职务排序">标识</a></td>
								</sec:authorize>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td><input type="checkbox" name="entityIds" value="${item.laData[4]}" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td class="table_txt_td">
									<a id="${item.laData[4]}" class="link1" href="javascript:void(0)" title="点击查看详细信息">${item.laData[0]}</a>
								</td>
								<td></td>
								<td>${item.laData[1]}</td>
								<td></td>
								<td><a id="${item.laData[5]}" class="linkA" href="javascript:void(0)" title="点击查看详细信息">${item.laData[2]}</a></td>
								<td></td>
								<td>${item.laData[3]}</td>
								<td></td>
								<td>${item.laData[6]}</td>
								<sec:authorize ifAnyGranted="ROLE_SYSTEM_KEY,ROLE_SYSTEM_KEY_VIEW,ROLE_SYSTEM_KEY_MODIFY">
									<td></td>
									<td>
										<a class="toggleKey" href="javascript:void(0)" alt="${item.laData[7]}" iskey="${item.laData[8]}">
										{if item.laData[8] == 1}<img src="image/person_on.png" title="重点人，点击切换至非重点人" />{else}<img src="image/person_off.png" title="非重点人，点击切换至重点人" />{/if}
										</a>
									</td>
								</sec:authorize>
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
							<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_UNIVERSITY_ADD">
								<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_UNIVERSITY_DELETE">
								<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PERSON_OFFICER_UNIVERSITY_MERGE">
								<td width="58"><input id="list_merge" type="button" class="btn1" value="合并" /></td>
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_INFORMATION_INBOX_ADD">
								<td width="58"><input id="list_sendInBox" type="button" class="btn1" value="发站内信" /></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<s:form id="list" theme="simple" action="delete" namespace="/person/universityOfficer">
					<s:hidden id="checkedIds" name="checkedIds" />
					<s:hidden id="mainId" name="mainId" />
					<div id="list_container" style="display:none;"></div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/person/officer/university/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
			seajs.use('javascript/person/pop_merge.js', function(merge) {
				$(function(){
					merge.popMergeDialog(1,"person/universityOfficer");
				})
			});
		</script>
	</body>
</html>
