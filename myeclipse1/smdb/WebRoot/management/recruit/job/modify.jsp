<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>修改</title>
	<s:include value="/innerBase.jsp" />
</head>

<body>
	<div class="link_bar">
		当前位置：招聘管理&nbsp;&gt;&nbsp;职位管理&nbsp;&gt;&nbsp;修改
	</div>
	
	<div class="main">
		
		<s:form id="form_job" theme="simple" action="modify" namespace="/management/recruit/job">
			<s:hidden id="entityId" name="entityId" value="%{entityId}" />
			<div id="info" style="display:none">
				<div class="main_content">
					<div id="procedure" class="step_css">
						<ul>
							<li class="proc" name="basic1"><span class="left_step"></span><span class="right_step">基本信息</span></li>
							<li class="proc" name="detail"><span class="left_step"></span><span class="right_step">详细要求</span></li>
							<li class="proc step_oo"><span class="left_step"></span><span class="right_step">完成</span></li>
						</ul>
					</div>
				</div>
				
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<div class="edit_info" id="basic1">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">职位名称：</span></td>
								<td class="table_td3"><s:textfield id="name" name="name" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">招聘人数：</span></td>
								<td class="table_td3"><s:textfield id="number" name="number" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">学位要求：</span></td>
								<td class="table_td3"><s:select id="degree" cssClass="select" name="degree" headerKey="" headerValue="--请选择--" list="{'无限制','本科','硕士','博士'}" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">年龄要求：</span></td>
								<td class="table_td3"><s:textfield id="age_min" size="2" /><s:label>&nbsp;--&nbsp;</s:label><s:textfield id="age_max" size="2" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">截止时间：</span></td>
								<td class="table_td3">
									<s:textfield id="endDate" name="endDate" cssClass="input_css FloraDatepick">
										<s:param name="value">
											<s:date name="%{endDate}" format="yyyy-MM-dd" />
										</s:param>
									</s:textfield>	
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">选择模板：</span></td>
								<td class="table_td3">
									<input id="selectTemplate" class="btn1" type="button" value="请选择" />
									<span id = "warning" style = "display:none"></span>
									<input type="hidden" id="selectedTemplates" />
									<input type="hidden" id="selectedTemplateIds" name="selectedTemplateIds"/>
									<div id='selected' style="margin-left:10px;"></div>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
					<div id="detail" class="edit_info">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="120"><span class="table_title4">详细要求：</span></td>
								<td class="table_td3"><s:textarea id="requirement" name="requirement" rows="25" cssClass="textarea_css" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</s:form>
		
		<div id="optr" class="btn_bar2">
			<input id="prev" class="btn2" type="button" style="display: none" value="上一步" />
			<input id="next" class="btn2" type="button" style="display: none" value="下一步" />
			<input id="finish" class="btn1" type="button" style="display: none" value="完成" />
			<input id="retry" class="btn2" type="button" style="display: none" value="重填" />
			<input id="confirm" class="btn1" type="button" style="display: none" value="确定" />
			<input id="cancel" class="btn1" type="button" style="display: none" value="取消" />
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('javascript/management/recruit/job/modify.js', function(modify) {
			$(function(){
				modify.init();
			})
		});
	</script>
</body>
</html>
