<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>高级检索</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" src="javascript/lib/jquery/jquery.js"></script>
			<script type="text/javascript" src="javascript/common.js"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.validate.js"></script> 
			<script type="text/javascript" src="tool/jquery.datepick/jquery.datepick.js"></script> 
			<script type="text/javascript" src="tool/jquery.datepick/jquery.datepick-zh-CN.js"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.datepick.self.js"></script> 
			<script type="text/javascript" src="javascript/selfspace/memo/validate.js"></script>
			<script type="text/javascript" src="javascript/selfspace/memo/edit.js"></script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：我的备忘录&nbsp;&gt;&nbsp;高级检索
			</div>
			
			<div class="main">
				<s:form id="form_memo" action="advSearch" theme="simple" namespace="/selfspace/memo">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="100">标题：</td>
								<td class="table_td3"><s:textfield name="memo.title" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">是否提醒：</td>
								<td class="table_td3"><s:select name="memo.isRemind" value="-1" headerKey="-1" headerValue="--请选择--" list="#{'1':'是','0':'否'}" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">提醒方式：</td>
								<td class="table_td3"><s:select name="memo.remindWay" value="-1" headerKey="-1" headerValue="--请选择--" list="#{'1':'指定日期','2':'按天','3':'按周','4':'按月'}"  /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">更新时间：</td>
								<td class="table_td3">
									<s:textfield name="datetime1" cssClass="start date_hint" disabled="disabled" size="10" />
									至
									<s:textfield name="datetime2" cssClass="end date_hint" disabled="disabled" size="10" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div> 
					<s:include value="/submit.jsp" />
				</s:form>
			</div>
		</body>
		<script type="text/javascript">
			$("#submit").click(function(){
				if($("input[name='datetime1']").val() == "--不限--"){$("input[name='datetime1']").val("")};
				if($("input[name='datetime2']").val() == "--不限--"){$("input[name='datetime2']").val("")};
			});
		</script>
	
</html>