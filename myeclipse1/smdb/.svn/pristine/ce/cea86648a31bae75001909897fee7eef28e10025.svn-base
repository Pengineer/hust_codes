<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>我的项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：我的项目
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="%{#request.url}" namespace="/project">
					</s:form>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DELETE,ROLE_PROJECT_INSTP_APPLICATION_APPLY_DELETE,ROLE_PROJECT_POST_APPLICATION_APPLY_DELETE,
										ROLE_PROJECT_KEY_APPLICATION_APPLY_DELETE,ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td>项目名称</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70">项目类型</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70">项目子类</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70">负责人</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70">本人排名</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100">依托高校</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70">项目年度</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70">项目状态</td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DELETE,ROLE_PROJECT_INSTP_APPLICATION_APPLY_DELETE,ROLE_PROJECT_POST_APPLICATION_APPLY_DELETE,
										ROLE_PROJECT_KEY_APPLICATION_APPLY_DELETE,ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
									</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" name="${item.laData[11]}" class="link1" href="" title="点击查看详细信息" type="7">${item.laData[1]}</a></td>
									<td></td>
									<td>${item.laData[4]}</td>
									<td></td>
									<td>${item.laData[5]}</td>
									<td></td>
									<td>
										{if item.laData[9]==""}${item.laData[10]}
										{else}<a id="${item.laData[9]}" class="view_director" href="" title="点击查看详细信息">${item.laData[10]}</a>
										{/if}
									</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>
										{if item.laData[2]==""}${item.laData[3]}
										{else}<a id="${item.laData[2]}" class="view_university" href="" title="点击查看详细信息">${item.laData[3]}</a>
										{/if}
									</td>
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>
										{if item.laData[8] == 0}未立项
										{elseif item.laData[8] == 1}在研
										{elseif item.laData[8] == 3}已中止
										{elseif item.laData[8] == 2}已结项
										{elseif item.laData[8] == 4}已撤项
										{elseif item.laData[8] == 5}已鉴定
										{else}
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
							<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_ADD,ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_ADD,
														ROLE_PROJECT_INSTP_APPLICATION_APPLY_ADD,ROLE_PROJECT_KEY_APPLICATION_APPLY_ADD,ROLE_PROJECT_POST_APPLICATION_APPLY_ADD">
								<td width="58"><input id="list_apply" type="button" class="btn1" value="申请" /></td>
							</sec:authorize>
							<td align="right" style="color:#FFF;"></td></tr>
						</table>
					</textarea>
					<div id="list_container" style="display:none;"></div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/list_my_project.js', function(list) {
					list.init();
				});
			</script>
		</body>
	
</html>