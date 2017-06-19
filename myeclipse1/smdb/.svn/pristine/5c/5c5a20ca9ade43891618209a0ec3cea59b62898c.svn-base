<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" >
				seajs.use('javascript/fundList/instp/add.js', function(add) {
					add.init();
				});
			</script>
		</head>
		
		<body>
			<div style="width:480px;">
				<s:form id="form_addFundList">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title6">清单名称：</span></td>
							<td class="table_td3"><s:textfield id="listName" name="listName" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title6">拨款比率：</span></td>
							<td class="table_td12"><s:textfield id="rate" name="rate" cssClass="input_css_other" /><span>%</span></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span  class="table_title6">项目年度：</span></td>
							<td class="adv_td3">
								<s:select cssClass="select" id="projectYear" name="projectYear"  list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">项目子类：</td>
							<td class="table_td12"><s:select cssClass="select" id="projectSubtype" name="projectSubtype" value="%{searchQuery.projectSubtype}" list="%{baseService.getSystemOptionMap('projectType', '01')}" headerKey="-1" headerValue="--不限--" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>清单备注：</span></td>
							<td class="table_td12"><s:textarea id="note" name="note" rows="2" cssClass="textarea_css" /><br/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="submit" class="btn1" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
		</body>
	
</html>