<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="affiliation">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			{for item in teachers}
			{if item_index != 0}<div style="height:10px;"></div>{/if}
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td rowspan="3" width="60">工作<span class="number">${item_index}</span></td>
					<td class="key" width="70">所在高校:</td>
					<td class="value" width="140">
						{if item[1] != null}
							<a id="${item[2]}" class="linkA" href="">${item[1]}</a>
						{else}
							<a id="${item[6]}" class="linkA" href="">${item[5]}</a>
						{/if}
					</td>
					<td class="key" width="70">所在{if item[1] != null}院系{else}基地{/if}:</td>
					<td class="value" width="140">
						{if item[1] != null}
							<a id="${item[4]}" class="linkD" href="">${item[3]}</a>
						{else}
							<a id="${item[8]}" class="linkI" href="">${item[7]}</a>
						{/if}
					</td>
					<td class="key" width="80">人员类型:</td>
					<td class="value">${item[0].type}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">定职时间:</td>
					<td class="value">${item[0].startDate}</td>
					<td class="key">离职时间:</td>
					<td class="value">${item[0].endDate}</td>
					<td />
					<td />
				</tr>
				<tr class="table_tr7">
					<td class="key">职务:</td>
					<td class="value">${item[0].position}</td>
					<td class="key">工作证号:</td>
					<td class="value">${item[0].staffCardNumber}</td>
					<td class="key">每年工作时间:</td>
					<td class="value">{if item[0].workMonthPerYear != null}${item[0].workMonthPerYear}个月{/if}</td>
				</tr>
			</table>
			{forelse}
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td align="center">暂无符合条件的记录</td>
				</tr>
			</table>
			{/for}
		</div>
	</div>
	<script type="text/javascript">
		$(".number").each(function(){
			var number = Num2Chinese(parseInt($(this).html()) + 1);
			$(this).html(number);
		});
	</script>
	</textarea>
</div>

