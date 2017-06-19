<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		<body>
			<div style="width:480px;">
				<s:form id="form_modifyFundList" action = "/funding/fundingList/project/modify.action" method = "post">
					<s:hidden id="entityId" name="entityId" value="%{entityId}" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title6">清单名称：</span></td>
							<td class="table_td3"><input id = "listName" value = '<s:property value="fundingList.name" />' name = "listName" class = "input_css"/></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>清单备注：</span></td>
							<td class="table_td12"><textarea id = "note" value = '<s:property value="fundingList.note" />' name = "note" class = "textarea_css"></textarea></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="submit" class="btn1" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript" >
				seajs.use('javascript/funding/researchProjectFee/projectFundingList/modify.js', function(modify) {
					modify.init();
				});
			</script>
		</body>
	
</html>