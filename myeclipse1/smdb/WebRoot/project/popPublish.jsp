<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
	<div style="width:520px;">
		<form id="publish">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td11"><span>申报时间：</span></td>
					<td class="table_td12">
						<input type="text" id="applyStartDate" name="applyStartDate" class="input_css_other date" readonly="true" placeholder = "--不限--"/>
						<span> 至 </span>
						<input type="text" id="applyEndDate" name="applyEndDate" class="input_css_other date" readonly="true" placeholder = "--不限--"/>
					</td>
					<td class="table_td13"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11"><span>审核时间：</span></td>
					<td class="table_td12">
						<input type="text" id="auditStartDate" name="auditStartDate" class="input_css_other date" readonly="true" placeholder = "--不限--"/>
						<span> 至 </span>
						<input type="text" id="auditEndDate" name="auditEndDate" class="input_css_other date" readonly="true" placeholder = "--不限--"/>
					</td>
					<td class="table_td13"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td11"><span class = "table_title4">是否同意发布：</span></td>
					<td class="table_td12">
						<input type="radio" name="isPublish" id="radio_publish2" value="1" class="j_showOpinionFeedbackOrNot">
						<label for="radio_publish2" class="j_showOpinionFeedbackOrNot">同意</label>
						<input type="radio" name="isPublish" id="radio_publish1" value="0" class="j_showOpinionFeedbackOrNot" checked="checked">
						<label for="radio_publish1" class="j_showOpinionFeedbackOrNot">不同意</label>
					</td>
					<td class="table_td13"></td>
				</tr>
							
			</table>
				<div class="btn_div_view">
					<input id="submit" class="btn1 j_addSubmit" type="submit" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			</form>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/popPublish.js', function(publish) {
					var nameSpace = top.PopLayer.instances[1].inData.nameSpace;
					publish.init(nameSpace);
				});
			</script>
		</body>
		
	</html>