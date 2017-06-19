<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="apply">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5">申报类型：</span></td>
			<td class="table_td3">
				<s:radio id="app" name="awardApplication.applicationType" value="#request.get('applicationType')" list="#{'1':getText('以个人名义申报'),'2':getText('以团队、课题组、机构等名义申报')}" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
   	<div id="individual">
		<table width="100%" border="0" cellspacing="2" cellpadding="2">
			<tr class="table_tr2">
				<td class="table_td2" width="130"><span class="table_title5">申请人：</span></td>
				<td class="table_td3"><s:textfield name="person.name" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">性别：</span></td>
				<td class="table_td3"><s:select cssClass="select" name="person.gender" headerKey="" headerValue="--%{getText('请选择')}--" list="#application.sexList" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">出生年月：</span></td>
				<td class="table_td3">
					<s:textfield name="person.birthday" cssClass="input_css FloraDatepick">
						<s:param name="value">
							<s:date name="%{person.birthday}" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">导师类型：</span></td>
				<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--%{getText('请选择')}--" list="%{baseService.getSystemOptionMapAsName('tutorType', null)}" name="academic.tutorType" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">专业技术职务：</span></td>
				<td class="table_td3"><s:select cssClass="select j_selectSubType" headerKey="-1" headerValue="--%{getText('请选择')}--" id="title" list="%{baseService.getSystemOptionMap('GBT8561-2001', null)}" name="specialityTitleId" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">二级职称：</td>
				<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--%{getText('请选择')}--" id="subTitle" list="#{'-1':'--请选择二级职称--'}" name="academic.specialityTitle" /></td>
				<s:hidden value="%{academic.specialityTitle}" id="subTitleId"></s:hidden>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">最后学位：</span></td>
				<td class="table_td3"><s:select cssClass="select" headerKey="" headerValue="--%{getText('请选择')}--" list="%{baseService.getSystemOptionMapAsName('degree', null)}" name="academic.lastDegree" /></td>
				<td class="table_td4"></td>
		    </tr>
		    <tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">外语语种：</span></td>
				<td class="table_td3">
					<input type="button" id="select_language_btn" class="btn1 select_btn" value="选择"/>
					<div id="language" class="choose_show"><s:property value="%{academic.language}"/></div>
					<s:hidden name="academic.language" />
				</td>
				<td class="table_td4"></td>
		    </tr>
			    <tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">博士后：</span></td>
				<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'0':getText('否'),'1':getText('在站'),'2':getText('出站')}" name="academic.postdoctor" /></td>
				<td class="table_td4"></td>
		    </tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">学科代码：</span></td>
				<td class="table_td3">
					<input type="button" class="btn1 select_btn" id="selectDiscipline" value="选择" />
					<s:hidden name="academic.discipline" id="9529" value="%{academic.discipline}" />
					<div id="9530" class="choose_show" style="display:none" ></div>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">通信地址：</span></td>
				<td class="table_td3"><s:textfield name="person.officeAddress" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">办公电话：</span></td>
				<td class="table_td3"><s:textfield name="person.officePhone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span>住宅电话：</span></td>
				<td class="table_td3"><s:textfield name="person.homePhone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">邮政编码：</span></td>
				<td class="table_td3"><s:textfield name="person.officePostcode" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">电子邮件：</span></td>
				<td class="table_td3"><s:textfield name="person.email" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">手机：</span></td>
				<td class="table_td3"><s:textfield name="person.mobilePhone" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">证件类型：</span></td>
				<td class="table_td3"><s:textfield name="person.idcardType" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">证件号码：</span></td>
				<td class="table_td3"><s:textfield name="person.idcardNumber" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div>	
	<div id="organization" style="display: none;">
		<table width="100%" border="0" cellspacing="2" cellpadding="2">
			<tr class="table_tr2">
				<td class="table_td2" width="130"><span class="table_title5">申请人：</span></td>
				<td class="table_td3">		
					<s:select cssClass="select" id="selectOrNot" name="isSelectOrgan" list="#{'1':getText('选择'),'0':getText('其他')}" headerKey="-1" headerValue="--%{getText('请选择')}--" />
					<span id="select_yes" style="display:none;"><s:select cssClass="select" id="select_organ_btn" name="product.id" list="%{#session.organizations}" headerKey="-1" headerValue="--%{getText('请选择')}--"/></span>
					<span id="select_no" style="display:none;"><s:textfield id="applicantName" name="product.orgName"/></span>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">所在单位：</span></td>
				<td class="table_td3">
					<input type="button" id="select_dep_btn" class="btn1 select_btn" value="院系" />
					<input type="button" id="select_ins_btn" class="btn2 select_btn" value="研究基地" />
					<div id="unit" class="choose_show"><s:property value="product.agencyName"/>&nbsp;<s:property value="product.divisionName"/></div>
					<s:hidden id="deptInstFlag" name="deptInstFlag" /><!-- 1：研究机构，2:院系 -->
					<s:hidden id="instituteId" name="product.institute.id" />
					<s:hidden id="departmentId" name="product.department.id"/>
					<s:hidden id="unitName" name="product.agencyName"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span>学科代码：</span></td>
				<td class="table_td3">
					<input type="button" id="organSelectDiscipline" class="btn1 select_btn" value="选择" />
					<s:hidden name="product.orgDiscipline" id="disciplineId" />
					<div id="disp" class="choose_show"><s:property value="product.orgDiscipline"/></div>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">通信地址：</span></td>
				<td class="table_td3"><s:textfield name="product.orgOfficeAddress" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">办公电话：</span></td>
				<td class="table_td3"><s:textfield name="product.orgOfficePhone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span>邮政编码：</span></td>
				<td class="table_td3"><s:textfield name="product.orgOfficePostcode" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">手机：</span></td>
				<td class="table_td3"><s:textfield name="product.orgMobilePhone" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5">电子邮件：</span></td>
				<td class="table_td3"><s:textfield name="product.orgEmail" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span>其他成员：</span></td>
				<td class="table_td3"><s:textfield name="product.orgMember" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div>
</div>