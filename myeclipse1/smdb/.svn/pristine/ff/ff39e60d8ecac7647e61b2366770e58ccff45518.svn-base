<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>创建任务</title>
    <s:include value="/innerBase.jsp"/>
    <style>.adv_td1{display:block;width:100%}</style>
</head>
<body style="height:110px;width:auto">

<div class="main" >
  <table class="adv_table" id = "selectAction" width="100%" border="0" cellspacing="2" cellpadding="0">
  	<tbody>
  		<tr class = "adv_tr">
  			<td class = "adv_td1"><label><input type = "radio" name = "selectOption" id = "createTask"/> 暂不执行（加入任务配置模块进行管理）</label></td>
  		</tr>
  		<tr class = "adv_tr">
  			<td class = "adv_td1"><label><input type = "radio" name = "selectOption" id = "runTask"/> 立即执行</label></td>
  		</tr>
  		<tr class = "adv_tr" style = "margin-top:10px">
  			<td class = "adv_td1">
  				<button id = "confirm" class = "btn1 pull-left">确定</button>
  				<button id = "cancel" class = "btn1 pull-right">取消</button>
  				<br class = "clear-fix"/>
  			</td>
  		</tr> 		
  	<tbody>
  <table class="adv_table" id = "tips" width="100%" border="0" cellspacing="2" cellpadding="0" style = "display:none">
  	<tbody>
  		<tr class = "adv_tr">
  			<td class = "adv_td1"><span>任务正在执行中，请稍后...</span></td>
  		</tr>
  	<tbody>
  	
  </table>
</div>
<script>
    seajs.use('javascript/dataProcessing/fromSourceToMid/popSelect.js', function (popSelect) {
        $(function () {
        	popSelect.init();
        })
    });
</script>
</body>

</html>

