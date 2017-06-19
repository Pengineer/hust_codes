<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT , @csdc.tool.bean.AccountType@STUDENT)">
	<div id = "same_academic">
		<textarea class="view_template" style="display:none;">
			<div class="p_box_t">
				<div class="p_box_t_t"><s:text name='信息推荐' /></div>
				<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
			</div>
			<div class="p_box_body">
				<table id="list_search" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<thead id="list_head">
						<tr class="table_title_tr">
							<td width="20"><s:text name="i18n_Number" /></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="20"><s:text name="姓名" /></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="55"><s:text name="学校" /></td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="70"><s:text name="学科" /></td>
						</tr>
					</thead>
					<tbody>
					{for item in search_list}
						<tr>
							<td class="index">${item_index}</td>
							<td></td>
							<td>
								<a id="${item[0][3]}" class="linkAF" href="javascript:void(0)" title="<s:text name='加为好友' />">${item[0][0]}</a>
							</td>
							<td></td>
							<td>${item[0][1]}</td>
							<td></td>
							<td>
								${item[0][2]}
							</td>
						</tr>
					{forelse}
						<tr>
							<td align="center"><s:text name="请完善您的学术信息，以便于我们为您准确的推荐信息！" /></td>
						</tr>
					{/for}
					</tbody>
				</table>
			</div>
		</textarea>
	</div>
</s:if>


