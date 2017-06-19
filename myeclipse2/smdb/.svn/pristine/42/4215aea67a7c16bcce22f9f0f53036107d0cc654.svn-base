<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<s:hidden id="entityId" name="entityId" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title3"><s:text name="标题" />：</span></td>
				<td class="table_td3"><s:textfield name="memo.title" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><s:text name="发布者" />：</td>
				<td class="table_td3"><s:property value="#session.loginer.passport.name" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title3"><s:text name="是否提醒" />：</span></td>
				<td class="table_td3"><s:radio name="memo.isRemind" value="%{memo.isRemind}" list="#{'1':getText('是'),'0':getText('否')}" cssClass="r_type" /></td>
				<td class="table_td4"></td>
			</tr>
				<tr id="s_type" class="table_tr2" style="display:none;">
				<td class="table_td2"><span class="table_title3"><s:text name="提醒方式" />：</span></td>
				<td class="table_td3"><s:select id="_type" name="memo.remindWay" value="%{memo.remindWay}" headerKey='0' headerValue="--%{getText('请选择')}--" 
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
		      	<td class="table_td2"><span class="table_title3"><s:text name="按天提醒" />：</span></td>
				<td class="table_td3" >
					  提醒时间段：<s:textfield name="memo.startDateDay" cssClass="start"><s:param name="value"><s:date name="%{memo.startDateDay}" format="yyyy-MM-dd" /></s:param></s:textfield>至
					  			<s:textfield name="memo.endDateDay" cssClass="end"><s:param name="value"><s:date name="%{memo.endDateDay}" format="yyyy-MM-dd"/></s:param></s:textfield>
					 <br /> <br />
					排除时间：<s:textarea name="memo.excludeDate" rows="2" cssClass="multiDate textarea_css"></s:textarea>
				</td>
				<td class="table_td4"></td>
		    </tr>
			<tr class="_remind table_tr2" style="display:none;">
				<td class="table_td2"><span class="table_title3"><s:text name="按周提醒" />：</span></td>
				<td class="table_td3">
						提醒时间段：<s:textfield name="memo.startDateWeek" cssClass="start"><s:param name="value"><s:date name="%{memo.startDateWeek}" format="yyyy-MM-dd"/></s:param></s:textfield>至
						           <s:textfield name="memo.endDateWeek" cssClass="end"><s:param name="value"><s:date name="%{memo.endDateWeek}" format="yyyy-MM-dd"/></s:param></s:textfield>
						<br /><br />每周提醒时间：<s:checkboxlist id="week" name="memo.week" list="#{'0':'星期日','1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六'}"></s:checkboxlist>
			   			<s:hidden value="%{memo.week}" id="m_week" />
				</td>
				<td class="table_td4"></td> 
			</tr>
			<tr class="_remind table_tr2" style="display:none;">
		      	<td class="table_td2"><span class="table_title3"><s:text name="按月提醒" />：</span></td>
				<td class="table_td3">
<%--					提醒时间段：<s:textfield name="memo.startDateMonth" cssClass="start"><s:param name="value"><s:date name="%{memo.startDateMonth}" format="yyyy-MM-dd" /></s:param></s:textfield>至--%>
<%--					  			<s:textfield name="memo.endDateMonth" cssClass="end"><s:param name="value"><s:date name="%{memo.endDateMonth}" format="yyyy-MM-dd"/></s:param></s:textfield><br/>--%>
					指定月、日：<s:textarea name="memo.month" rows="2" cssClass="multiDate textarea_css"></s:textarea>
					</td>
					<td class="table_td4"></td>
		    </tr>
					<%--指定月、日：--%>
<%--				<div>--%>
<%--					<s:hidden id="memo_month" name="memo.month" />--%>
<%--					<table border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">--%>
<%--						<tr class="table_tr7"><td width="30">&nbsp;</td><td width="30">&nbsp;</td><td width="30" class="month">1</td><td width="30" class="month">2</td><td width="30" class="month">3</td><td width="30" class="month">4</td><td width="30" class="month">5</td></tr>--%>
<%--						<tr class="table_tr7"><td class="month">6</td><td class="month">7</td><td class="month">8</td><td class="month">9</td><td class="month">10</td><td class="month">11</td><td class="month">12</td></tr>--%>
<%--						<tr class="table_tr7"><td class="month">13</td><td class="month">14</td><td class="month">15</td><td class="month">16</td><td class="month">17</td><td class="month">18</td><td class="month">19</td></tr>--%>
<%--						<tr class="table_tr7"><td class="month">20</td><td class="month">21</td><td class="month">22</td><td class="month">23</td><td class="month">24</td><td class="month">25</td><td class="month">26</td></tr>--%>
<%--						<tr class="table_tr7"><td class="month">27</td><td class="month">28</td><td class="month">29</td><td class="month">30</td><td class="month">31</td><td>&nbsp;</td ><td>&nbsp;</td></tr>--%>
<%--					</table>--%>
<%--				</div>--%>
				
				
<%--		    <tr class="_remind table_tr2" style="display:none;">--%>
<%--		    	<td class="table_td2"><span class="table_title3"><s:text name="按倒计时" />：</span></td>--%>
<%--		    	<td class="table_td3">--%>
<%--		    		开始时间：<s:textfield name="memo.startDateReverse" cssClass="start"><s:param name="value"><s:date name="%{memo.startDateReverse}" format="yyyy-MM-dd" /></s:param></s:textfield>--%>
<%--		    					<br/><br/>时<s:select name="starthours" value="%{starthours}" headerKey='-1' headerValue="--%{getText('请选择')}--" --%>
<%--							list="#{'0':'00','1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23'}"/> --%>
<%--								&nbsp;&nbsp;&nbsp;分<s:select name="startminutes" value="%{startminutes}" headerKey='-1' headerValue="--%{getText('请选择')}--" --%>
<%--							list="#{'0':'00','1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23',--%>
<%--									'24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47',--%>
<%--									'48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59'}"/> --%>
<%--									&nbsp;&nbsp;&nbsp;秒<s:select name="startseconds" value="%{startseconds}" headerKey='-1' headerValue="--%{getText('请选择')}--" --%>
<%--							list="#{'0':'00','1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23',--%>
<%--									'24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47',--%>
<%--									'48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59'}"/> --%>
<%--							<br /><br />--%>
<%--					请选择提醒时间：--%>
<%--					<s:textfield name="reverseDate" cssClass="start" size="10" />--%>
<%--						<s:textfield name="memo.reverseRemindTime" cssClass="end"><s:param name="value"><s:date name="%{memo.reverseRemindTime}" format="yyyy-MM-dd" /></s:param></s:textfield>--%>
<%--		    				 &nbsp;&nbsp;时<s:select cssStyle="width:44px" name="hours" value="%{hours}" headerKey='0' headerValue="00"  --%>
<%--							list="#{'1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23'}"/> --%>
<%--								分<s:select cssStyle="width:44px" name="minutes" value="%{minutes}" headerKey='0' headerValue="00" --%>
<%--							list="#{'1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23',--%>
<%--									'24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47',--%>
<%--									'48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59'}"/> --%>
<%--									&nbsp;&nbsp;&nbsp;秒<s:select name="seconds" value="%{seconds}" headerKey='-1' headerValue="--%{getText('请选择')}--" --%>
<%--							list="#{'0':'00','1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23',--%>
<%--									'24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47',--%>
<%--									'48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59'}"/> --%>
<%--						</td>--%>
<%--				<td class="table_td4"></td>--%>
<%--		    </tr>--%>
<%--			<tr class="_remind table_tr2" style="display:none;">--%>
<%--				<td class="table_td2"><span class="table_title3"><s:text name="阴历提醒" />：</span></td>--%>
<%--				<td class="table_td3">--%>
<%--			    指定时间：<s:textarea name="memo.remindDay" rows="2" cssClass="multiDate textarea_css"></s:textarea>--%>
<%--				 </td>--%>
<%--				<td class="table_td4"></td>--%>
<%--			</tr>--%>
			<tr class="table_tr2">
				<td class="table_td2"><s:text name="正文" />：</td>
				<td colspan="2">
					<FCK:editor instanceName="memo.content" value="${memo.content}" basePath="/tool/fckeditor" width="100%" height="250" toolbarSet="Message"></FCK:editor>
				</td>
			</tr>
		</table>
	</div>
	<s:include value="/submit.jsp" />
</div>