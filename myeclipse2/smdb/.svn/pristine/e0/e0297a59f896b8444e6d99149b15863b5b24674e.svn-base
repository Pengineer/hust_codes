<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>后期资助项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;后期资助项目&nbsp;&gt;&nbsp;立项数据
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/post/application/granted/search.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有项目" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="80"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按批准号排序">批准号</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
								</s:if>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目类别排序">项目子类</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科门类排序">学科门类</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目年度排序">项目年度</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目状态排序">项目状态</a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" type="2">${item.laData[3]}</a></td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>
										<s:hidden id="directors" value="${item.laData[4]}" name="${item.laData[5]}" cssClass="directors" />
									</td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td></td>
									<td>
										{if item.laData[6]==""}${item.laData[7]}
										{else}<a id="${item.laData[6]}" class="view_university" href="" title="点击查看详细信息">${item.laData[7]}</a>
										{/if}
									</td>
								</s:if>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
									<td>${item.laData[9]}</td>
									<td></td>
									<td>${item.laData[10]}</td>
									<td></td>
									<td>
										{if item.laData[12] == 0}
										{elseif item.laData[12] == 1}在研
										{elseif item.laData[12] == 3}已中止
										{elseif item.laData[12] == 2}已结项
										{elseif item.laData[12] == 4}已撤项
										{elseif item.laData[12] == 5}已鉴定
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
								<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_ADD">
									<td width="58"><input class="btn1" type="button" id="list_add" value="添加" />
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="删除" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_DATA_EXPORT">
								<td width="58"><input id="list_export" type="button" class="btn1" value="导出" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_PUBLISH">
									<td width="58"><input id="list_publish" type="button" class="btn1" value="发布" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_POST_APPLICATION_GRANTED_PUBLISH">
									<td width="70"><input id="list_notPublish" type="button" class="btn2" value="取消发布" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/project/post/application/granted">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/post/application/granted/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	
</html>