<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Memo">
		<head>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/view/view_memo.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
<%--				$(function(){--%>
<%--					function modifyFlag(url) {// 不提醒单个备忘--%>
<%--						if (confirm("您确定不提醒该备忘吗？")) {--%>
<%--							$.ajax({--%>
<%--								url: url,--%>
<%--								type: "post",--%>
<%--								dataType: "json",--%>
<%--								success: function(result) {--%>
<%--									if (result.errorInfo == null || result.errorInfo == "") {--%>
<%--										$("#memo_count").html(--%>
<%--											parseInt($("#memo_count").html()) - 1--%>
<%--										);--%>
<%--										$("#" + result.entityId).parent().parent().remove();--%>
<%--									} else {--%>
<%--										alert(result.errorInfo); --%>
<%--									}--%>
<%--								}--%>
<%--							});--%>
<%--						}--%>
<%--					};--%>
<%--				--%>
<%--				$(".modify_flag").bind("click", function() {--%>
<%--					modifyFlag("selfspace/memo/modify.action?modifyFlag=2&entityId=" + this.id);--%>
<%--					return false;--%>
<%--				});--%>
<%--			});--%>
		</head>

		<body>
			<div style="width:400px;">
				<s:if test="#request.map.get('errorInfo') == null || #request.map.get('errorInfo') == ''">
					<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td"  align="left">
 					                <a href="selfspace/memo/toList.action" title="<s:text name='点击进入备忘列表' />" target="main">今天您有 <span style="color:red;" id="memo_count"><s:property value="#request.map.get('map1').get('memoCount')" /></span> 条备忘，请点击查看！</a>
 					            </td>
							</tr>
							<s:iterator value="#request.map.get('map2')" status="stat">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				                <td class="title_bar_td"  align="left">
					                [<s:property value="#stat.index+1" />]
									<a href="selfspace/memo/toView.action?viewFlag=2&entityId=<s:property value='key'/>" title="<s:text name='i18n_ViewDetails' />" target="main"><s:property value="value"/></a>
				               </td>
				               <td class="title_bar_td"  align="right">
									<a class="modify_flag" href="" id="<s:property value='key'/>" title="<s:text name='取消提醒' />" ><span style="color:blue;"><s:text name='不再提醒' /></span></a>
				               </td>
							</tr>
				            </s:iterator>   
						</table>
					</div>
				</s:if>
				<s:else>
					<div style="text-align:center;"><s:property value="#request.map.get('errorInfo')" /></div>
				</s:else>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="<s:text name='i18n_Ok' />" />
				</div>
			</div>
		</body>
	</s:i18n>
</html>