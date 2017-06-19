<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="220"><span class="table_title6">业务类型：</span></td>
				<td class="table_td3"><s:select cssClass="select j_selectSubType" name="business.type" id="type" headerKey="-1" headerValue="--请选择业务类型--" list="%{baseService.getSystemOptionMap('businessType', null)}" /></td>
				<td class="table_td4" width="80"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title6">业务子类：</td>
				<td class="table_td3"><s:select cssClass="select" name="business.subType.id" id="subType" list="#{'-1':'--请选择业务子类--'}" />
					<s:hidden id ="subTypeId" value="%{business.subType.id}" />
					<s:hidden name="entityId" id="business.id"/>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title6">业务设置：</span></td>
				<td class="table_td3">
					<s:select cssClass="select" name="business.status" headerKey="-1" headerValue="--%{getText('请选择')}--"
						list="#{'1':getText('业务激活中'),'0':getText('业务停止')}" />
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title6">业务起始时间：</span></td>
				<td class="table_td3">
		 			<s:textfield name="business.startDate" cssClass="FloraDatepick" readonly="true">
						<s:param name="value">
							<s:date name="business.startDate" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span id="startEndYear">业务对象起止年份：</span></td>
				<td class="table_td3">
					<s:select cssClass="select" name="business.startYear" list="%{businessService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
					至
					<s:select cssClass="select" name="business.endYear" list="%{businessService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				</td>
				<td class="table_td4"></td>
			</tr>
		    <tr class="table_tr2" id="businessYear">
			    <td class="table_td2"><span class="table_title6">业务年份：</span></td>
			    <td class="table_td3">
			        <s:select cssClass="select" name="business.businessYear" list="%{businessService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
			    </td>
			    <td class="table_td4"></td>
		    </tr>
			<tr class="table_tr2">
				<td class="table_td2">申报截止时间：</td>
				<td class="table_td3">
					<s:textfield name="business.applicantDeadline" cssClass="FloraDatepick" readonly="true">
						<s:param name="value">
							<s:date name="business.applicantDeadline" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">部门截止时间：</td>
				<td class="table_td3">
					<s:textfield name="business.deptInstDeadline" cssClass="FloraDatepick" readonly="true">
						<s:param name="value">
							<s:date name="business.deptInstDeadline" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">高校截止时间：</td>
				<td class="table_td3">
					<s:textfield name="business.univDeadline" cssClass="FloraDatepick" readonly="true">
						<s:param name="value">
							<s:date name="business.univDeadline" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">省厅截止时间：</td>
				<td class="table_td3">
					<s:textfield name="business.provDeadline" cssClass="FloraDatepick" readonly="true">
						<s:param name="value">
							<s:date name="business.provDeadline" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2" id="reviewDeadline">
				<td class="table_td2">专家评审截止时间：</td>
				<td class="table_td3">
					<s:textfield name="business.reviewDeadline" cssClass="FloraDatepick" readonly="true">
						<s:param name="value">
							<s:date name="business.reviewDeadline" format="yyyy-MM-dd" />
						</s:param>
					</s:textfield>
				</td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div> 
	<div class="btn_bar2">
		<input class="btn1 j_submitBusiness" type="button" value="确定"/>
		<input class="btn1" type="button" value="取消" onclick="history.back();" />
	</div>
</div>


