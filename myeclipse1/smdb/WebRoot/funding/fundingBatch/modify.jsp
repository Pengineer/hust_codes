<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>添加批次</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
		<div class="link_bar">
			当前位置：拨款批次管理&nbsp;&gt;&nbsp;添加
		</div>
			<div class = "main_content">
				<s:include value="/validateError.jsp" />
				<s:form id="addBtach" action = "funding/fundingBatch/modify.action">
				<s:hidden id="entityId" name="entityId" value="%{entityId}"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="100"><span class="table_title6">批次名称：</span></td>
							<td class="table_td12"><s:textfield id="fundingBatchName" name="fundingBatchName" cssClass="input_css" value ="%{fundingBatch.name}"/></td>
							<td class="table_td13" width="80"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" width="100"><span class="table_title6">批次日期：</span></td>
							<td class="table_td12"><s:textfield name = "fundingBatchDate" id = "fundingBatchDate" cssClass="input_css date" value = "%{fundingBatch.date}"/></td>
							<td class="table_td13" width="80"></td>
						</tr>
					</table>
					<div class="btn_div_view">
					<input id="submit" class="btn1 addSubmit" type="submit" value="提交"/>
					<input id="cancel" class="btn1" type="button" value="取消" />
					</div>
					</s:form>
				
			</div>
			<script type="text/javascript">
				seajs.use('javascript/funding/fundingBatch/modify.js', function(add) {
					add.init();
				});
			</script>
		</body>
	
</html>