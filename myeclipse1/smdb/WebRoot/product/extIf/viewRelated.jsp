<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_relprod_template" style="display:none;">
	<div style="margin-bottom:5px;">相关成果
		<span id="rel_total">${relProdInfo[0].productSize}</span>
		<span style="margin-right:20px;">项
			{if relProdInfo[0].productSize!=0}(&nbsp;
				{if relProdInfo[0].paperSize != null && relProdInfo[0].paperSize != 0}论文${relProdInfo[0].paperSize}项&nbsp;{/if}
				{if relProdInfo[0].bookSize != null && relProdInfo[0].bookSize != 0}著作${relProdInfo[0].bookSize}项&nbsp;{/if}
				{if relProdInfo[0].consultationSize != null && relProdInfo[0].consultationSize != 0}研究咨询报告${relProdInfo[0].consultationSize}项&nbsp;{/if}
				{if relProdInfo[0].electronicSize != null && relProdInfo[0].electronicSize != 0}电子出版物${relProdInfo[0].electronicSize}项&nbsp;{/if}
				{if relProdInfo[0].patentSize != null && relProdInfo[0].patentSize != 0}专利${relProdInfo[0].patentSize}项&nbsp;{/if}
				{if relProdInfo[0].otherProductSize != null && relProdInfo[0].otherProductSize != 0}其他成果${relProdInfo[0].otherProductSize}项&nbsp;{/if}
				)
			{/if}
		</span>
		{if isDirector==1}
			<input id="view_add_product" class="btn1" type="button" value="添加" name="rel_prod"/>
			<input id="view_mod_product" class="btn1" type="button" value="修改" name="rel_prod"/>
		{/if}
	</div>
	<table id="list_table_rel" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<thead id="list_head">
			<tr class="table_title_tr3">
			
				{if isDirector==1}
					<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有账号"/></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
				{/if}
				
				<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有账号"/></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
					</s:if>
				</sec:authorize>
				
				<td width="40">序号</td>
				
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td>成果名称</td>
				
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="35">成果形式</td>

				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="55">第一作者</td>
				
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="55">所属单位</td>
		
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="35">学科门类</td>
				
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="95">是否标注教育部社科项目资助</td>
				
				{if granted.projectType != 'entrust'}
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="50">是否年检成果</td>
				{/if}
				
				{if granted.projectType != 'post'}
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="50">是否中检成果</td>
				{/if}
				
				<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
				<td width="50">是否结项成果</td>
				
				<td width="2"><img src="image/table_line.gif" width="2" height="24"/></td>
				<td width="35">审核结果</td>
			</tr>
		</thead>
		<tbody>
		{for item in relProdInfo[0].productList}
			<tr class="table_txt_tr3">
				{if isDirector==1}
					<td><input type="checkbox" name="entityIds" value="${item[0]}" class="selectProduct" alt="${item[2]}"/></td>
					<td>
						<div style="display:none;">
							<input name="isMid" type="hidden" value="${item[9]}"/>
							<input name="isEnd" type="hidden" value="${item[10]}"/>
						</div>
					</td>
				{/if}
				<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
					<s:if test="#session.loginer..within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<td><input type="checkbox" name="entityIds" value="${item[0]}" class="selectProduct" alt="${item[2]}"/></td>
						<td><div style="display:none;"><input type="checkbox" name="productTypes" value="${item[2]}"/></div></td>
					</s:if>
				</sec:authorize>
				<td class="index">${item_index}</td>
				<td></td>
				<td>
					<a id="${item[0]}" type="${item[2]}" class="view_product" href="">${item[1]}</a>
				</td>
				<td></td>
				<td>
					{if item[2] == 'paper'}论文
					{elseif item[2] == 'book'}著作
					{elseif item[2] == 'consultation'}研究咨询报告
					{elseif item[2] == 'electronic'}电子出版物
					{elseif item[2] == 'patent'}专利
					{elseif item[2] == 'otherProduct'}其他成果
					{/if}
				</td>
				<td></td>
				<td>{if item[4] != null && item[4] != ""}
						<a id="${item[4]}" class="view_author" href="">${item[3]}</a>
					{else}${item[3]}
					{/if}
				</td>
				<td></td>
				<td>{if item[6] != null && item[6] != ""}
						<a id="${item[6]}" class="view_university" href="">${item[5]}</a>
					{else}${item[5]}
					{/if}
				</td>
				<td></td>
				<td>${item[7]}</td>
				<td></td>
				<td>
					{if item[8] == 1}是
					{else}否
					{/if}
				</td>
				<td></td>
				{if granted.projectType != 'entrust'}
					<td>
						{if item[9] == 1}是
						{else}否
						{/if}
					</td>
					<td></td>
				{/if}
				{if granted.projectType != 'post'}
					<td>
						{if item[10] == 1}是
						{else}否
						{/if}
					</td>
					<td></td>
				{/if}
				<td>
					{if item[11] == 1}是
					{else}否
					{/if}
				</td>
				<td></td>
				<td>
					{if item[12] == 0}待审
					{elseif item[12] == 1 }不同意
					{elseif item[12] == 2 }同意
					{/if}
				</td>
			</tr>
		{forelse}
			<tr class="table_txt_tr3">
				<td align="center">暂无符合条件的记录</td>
			</tr>	
		{/for}
		</tbody>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<tr class="table_main_tr2">
			<td width="69" align="left">
				{if isDirector==1}
					<input id="view_del_product" name="rel_prod" type="button" class="btn1" value="删除"/>
				{/if}
				<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
					<s:if test="#session.loginer..within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<input id="view_aud_product" name="rel_prod" type="button" class="btn1" value="审核"/>
					</s:if>
				</sec:authorize>
			</td>
		</tr>
	</table>
</textarea>
<s:form id="list_rel" theme="simple" action="toAudit" namespace="/product">
	<div id="view_relprod" style="display:none;"></div>
</s:form>