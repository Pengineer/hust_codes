<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>配置奖励</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/system/config/validate.js', function(validate) {
					$(function(){
						validate.valid();
					})
				});
			</script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统配置&nbsp;&gt;&nbsp;配置奖励
			</div>
			
			<div class="main" style="width:100%">
				<s:form id="form_config" action="configAward" namespace="/system/config">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="150">人文社科奖</td>
								<td class="table_td3"><s:textfield name="awardSession" value="%{#application.awardSession}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">特等奖得分</td>
								<td class="table_td3"><s:textfield name="specialAwardScore" value="%{#application.specialAwardScore}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">一等奖得分</td>
								<td class="table_td3"><s:textfield name="firstAwardScore" value="%{#application.firstAwardScore}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">二等奖得分</td>
								<td class="table_td3"><s:textfield name="secondAwardScore" value="%{#application.secondAwardScore}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">三等奖得分</td>
								<td class="table_td3"><s:textfield name="thirdAwardScore" value="%{#application.thirdAwardScore}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">普及奖得分</td>
								<td class="table_td3"><s:textfield name="popularAwardScore" value="%{#application.popularAwardScore}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
						<s:include value="/submit.jsp" />
					</div>
				</s:form>
			</div>
		</body>
	
</html>
