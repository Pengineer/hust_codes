<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<s:hidden id = "saveOrSubmit" name ="saveOrSubmit"></s:hidden>
		<s:hidden id="rightNodeValues" name="rightNodeValues" />
		<s:hidden id="mainRoleId" name="mainRoleId" />
		<s:hidden id="defaultAccountType" name="role.defaultAccountType" />
		<s:hidden id="parentId" name="role.parentId" />
		<s:hidden id="defaultAgencyIds" name="defaultAgencyIds" />
		<s:hidden id="accountTypes" value="%{#session.loginer.currentType}"/>
		<s:hidden id="isPrincipal" value="%{#session.loginer.isPrincipal}"/>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">角色名称：</span></td>
				<td class="table_td3"><s:textfield name="role.name" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td  class="table_td2"><span class="table_title2">角色描述：</span></td>
				<td class="table_td3"><s:textarea name="role.description" rows="6" cssClass="textarea_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2" style="display:none;" id = "RoleType">
				<td class="table_td2">角色类型：</td>
				<td class="table_td3">
					<div id="type">
						<s:radio name="type" value="%{type}" list="#{'0':getText('非默认角色'),'1':getText('指定账号类型的默认角色'),'2':getText('指定机构的默认角色')}" />
					</div>
					<div id="accountType" class="role_type" style="display:none;">
						<table class="checkbox_css" width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="50%" style="display:;" id="MinistryMainAccount"><input type="checkbox" />部级主账号</td>
								<td width="50%" style="display:;" id="MinistrySubAccount"><input type="checkbox" />部级子账号</td>
							</tr>
							<tr>
								<td><input type="checkbox" style="display:;" id="ProvinceMainAccount"/>省级主账号</td>
								<td><input type="checkbox" style="display:;" id="ProvinceSubAccount"/>省级子账号</td>
							</tr>
							<tr>
								<td><input type="checkbox" style="display:;" id="MOEUniversityMainAccount"/>部属高校主账号</td>
								<td><input type="checkbox" style="display:;" id="MOEUniversitySubAccount"/>部属高校子账号</td>
							</tr>
							<tr>
								<td><input type="checkbox" style="display:;" id="LocalUniversityMainAccount"/>地方高校主账号</td>
								<td><input type="checkbox" style="display:;" id="LocalUniversitySubAccount"/>地方高校子账号</td>
							</tr>
							<tr>
								<td><input type="checkbox" style="display:;" id="DepartmentMainAccount"/>院系主账号</td>
								<td><input type="checkbox" style="display:;" id="DepartmentSubAccount"/>院系子账号</td>
							</tr>
							<tr>
								<td><input type="checkbox" style="display:;" id="InstituteMainAccount"/>基地主账号</td>
								<td><input type="checkbox" style="display:;" id="InstituteMainAccount"/>基地子账号</td>
							</tr>
							<tr>
								<td><input type="checkbox" style="display:;" id="ExpertAccount"/>外部专家账号</td>
								<td><input type="checkbox" style="display:;" id="TeacherAccount"/>教师账号</td>
							</tr>
							<tr>
								<td colspan="2"><input type="checkbox" style="display:;" id="StudentAccount"/>学生账号</td>
							</tr>
						</table>
					</div>
					<div id="agencyId" class="role_type" style="display:none;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr style="display:none;" id="DefaultMinistry">
								<td class="role_unit" width="90">部级管理机构：</td>
								<td class="role_unit">
									<input id="ministry" type="button" class="btn1 select_btn" value="选择" />
									<div id="ministry_name" class="choose_show"><s:property value="ministryName"/></div>
									<s:hidden name="ministryName" />
									<s:hidden id="ministry_id" name="ministryId" />
								</td>
							</tr>
							<tr style="display:none;" id="DefaultProvince">
								<td class="role_unit">省级管理机构：</td>
								<td class="role_unit">
									<input id="province" type="button" class="btn1 select_btn" value="选择" />
									<div id="province_name" class="choose_show"><s:property value="provinceName"/></div>
									<s:hidden name="provinceName" />
									<s:hidden id="province_id" name="provinceId" />
								</td>
							</tr>
							<tr style="display:none;" id="DefaultUniversity">
								<td class="role_unit">校级管理机构：</td>
								<td class="role_unit">
									<input id="university" type="button" class="btn1 select_btn" value="选择" />
									<div id="university_name" class="choose_show"><s:property value="universityName"/></div>
									<s:hidden name="universityName" />
									<s:hidden id="university_id" name="universityId" />
								</td>
							</tr>
						</table>
						<hr />
						<table id="applyTo" class="checkbox_css" width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr style="display:none;" id="MainSubAccount">
								<td align="center" width="50%">
									<input type="checkbox" />主账号
								</td>
								<td align="center">
									<input type="checkbox" />子账号
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2" id="ownRole" style="display:;">
				<td class="table_td2"><s:text name="主角色" />：</td>
				<td class="adv_td3" colspan="5">
						<table id="role_list" width="100%" border="0" cellspacing="0" cellpadding="2">
							<s:iterator value="roles" status="stat">
								<s:if test="(#stat.index)%4 == 0"><tr></s:if>
								<td width="20" style="text-align:center;" valign="top" ><input id="<s:property value='roles[#stat.index][0].id' />" type="radio" name="mainRole" value="<s:property value='roles[#stat.index][0].name' />" /></td>
								<td width="90" valign="top"><s:property value="roles[#stat.index][0].name" /></td>
								<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
							</s:iterator>
						</table>
					</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><s:text name="权限名称" />：</td>
				<td class="table_td3"><s:textfield id="keyword" cssClass="input_css" /></td>
				<td  class="table_td4"><input id="button_query" type="button" value="检索" class="btn1" /></td>
			</tr>
			<tr class="table_tr2" id="rightTree" style="display:none;">
				<td class="table_td2">角色权限：</td>
				<td class="table_td3"><div id="treeId" style="border:solid 1px #aaa;"></div></td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div> 
	<div class="btn_bar2">
		<input id="save" class="btn1" type="submit" value="<s:text name='保存' />" />
		<input id="submit" class="btn1" type="submit" value="确定" />
		<input id="cancel" class="btn1" type="button" value="取消" onclick="history.back();" />
	</div>
</div>