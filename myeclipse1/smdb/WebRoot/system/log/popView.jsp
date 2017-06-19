<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title></title>
			<s:include value="/innerBase.jsp" />
			<style type="text/css">
				.mapKey {text-align:right; height:28px; line-height:20px;}
				.mapValue {text-align:left; height:28px; line-height:20px; padding-left:2px;}
			</style>
			
		</head>

		<body>
			<div style="width:380px;">
				<s:form action="statistic" namespace="/log" theme="simple">
					<div style="margin-bottom:10px;">
						<table style="width:350px;" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="mapKey" width="60">统计账号：</td>
								<td><s:radio name="type" value="%{type}" list="#{'0':'指定级别','1':'指定名称'}"></s:radio></td>
								<td style="width:100px; text-align:right;">
									<s:select cssClass="select" id="specifyType" cssStyle="display:none;" headerKey="-1" headerValue="--所有级别--"
										list="#{'1':'系统管理员账号','2':'部级账号','3':'省级账号','4':'校级账号','6':'院系账号','7':'基地账号','8':'外部专家账号','9':'教师账号','10':'学生账号'}" />
									<s:textfield id="specifyName" name="accountName" cssClass="input_css_self" cssStyle="display:none; width:116px;" />
									<s:hidden id="logType" name="logType" />
								</td>
							</tr>
						</table>
						<table style="width:350px;" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="mapKey" width="60">统计时间：</td>
								<td>
									<s:textfield name="startDate" cssClass="input_css_self" cssStyle="width:75px;">
										<s:param name="value"><s:date name="%{startDate}" format="yyyy-MM-dd" /></s:param>
									</s:textfield>&nbsp;至&nbsp;<s:textfield name="endDate" cssClass="input_css_self" cssStyle="width:75px;"><s:param name="value"><s:date name="%{endDate}" format="yyyy-MM-dd" /></s:param></s:textfield>
								</td>
								<td style="width:100px; text-align:right;"><input id="statistic" type="submit" class="btn1" style="margin-left:10px;" value="统计" /></td>
							</tr>
						</table>
					</div>
				</s:form>
				
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="mapKey" width="100">登录各服务器：</td>
							<td class="mapValue"><s:property value="map.get('serverCount')" />次</td>
							<td class="mapKey" width="100">系统功能模块：</td>
							<td class="mapValue"><s:property value="map.get('systemCount')" />次</td>
						</tr>
						<tr>
							<td class="mapKey">系统安全模块：</td>
							<td class="mapValue"><s:property value="map.get('securityCount')" />次</td>
							<td class="mapKey">人员数据模块：</td>
							<td class="mapValue"><s:property value="map.get('personCount')" />次</td>
						</tr>
						<tr>
							<td class="mapKey">机构数据模块：</td>
							<td class="mapValue"><s:property value="map.get('unitCount')" />次</td>
							<td class="mapKey">项目数据模块：</td>
							<td class="mapValue"><s:property value="map.get('projectCount')" />次</td>
						</tr>
						<tr>
							<td class="mapKey">成果数据模块：</td>
							<td class="mapValue"><s:property value="map.get('productCount')" />次</td>
							<td class="mapKey">奖励数据模块：</td>
							<td class="mapValue"><s:property value="map.get('awardCount')" />次</td>
						</tr>
					</table>
				</div>
				
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="确定" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/log/pop_view.js', function(pop_view) {
					$(function(){
						pop_view.init();
					})
				});
			</script>
		</body>

</html>