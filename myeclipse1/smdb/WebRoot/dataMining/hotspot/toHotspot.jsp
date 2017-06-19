<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>研究热点分析</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：领域热点分析&nbsp;&gt;&nbsp;研究热点分析配置
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="form_dm" action="toHotspot" namespace="/dataMining/hotspot" theme="simple" >
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">热点类型：</span></td>
							<td class="table_td3"><s:select cssClass="select" id="type" name="type" headerKey="-1" headerValue="--请选择--" list="#{'general':'一般项目', 'key':'重大攻关项目基地项目', 'instp':'基地项目', 'post':'后期资助项目', 'entrust':'应急委托课题'}"/></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">分析角度：</span></td>
							<td class="table_td3">
								<s:radio name="analyzeAngle" value="1" list="#{'0':'申请数据', '1':'立项数据'}" />
							</td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">挖掘条数：</span></td>
							<td class="table_td3"><input type="text" name="topKnum" value="100"/></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">结果入库：</span></td>
							<td class="table_td3"><s:radio name="toDataBase" value="0" list="#{'1':'入库', '0':'不入库'}" /></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_bar3">
					<input id="submit" class="btn2" type="button" value="开始分析" />
					<sec:authorize ifAnyGranted="ROLE_DATAMINING_HOTSPOT_UPDATEINDEX">
						<input id="updateIndex" class="btn2" type="button" value="更新索引" />
						<span id="updating" style="color: red; display: none;">正在更新，请稍候...</span>
					</sec:authorize>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			seajs.use(['javascript/dataMining/hotspot/validate.js', 'javascript/dataMining/hotspot/toHotspot.js'], function(validate, toHotspot) {
				$(function(){
					validate.valid();
					toHotspot.init();
				})
			});
		</script>
	</body>
</html>