<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>关联规则定制分析</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：关联规则挖掘&nbsp;&gt;&nbsp;关联规则定制分析
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="form_dm" action="fetchAssociationRules" namespace="/dataMining/association" theme="simple" >
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">查询语句：</span></td>
							<td class="table_td3"><s:textarea name="queryString" cols="80" rows="3" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">语句类型：</span></td>
							<td class="table_td3"><s:radio name="languageType" value="0" list="#{'0':'HQL','1':'SQL'}" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">最小频度：</span></td>
							<td class="table_td3"><input type="text" name="minFrequency" value="100"/> </td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">最小支持度：</span></td>
							<td class="table_td3"><input type="text" name="minSupport" value="0.0003"/> 注：根据需求，调整该参数，值越小结果越多。</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">最小置信度：</span></td>
							<td class="table_td3"><input type="text" name="minConfidence" value="0.3"/> 注：根据需求，调整该参数，值越小结果越多。</td>
							<td class="table_td4"></td>
						</tr>
					</table>
				
					<div class="btn_bar3">
						<input id="submit" class="btn2" type="submit" value="确定" />
						<input id="reset" class="btn2" type="button" value="页面重载" onclick="document.location.reload();"/>
					</div>
				</s:form>
			</div>
			
			<textarea class="view_template" style="display:none">
			{if rules == null}
				<span> 分析 <font color="red">失败</font></span>
				<pre>${errorInfo}</pre>
			{else}
				<span> 分析 <font color="green">成功</font>, 共 <span style="weitght:bold;font-size:20px;color:blue">${rules.length}</span> 条记录, </>耗时 <span style="weitght:bold;font-size:20px;color:blue">${costTime}</span> ms !</span>
				<br/>
				<span style="font-weight: bold;">关联规则：（最小支持度=${minSupport}，最小置信度=${minConfidence}）</span>
				<table width="100%" cellspacing="3" cellpadding="2" style="border: 1px solid #AAAAAA">
				{for rule in rules}
					<tr>
					{if rule instanceof Array}
						{for col in rule}
							<td>${JSON.stringify(col)}</td>
						{/for}
					{else}
						<td>${JSON.stringify(rule)}</td>
					{/if}
					</tr>
				{forelse}
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>	
				{/for}
				</table>
			{/if}
			</textarea>
		</div>
		
		<script type="text/javascript">
			seajs.use('javascript/dataMining/association/toCustom.js', function(toCustom) {
				$(function(){
					toCustom.init();
				})
			});
		</script>
	</body>
</html>