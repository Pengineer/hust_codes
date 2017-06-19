<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<!-- 成果相关 -->
<div id="view_product_container" style="display:none;"></div>
<textarea id="view_product" style="display:none;">
	<div class="p_box_body">
		<table id="list_product" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="30%" style = "text-align:center;"><s:text name="成果名称" /></td>
					<td width="30%" style = "text-align:center;"><s:text name="成果类型" /></td>
					<td width="20%" style = "text-align:center;"><s:text name="所属单位" /></td>
					<td style = "text-align:center;"><s:text name="发布刊物" /></td>
				</tr>
			</thead>
			<tbody>
			{if paperList != null || bookList != null || consuList != null || elecList != null}
				{for item in paperList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[2]}
						</td>
						<td>
							${item[3]}
						</td>
						<td>
							${item[4]}
						</td>
					</tr>
				{/for}
				{for item in bookList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[2]}
						</td>
						<td>
							${item[3]}
						</td>
						<td>
							${item[4]}
						</td>
					</tr>
				{/for}
				{for item in consuList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[2]}
						</td>
						<td>
							${item[3]}
						</td>
						<td>
							${item[4]}
						</td>
					</tr>
				{/for}
				{for item in elecList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[2]}
						</td>
						<td>
							${item[3]}
						</td>
						<td>
							${item[4]}
						</td>
					</tr>
				{/for}
			{else}
				<tr class="even">
					<td align="center" colspan="4"><s:text name="i18n_NoRecords" /></td>
				</tr>
			{/if}
			</tbody>
		</table>
	</div>
</textarea>
