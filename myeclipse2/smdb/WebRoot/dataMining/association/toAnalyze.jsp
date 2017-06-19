<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>关联性分析</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar">
			当前位置：关联规则挖掘&nbsp;&gt;&nbsp;项目关联性分析
		</div>
		
		<div class="main">
			<div class="main_content">
				<s:form id="form_dm" action="toAnalyze" namespace="/dataMining/association" theme="simple" >
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">分析类型：</span></td>
							<td class="table_td3"><s:select cssClass="select" id="type" name="type" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'discipline':getText('学科关联性分析'), 'university':getText('高校关联性分析'), 'member':getText('研究人员关联性分析')}"/></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">分析角度：</span></td>
							<td class="table_td3">
								<s:radio name="analyzeAngle" value="1" list="#{'0':getText('申报数据'), '1':getText('立项数据'), '2':getText('批准经费')}" />
							</td>
							<td class="table_td4"></td>
						</tr>
						
						<tr id="tr_projectYear" class="table_tr2">
							<td class="table_td2" ><span class="table_title3" style="_padding-top:5px;font-weight:bold;">分析年度：</span></td>
							<td class="table_td31">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td>
											<s:select cssClass="select" onchange="checkYear();" id="analyze_startYear" name="analyze_startYear" list="%{associationService.getProjectYear()}" />
											&nbsp;至&nbsp;
											<s:select cssClass="select" id="analyze_endYear" onchange="checkYear();" name="analyze_endYear" list="%{associationService.getProjectYear()}" />
										</td>
										<td></td>
									</tr>
								</table>
							</td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">挖掘条数：</span></td>
							<td class="table_td3"><input type="text" name="topKnum" value="10"/></td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2" id="minFrequency">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">最小支持度：</span></td>
							<td class="table_td3"><input type="text" name="minFrequency" /> 注：此参数越小结果越多（当前的默认值是针对所有项目年度的，如果限定项目年度，请酌情减小此参数）。</td>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">结果入库：</span></td>
							<td class="table_td3"><s:radio name="toDataBase" value="0" list="#{'1':getText('入库'), '0':getText('不入库')}" /></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				
					<div class="btn_bar3">
						<input id="submit" class="btn2" type="submit" value="确定" />
						<input id="reset" class="btn2" type="reset" value="重置" />
					</div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use(['javascript/dataMining/association/validate.js', 'javascript/dataMining/association/toAnalyze.js'], function(validate, edit) {
				$(function(){
					validate.valid();
					edit.init();
				});
			});
		</script>
	</body>
</html>