<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>系统选项</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/system/config/validate.js', function(validate) {
					validate.valid();
				});
			</script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统选项&nbsp;&gt;&nbsp;下载标准XML数据
			</div>
			
			<div class="main" style="width:380px;">
				<s:form id="form_config" action="downloadXML" namespace="/system/config">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td3"><s:checkbox name="xmlConfig" value="false" fieldValue="university"/>&nbsp;高校数据</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td3"><s:checkbox name="xmlConfig" value="false" fieldValue="discipline"/>&nbsp;学科代码数据</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td3"><s:checkbox name="xmlConfig" value="false" fieldValue="specialityTitle"/>&nbsp;职称数据</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td align="center">
									<input id="submit" class="btn1" type="submit" value="导出" />
								</td>
							</tr>
						</table>
					</div>
				</s:form>
			</div>
		</body>
	
</html>
