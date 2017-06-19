<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="switchServer" style="width:300px; top:24px; left:-80px; border:1px solid #HHHHHH; z-index:100; position:absolute; overflow:hidden; background:white; display:none">
	<table width="100%" border="0" cellspacing="5px" cellpadding="0">
		<s:iterator value="serverList" status="stat">
			<s:if test="(#stat.index)%3 == 0"><tr height="70px"></s:if>
			<s:if test='serverList[#stat.index][1] == 1'>
				<td class="choose1" align="center">
					<img src="image/ico01.png" alt="<s:property value="serverList[#stat.index][0]" />" name="serverName" value="<s:property value="serverList[#stat.index][2]" />"/><br/>
					<span style="color:#6C6C96;"><strong><s:property value="serverList[#stat.index][0]" /></strong></span>
				</td>
			</s:if>
			<s:else>
				<td class="choose1" align="center">
					<img src="image/ico01.png" alt="<s:property value="serverList[#stat.index][0]" />" name="serverName" disabled="disabled" value="<s:property value="serverList[#stat.index][2]" />"/><br/>
					<span class="disable"><strong><s:property value="serverList[#stat.index][0]" /></strong></span>
				</td>
			</s:else>
			</td>
			<s:if test="(#stat.index+1)%3 == 0"></tr></s:if>
		</s:iterator>
	</table>
</div>