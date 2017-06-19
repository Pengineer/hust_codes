<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;年度选题&nbsp;&gt;&nbsp;添加
			</div>
			
			<s:form id="topicSelection_form" action="add" namespace="/project/key/topicSelection/apply">
				<s:hidden id="deadline" name="deadline" />
				<s:hidden id="editFlag" name="editFlag" value="1"/>
				<s:hidden id="appFlag" name="appFlag"/>
				<s:hidden name="accountType" id="accountType" value="%{#session.loginer.currentType}"/>
				<s:hidden id="topsApplicantSubmitStatus" name="topsApplicantSubmitStatus"/>
				<s:hidden id="topsUniversitySubmitStatus" name="topsUniversitySubmitStatus"/>
				<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="130"><span class="table_title5">课题名称：</span></td>
								<td class="table_td3"><s:textfield name="topicSelection.name" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span>英文名称：</span></td>
								<td class="table_td3"><s:textfield name="topicSelection.englishName" cssClass="input_css" /></td>
								<td class="table_td4" width="90"></td>
							</tr>
							<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">选题年度：</span></td>
								<td class="table_td3"><s:textfield name="topsYear" cssClass="input_css"  readonly="true"/></td>
								<td class="table_td4"></td>
							</tr>
							</s:if>
		                    <s:else>
		                    <tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">选题年度：</span></td>
								<td class="table_td3"><s:textfield name="topsYear" cssClass="input_css" readonly="true"/></td>
								<td class="table_td4"></td>
							</tr>
							</s:else>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
								<tr class="table_tr2 university">
									<td class="table_td2" width="130"><span class="table_title5">项目来源：</span></td>
									<td class="table_td3">
										<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="projectTypeFlag" name="projectTypeFlag" list="#{'1': getText('一般项目'), '3': getText('基地项目')}" headerKey="-1" headerValue="--%{getText('请选择')}--"/>
										<input type="button" class="btn2 select_btn select_project_btn" value="选择"/>
										<div id="project_div" class="choose_show"></div>
										<s:hidden id="projectId" name="projectId"/>
										<s:hidden name="topicSelection.university.id"/>
									</td>
									<td class="table_td4"></td>
								</tr>
							</s:if>
							<tr class="table_tr2">
								<td class="table_td2"><span>申报时间：</span></td>
								<td class="table_td3">
									<s:textfield name="topsDate" cssClass="FloraDatepick" readonly="true">
										<s:param name="value">
											<s:date name="topsDate" format="yyyy-MM-dd" />
										</s:param>
									</s:textfield>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span>简要论证：<br/>（限300字）</span></td>
								<td class="table_td3"><s:textarea name="topicSelection.summary" rows="3" cssClass="textarea_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div> 
					<div class="btn_div_view">
						<input class="btn1" type="button" value="保存" onclick="addTopsApply(2);" />
						<input class="btn1" type="button" value="提交" onclick="addTopsApply(3);" />
						<input class="btn1" type="button"  value="取消" onclick="history.back()"/>
					    <s:hidden name="deadline" id="deadline" value="%{projectService.checkIfTimeValidate(#session.loginer.currentType, '046')}"/>
					    <s:hidden name="topsStatus" id="topsStatus" value="%{projectService.getBusinessStatus('046')}"/>					
					</div>
				</div>
			</s:form>
			<script type="text/javascript">
				seajs.use(['javascript/project/key/topicSelection/edit.js','javascript/project/key/topicSelection/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>