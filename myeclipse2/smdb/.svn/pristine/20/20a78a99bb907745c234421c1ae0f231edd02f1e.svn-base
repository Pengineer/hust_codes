<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:hidden id="entityId" name="entityId" />
<s:hidden id="selectedTab" name="selectedTab"/>
<s:hidden id="listType" name="listType" />
<s:hidden id="projectid" name="projectid" />
<s:hidden id="varId" name="varId"/>
<s:hidden name="submitStatus"/>
<s:hidden id="defaultSelectCode" name="defaultSelectCode" />
<s:hidden id="defaultSelectProductTypeCode" name="defaultSelectProductTypeCode" />
<s:hidden id="oldProductTypeId" name="oldProductTypeId"/>
<s:hidden id="oldProductTypeOther" name="oldProductTypeOther"/>
<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
<table width="100%" border="0" cellspacing="2" cellpadding="2">
	<tr>
		<td colspan="3">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="line-height:28px; border:solid #999; border-width:0 0 1px 0;">
				<td align="left" style="padding-left:15px; font-weight:bold;">第<span id="num"><s:property value="times" /></span>次变更</td>
				<td align="right"><a id="download_model" href="">下载变更申请书模板</a></td>
			</table>
		</td>
	</tr>
	<tr class="table_tr2">
		<td class="table_td11" width="150">
			<span class="table_title6">
				变更事项：<br />
				<span class="select_all_box">全选&nbsp;<input id="checkAllVarItem" name="check" type="checkbox"  title="点击全选"/>
			</span>
		</td>
		<td class="table_td12">
<%--			<table id="var_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">--%>
<%--				<s:iterator value="#application.varItems" status="stat">--%>
<%--					<s:if test="(#stat.index)%4 == 0"><tr></s:if>--%>
<%--					<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.varItems[#stat.index][0]' />" type="checkbox" name="selectIssue" value="<s:property value='#application.varItems[#stat.index][0]' />" class="var_item" /></td>--%>
<%--					<td width="100" valign="top"><s:property value="#application.varItems[#stat.index][1]" /></td>--%>
<%--					<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>--%>
<%--				</s:iterator>--%>
<%--			</table>--%>
			<table id="var_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">
				<s:iterator value="varListForSelect" status="stat">
					<s:if test="(#stat.index)%4 == 0"><tr></s:if>
					<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='varListForSelect[#stat.index][0]' />" type="checkbox" name="selectIssue" value="<s:property value='varListForSelect[#stat.index][0]' />" class="var_item" /></td>
					<td width="100" valign="top"><s:property value="varListForSelect[#stat.index][1]" /></td>
					<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
				</s:iterator>
			</table>
		</td>
		<td class="table_td13" width='120'></td>
	</tr>

	<tr class="table_tr2">
		<td class="table_td11"><span class="table_title6">上传变更申请书：</span></td>
		<s:if test="flag == 0"><%-- 添加 --%>
			<td class="table_td12">
				<input type="file" id="file_add" />
				<s:hidden name="file"/>
			</td>
		</s:if>
		<s:elseif test="flag == 1"><%-- 修改 --%>
			<td class="table_td12">
				<input type="file" id="file_${varId}" />
				<s:hidden name="file"/>
			</td>
		</s:elseif>
		<td class="table_td13"></td>
	</tr>
	
	<tr id="cD" style="display:none;">
		<td colspan = '6'>
			<div id="member">
				<s:iterator value="members" status="stat">
					<table class="table_valid" width="100%" border="0" cellspacing="0" cellpadding="2">
						<tr>
							<td class="table_td8" width="130"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
							<td class="table_td17" colspan='4'>
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
								<s:select cssClass="select" name="%{'members['+#stat.index+'].idcardType'}" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
							</td>
							<td class="table_td10" width="70"></td>
							<td class="table_td8" width="80"><span class="table_title2">证件号：</span></td>
							<td class="table_td18" width="150">
								<s:textfield name="%{'members['+#stat.index+'].idcardNumber'}" value="%{members[#stat.index].idcardNumber}" cssClass="input_css2"/></td>
							<td class="table_td10" width="90"></td>
						</tr>
						<tr>
							<td class="table_td8"><span class="table_title4">成员类型：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="%{'members['+#stat.index+'].memberType'}" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('教师'),'2':getText('外部专家'),'3':getText('学生')}" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">姓名：</span></td>
							<td class="table_td18">
								<s:textfield name="%{'members['+#stat.index+'].memberName'}" value="%{members[#stat.index].memberName}" cssClass="input_css2"/>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span class="table_title4">性别：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="%{'members['+#stat.index+'].gender'}" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#application.sexList" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">所在单位：</span></td>
							<td class="table_td18">
								<div name="inputUniDiv" style="display:none;">
									<s:textfield name="%{'members['+#stat.index+'].agencyName'}" value ="%{members[#stat.index].agencyName}" cssClass="input_css2"/>
								</div>
								<div name="selectUniDiv">
									<input name="selectUniversity" type="button" class="btn1 select_btn" value="选择" />
									<div name="universityNameDiv" class="choose_show"><s:property value="%{members[#stat.index].agencyName}"/></div>
									<s:hidden name="%{'members['+#stat.index+'].university.id'}" value="%{members[#stat.index].university.id}" />
								</div>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span class="table_title4">部门类别：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="%{'members['+#stat.index+'].divisionType'}" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'1':getText('研究基地'),'2':getText('院系'),'3':getText('外部部门')}" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">所在部门：</span></td>
							<td class="table_td18">
								<s:textfield name="%{'members['+#stat.index+'].divisionName'}" value ="%{members[#stat.index].divisionName}" cssClass="input_css2"/>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span  class="table_title4">职称：</span></td>
							<td class="table_td17">
								<s:select cssClass="select"  name="%{'members['+#stat.index+'].specialistTitle'}" headerKey="-1" headerValue="--%{getText('请选择')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span>专业：</span></td>
							<td class="table_td18">
								<s:textfield name="%{'members['+#stat.index+'].major'}" cssClass="input_css2"></s:textfield>
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span class="table_title4">每年工作时间（月）：</span></td>
							<td class="table_td17">
								<s:textfield name="%{'members['+#stat.index+'].workMonthPerYear'}" cssClass="input_css1"/>
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span>分工情况：</span></td>
							<td class="table_td18">
								<s:textfield name="%{'members['+#stat.index+'].workDivision'}" cssClass="input_css2" />
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span class="table_title4">是否首席专家：</span></td>
							<td class="table_td17">
								<s:select cssClass="select" name="%{'members['+#stat.index+'].isDirector'}" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('是'),'0':getText('否')}" />
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span class="table_title2">是否子课题负责人</td>
							<td class="table_td18">
								<s:select cssClass="select" name="%{'members['+#stat.index+'].isSubprojectDirector'}" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('是'),'0':getText('否')}" />
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span><s:text name="出生日期" />：</span></td>
							<td class="table_td17">
								<s:textfield id="variation5" name="%{'members['+#stat.index+'].birthday'}" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="%{'members['+#stat.index+'].birthday'}" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td10"></td>
							<td class="table_td8"><span><s:text name="学历" />：</span></td>
							<td class="table_td18">
								<s:textfield name="%{'members['+#stat.index+'].education'}" />
							</td>
							<td class="table_td10"></td>
						</tr>
						<tr>
							<td class="table_td8"><span><s:text name="职务" />：</span></td>
							<td class="table_td17">
								<s:textfield name="%{'members['+#stat.index+'].position'}" />
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
					</table>
				</s:iterator>
			</div>
			<div id="last_add_div" style="display:none;">
				<table  width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="table_td8" width="130"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"></span></b></div>&nbsp;</td>
						<td class="table_td17" colspan='4'>
							<input type="button" class="add_last_table btn1" value="添加" />
						</td>
						<td class="table_td10" width="90"></td>
						<td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr id="cA" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">变更项目管理机构：</span></td>
		<td class="table_td12" style="line-height:22px;">
			<input type="button" id="select_dep_btn" class="btn1 select_btn" value="院系" />
			<input type="button" id="select_ins_btn" class="btn2 select_btn" value="研究基地" />
			<div id="unit" class="choose_show"><s:property value="ageDeptInst"/></div>
			<s:hidden id="deptInstFlag" name="deptInstFlag" /><!-- 1：研究机构，2:院系 -->
			<s:hidden id="deptInstId" name="deptInstId" />
			<s:hidden id="oldDeptInstId" name="oldDeptInstId"/>
		</td>
		<td class="table_td13"></td>
	</tr>
	<tr id="cPF" class="table_tr2" style="display:none;">
		<td class="table_td11" valign="top">
			<span class="table_title6">
				变更成果形式：</br>
				<span class="select_all_box">全选&nbsp;<input id="checkAllProductTypeItem" name="check" type="checkbox"  title="点击全选" />
			</span>
		</td>
		<td class="table_td12">
			<table border="0" cellspacing="0" cellpadding="2">
				<s:iterator value="#application.pdtItems" status="stat">
					<s:if test="(#stat.index)%4 == 0"><tr></s:if>
					<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.pdtItems[#stat.index][0]' />" type="checkbox" name="selectProductType" value="<s:property value='#application.pdtItems[#stat.index][0]' />" /></td>
					<td width="110" valign="top"><s:property value="#application.pdtItems[#stat.index][1]" /></td>
					<s:if test="#stat.last">
						<td colspan='3'>
							<span id="productTypeOtherSpan" style="display:none;">
								<s:textfield name="productTypeOther" cssClass="input_css_other"/>
								<br/><span class="tip">多个以分号（即;或；）隔开</span>
							</span>
						</td>
						<td></td>
					</s:if>
					<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
				</s:iterator>
			</table>
		</td>
		<td class="table_td13"></td>
	</tr>
	<s:hidden id="oldProductTypeId" name="oldProductTypeId"/>
	<s:hidden id="oldProductTypeOther" name="oldProductTypeOther"/>
	<tr id="cN" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">变更项目名称：</span></td>
		<td class="table_td12">
			中文名称：<s:textfield name="chineseName" cssClass="input_css1"/>
			</br></br>
			英文名称：<s:textfield name="englishName" cssClass="input_css1"/>
		</td>
		<td class="table_td13"></td>
	</tr>
	<s:hidden id="projectName" name="projectName"/>
	<tr id="cC" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">研究内容有重大调整：</span></td>
		<td class="table_td12">是</td>
		<td class="table_td13"></td>
	</tr>
	<tr id="pO" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">延期：</span></td>
		<td class="table_td12">
			<s:textfield id="variation5" name="newDate1" cssClass="FloraDatepick" readonly="true">
				<s:param name="value">
					<s:date name="newDate1" format="yyyy-MM-dd" />
				</s:param>
			</s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>上传延期项目计划书：</span>
			<s:if test="flag == 0"><%-- 添加 --%>
				<td class="table_td12">
					<input type="file" id="file_postponementPlan_add" />
					<s:hidden name="postponementPlanFile"/>
				</td>
			</s:if>
			<s:elseif test="flag == 1"><%-- 修改 --%>
				<td class="table_td12">
					<input type="file" id="file_postponementPlan_${varId}" />
					<s:hidden name="postponementPlanFile"/>
				</td>
			</s:elseif>
		</td>
		<td class="table_td13"></td>
	</tr>
	<s:hidden id="planEndDate" name="planEndDate"/>
	<s:hidden id="limitedDate" value="%{#request.limitedDate}" name="limitedDate"/>
	<tr id="sBO" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">自行中止项目：</span></td>
		<td class="table_td12">是</td>
		<td class="table_td13"></td>
	</tr>
	<tr id="aW" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">申请撤项：</span></td>
		<td class="table_td12">是</td>
		<td class="table_td13"></td>
	</tr>
	<tr id="o" class="table_tr2" style="display:none;">
		<td class="table_td11"><span class="table_title6">其他：</span></td>
		<td class="table_td12"><s:textfield name="otherInfo" cssClass="input_css"/></td>
		<td class="table_td13"></td>
	</tr>
	<tr id="fEE" class="table_tr2" style="display:none;">
		<td class="table_td11"><span><s:text name="变更项目经费预算"/>：</span></td>
		<td class="table_td12">
			<input type="button" id="addVarFee" class="btn1 select_btn" value="<s:text name="添加" />" />
			<div id="totalFee" class="choose_show"><s:property value="newFee.totalFee"/></div>
		</td>
			<s:hidden id="feeNote" name="newFee.feeNote" />
			<s:hidden id="bookFee" name="newFee.bookFee" />
			<s:hidden id="bookNote" name="newFee.bookNote" />
			<s:hidden id="dataFee" name="newFee.dataFee" />
			<s:hidden id="dataNote" name="newFee.dataNote" />
			<s:hidden id="travelFee" name="newFee.travelFee" />
			<s:hidden id="travelNote" name="newFee.travelNote" />
			<s:hidden id="conferenceFee" name="newFee.conferenceFee" />
			<s:hidden id="conferenceNote" name="newFee.conferenceNote" />
			<s:hidden id="internationalFee" name="newFee.internationalFee" />
			<s:hidden id="internationalNote" name="newFee.internationalNote" />
			<s:hidden id="deviceFee" name="newFee.deviceFee" />
			<s:hidden id="deviceNote" name="newFee.deviceNote" />
			<s:hidden id="consultationFee" name="newFee.consultationFee" />
			<s:hidden id="consultationNote" name="newFee.consultationNote" />
			<s:hidden id="laborFee" name="newFee.laborFee" />
			<s:hidden id="laborNote" name="newFee.laborNote" />
			<s:hidden id="printFee" name="newFee.printFee" />
			<s:hidden id="printNote" name="newFee.printNote" />
			<s:hidden id="indirectFee" name="newFee.indirectFee" />
			<s:hidden id="indirectNote" name="newFee.indirectNote" />
			<s:hidden id="otherFeeD" name="newFee.otherFee" />
			<s:hidden id="otherNote" name="newFee.otherNote" />
			<s:hidden id="totalFeeD" name="newFee.totalFee"/>
		<td class="table_td13"></td>
	</tr>
	<tr class="table_tr2">
		<td class="table_td11"><s:text name='变更原因'/>：</td>
		<td class="table_td12"><s:textarea name='variationReason' rows="2" cssClass="textarea_css"/></td>
		<td class="table_td13"></td>
	</tr>
	<tr class="table_tr2">
		<td class="table_td11"><span>备注：</span></td>
		<td class="table_td12"><s:textarea name="note" rows="2" cssClass="textarea_css" /></td>
		<td class="table_td13"></td>
	</tr>
</table>
<table id="table_member" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
	<tr>
		<td class="table_td8" width="130"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
		<td class="table_td17" colspan='4'>
			<input type="button" class="add_member btn1"  value="添加" />&nbsp;
			<input type="button" class="delete_row btn1" value="删除" />&nbsp;
			<input type="button" class="up_row btn1" value="上移" />&nbsp;
			<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
			<span class="tip">可拖拽空白区域对成员排序</span></td>
		<td class="table_td10" width="90"></td>
	</tr>
	<tr>
		<td class="table_td8" width="110"><span class="table_title9">证件类型：</span></td>
		<td class="table_td17" width="100">
			<s:select cssClass="select" name="members[].idcardType" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
		</td>
		<td class="table_td10" width="70"></td>
		<td class="table_td8" width="80"><span class="table_title2">证件号：</span></td>
		<td class="table_td18" width="150">
			<s:textfield name="members[].idcardNumber" cssClass="input_css2"/></td>
		<td class="table_td10" width="90"></td>
	</tr>
	<tr>
		<td class="table_td8"><span class="table_title9">成员类型：</span></td>
		<td class="table_td17">
			<s:select cssClass="select" name="members[].memberType" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('教师'),'2':getText('外部专家'),'3':getText('学生')}" />
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span class="table_title2">姓名：</span></td>
		<td class="table_td18">
			<s:textfield name="members[].memberName" cssClass="input_css2"/>
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span class="table_title9">性别：</span></td>
		<td class="table_td17">
			<s:select cssClass="select" name="members[].gender" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#application.sexList" />
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span class="table_title2">所在单位：</span></td>
		<td class="table_td18">
			<div name="inputUniDiv" style="display:none;">
				<s:textfield name="members[].agencyName" cssClass="input_css2"/>
			</div>
			<div name="selectUniDiv">
				<input name="selectUniversity" type="button" class="btn1 select_btn" value="选择" />
				<div name="universityNameDiv" class="choose_show"></div>
				<s:hidden name="members[].university.id"/>
			</div>
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span class="table_title9">部门类别：</span></td>
		<td class="table_td17">
			<s:select cssClass="select" name="members[].divisionType" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'1':getText('研究基地'),'2':getText('院系'),'3':getText('外部部门')}" />
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span class="table_title2">所在部门：</span></td>
		<td class="table_td18">
			<s:textfield name="members[].divisionName" cssClass="input_css2"/>
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span  class="table_title9">职称：</span></td>
		<td class="table_td17">
			<s:select cssClass="select" name="members[].specialistTitle" headerKey="-1" headerValue="--%{getText('请选择')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span>专业：</span></td>
		<td class="table_td18">
			<s:textfield name="members[].major" cssClass="input_css2"></s:textfield>
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span>分工情况：</span></td>
		<td class="table_td17">
			<s:textfield name="members[].workDivision" cssClass="input_css2" />
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span class="table_title2">每年工作时间（月）：</span></td>
		<td class="table_td18">
			<s:textfield name="members[].workMonthPerYear" cssClass="input_css1"/>
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span class="table_title9">是否首席专家：</span></td>
		<td class="table_td17">
			<s:select cssClass="select" name="members[].isDirector" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('是'),'0':getText('否')}" />
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span class="table_title2">是否子项目负责人：</span></td>
		<td class="table_td18">
			<s:select cssClass="select" name="members[].isSubprojectDirector" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('是'),'0':getText('否')}" />	
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span><s:text name="出生日期" />：</span></td>
		<td class="table_td17">
			<s:textfield id="variation5" name="members[].birthday" cssClass="FloraDatepick" readonly="true">
				<s:param name="value">
					<s:date name="members[].birthday" format="yyyy-MM-dd" />
				</s:param>
			</s:textfield>
		</td>
		<td class="table_td10"></td>
		<td class="table_td8"><span><s:text name="学历" />：</span></td>
		<td class="table_td18">
			<s:textfield name="members[].education" />
		</td>
		<td class="table_td10"></td>
	</tr>
	<tr>
		<td class="table_td8"><span><s:text name="职务" />：</span></td>
		<td class="table_td17">
			<s:textfield name="members[].position" />
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
</table>
