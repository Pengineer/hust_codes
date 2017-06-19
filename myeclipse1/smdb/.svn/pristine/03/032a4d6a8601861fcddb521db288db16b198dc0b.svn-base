<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>高校合并</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div style="width:450px;">
			<s:include value="/validateError.jsp" />
			<s:hidden name="checkedIds" id="checkedIds" />
			<s:hidden name="mainId" id="mainId" />
			<div>
				<p style="margin:0; padding:5px"><span class="highlight" style="float:left; margin-right:5px;"></span><span class="tip">请选择并修改合并后的单位信息</span></p>
			</div>
			<div id="university" style="height: 237px; overflow-y: scroll; overflow-x: hidden;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
					<thead id="list_head">
						<tr class="table_title_tr">
							<td width="30">选择</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td width="30">序号</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td>单位名称</td>
							<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
							<td>单位代码</td>
						</tr>
					</thead>
					<tbody>
					<s:iterator value="#request.list" status="stat">
						<tr>
							<td><input type="radio" name="entityId" value="<s:property value="#request.list[#stat.index][0]" />" /></td>
							<td></td>
							<td class="index"><s:property value="#stat.index+1" /></td>
							<td></td>
							<td class=""><s:property value="#request.list[#stat.index][1]" /></td>
							<td></td>
							<td class=""><s:property value="#request.list[#stat.index][2]" /></td>
						</tr>
					</s:iterator>
						<tr>
							<td colspan="3">合并后高校</td>
							<td></td>
							<td><s:textfield id="name" name="name"></s:textfield></td>
							<td></td>
							<td><s:textfield id="code" name="code"></s:textfield></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div>
				<p style="margin:0; padding:5px"><span class="error2" style="float:left; margin-right:5px;"></span><span class="warning">合并后以上所有单位的人员、项目、成果、奖励等信息将归并到选中单位。合并后将不可回退。</span></p>
			</div>
			<div class="btn_div_view">
				<input id="confirm" class="btn1" type="button" value="确定" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/unit/agency/university/merge.js', function(merge) {
				merge.init();
			});
		</script>
	</body>
</html>