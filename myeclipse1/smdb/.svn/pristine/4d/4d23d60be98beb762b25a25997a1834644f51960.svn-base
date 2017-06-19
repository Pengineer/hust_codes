<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored="true"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<div class="title_statistic">参数配置</div>
		<table width="100%" border="0" cellspacing="2" cellpadding="2">
			<tr class="table_tr2">
				<td class="table_td2" width="110"><span class="table_title3"
					style="_padding-top:5px;font-weight:bold;">省级机构：</span>
				</td>
				<td class="table_td31">
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tr>
							<td id="chose"><input type="button" class="btn1 select_btn"
								id="selectProvince" value="选择" />
								<div id="entityName" class="choose_show"></div> 
								<s:hidden id="entityId" name="entityId" />
								<s:hidden id="entityNameValue" name="entityNameValue" />
							</td>
						</tr>
					</table></td>
			</tr>
			<tr class="table_tr2 ">
				<td class="table_td2"><span class="table_title3"
					style="_padding-top:5px;font-weight:bold;">各项权重：</span>
				</td>
				<td class="table_td31">
					<table width="100%" border="0" cellspacing="0">
						<tr>
							<td style="width:25%;"><span>人员权重：</span> <s:textfield
									id="perWeight" name="perWeight" cssClass="keyword"
									style="width:40%; " /></td>
							<td style="width:25%;"><span>立项权重：</span> <s:textfield
									id="proWeight" name="proWeight" cssClass="keyword"
									style="width:40%; " /></td>
							<td style="width:25%;"><span>奖励权重：</span> <s:textfield
									id="awrWeight" name="awrWeight" cssClass="keyword"
									style="width:40%; " />
							<td>
							<td style="width:25%;"><span>成果权重：</span> <s:textfield
									id="prdWeight" name="prdWeight" cssClass="keyword"
									style="width:40%; " />
							<td>
						</tr>
					</table></td>
			</tr>
			<tr class="table_tr2 ">
				<td class="table_td2"><span class="table_title3"
					style="_padding-top:5px;font-weight:bold;">显示条数：</span>
				</td>
				<td class="table_td3">
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tr>
							<td style="width:25%;"><input type="radio"
								name="showLineNum" checked="checked" value="10" /> <span>前10条</span>
							</td>
							<td style="width:25%;"><input type="radio"
								name="showLineNum" value="15" /> <span>前15条</span></td>
							<td style="width:25%;"><input type="radio"
								name="showLineNum" value="20" /> <span>前20条</span></td>
							<td style="width:25%;"><input type="radio"
								name="showLineNum" value="0" /> <span>只显示非零行</span></td>
						</tr>
					</table></td>
			</tr>

			<tr class="table_tr2 j_time" style="width:15%;">
				<td class="table_td2"><span class="table_title3"
					style="_padding-top:5px;font-weight:bold;">统计图表：</span>
				</td>
				<td class="table_td31">
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tr>
							<td style="width:25%;"><input type="radio" name="chartType"
								checked="checked" value="1" /> <span>垂直柱图</span></td>
							<td style="width:25%;"><input type="radio" name="chartType"
								value="2" /> <span>水平柱图</span></td>
							<%--<td style="width:20%;"><input type="radio" name="chartType"
								value="3" /> <span>饼状图</span></td>--%>
							<td style="width:25%;"><input type="radio" name="chartType"
								value="3" /> <span>折线图</span></td>
							<td></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
</div>

