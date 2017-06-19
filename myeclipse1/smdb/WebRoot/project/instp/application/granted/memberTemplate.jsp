<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>

<table id="table_member" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
	<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
		<tr>
			<td class="table_td8" width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"></span></b></div>&nbsp;</td>
			<td class="table_td9">
				<input type="button" class="add_member btn1" value="添加" />&nbsp;
				<input type="button" class="delete_row btn1" value="删除" />&nbsp;
				<input type="button" class="up_row btn1" value="上移" />&nbsp;
				<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
				<span class="tip">可拖拽空白区域对成员排序</span>
			</td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8" width="130"><span class="table_title5">成员类型：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].memberType" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家','3':'学生'}" />
			</td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title5">姓名：</span></td>
			<td class="table_td9">
				<input name="selectMember" type="button" class="btn1 select_btn" value="选择" />
				<div name="memberNameDiv" class="choose_show"></div>
				<s:hidden name="rms[].member.id"/>
				<s:hidden name="rms[].memberName"/>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>所在单位与部门：</span></td>
			<td class="table_td9">
				<div name="memberUnitDiv" class="unit_show"></div>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>职称：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].specialistTitle" headerKey="-1" headerValue="--请选择--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>专业：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].major" cssClass="input_css"></s:textfield>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>每年工作时间（月）：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].workMonthPerYear" cssClass="input_css" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>分工情况：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].workDivision" size="12" cssClass="input_css" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title5">是否负责人：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].isDirector" headerKey='-1' headerValue="--请选择--" list="#{'1':'是','0':'否'}" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title5">是否子项目负责人：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].isSubprojectDirector" headerKey='-1' headerValue="--请选择--" list="#{'1':'是','0':'否'}" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>职务：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].position" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>出生日期：</span></td>
			<td class="table_td9">
				<s:textfield id="variation5" name="rms[].birthday" cssClass="FloraDatepick" readonly="true">
					<s:param name="value">
						<s:date name="rms[].birthday" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>学历：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].education" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td8"></td>
			<td class="table_td9"></td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"></td>
			<td class="table_td9"></td>
			<td class="table_td10"></td>
		</tr>
	</s:if>
	<s:else>
		<tr>
			<td class="table_td8" width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
			<td class="table_td17" colspan="4">
				<input type="button" class="add_member btn1"  value="添加" />&nbsp;
				<input type="button" class="delete_row btn1" value="删除" />&nbsp;
				<input type="button" class="up_row btn1" value="上移" />&nbsp;
				<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
				<span class="tip">可拖拽空白区域对成员排序</span>
			</td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8" width="110"><span class="table_title4">证件类型：</span></td>
			<td class="table_td17" width="100">
				<s:select cssClass="select" name="rms[].idcardType" headerKey="-1" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
			</td>
			<td class="table_td10" width="70"></td>
			<td class="table_td8" width="80"><span class="table_title2">证件号：</span></td>
			<td class="table_td18" width="150">
				<s:textfield name="rms[].idcardNumber" cssClass="input_css2"/></td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4">成员类型：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].memberType" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家','3':'学生'}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title2">姓名：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].memberName" cssClass="input_css2"/>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4">性别：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].gender" headerKey="-1" headerValue="--请选择--" list="#application.sexList" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title2">所在单位：</span></td>
			<td class="table_td18">
				<div name="inputUniDiv" style="display:none;">
					<s:textfield name="rms[].agencyName" cssClass="input_css2"/>
				</div>
				<div name="selectUniDiv">
					<input name="selectUniversity" type="button" class="btn1 select_btn" value="选择" />
					<div name="universityNameDiv" class="choose_show"></div>
					<s:hidden name="rms[].university.id"/>
				</div>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4">部门类别：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].divisionType" headerKey="-1" headerValue="--请选择--" list="#{'1':'研究基地','2':'院系','3':'外部部门'}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title2">所在部门：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].divisionName" cssClass="input_css2"/>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>职称：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].specialistTitle" headerKey="-1" headerValue="--请选择--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span>专业：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].major" cssClass="input_css2"></s:textfield>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>每年工作时间（月）：</span></td>
			<td class="table_td17">
				<s:textfield name="rms[].workMonthPerYear" cssClass="input_css1"/>
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span>分工情况：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].workDivision" cssClass="input_css2" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4">是否负责人：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].isDirector" headerKey='-1' headerValue="--请选择--" list="#{'1':'是','0':'否'}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title4">是否子项目负责人：</span></td>
			<td class="table_td18">
				<s:select cssClass="select" name="rms[].isSubprojectDirector" headerKey='-1' headerValue="--请选择--" list="#{'1':'是','0':'否'}" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>出生日期：</span></td>
			<td class="table_td18">
				<s:textfield id="variation5" name="%{'rms['+#stat.index+'].birthday'}" cssClass="FloraDatepick" readonly="true">
					<s:param name="value">
						<s:date name="rms[].birthday" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span>学历：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].education" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span>职务：</span></td>
			<td class="table_td17">
				<s:textfield name="rms[].position" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"></td>
			<td class="table_td18"></td>
			<td class="table_td10"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td8"></td>
			<td class="table_td17"></td>
			<td class="table_td10"></td>
			<td class="table_td8"></td>
			<td class="table_td18"></td>
			<td class="table_td10"></td>
		</tr>
	</s:else>
</table>