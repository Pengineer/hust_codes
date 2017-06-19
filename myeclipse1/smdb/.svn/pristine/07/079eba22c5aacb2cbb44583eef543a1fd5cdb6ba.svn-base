<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>查看任务列表</title>
    <s:include value="/innerBase.jsp" />
    <style>.auto_task_config{display:none}</style>
</head>

<body>
    <div class="link_bar">
        当前位置：任务配置模块&nbsp;&gt;&nbsp; 创建事务
    </div>
 		<form id = "template" method = "post" action = "">
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="130"><span class="table_title2">事务名：</span></td>
				<td class="table_td3">
					<input type = "text"  id = "taskConfigName" name = "taskConfigName" class = "input_css"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">任务类型：</span></td>
				<td class="table_td3">
				<input type = "radio" name = "taskType" value = "1"/> 自动 
				<input type = "radio" name = "taskType" value = "0"/> 手动
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2 auto_task_config">
				<td class="table_td2" width="100"><span class="table_title2">执行日期：</span></td>
				<td class="table_td3">
				<select name="executeTimeDay" id="executeTimeDay" data-unit="hour" data-min="0" data-max="23" data-step="1">
                            <option value="Monday">周一</option>
                            <option value="Tuesday">周二</option>
                            <option value="Wednesday">周三</option>
                            <option value="Thursday">周四</option>
                            <option value="Friday">周五</option>
                            <option value="Saturday">周六</option>
                            <option value="Sunday">周日</option>
                </select> &nbsp; 小时：
                 <select name="executeTimeHour" id="executeTimeHour" data-unit="hour" data-min="0" data-max="23" data-step="1">
                       <option value="0">12 am</option>
                       <option value="1">01 am</option>
                       <option value="2">02 am</option>
                       <option value="3">03 am</option>
                       <option value="4">04 am</option>
                       <option value="5">05 am</option>
                       <option value="6">06 am</option>
                       <option value="7">07 am</option>
                       <option value="8">08 am</option>
                       <option value="9">09 am</option>
                       <option value="10">10 am</option>
                       <option value="11">11 am</option>
                       <option value="12">12 pm</option>
                       <option value="13">01 pm</option>
                       <option value="14">02 pm</option>
                       <option value="15">03 pm</option>
                       <option value="16">04 pm</option>
                       <option value="17">05 pm</option>
                       <option value="18">06 pm</option>
                       <option value="19">07 pm</option>
                       <option value="20">08 pm</option>
                       <option value="21">09 pm</option>
                       <option value="22">10 pm</option>
                       <option value="23">11 pm</option>
                   </select> &nbsp; 分钟：
                   <select name="executeTimeMin" id="executeTimeMin" data-unit="minute" data-min="0" data-max="59" data-step="1">
                            <option value="0">00</option>
                            <option value="1">01</option>
                            <option value="2">02</option>
                            <option value="3">03</option>
                            <option value="4">04</option>
                            <option value="5">05</option>
                            <option value="6">06</option>
                            <option value="7">07</option>
                            <option value="8">08</option>
                            <option value="9">09</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                            <option value="13">13</option>
                            <option value="14">14</option>
                            <option value="15">15</option>
                            <option value="16">16</option>
                            <option value="17">17</option>
                            <option value="18">18</option>
                            <option value="19">19</option>
                            <option value="20">20</option>
                            <option value="21">21</option>
                            <option value="22">22</option>
                            <option value="23">23</option>
                            <option value="24">24</option>
                            <option value="25">25</option>
                            <option value="26">26</option>
                            <option value="27">27</option>
                            <option value="28">28</option>
                            <option value="29">29</option>
                            <option value="30">30</option>
                            <option value="31">31</option>
                            <option value="32">32</option>
                            <option value="33">33</option>
                            <option value="34">34</option>
                            <option value="35">35</option>
                            <option value="36">36</option>
                            <option value="37">37</option>
                            <option value="38">38</option>
                            <option value="39">39</option>
                            <option value="40">40</option>
                            <option value="41">41</option>
                            <option value="42">42</option>
                            <option value="43">43</option>
                            <option value="44">44</option>
                            <option value="45">45</option>
                            <option value="46">46</option>
                            <option value="47">47</option>
                            <option value="48">48</option>
                            <option value="49">49</option>
                            <option value="50">50</option>
                            <option value="51">51</option>
                            <option value="52">52</option>
                            <option value="53">53</option>
                            <option value="54">54</option>
                            <option value="55">55</option>
                            <option value="56">56</option>
                            <option value="57">57</option>
                            <option value="58">58</option>
                            <option value="59">59</option>
                        </select>
				</td>
				<td class="table_td4"><input type = "checkbox" name = "auto_time_config" id = "_time" checked/></td>
			</tr>
			<tr class="table_tr2 auto_task_config">
				<td class="table_td2" width="100"><span class="table_title2">时间间隔：</span></td>
				<td class="table_td3"><input type="text" name="interval" disabled/> 分钟</td> 
				<td class="table_td4"><input type = "checkbox" name = "auto_time_config" id = "_interval"/></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">添加任务：</td>
				<td class="table_td3">
				<table id="task-table">
				<tbody>
					<tr>
						<td colspan="5"><input id="add-task" class="btn1" type="button" value="添加"></td>
						<td></td>
					</tr>
					<tr id="tr_task_template"  style="display:none;">
						<td width="60">子任务<span class = "order" style = "color:red">1 </span> ：</td>
						<td>
							<select class = "taskList">
							<option value = "">--请选择任务--</option>
							</select>
							<input class="delete_row btn1" type="button" value="删除" name="">
						</td>
						<td width="100"></td>
					</tr>
				</tbody>
				</table>
				</td>
				<td class="table_td4"></td>
			</tr>
		</table>
		
		<div id="optr" class="btn_div_view">
			<input id="confirm" class="btn1" type="button" value="提交" />
			<input id="cancel" class="btn1" type="button" value="取消" />
		</div>
		</form>
    <script>
    seajs.use('javascript/dataProcessing/taskConfig/taskList.js', function(taskList) {
        $(function() {
            taskList.init();
        })
    });
    </script>
</body>

</html>

