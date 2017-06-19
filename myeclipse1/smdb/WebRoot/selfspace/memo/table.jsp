<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<s:hidden id="entityId" name="entityId" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title3">标题：</span></td>
				<td class="table_td3"><s:textfield name="memo.title" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">发布者：</td>
				<td class="table_td3"><s:property value="#session.loginer.passport.name" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title3">是否提醒：</span></td>
				<td class="table_td3"><s:radio name="memo.isRemind" value="%{memo.isRemind}" list="#{'1':'是','0':'否'}" cssClass="r_type" /></td>
				<td class="table_td4"></td>
			</tr>
				<tr id="s_type" class="table_tr2" style="display:none;">
				<td class="table_td2"><span class="table_title3">提醒方式：</span></td>
				<td class="table_td3"><s:select id="_type" name="memo.remindWay" value="%{memo.remindWay}" headerKey='0' headerValue="--请选择--" 
					list="#{'1':'指定日期','2':'按天提醒','3':'按周提醒','4':'按月提醒'}"/> </td>
				<td class="table_td4"></td>
			</tr>
			<tr class="_remind table_tr2" style="display:none;">
				<td class="table_td2" style="width: 100px;"><span class="table_title3">指定日期：</span></td>
				<td class="table_td3"><s:textfield name="memo.remindTime">
					  <s:param name="value">
						 <s:date name="%{memo.remindTime}" format="yyyy-MM-dd" />
					  </s:param>
				   </s:textfield>
			   </td>
				<td class="table_td4"></td>
			</tr>
		   <tr class="_remind table_tr2" style="display:none;">
		      	<td class="table_td2"><span class="table_title3">按天提醒：</span></td>
				<td class="table_td3" >
					  提醒时间段：<s:textfield name="memo.startDateDay" cssClass="start"><s:param name="value"><s:date name="%{memo.startDateDay}" format="yyyy-MM-dd" /></s:param></s:textfield>至
					  			<s:textfield name="memo.endDateDay" cssClass="end"><s:param name="value"><s:date name="%{memo.endDateDay}" format="yyyy-MM-dd"/></s:param></s:textfield>
					 <br /> <br />
					排除时间：<s:textarea name="memo.excludeDate" rows="2" cssClass="multiDate textarea_css"></s:textarea>
				</td>
				<td class="table_td4"></td>
		    </tr>
			<tr class="_remind table_tr2" style="display:none;">
				<td class="table_td2"><span class="table_title3">按周提醒：</span></td>
				<td class="table_td3">
						提醒时间段：<s:textfield name="memo.startDateWeek" cssClass="start"><s:param name="value"><s:date name="%{memo.startDateWeek}" format="yyyy-MM-dd"/></s:param></s:textfield>至
						           <s:textfield name="memo.endDateWeek" cssClass="end"><s:param name="value"><s:date name="%{memo.endDateWeek}" format="yyyy-MM-dd"/></s:param></s:textfield>
						<br /><br />每周提醒时间：<s:checkboxlist id="week" name="memo.week" list="#{'0':'星期日','1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六'}"></s:checkboxlist>
			   			<s:hidden value="%{memo.week}" id="m_week" />
				</td>
				<td class="table_td4"></td> 
			</tr>
			<tr class="_remind table_tr2" style="display:none;">
		      	<td class="table_td2"><span class="table_title3">按月提醒：</span></td>
				<td class="table_td3">
					指定月、日：<s:textarea name="memo.month" rows="2" cssClass="multiDate textarea_css"></s:textarea>
					</td>
					<td class="table_td4"></td>
		    </tr>
			<tr class="table_tr2">
				<td class="table_td2">正文：</td>
				<td colspan="2">
					<FCK:editor instanceName="memo.content" value="${memo.content}" basePath="/tool/fckeditor" width="100%" height="250" toolbarSet="Message"></FCK:editor>
				</td>
			</tr>
		</table>
	</div>
	<s:include value="/submit.jsp" />
</div>