<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="person">
	<textarea id="view_template_person" style="display:none;">
	
		<div class="p_box_t">
			<div class="p_box_t_t">社科管理人员</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<table id="list_officer" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td>姓名</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="120">工作证号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80">行政职务</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="90">定职时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80">离职时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="100">人员类型</td>
					</tr>
				</thead>
				<tbody>
				{for item in officer}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class="table_txt_td">${item[1]}</td>
						<td></td>
						<td class="table_txt_td">${item[2]}</td>
						<td></td>
						<td class="table_txt_td">${item[3]}</td>
						<td></td>
						<td class="table_txt_td" >${item[4]}</td>
						<td></td>
						<td class="table_txt_td">${item[5]}</td>
						<td></td>
						<td class="table_txt_td">${item[6]}</td>
					</tr>
				{forelse}
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>
				{/for}
				</tbody>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr2">
					<td width="4"></td>
				</tr>
			</table>
		</div>
		
		<div class="p_box_t">
			<div class="p_box_t_t">教师</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<table id="list_teacher" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td>姓名</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="100">工作证号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80">职务</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="70">每年工作时间（月）</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="70">定职时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="70">离职时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80">人员类型</td>
					</tr>
				</thead>
				<tbody>
				{for item in teacher}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class="table_txt_td">${item[1]}</td>
						<td></td>
						<td class="table_txt_td">${item[2]}</td>
						<td></td>
						<td class="table_txt_td">${item[3]}</td>
						<td></td>
						<td class="table_txt_td">${item[4]}</td>
						<td></td>
						<td class="table_txt_td">${item[5]}</td>
						<td></td>
						<td class="table_txt_td">${item[6]}</td>
						<td></td>
						<td class="table_txt_td">${item[7]}</td>
					</tr>
				{forelse}
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>
				{/for}
				</tbody>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr2">
					<td width="4"></td>
				</tr>
			</table>
		</div>
		
		<div class="p_box_t">
			<div class="p_box_t_t">学生</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<table id="list_student" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td>姓名</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="60">学生证号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="30">学生类别</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="30">学生状态</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="40">入学时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="40">毕业时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="30">导师</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="40">专业</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="50">研究方向</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80">参与项目</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="90">学位论文题目</td>
					</tr>
				</thead>
				<tbody>
				{for item in student}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class="table_txt_td">${item[1]}</td>
						<td></td>
						<td class="table_txt_td">${item[12]}</td>
						<td></td>
						<td class="table_txt_td">${item[2]}</td>
						<td></td>
						<td class="table_txt_td">${item[3]}</td>
						<td></td>
						<td class="table_txt_td">${item[4]}</td>
						<td></td>
						<td class="table_txt_td">${item[5]}</td>
						<td></td>
						<td class="table_txt_td"><a id="${item[6]}" href="" class="link5" >${item[7]}</a></td>
						<td></td>
						<td class="table_txt_td">${item[8]}</td>
						<td></td>
						<td class="table_txt_td">${item[9]}</td>
						<td></td>
						<td class="table_txt_td">${item[10]}</td>
						<td></td>
						<td class="table_txt_td">${item[11]}</td>
					</tr>
				{forelse}
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>
				{/for}
				</tbody>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr2">
					<td width="4"></td>
				</tr>
			</table>
		</div>
	</textarea>
	<div id="view_container_person" style="display:none; clear:both;"></div>
</div>