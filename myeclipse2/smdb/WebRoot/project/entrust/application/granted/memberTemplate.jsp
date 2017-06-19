<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>

<table id="table_member" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
	<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)">
		<tr>
			<td class="table_td8" width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"></span></b></div>&nbsp;</td>
			<td class="table_td9">
				<input type="button" class="add_member btn1" value="<s:text name="i18n_Add" />" />&nbsp;
				<input type="button" class="delete_row btn1" value="<s:text name="i18n_Delete" />" />&nbsp;
				<input type="button" class="up_row btn1" value="上移" />&nbsp;
				<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
				<span class="tip">可拖拽空白区域对成员排序</span>
			</td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8" width="130"><span class="table_title5"><s:text name="i18n_MemberType" />：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].memberType" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Teacher'),'2':getText('i18n_Expert'),'3':getText('i18n_Student')}" />
			</td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title5"><s:text name="i18n_Name" />：</span></td>
			<td class="table_td9">
				<input name="selectMember" type="button" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
				<div name="memberNameDiv" class="choose_show"></div>
				<s:hidden name="rms[].member.id"/>
				<s:hidden name="rms[].memberName"/>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_LocalUnitAndDeptInst" />：</span></td>
			<td class="table_td9">
				<div name="memberUnitDiv" class="unit_show"></div>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_SpecialistTitle" />：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].specialistTitle" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_Major" />：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].major" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_WorkMonthPerYear" />：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].workMonthPerYear" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_WorkDivision" />：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].workDivision" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title5"><s:text name="i18n_IsDirector" />：</span></td>
			<td class="table_td9">
				<s:select cssClass="select" name="rms[].isDirector" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="职务" />：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].position" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="出生日期" />：</span></td>
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
			<td class="table_td8"><span><s:text name="学历" />：</span></td>
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
				<input type="button" class="add_member btn1"  value="<s:text name="i18n_Add" />" />&nbsp;
				<input type="button" class="delete_row btn1" value="<s:text name="i18n_Delete" />" />&nbsp;
				<input type="button" class="up_row btn1" value="上移" />&nbsp;
				<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
				<span class="tip">可拖拽空白区域对成员排序</span>
			</td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8" width="110"><span class="table_title4"><s:text name='i18n_IdcardType' />：</span></td>
			<td class="table_td17" width="100">
				<s:select cssClass="select" name="rms[].idcardType" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
			</td>
			<td class="table_td10" width="70"></td>
			<td class="table_td8" width="80"><span class="table_title2"><s:text name='i18n_IdcardNumber' />：</span></td>
			<td class="table_td18" width="150">
				<s:textfield name="rms[].idcardNumber" cssClass="input_css2"/></td>
			<td class="table_td10" width="90"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4"><s:text name="i18n_MemberType" />：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].memberType" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Teacher'),'2':getText('i18n_Expert'),'3':getText('i18n_Student')}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title2"><s:text name="i18n_Name" />：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].memberName" cssClass="input_css2"/>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4"><s:text name="i18n_Gender" />：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].gender" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#application.sexList" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title2"><s:text name="i18n_LocalUnit" />：</span></td>
			<td class="table_td18">
				<div name="inputUniDiv" style="display:none;">
					<s:textfield name="rms[].agencyName" cssClass="input_css2"/>
				</div>
				<div name="selectUniDiv">
					<input name="selectUniversity" type="button" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
					<div name="universityNameDiv" class="choose_show"></div>
					<s:hidden name="rms[].university.id"/>
				</div>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4"><s:text name="i18n_DeptInstFlag" />：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].divisionType" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Institute2'),'2':getText('i18n_Department2'),'3':getText('i18n_OtherDeptInst')}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span class="table_title2"><s:text name="i18n_LocalDeptInst" />：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].divisionName" cssClass="input_css2"/>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_SpecialistTitle" />：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].specialistTitle" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span><s:text name="i18n_Major" />：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].major" cssClass="input_css2"></s:textfield>
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="i18n_WorkMonthPerYear" />：</span></td>
			<td class="table_td17">
				<s:textfield name="rms[].workMonthPerYear" cssClass="input_css1"/>
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span><s:text name="i18n_WorkDivision" />：</span></td>
			<td class="table_td18">
				<s:textfield name="rms[].workDivision" cssClass="input_css2" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span class="table_title4"><s:text name="i18n_IsDirector" />：</span></td>
			<td class="table_td17">
				<s:select cssClass="select" name="rms[].isDirector" value="1" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" />
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span><s:text name="职务" />：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].position" />
			</td>
			<td class="table_td10"></td>
		</tr>
		<tr>
			<td class="table_td8"><span><s:text name="出生日期" />：</span></td>
			<td class="table_td9">
				<s:textfield id="variation5" name="rms[].birthday" cssClass="FloraDatepick" readonly="true">
					<s:param name="value">
						<s:date name="rms[].birthday" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td10"></td>
			<td class="table_td8"><span><s:text name="学历" />：</span></td>
			<td class="table_td9">
				<s:textfield name="rms[].education" />
			</td>
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