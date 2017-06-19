<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="table_main_tr_adv">
	<s:include value="/projectFund/instpFund/searchForFundList.jsp"/>
</div>
<textarea id="list_template" style="display:none;">
	<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<thead id="list_head">
			<tr class="table_title_tr">
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_DELETE">
				<td width="10"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllProjectOrNot' />" /></td>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			</sec:authorize>
				<td width="30"><s:text name="i18n_Number" /></td>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectName' />"><s:text name="i18n_ProjectName" /></a></td>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="60"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectNumber' />"><s:text name="i18n_ProjectNumber" /></a></td>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="60"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDirector' />"><s:text name="i18n_Director" /></a></td>
			<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUniversity' />"><s:text name="i18n_University" /></a></td>
			</s:if>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="70"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectSubtype' />"><s:text name="i18n_ProjectSubtype" /></a></td>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectYear' />"><s:text name="i18n_ProjectYear" /></a></td>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='点击按批准经费金额排序' />"><s:text name="批准经费" /></a></td>
<%--									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>--%>
<%--									<td ><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='点击按立项拨款金额排序' />"><s:text name="立项拨款" /></a></td>--%>
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='点击按中检拨款金额排序' />"><s:text name="中检拨款" /></a></td>
<%--									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>--%>
<%--									<td><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='点击按结项拨款金额排序' />"><s:text name="结项拨款" /></a></td>--%>
			</tr>
		</thead>
		<tbody>
		{for item in root}
			<tr>
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_DELETE">
				<td><input type="checkbox" name="entityIds" value="${item.laData[18]}" /></td>
				<td></td>
			</sec:authorize>
				<td>${item.num}</td>
				<td></td>
				<td class="table_txt_td">${item.laData[3]}</td>
<%--									<td class="table_txt_td"><a id="${item.laData[1]}" name="${item.laData[16]}" class="view_projectFunding" href="" title="<s:text name='i18n_ViewDetails' />" type="2">${item.laData[3]}</a></td>--%>
				<td></td>
				<td>${item.laData[2]}</td>
				<td></td>
				<td>
					{if item.laData[4]==""}${item.laData[5]}
					{else}<s:hidden id="directors" value="${item.laData[4]}" name="${item.laData[5]}" cssClass="directors" />
					{/if}
				</td>
			<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
				<td></td>
				<td>
					{if item.laData[6]==""}${item.laData[7]}
					{else}<a id="${item.laData[6]}" class="view_university" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[7]}</a>
					{/if}
				</td>
			</s:if>
				<td></td>
				<td>${item.laData[8]}</td>
				<td></td>
				<td>${item.laData[9]}</td>
				<td></td>
				<td>${item.laData[10]}</td>
<%--									<td></td>--%>
<%--									{if item.laData[15]==0}<td style="color:red;">${item.laData[12]}</td>--%>
<%--									{else}<td style="color:green;">${item.laData[12]}</td>--%>
<%--									{/if}--%>
				<td></td>
				<td>
				{if item.laData[13]==0}<a id="${item.laData[14]}" class="modify_fee" href="" title="<s:text name='点击修改金额' />">${item.laData[12]}</a>
				{else}${item.laData[12]}
				{/if}
				</td>
<%--									{if item.laData[17]==0}<td style="color:red;">${item.laData[14]}</td>--%>
<%--									{else}<td style="color:green;">${item.laData[14]}</td>--%>
<%--									{/if}--%>
			</tr>
		{forelse}
			<tr>
				<td align="center"><s:text name="i18n_NoRecords" /></td>
			</tr>
		{/for}
		</tbody>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<tr class="table_main_tr2">
			<td width="4"></td>
			{if  item != null && item.laData[13] ==0}
				<td width="58"><input id="list_modify" type="button" class="btn1" value="<s:text name='删除' />" /></td>
			{/if}
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_DATA_EXPORT">
				<td width="58"><input id="list_export_fund" type="button" class="btn1" value="导出清单" /></td>
			</sec:authorize>
		</tr>
	</table>
</textarea>
<s:form id="list" theme="simple" action="fundListDelete" namespace="/fundList/instp/mid">
	<s:hidden id="fundList_entityId" name="entityId" value="%{entityId}" />
	<s:hidden id="pagenumber" name="pageNumber" />
	<s:hidden id="type" name="type" value="1" />
	<div id="list_container" style="display:none;"></div>
</s:form>