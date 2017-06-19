<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
	<title>初审</title>
	<s:include value="/innerBase.jsp" />

  </head>
  
  <body>
	<div class="link_bar" id="view_project_general">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;专项任务项目&nbsp;&gt;&nbsp;申请数据&nbsp;&gt;&nbsp;初审
			</div>

			<div class="main">
				<div class="p_box_t">
					<div class="p_box_t_t">初审配置</div>
					<div class="p_box_t_b">
						<img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" />
					</div>
				</div>
				<div class="p_box_body">
			        <table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tbody>
							<tr class="table_tr7">
								<td class="head_title1" width="80">项目年度</td>
								<td class="value"><s:select cssClass="select" id="select_year" name="year" list="%{baseService.getYearMap()}" /></td>
							</tr>
							<tr class="table_tr7">
								<td class="head_title1">初审规则</td>
								<td class="value">（1）职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁（1973年1月1日以后出生）；</td>
								<td class="value">（2）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、专项任务项目、重大攻关项目、后期资助项目、基地项目；</td>
								<td class="value">（3）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、专项任务项目、重大攻关项目、后期资助项目、基地项目；</td>
								<td class="value">（4）青年基金限制：已获得过青年基金项目资助的申请人（不管结项与否）不得再次申请青年基金项目，即申请人只能获得一次青年基金项目资助。</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align:center; margin-top:10px;">
						<input id="firstAudit" class="btn2" type="button" value="开始初审" />
						&nbsp;&nbsp;&nbsp;
						<input id="viewResult" class="btn2" type="button" value="查看结果" />
					</div>
				</div>
			</div>
			<div id="firstAuditResult" class="main">
				<div class="p_box_t">
					<div class="p_box_t_t">初审结果</div>
					<div class="p_box_t_b">
						<img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" />
					</div>
				</div>
				<div class="p_box_body">
					<div id="main">
						<s:form id="search" theme="simple" action="simpleSearch" namespace="/project/special/application/firstAudit">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
								<tr class="table_main_tr">
									<td align="right"><span class="choose_bar">
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
											list="#{
												'0':'项目名称',
												'1':'项目类别',
												'13':'依托高校',
												'3':'申请人',
												'12':'初审时间',
												'11':'初审结果'
											}"
										/>
									</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
										<s:hidden id="list_pagenumber" name="pageNumber" />
										<s:hidden id="list_sortcolumn" name="sortColumn" />
										<s:hidden id="list_pagesize" name="pageSize" />
										<s:hidden id="search_year" name="year" />
									</td>
									<td width="66"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
								</tr>
							</table>
						</s:form>
	
						<textarea id="list_template" style="display:none;">
							<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
								<thead id="list_head">
									<tr class="table_title_tr">
										<td width="40">序号</td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="150"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目类别排序">项目类别</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="90"><a id="sortcolumn13" href="" class="{if sortColumn == 13}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="70"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按申请人排序">申请人</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="70"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按初审时间排序">初审时间</a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按初审结果排序">初审结果</a></td>
									</tr>
								</thead>
								<tbody>
								{for item in root}
									<tr>
										<td>${item.num}</td>
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
									</tr>
								{forelse}
									<tr>
										<td align="center">无数据记录</td>
									</tr>
								{/for}
								</tbody>
							</table></div>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
								<tr class="table_main_tr2">
									<td width="4"></td>
									<td width="58"><input id="export" type="button" class="btn1" value="导出" /></td>
								</tr>
							</table>
						</textarea>
						<div id="list_container" style="display:none;"></div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/special/application/firstAudit/firstAuditConfig.js', function(view) {
					view.init();
				});
			</script>
  </body>
  
</html>
