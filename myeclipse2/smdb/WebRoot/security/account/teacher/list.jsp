<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>教师账号</title>
			<s:include value="/innerBase.jsp" />
			
		</head>
	
		<body>
			<div class="link_bar">
				当前位置：账号管理&nbsp;&gt;&nbsp;教师账号
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/security/account/teacher/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_TEACHER_DELETE,ROLE_SECURITY_ACCOUNT_TEACHER_STATUS,ROLE_SECURITY_ACCOUNT_ASSIGNROLE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有日志" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按通行证排序">通行证</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号所属人员排序">所属人员</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									
									<td width="65"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属院系排序"><s:text name="所在部门" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按所属高校排序">所属高校</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									
									<td width="60"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号起始时间排序">创建时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号有效期限排序">有效期限</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按账号状态排序">账号状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按上次登录时间排序">上次登录时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65">角色分配</td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_TEACHER_DELETE,ROLE_SECURITY_ACCOUNT_TEACHER_STATUS,ROLE_SECURITY_ACCOUNT_ASSIGNROLE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[2]}" class="linkP" href="" title="点击查看详细信息">${item.laData[3]}</a></td>
									<td></td>
									{if item.laData[6] == ""}
									<td class="table_txt_td"><a id="${item.laData[4]}" class="linkD" href="" title="点击查看详细信息">${item.laData[5]}</a></td>
									<td></td>
									{else}
									<td class="table_txt_td"><a id="${item.laData[6]}" class="linkI" href="" title="点击查看详细信息">${item.laData[7]}</a></td>
									<td></td>
									{/if}
									<td class="table_txt_td"><a id="${item.laData[8]}" class="linkA" href="" title="点击查看详细信息">${item.laData[9]}</a></td>
									<td></td>
									<td>${item.laData[10]}</td>
									<td></td>
									<td>${item.laData[11]}</td>
									<td></td>
									<td>
										{if item.laData[12] == 1}
										启用
										{else}
										停用
										{/if}
									</td>
									<td></td>
									<td>
										{if item.laData[13] == ""}
										[尚未登录]
										{else}
										${item.laData[13]}
										{/if}
									</td>
									<td></td>
									<td>
										{if item.laData[14] == ""}
										[未分配]
										{else}
										${item.laData[14]}
										{/if}
									</td>
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
								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_ADD">
									<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="添加" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_STATUS">
									<td width="58"><input id="list_enable" type="button" class="btn1" value="启用" /></td>
									<td width="58"><input id="list_disable" type="button" class="btn1" value="停用" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_ASSIGNROLE">
									<td width="70"><input id="list_district" type="button" class="btn2" value="分配角色" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:include value="/security/account/list.jsp" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/security/account/teacher/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	
</html>