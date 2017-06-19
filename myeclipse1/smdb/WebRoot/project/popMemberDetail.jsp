<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<s:include value="/innerBase.jsp" />
	</head>
	
	<body style="width:400px;">
		<table class="table_valid" width="100%" border="0" cellspacing="0" cellpadding="2">
			<tr>
				<td class="table_td8" width="150"><span class="table_title6">类型：</span></td>
				<td><s:select id="memberType" cssClass="select" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家','3':'学生'}" readonly="true" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8" width="150"><span class="table_title6">姓名：</span></td>
				<td style="position:relative;">
					<s:textfield cssClass="keyword" id="memberName" />
					<div class="info" style="position:absolute;top:27px;left:4px;width:250px;background:#eee;z-index:100;display:none;">
						<div class="item" style="overflow:scroll;max-height:200px;"></div>
						<div style="position:absolute;bottom:0;height:17px;background:#CDAAEB;width:250px;text-align:center;"><a class="selectPerson" href="javascript:void(0);">更多</a></div>
					</div>
					<s:hidden id="memberId" />
					<s:hidden id="personId" />
				</td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>关联/新建：</span></td>
				<td class="table_td9"><span id="type"></span></td>
				<td class="table_td10"></td>
				<s:hidden id="typeId" />
			</tr>
			<tr class = "j_newMember" style="display: none;">
				<td class="table_td8"><span>所在单位及部门：</span></td>
				<td class="table_td9">
					<span class="choose_division" style="display:none;">
						<input type="button" class="btn1 select_dep_btn_1" value="院系" />
						<input type="button" class="btn2 select_ins_btn_1" value="研究基地" />
					</span>
					<span class="agencyName"></span><span class="divisionName"></span>
					<a id="selected_divison" class="reselect" href='javascript:void(0);' title='重选'><img src='image/del.gif' /></a>
					<s:hidden cssClass="universityId" />
					<s:hidden cssClass="departmentId" />
					<s:hidden cssClass="instituteId" />
					<s:hidden cssClass="divisionType" />
				</td>
				<td class="table_td10"></td>
			</tr>
			<tr class = "j_choseMember">
				<td class="table_td8"><span>所在单位及部门：</span></td>
				<td class="table_td9">
					<span class="agencyName"></span><span class="divisionName"></span>
					<s:hidden cssClass="universityId" />
					<s:hidden cssClass="departmentId" />
					<s:hidden cssClass="instituteId" />
					<s:hidden cssClass="divisionType" />
				</td>
				<td class="table_td10"></td>
			</tr>
			<tr class = "j_newMember" style="display: none">
				<td class="table_td8" ><span>证件类型：</span></td>
				<td class="table_td9"><s:select id="idcardType" cssClass="select" headerKey="-1" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/></td>
				<td class="table_td10"></td>
			</tr>
			<tr class = "j_newMember" style="display: none">
				<td class="table_td8" ><span>证件号：</span></td>
				<td class="table_td9"><s:textfield id="idcardNumber" cssClass="input_css2"  value="" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr class = "j_newMember" style="display: none">
				<td class="table_td8" ><span>性别：</span></td>
				<td class="table_td9"><s:select id="gender" cssClass="select" headerKey="-1" headerValue="--请选择--" list="#application.sexList" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>职称：</span></td>
				<td class="table_td9"><s:select id="specialistTitle" cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{projectService.getChildrenMapByParentIAndStandard()}" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>专业：</span></td>
				<td class="table_td9"><s:textfield id="major" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>每年工作时间（月）：</span></td>
				<td class="table_td9"><s:textfield id="workMonthPerYear" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>分工情况：</span></td>
				<td class="table_td9"><s:textfield id="workDivision" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span class="table_title6">是否负责人：</span></td>
				<td class="table_td9"><s:select id="isDirector" cssClass="select" headerKey='-1' headerValue="--请选择--" list="#{'1':'是','0':'否'}" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>职务：</span></td>
				<td class="table_td9"><s:textfield id="position" /></td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>出生日期：</span></td>
				<td class="table_td9">
					<s:textfield id="variation5" name="birthday" cssClass="FloraDatepick">
						<s:param name="value">
							<s:date name="birthday" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td10"></td>
			</tr>
			<tr>
				<td class="table_td8"><span>学历：</span></td>
				<td class="table_td9"><s:textfield id="education" /></td>
				<td class="table_td10"></td>
			</tr>
		</table>
		<div class="btn_div_view">
			<input id="confirm" class="btn1" type="button" value="确定">
			<input id="cancel" class="btn1" type="button" value="取消">
		</div>
		<script type="text/javascript">
			seajs.use('javascript/project/project_share/variation/popMemberDetail.js', function(detail) {
				detail.init();
			});
		</script>
	</body>
</html>