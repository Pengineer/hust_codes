<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>后期资助项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：评审项目&nbsp;&gt;&nbsp;后期资助项目&nbsp;&gt;&nbsp;申请数据
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/post/application/review/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="30">序号</td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目名称排序">项目名称</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="40"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按负责人排序">负责人</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按依托高校排序">依托高校</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目类别排序">项目子类</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="52"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按学科门类排序">学科门类</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="52"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按项目年度排序">项目年度</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="52"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审状态排序">评审状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按评审时间排序">评审时间</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="51"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按小组评审状态排序">小组评审状态</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="38"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按分数排序">分数</a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="39">申请书</td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="点击查看详细信息" type="10">${item.laData[1]}</a></td>
									<td></td>
									<td><a id="${item.laData[4]}" class="view_applicant" href="" title="点击查看详细信息">${item.laData[5]}</a></td>
									<td></td>
									<td><a id="${item.laData[2]}" class="view_university" href="" title="点击查看详细信息">${item.laData[3]}</a></td>
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
									<td>
										{if item.laData[9]==0}待审
										{elseif item.laData[9]==2}暂存
										{elseif item.laData[9]==3}提交
										{else}
										{/if}
									</td>
									<td></td>
									<td>${item.laData[10]}</td>
									<td></td>
									<td>
										{if item.laData[13]==0}待审
										{elseif item.laData[13]==2 && item.laData[14]==1}不同意（暂存）
										{elseif item.laData[13]==2 && item.laData[14]==2}同意（暂存）
										{elseif item.laData[13]==3 && item.laData[14]==1}不同意
										{elseif item.laData[13]==3 && item.laData[14]==2}同意
										{else}
										{/if}
									</td>
									<td></td>
									<td>${item.laData[11]}</td>
									<td></td>
									<td>
										{if item.laData[12]!=null && item.laData[12]!=""}
											<a id="${item.laData[0]}" name="${item.laData[0]}"  class="download_post_3" href="">
												<img src="image/ico03.gif" title="下载" /></a>
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
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="" namespace="/project/post/application/review">
						<s:hidden id="pagenumber" name="pageNumber" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/post/application/review/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	
</html>