<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
	</head>
	
	<body>
			<div class="div_table">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="28" class="tdbg1"><s:text name="基本信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产编号" />：</td>
						<td class="txtpadding" width="300"><s:property value="asset.assetnumber" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产状态" />：</td>
						<td class="txtpadding"><s:property value="asset.status.value" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产类型" />：</td>
						<td class="txtpadding" width="300"><s:property value="asset.type" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产名称" />：</td>
						<td class="txtpadding"><s:property value="asset.name" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产配置" />：</td>
						<td class="txtpadding" colspan="3"><pre style="margin:10;">${asset.spec}</pre></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="购置人信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="购置人" />：</td>
						<td class="txtpadding"><s:property value="asset.pcher.chinesename" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="购置时间" />：</td>
						<td class="txtpadding">
							<s:if test="#session.locale == 'zh_CN'"><s:date name="asset.datetime" format="yyyy-MM-dd" /></s:if>
							<s:else><s:date name="asset.datetime" format="dd/MM/yyyy" /></s:else>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="购置价格" />：</td>
						<td class="txtpadding"><s:property value="asset.price" /></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="使用者信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="使用人姓名" />：</td>
						<td class="txtpadding"><s:property value="asset.rsper.chinesename" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="开始使用时间" />：</td>
						<td class="txtpadding">
							<s:if test="#session.locale == 'zh_CN'"><s:date name="asset.begtime" format="yyyy-MM-dd" /></s:if>
							<s:else><s:date name="asset.begtime" format="dd/MM/yyyy" /></s:else>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="用途" />：</td>
						<td class="txtpadding"><s:property value="asset.usage" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
						<td class="txtpadding"><s:property value="asset.remark" /></td>
					</tr>					
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="历史信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
				         <thead>
					         <tr>
						         <td class="txtcenter" width="8%" bgcolor="#b3c6d9"><s:text name="i18n_Number" /></td>
						         <td class="txtcenter" width="15%" bgcolor="#b3c6d9">责任人</td>
						         <td class="txtcenter" width="20%" bgcolor="#b3c6d9">使用用途</td>
						         <td class="txtcenter" width="15%" bgcolor="#b3c6d9">开始使用时间</td>
						         <td class="txtcenter" width="15%" bgcolor="#b3c6d9">结束使用时间</td>
			<%--			         <td class="txtcenter" width="100" bgcolor="#b3c6d9">当前资产状态</td>--%>
						         <td class="txtcenter" bgcolor="#b3c6d9">备注</td>
					       </tr>
				        </thead>
				        <tbody id="list_table">
					     <s:if test="pageList.isEmpty()">
						  <tr class="trbg1" onmouseover="javascript:this.className='trbg11';" onmouseout="javascript:this.className='trbg1';">
							<s:if test="(#session.visitor.userRight.contains(\"ROLE_ASSET_MODIFY\"))">
								<td colspan="17" class="txtcenter">
							</s:if>
							<s:else>
								<td colspan="15" class="txtcenter">
							</s:else>
								<s:text name="i18n_NoRecord" />
							</td>
						</tr>
					</s:if>
							<input type="hidden" id="pageListSize" value="<s:property value='pageList.size()' />"/>
						<s:iterator value="pageList" status="stat">
							<tr>
								<td class="txtcenter" bgcolor="#b3c6d9"><s:property value="#stat.index+1" /></td>
								<td class="txtcenter"><s:property value="pageList[#stat.index][1]" /></td>
								<td class="txtcenter"><s:property value="pageList[#stat.index][2]" /></td>
								<td class="txtcenter"><s:date name="pageList[#stat.index][3]" format="yyyy-MM-dd" /></td>
								<td class="txtcenter"><s:date name="pageList[#stat.index][4]" format="yyyy-MM-dd" /></td>
								<td class="txtcenter"><s:property value="pageList[#stat.index][5]" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>	
	</body>
</html>