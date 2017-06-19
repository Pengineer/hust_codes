<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>

<div class="list_edit edit_info" id="member">
	<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
	<div id="membersort">
	<s:iterator value="rms" status="stat">
		<table class="table_valid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
				<tr>
					<td class="table_td8" width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
					<td class="table_td9">
						<input type="button" class="add_member btn1"  value="<s:text name="i18n_Add" />" />&nbsp;
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
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].memberType'}" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Teacher'),'2':getText('i18n_Expert'),'3':getText('i18n_Student')}" />
					</td>
					<td class="table_td10" width="90"></td>
				</tr>
				<s:if test="rms[#stat.index].member.id != null">
					<tr>
						<td class="table_td8"><span class="table_title5"><s:text name="i18n_Name" />：</span></td>
						<td class="table_td9">
							<input name="selectMember" type="button" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
							<div name="memberNameDiv" class="choose_show"><s:property value="%{rms[#stat.index].memberName}"/></div>
							<s:hidden name="%{'rms['+#stat.index+'].member.id'}" />
							<s:hidden name="%{'rms['+#stat.index+'].memberName'}"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr>
						<td class="table_td8"><span><s:text name="i18n_LocalUnitAndDeptInst" />：</span></td>
						<td class="table_td9">
							<div name="memberUnitDiv" class="unit_show"><s:property value="%{rms[#stat.index].agencyName}"/>&nbsp;<s:property value="%{rms[#stat.index].divisionName}"/></div>
						</td>
						<td class="table_td10"></td>
					</tr>
				</s:if>
				<s:else>
					<tr class="validTr">
						<td class="table_td8"><span class="table_title5"><s:text name="i18n_Name" />：</span></td>
						<td class="table_td9">
							<s:textfield name="%{'rms['+#stat.index+'].memberName'}" value="%{rms[#stat.index].memberName}" />
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class="validTr">
						<td class="table_td8"><span class="table_title5"><s:text name="i18n_LocalUnit" />：</span></td>
						<td class="table_td9">
							<s:textfield name="%{'rms['+#stat.index+'].agencyName'}" value ="%{rms[#stat.index].agencyName}" />
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class="validTr">
						<td class="table_td8"><span class="table_title5"><s:text name="i18n_LocalDeptInst" />：</span></td>
						<td class="table_td9">
							<s:textfield name="%{'rms['+#stat.index+'].divisionName'}" value ="%{rms[#stat.index].divisionName}" />
						</td>
						<td class="table_td10"></td>
					</tr>
				</s:else>
				<tr>
					<td class="table_td8"><span><s:text name="i18n_SpecialistTitle" />：</span></td>
					<td class="table_td9">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].specialistTitle'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="i18n_Major" />：</span></td>
					<td class="table_td9">
						<s:textfield name="%{'rms['+#stat.index+'].major'}" cssClass="input_css"></s:textfield>
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="i18n_WorkMonthPerYear" />：</span></td>
					<td class="table_td9">
						<s:textfield name="%{'rms['+#stat.index+'].workMonthPerYear'}" cssClass="input_css" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="i18n_WorkDivision" />：</span></td>
					<td class="table_td9">
						<s:textfield name="%{'rms['+#stat.index+'].workDivision'}" size="12" cssClass="input_css" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span class="table_title5"><s:text name="i18n_IsDirector" />：</span></td>
					<td class="table_td9">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].isDirector'}" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span class="table_title5"><s:text name="i18n_IsSubprojectDirector" />：</span></td>
					<td class="table_td9">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].isSubprojectDirector'}" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="职务" />：</span></td>
					<td class="table_td9">
						<s:textfield name="%{'rms['+#stat.index+'].position'}" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="出生日期" />：</span></td>
					<td class="table_td9">
						<s:textfield id="variation5" name="%{'rms['+#stat.index+'].birthday'}" cssClass="FloraDatepick" readonly="true">
							<s:param name="value">
								<s:date name="%{'rms['+#stat.index+'].birthday'}" format="yyyy-MM-dd" />
							</s:param>
						</s:textfield>
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="学历" />：</span></td>
					<td class="table_td9">
						<s:textfield name="%{'rms['+#stat.index+'].education'}" />
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
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].idcardType'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
					</td>
					<td class="table_td10" width="70"></td>
					<td class="table_td8" width="80"><span class="table_title2"><s:text name='i18n_IdcardNumber' />：</span></td>
					<td class="table_td18" width="150">
						<s:textfield name="%{'rms['+#stat.index+'].idcardNumber'}" value="%{rms[#stat.index].idcardNumber}" cssClass="input_css2"/></td>
					<td class="table_td10" width="90"></td>
				</tr>
				<tr>
					<td class="table_td8"><span class="table_title4"><s:text name="i18n_MemberType" />：</span></td>
					<td class="table_td17">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].memberType'}" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Teacher'),'2':getText('i18n_Expert'),'3':getText('i18n_Student')}" />
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span class="table_title2"><s:text name="i18n_Name" />：</span></td>
					<td class="table_td18">
						<s:textfield name="%{'rms['+#stat.index+'].memberName'}" value="%{rms[#stat.index].memberName}" cssClass="input_css2"/>
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span class="table_title4"><s:text name="i18n_Gender" />：</span></td>
					<td class="table_td17">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].gender'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#application.sexList" />
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span class="table_title2"><s:text name="i18n_LocalUnit" />：</span></td>
					<td class="table_td18">
						<div name="inputUniDiv" style="display:none;">
							<s:textfield name="%{'rms['+#stat.index+'].agencyName'}" value ="%{rms[#stat.index].agencyName}" cssClass="input_css2"/>
						</div>
						<div name="selectUniDiv">
							<input name="selectUniversity" type="button" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
							<div name="universityNameDiv" class="choose_show"><s:property value="%{rms[#stat.index].agencyName}"/></div>
							<s:hidden name="%{'rms['+#stat.index+'].university.id'}" value="%{rms[#stat.index].university.id}" />
						</div>
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span class="table_title4"><s:text name="i18n_DeptInstFlag" />：</span></td>
					<td class="table_td17">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].divisionType'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Institute2'),'2':getText('i18n_Department2'),'3':getText('i18n_OtherDeptInst')}" />
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span class="table_title2"><s:text name="i18n_LocalDeptInst" />：</span></td>
					<td class="table_td18">
						<s:textfield name="%{'rms['+#stat.index+'].divisionName'}" value ="%{rms[#stat.index].divisionName}" cssClass="input_css2"/>
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="i18n_SpecialistTitle" />：</span></td>
					<td class="table_td17">
						<s:select cssClass="select"  name="%{'rms['+#stat.index+'].specialistTitle'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span><s:text name="i18n_Major" />：</span></td>
					<td class="table_td18">
						<s:textfield name="%{'rms['+#stat.index+'].major'}" cssClass="input_css2"></s:textfield>
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="i18n_WorkMonthPerYear" />：</span></td>
					<td class="table_td17">
						<s:textfield name="%{'rms['+#stat.index+'].workMonthPerYear'}" cssClass="input_css1"/>
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span><s:text name="i18n_WorkDivision" />：</span></td>
					<td class="table_td18">
						<s:textfield name="%{'rms['+#stat.index+'].workDivision'}" cssClass="input_css2" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span class="table_title4"><s:text name="i18n_IsDirector" />：</span></td>
					<td class="table_td17">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].isDirector'}" value="1" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" />
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span class="table_title4"><s:text name="i18n_IsSubprojectDirector" />：</span></td>
					<td class="table_td18">
						<s:select cssClass="select" name="%{'rms['+#stat.index+'].isSubprojectDirector'}" headerKey='-1' headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="出生日期" />：</span></td>
					<td class="table_td17">
						<s:textfield id="variation5" name="%{'rms['+#stat.index+'].birthday'}" cssClass="FloraDatepick" readonly="true">
							<s:param name="value">
								<s:date name="%{'rms['+#stat.index+'].birthday'}" format="yyyy-MM-dd" />
							</s:param>
						</s:textfield>
					</td>
					<td class="table_td10"></td>
					<td class="table_td8"><span><s:text name="学历" />：</span></td>
					<td class="table_td18">
						<s:textfield name="%{'rms['+#stat.index+'].education'}" />
					</td>
					<td class="table_td10"></td>
				</tr>
				<tr>
					<td class="table_td8"><span><s:text name="职务" />：</span></td>
					<td class="table_td17">
						<s:textfield name="%{'rms['+#stat.index+'].position'}" />
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
	</s:iterator>
	</div>
	<div id="last_add_div" style="display:none;">
		<table  width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="table_td8" width="130" style="padding-left: 10px;"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"></span></b></div>&nbsp;</td>
				<td class="table_td9">
					<input type="button" class="add_last_table btn1" value="<s:text name="i18n_Add" />" />
				</td>
				<td class="table_td10" width="90"></td>
				<td>
			</tr>
		</table>
	</div>
</div>