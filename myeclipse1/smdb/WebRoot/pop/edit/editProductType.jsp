<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use('javascript/pop/view/view.js', function(view) {
				
			});
			seajs.use('javascript/pop/edit/edit_product_type.js', function(edit) {
				$(function(){
					edit.init();
				})
			});
		</script>
	</head>
	
	<body>
		<div style="width:380px;">
			<s:form id="product_type_form">
				<table class="dashedTable" width="100%" border="0" cellspacing="0" cellpadding="2">
					<thead>
						<tr>
							<td width="30" style="text-align:center;"><input id="check" name="check" type="checkbox" /></th>
							<td width="2"></th>
							<td width="100">成果形式</th>
							<td width="2"></th>
							<td width="90">成果总数</th>
							<td width="2"></th>
							<td width="60">成果合格数</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="productTypeList" status="stat">
							<tr class="itl">
								<td style="text-align:center;"><input type="checkbox" name="entityNames" alt="<s:property value='productTypeList[#stat.index][1]'/>" /></td>
								<td class="table_td16"></td>
								<td>
									<s:property value="productTypeList[#stat.index][1]" />
									<s:if test="productTypeList[#stat.index][1]=='其他'">
										<span id="productTypeOtherSpan" style="display:none;"><s:textfield name="productTypeOther" cssClass="input_css_other1"/></span>
									</s:if>
								</td>
								<td class="table_td16" id="profuctTypeOtherTd"></td>
								<td class="level">
									<s:textfield cssStyle="width:50px;" name="totalNumber[%{#stat.index}]" />
								</td>
								<td class="table_td16"></td>
								<td>
									<s:textfield cssStyle="width:50px;" name="realNumber[%{#stat.index}]" />
								</td>
								<td class="table_td16" style="vertical-align:middle;"></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:form>
			<div class="btn_div_view">
				<input id="confirm" class="btn1" type="submit" value="确定" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
	</body>
</html>