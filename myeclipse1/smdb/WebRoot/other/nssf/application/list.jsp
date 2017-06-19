<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>国家社会科学基金项目申请数据</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：社科其他数据&nbsp;&gt;&nbsp;国家社会科学基金项目&nbsp;&gt;&nbsp;申请数据
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="search" theme="simple" action="list" namespace="/other/nssf/application">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr">
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
									list="#{
										'0':'项目名称',
										'1':'项目类别',
										'2':'学科',
										'3':'项目负责人',
										'4':'性别',
										'5':'民族',
										'6':'出生日期',
										'7':'依托单位',
										'8':'所在省区市',
										'9':'成果名称',
										'10':'年份'
									}"
								/>
							</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="list_pagesize" name="pageSize" />
							</td>
							<td width="66"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
						</tr>
					</table>
				</s:form>

				<textarea id="list_template" style="display:none;"><div style="overflow-x:scroll">
					<table id="list_table" width="1200" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr">
								<td width="40">序号</td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td>           <a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按项目名称排序">项目名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按项目类别排序">项目类别</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="65"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按学科排序">学科</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按项目负责人排序">项目负责人</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按性别排序">性别</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="90"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按民族排序">民族</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按出生日期排序">出生日期</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按依托单位排序">依托单位</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按所在省区市排序">所在省区市</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按成果名称排序">成果名称</a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<td width="70"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="按年份排序">年份</a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td>${item.num}</td>
								<td></td>
								<td>${item.laData[0]}</td>
								<td></td>
								<td>${item.laData[1]}</td>
								<td></td>
								<td>${item.laData[2]}</td>
								<td></td>
								<td>${item.laData[3]}</td>
								<td></td>
								<td>${item.laData[4]}</td>
								<td></td>
								<td>${item.laData[5]}</td>
								<td></td>
								<td>${item.laData[6]}</td>
								<td></td>
								<td>${item.laData[7]}</td>
								<td></td>
								<td>${item.laData[8]}</td>
								<td></td>
								<td>${item.laData[9]}</td>
								<td></td>
								<td>${item.laData[10]}</td>
							</tr>
						{forelse}
							<tr>
								<td align="center">暂无符合条件的记录</td>
							</tr>
						{/for}
						</tbody>
					</table></div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<sec:authorize ifAllGranted="ROLE_OTHER_NSSF_FIRST_AUDIT">
								<td width="58"><input id="firstAudit" type="button" class="btn1" value="初审" /></td>
							</sec:authorize>
						</tr>
					</table>
				</textarea>
				<div id="list_container" style="display:none;"></div>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/other/nssf/application/list.js', function(list) {
				$(function(){
					list.init();
				})
			});
		</script>
	</body>
</html>