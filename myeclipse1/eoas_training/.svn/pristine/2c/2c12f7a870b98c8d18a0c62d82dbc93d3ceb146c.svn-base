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
		<title><s:text name="客户管理" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="javascript/common.js"></script>
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.self.js"></script>
		<script type="text/javascript" src="javascript/oa/asset/asset.js"></script>
	</head>

		<body>
			<div class="title_bar">
				<ul>
					<li class="m"><s:text name="客户关系管理" /></li>
					<li class="text_red"><s:text name="添加客户" /></li>
				</ul>
			</div>
			<div class="div_table">

					<table>
						<tr><td>客户信息</td></tr>
						<tr>
							<td><s:text name = "客户名称"/>：</td>
							<td><s:property value = "customer.name"></s:property></td>	
							<td><s:text name = "客户分类" />：</td>
							<td><s:select list = "#{1:'目标客户',2:'意向客户',3:'成交客户',4:'放弃客户',5:'目标代理',6:'意向代理',7:'签约代理',8:'放弃代理',9:'合作伙伴'}" 
								name = "customer.type" headerKey = "0" headerValue = "--请选择--"></s:select></td>
						</tr>
						<tr>
							<td><s:text name = "客户简称"/>：</td>
							<td><s:textfield name = "customer.abbreviation"></s:textfield></td>	
							<td><s:text name = "地区" />：</td>
							<td><s:textfield name = "customer.region"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "地址"/>：</td>
							<td><s:textfield name = "customer.address"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "行业"/>：</td>
							<td><s:textfield name = "customer.industry"></s:textfield></td>	
							<td><s:text name = "重要级别" />：</td>
							<td><s:select list = "#{1:'A',2:'B',3:'C',4:'D',5:'E' }"
							 name = "customer.level" headerKey = "0" headerValue = "--请选择--"></s:select></td>
						</tr>
						<tr>
							<td><s:text name = "主页"/>：</td>
							<td><s:textfield name = "customer.homepage"></s:textfield></td>	
							<td><s:text name = "主要产品" />：</td>
							<td><s:textfield name = "customer.mainproducts"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "客户来源"/>：</td>
							<td><s:select list = "#{1:'网络',2:'合作伙伴推荐',3:'来电咨询',4:'客户推荐',5:'行业搜集',6:'二次销售',7:'研讨会'}"
									 name = "customer.source" headerKey = "-1" headerValue = "--请选择--"/></td>
									 
							<td><s:text name = "客户价值" />：</td>	
							<td><s:select list = "#{1:'铂金用户',2:'黄金用户',3:'普通用户',4:'垃圾用户',5:'其他类型'}" name = "customer.value" headerKey = "-1" headerValue = "--请选择--" />：</td>
						</tr>
						<tr>
							<td><s:text name = "咨询时间"/>：</td>
							<td><s:textfield name = "customer.name"></s:textfield></td>	
							<td><s:text name = "所在城市" />：</td>
							<td><s:textfield name = "customer.city"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "备注"/>：</td>
							<td><s:textfield name = "customer.note"></s:textfield></td>
						</tr>
						
						<tr><td>联系人信息</td></tr>
						<tr>
							<td><s:text name = "联系人"/>：</td>
							<td><s:textfield name = "contact.name"></s:textfield></td>	
							<td><s:text name = "性别" />：</td>
							<td><s:radio name = "contact.gender" list = "#{1:'男',2:'女' }"></s:radio></td>
						</tr>
						<tr>
							<td><s:text name = "生日"/>：</td>
							<td><s:textfield name = "contact.birthday"></s:textfield></td>	
							<td><s:text name = "职务" />：</td>
							<td><s:textfield name = "contact.position"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "单位电话"/>：</td>
							<td><s:textfield name = "contact.companyPhone"></s:textfield></td>	
							<td><s:text name = "手机" />：</td>
							<td><s:textfield name = "contact.mobilePhone"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "传真"/>：</td>
							<td><s:textfield name = "contact.fax"></s:textfield></td>	
							<td><s:text name = "QQ" />：</td>
							<td><s:textfield name = "contact.qq"></s:textfield></td>
						</tr>
						<tr>
							<td><s:text name = "Email"/>：</td>
							<td><s:textfield name = "contact.email"></s:textfield></td>
							<td><s:text name = "角色" />：</td>
							<td><s:select list = "#{1:'影响着',2:'推荐者',3:'内线',4:'决策者'}" headerKey = "-1" headValue = "--请选择--" name = "contact.role"></s:select></td>
						</tr>
					</table>

			</div>
		</body>
</html>