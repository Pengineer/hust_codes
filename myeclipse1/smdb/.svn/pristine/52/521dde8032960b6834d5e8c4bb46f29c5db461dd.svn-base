<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>添加任务</title>
    <s:include value="/innerBase.jsp"/>
    <style>
  	.node {cursor: pointer; }
	.node ellipse {fill: #FDAE6B;  /*stroke: steelblue; stroke-width: 2px;*/ }
	.current {fill: #10B5DB !important}
	.success {fill:#5cb85c }
	.node text { }
	.link {fill: none; stroke: #ccc; stroke-width: 2px; }
	.input_dataprocessing{border: 0; background: transparent; height: 18px; line-height: 18px; width: 99%; font-size: 12px; } 
	.table_td2_ {padding: 5px 2px; vertical-align: top; text-align: right; line-height: 20px; }
	.table_td3_ {padding: 5px 2px; vertical-align: top; width: 500px; line-height: 20px; }
	.table_td4_ {padding: 5px 2px; vertical-align: top; line-height: 20px; }
    </style>
</head>
<body>
<div class="link_bar">当前位置：数据入库&nbsp;&gt;&nbsp; 从数据源到中间表&nbsp;&gt;&nbsp; 创建任务
</div>
<div class="main">
    <s:form id="form_createtask" theme="simple" action="" namespace="dataProcessing/">
        <div id="info" style="display:none">
            <div class="">
                <div id="procedure" class="step_css" style = "display:none">
                    <ul>
                        <li class="proc" name="basic1"><span class="left_step"></span><span class="right_step">配置信息</span></li>
                        <li class="proc" name="basic2"><span class="left_step"></span><span class="right_step">主表XML与数据库字段对应关系</span></li>
                        <li class="proc" name="basic3"><span class="left_step"></span><span class="right_step">主外键关系</span></li>
                        <li class="proc" name="showDetail"><span class="left_step"></span><span class="right_step">详情</span></li>
                        <li class="proc step_oo"><span class="left_step"></span><span class="right_step">完成</span></li>
                    </ul>
                </div>
            </div>
            <div class="">
                <s:include value="/validateError.jsp"/>
                <div class="edit_info" id="basic1">
                    <table width="100%" border="0" cellspacing="2" cellpadding="0">
                        <tr class="table_tr2">
                            <td class="table_td2" width="120"><span class="table_title4">数据源：</span></td>
                            <td class="table_td3">
                                <select name = "sourceName" id = "sourceName">
                                </select>
                            </td>
                            <td class="table_td4"></td>
                        </tr>
                        <tr class="table_tr2">
                            <td class="table_td2" width="120"><span class="table_title4">数据类型：</span></td>
                            <td class="table_td3">
                            <select name = "typeName" id = "typeName">
                            </td>
                            <td class="table_td4"></td>
                        </tr>
                    </table>
                </div>
                <div id="basic2" class="edit_info">
                
	                <textarea id="basic2_template" style="display:none;">
	                    <table width="100%"  cellspacing="0" cellpadding="2" border = "0" style="text-align:center">
	                        <thead>
	                        	<tr class = "table_title_tr">
	                           	 	<td class="">Key</td>
	                           	 	<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
	                            	<td class="">value</td>
	                            	<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
	                            	<td width = "75"><button class = "btn2" id = "basic2_add" >添加一条</button></td>
	                       		</tr>
	                         </thead>
	                        <tbody>
	                        {for item in root}
	                        	<tr>
	                        		<td><input class = "input_dataprocessing key" readonly value = "${item.key}" name = "xmlKey${item_index}"/></td>
	                        		<td></td>
	                        		<td><select class = "input_dataprocessing value"  name = "xmlValue${item_index}" data = "${item.value}"></select></td>
	                        		<td></td>
	                        		<td><button class = "btn1 basic2_del"  >删除</button></td>
	                        	</tr>
	                        {/for}
	                        </tbody>
	                    </table>
	                   
	                 </textarea>
	                 <div id = "basic2Container" style ="" isempty = "true"></div>
                </div>
        </div>
    </s:form>

    <div id="optr" class="btn_bar2">
        <input id="prev" class="btn2" type="button" style="display: none" value="上一步"/>
        <input id="next" class="btn2" type="button" style="display: none" value="下一步"/>
        <input id="finish" class="btn1" type="button" style="display: none" value="完成"/>
        <input id="retry" class="btn2" type="button" style="display: none" value="重填"/>
        <input id="confirm" class="btn1" type="button" style="display: none" value="确定"/>
        <input id="cancel" class="btn1" type="button" style="display: none" value="取消"/>
    </div>
</div>
<script>
    seajs.use(['javascript/dataProcessing/fromSourceToMid/createTask.js','tool/d3/d3.v3.js'], function (create) {
        $(function () {
            create.init();
        })
    });
</script>
</body>

</html>

