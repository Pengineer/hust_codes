<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
   		<title>成果审核</title>
   		<s:include value="/innerBase.jsp" />
		</head>
 
	<body>
		<div style="width:400px;">
			<s:form id="audit_product">
			 	<s:hidden id="pids" name="productids"/>
			 	<s:hidden id="pagenumber" name="pageNumber" />
			 	<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="100"><span class="table_title2">审核结果：</span></td>
						<td class="table_td3"><s:radio name="auditResult" cssClass="audit_result" list="#{'2': '同意', '1': '不同意'}"/></td>
						<td class="table_td4" width="90"></td>
					</tr>
				</table>
				<div class="btn_div_view">
					<input id="confirm" class="btn1" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消"/>
				</div>
			</s:form>
		</div>
   		<script type="text/javascript">
			seajs.use('javascript/product/audit.js', function(audit) {
				audit.init();
			});
		</script>
	</body>
</html>