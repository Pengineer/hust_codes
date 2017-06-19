<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="main">
	<s:form id="search" theme="simple" action="list" namespace="/project/general/application/applyStrict">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--" value="-1"
						list="#{
						'0':'高校名称',
						'1':'高校类型',
						'3':'所在省份'}" 
					/>
				</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
				</td>
				<td width="60"><input id="list_query" type="button" value="检索" class="btn1" /></td>
			</tr>
		</table>
		<s:hidden id="list_pagenumber" name="pageNumber" value="1" />
		<s:hidden id="list_sortcolumn" name="sortColumn" />
		<s:hidden id="list_pagesize" name="pageSize" value="10" />
		<s:hidden id="update" name="update" value="1" />
		<s:hidden id="moeTilt1" name="moeTilt" />
		<s:hidden id="strictAppYear" name="strictAppYear" />
		<s:hidden id="westTilt1" name="westTilt" />
		<s:hidden id="univStrictLB1" name="univStrictLB" />
		<s:hidden id="annStrictTarget1" name="annStrictTarget" />
		<s:hidden id="grweight1" name="grweight" />
		<s:hidden id="moweight1" name="moweight" />
		<s:hidden id="eoweight1" name="eoweight" />
		<s:hidden id="asweight1" name="asweight" />
		<s:hidden id="graStartYear1" name="graStartYear" />
		<s:hidden id="graEndYear1" name="graEndYear" />
		<s:hidden id="midStartYear1" name="midStartYear" />
		<s:hidden id="midEndYear1" name="midEndYear" />
		<s:hidden id="endStartYear1" name="endStartYear" />
		<s:hidden id="endEndYear1" name="endEndYear" />
		<s:hidden id="bookAwardFir1" name="bookAwardFir" />
		<s:hidden id="paperAwardFir1" name="paperAwardFir" />
		<s:hidden id="achPopuAward1" name="achPopuAward" />
		<s:hidden id="bookAwardSec1" name="bookAwardSec" />
		<s:hidden id="paperAwardSec1" name="paperAwardSec" />
		<s:hidden id="bookAwardThi1" name="bookAwardThi" />
		<s:hidden id="paperAwardThi1" name="paperAwardThi" />
		<s:hidden id="ResAdoAwardFir1" name="ResAdoAwardFir" />
		<s:hidden id="ResAdoAwardSec1" name="ResAdoAwardSec" />
		<s:hidden id="ResAdoAwardThi1" name="ResAdoAwardThi" />
		<s:hidden id="flag" name="flag" />
		<div id="list_container" style="display:none;"></div>
	</s:form>
	<textarea id="list_template" style="display:none;"><div style="overflow-x:scroll">
		<table id="list_table" width="907" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead id="list_head">
				<tr class="table_title_tr3">
					<td width="30">序号</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="60"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按高校名称排序">高校名称</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="120"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按年度限项申请基数（往年平均申请数）排序">年度限项申请基数（往年平均申请数）</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="50"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按立项率奖惩数排序">立项率奖惩数</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="52"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按逾期中检惩罚数排序">逾期中检惩罚数</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="52"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按逾期结项惩罚数排序">逾期结项惩罚数</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="52"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按奖励得分奖励数排序">奖励得分奖励数</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="65"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按初算：限项申请数量排序">初算：限项申请数量</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="50"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按部属高校倾斜排序">部属高校倾斜</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="50"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按西部高校倾斜排序">西部高校倾斜</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="65"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按发布：限项申请数量排序">发布：限项申请数量</a></td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按预测：预期申请数量排序">预测：预期申请数量</a></td>
				</tr>
			</thead>
			<tbody>
			{for item in root}
				<tr>
					<td>${item.num}</td>
					<td></td>
					<td class="viewDetail">${item.laData[0]}
						<div class="table_tr8" style="z-index:100; position:absolute; background:white; left:140px; display:none; padding:2px">
							<ul>
								<li>高校类型：<br/><span>${item.laData[1]}</span></li>
								<li>所在地区：<br/><span>${item.laData[2]}</span></li>
								<li>所在省份：<br/><span>${item.laData[3]}</span></li>
							</ul>
							<ul>
								<li>2009-2013年度项目申请数量：<br/><span>${item.laData[4]}</span></li>
								<li>2009-2013年度项目立项数量：<br/><span>${item.laData[5]}</span></li>
								<li>2009-2013年度项目立项率：<br/><span>${item.laData[6]}</span></li>
							</ul>
							<ul>
								<li>2006-2010年度项目立项数量：<br/><span>${item.laData[7]}</span></li>
								<li>2006-2010年度项目按期中检数量：<br/><span>${item.laData[8]}</span></li>
								<li>2006-2010年度项目按期中检率：<br/><span>${item.laData[9]}</span></li>
							</ul>
							<ul>
								<li>2005-2007年度项目立项数量：<br/><span>${item.laData[10]}</span></li>
								<li>2005-2007年度项目按期结项数量：<br/><span>${item.laData[11]}</span></li>
								<li>2005-2007年度项目按期结项率：<br/><span>${item.laData[12]}</span></li>
							</ul>
							<ul>
								<li>最近一届获奖得分：<br/><span>${item.laData[13]}</span></li>
							</ul>
						</div>
					</td>
					<td></td>
					<td>${item.laData[14]}</td>
					<td></td>
					<td>${item.laData[15]}</td>
					<td></td>
					<td>${item.laData[16]}</td>
					<td></td>
					<td>${item.laData[17]}</td>
					<td></td>
					<td>${item.laData[18]}</td>
					<td></td>
					<td>${item.laData[19]}</td>
					<td></td>
					<td>${item.laData[20]}</td>
					<td></td>
					<td>${item.laData[21]}</td>
					<td></td>
					<td>${item.laData[22]}</td>
					<td></td>
					<td>${item.laData[23]}</td>
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
			</tr>
		</table>
	</textarea>
</div>