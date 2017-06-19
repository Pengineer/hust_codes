<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="info" class="main" style="display:none">
	<div class="main_content">
		<div class="step_css">
			<ul>
				<li class="proc" name="basicInfo"><span class="left_step"></span><span class="right_step">基本信息</span></li>
				<li class="proc" name="managementInfo"><span class="left_step"></span><span class="right_step">社科管理部门</span></li>
				<li class="proc" name="financialInfo"><span class="left_step"></span><span class="right_step">财务管理部门</span></li>
				<li class="proc step_oo"><span class="left_step"></span><span class="right_step">完成</span></li>
			</ul>
		</div>
	</div>
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<div id="basicInfo">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="120"><span class="table_title4">单位名称：</span></td>
					<td class="table_td3"><s:textfield id="name" name="agency.name" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">英文名称：</td>
					<td class="table_td3"><s:textfield name="agency.englishName" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">名称缩写：</td>
					<td class="table_td3"><s:textfield name="agency.abbr" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">单位代码：</td>
					<td class="table_td3"><s:textfield name="agency.code" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title4">所在省份：</span></td>
					<td class="table_td3"><s:select cssClass="select" name="agency.province.id" id="province" headerKey="-1" headerValue="--请选择省--" list="%{unitService.getProvinceList()}" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">所在市：</td>
					<td class="table_td3"><s:select cssClass="select" name="agency.city.id" id="city" list="#{'':'--请选择市--'}" /></td>
					<td class="table_td4"></td>
				</tr>
				<s:if test="pageName == 'universityPage'">
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title4">单位类型：</span></td>
						<td class="table_td3"><s:select cssClass="select" name="agency.type" id="agencyType" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'3':getText('部属高校'),'4':getText('地方高校')}" /></td>
						<td class="table_td4"></td>
					</tr>
				</s:if>
				<s:else>
					<tr class="table_tr2">
						<td class="table_td2">单位类型：</td>
						<td class="table_td3">${param.type}</td>
						<td class="table_td4"></td>
					</tr>
				</s:else>
				<s:if test="pageName == 'universityPage'">
					<tr class="table_tr2">
						<td class="table_td2">举办者：</td>
						<td class="table_td3"><s:textfield name="univOrganizer" cssClass="input_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">举办者代码：</td>
						<td class="table_td3"><s:textfield name="univOrganizerCode" cssClass="input_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span class="table_title4">上级管理机构：</span></td>
						<td class="table_td3">
							<input type="button" id="select_subjection_btn" class="btn1 select_btn" value="选择"/>
							<span id="subjectionName" name="subjectionName" ></span>
							<s:hidden name="agency.subjection.id" id = "subjectionId" />
						</td>
						<td class="table_td4"></td>
					</tr>
<%--					<tr class="table_tr2">--%>
<%--						<td class="table_td2">高校性质：</td>--%>
<%--						<td class="table_td3"><s:select cssClass="select" name="agency.category" id="category" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('universityCategory', '00')}" /></td>--%>
<%--						<td class="table_td4"></td>--%>
<%--					</tr>--%>
<%--					<tr class="table_tr2">--%>
<%--						<td class="table_td2">办学类型：</td>--%>
<%--						<td class="table_td3"><s:select cssClass="select" name="agency.style" id="style" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('universityStyle', '00')}" /></td>--%>
<%--						<td class="table_td4"></td>--%>
<%--					</tr>--%>
					<tr class="table_tr2">
						<td class="table_td2">高校性质：</td>
						<td class="table_td3"><s:select cssClass="select" name="agency.category" id="category" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('universityCategory', null)}" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2">办学类型：</td>
						<td class="table_td3"><s:select cssClass="select" name="agency.style" id="style" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('universityStyle', null)}" /></td>
						<td class="table_td4"></td>
					</tr>
				</s:if>
				<s:else>
				</s:else>
				<tr class="table_tr2">
					<td class="table_td2">简介：</td>
					<td class="table_td3"><s:textarea name="agency.introduction" rows="6" cssClass="textarea_css" /></td>
					<td class="table_td4"></td>
				</tr>
			</table>
		</div>
		<div id="managementInfo" style="display:none">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="120"><span class="">社科管理部门：</span></td>
					<td class="table_td3"><s:textfield name="agency.sname" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门地址：</td>
					<td class="table_td3"><s:textfield name="agency.saddress" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门邮编：</td>
					<td class="table_td3"><s:textfield name="agency.spostcode" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">电话：</td>
					<td class="table_td3"><s:textfield name="agency.sphone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">传真：</td>
					<td class="table_td3"><s:textfield name="agency.sfax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="">邮箱：</span></td>
					<td class="table_td3"><s:textfield name="agency.semail" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门主页：</td>
					<td class="table_td3"><s:textfield name="agency.shomepage" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
			</table>
		</div>
		<div id="financialInfo" style="display:none">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="table_tr2">
					<td class="table_td2" width="120"><span class="">部门名称：</span></td>
					<td class="table_td3"><s:textfield name="agency.fname" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门负责人：</td>
					<td class="table_td3"><s:textfield name="agency.fdirector" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门联系人：</td>
					<td class="table_td3"><s:textfield name="agency.flinkman" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门地址：</td>
					<td class="table_td3"><s:textfield name="agency.faddress" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">部门邮编：</td>
					<td class="table_td3"><s:textfield name="agency.fpostcode" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2"><span class="">电话：</span></td>
					<td class="table_td3"><s:textfield name="agency.fphone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">传真：</td>
					<td class="table_td3"><s:textfield name="agency.ffax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">邮箱：</td>
					<td class="table_td3"><s:textfield name="agency.femail" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">开户银行：</td>
					<td class="table_td3"><s:select cssClass="select" name="agency.fbank" value="%{agency.fbank}" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('bank', null)}" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">银行支行：</td>
					<td class="table_td3"><s:textfield name="agency.fbankBranch" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">银联行号：</td>
					<td class="table_td3"><s:textfield name="agency.fcupNumber" cssClass="input_css" /></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">银行户名：</td>
					<td class="table_td3"><s:textfield name="agency.fbankAccountName" cssClass="input_css" /><span class="tip">请谨慎填写！</span></td>
					<td class="table_td4"></td>
				</tr>
				<tr class="table_tr2">
					<td class="table_td2">银行账号：</td>
					<td class="table_td3"><s:textfield name="agency.fbankAccount" cssClass="input_css" /><span class="tip">请谨慎填写！</span></td>
					<td class="table_td4"></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="optr" class="btn_bar2">
		<input id="prev" class="btn2" type="button" style="display: none" value="上一步" />
		<input id="next" class="btn2" type="button" style="display: none" value="下一步" />
		<input id="save" class="btn1" type="button" style="display: none" value="保存"/>
		<input id="cancel" class="btn1" type="button" style="display: none" value="取消" />
	</div>
</div>