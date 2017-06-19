<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="资产管理" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="javascript/common.js"></script>
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.self.js"></script>
		<script type="text/javascript" src="javascript/oa/asset/asset.js"></script>
		
	</head>

		<body onload="selectType('type');">
			<div class="title_bar">
				<ul>
					<li class="m"><s:text name="资产管理" /></li>
					<li class="text_red"><s:text name="添加资产" /></li>
				</ul>
			</div>
			<div class="div_table">
				<s:form action="add" id="form_asset" namespace="/asset" theme="simple" method="post">
					<div class="errorInfo">
						<s:property value="tip" />
						<s:fielderror />
					</div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="基本信息" /></td>
						</tr>
					</table>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产编号" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield name="asset.assetNumber" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产类型" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:select name="asset.type" id="type" headerKey="-1" headerValue="请选择" 
										list="{'软件','设备','家具','其他'}" cssClass="inputcss" onchange="selectType('type');" theme="simple" />
								</div>
								<div style="float:left;"></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产名称" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:select name="asset.name" list="softwareAdd" id="fromdb1" 
										onchange="addNameByUser('fromdb1');" theme="simple" 
										disabled="true" cssStyle="display:none" cssClass="inputcss" />
									<s:select name="asset.name" id="fromdb2" list="hardwareAdd" 
										onchange="addNameByUser('fromdb2');" disabled="true" 
										theme="simple" cssClass="select inputwd" cssStyle="display:none" />
									<s:select name="asset.name" id="fromdb3" list="furAdd" 
										onchange="addNameByUser('fromdb3');" disabled="true" 
										cssStyle="display:none" theme="simple" cssClass="inputcss" />
									<s:select name="asset.name" id="fromdb4" list="otherAdd" 
										onchange="addNameByUser('fromdb4');" disabled="true" 
										cssStyle="display:none" theme="simple" cssClass="inputcss" />
								</div>
								<div></div>
								<div id="table_name" style="float:left;">
									<s:textfield name="nameByAccount" id="fromuser" 
										cssStyle="display:none" theme="simple" cssClass="inputcss" />
								</div>
								<div id="divid"></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" valign="top" bgcolor="#b3c6d9"><s:text name="资产配置" />：</td>
							<td>
								<div class="input0" style="float:left; height:77px;">
									<s:textarea cssStyle="font-size:12px; width:505px; height:73px;" name="asset.spec" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="资产状态" />：</td>
							<td>
								<div class="input0">
									<s:select name="asset.type" id="type" headerKey="-1" headerValue="请选择" 
										list="{'正常','维修','报废','丢失'}" cssClass="inputcss"  theme="simple" />
								</div>
							</td>
						</tr>
					</table>
 					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="购置信息" /></td>
						</tr>
					</table>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="购置人" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:select name="asset.pcher.id" headerKey="-1" headerValue="请选择" 
										listKey="id" listValue="email" list="#application.accountList" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="购置时间" />：</td>
							<td>
								<div class="input0" style="float:left;">	
										<input type="text" name="asset.datetime" id="datepicke" 
											value="<s:date name="asset.datetime" format="yyyy-MM-dd" />" class="inputcss"/>
								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="购置价格" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield name="asset.price" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="使用信息" /></td>
						</tr>
					</table>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="责任人" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:select name="asset.rsper.id" headKey="-1" headValue="请选择" 
										listKey="id" listValue="email" list="#application.accountList" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="起始时间" />：</td>
							<td>
								<div class="input0" style="float:left;">

									<input type="text" name="asset.begtime" id="datepicks" 
										value="<s:date name="asset.begtime" format="yyyy-MM-dd" />" class="inputcss"/>


								</div>
								<div></div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="使用用途" />：</td>
							<td>
								<div class="input0" style="float:left;">
									<s:textfield name="asset.usage" cssClass="inputcss" />
								</div>
								<div></div>
							</td>
						</tr> 
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="历史信息" /></td>
						</tr>
					</table>
					<tr>
						<!-- <td align="right" bgcolor="#b3c6d9">&nbsp;</td> -->
						<td colspan="7">
							<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="90"><input type="submit" class ="btn1" value="<s:text name='确定' />" /></td>
										<td><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
									</tr>
							</table>
						</td>
					</tr>
				</s:form>
			</div>
		</body>
</html>