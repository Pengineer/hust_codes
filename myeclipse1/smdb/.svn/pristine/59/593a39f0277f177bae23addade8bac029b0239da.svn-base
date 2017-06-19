<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;重大攻关项目&nbsp;&gt;&nbsp;年度选题&nbsp;&gt;&nbsp;修改
			</div>
			
			<s:form id="topicSelection_form" action="modifyResult" namespace="/project/key/topicSelection/apply">
				<s:hidden id="topsId" name="topsId"/>
				<s:hidden id="editFlag" name="editFlag" value="2"/>
				<s:hidden id="topsImportedStatus" name="topsImportedStatus"/>
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
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">项目课题来源：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" name="topicSelection.topicSource" headerKey="-1" headerValue="--请选择--"
										list="#{'0':'教育部','1':'高校','2':'专家'}" />
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2 university" style="display:none;">
								<td class="table_td2"><span class="table_title5">高校：</span></td>
								<td class="table_td3">
									<div name="selectUniDiv">
										<input name="selectUniversity" type="button" class="btn1 select_btn" value="选择" />
										<div name="universityNameDiv" class="choose_show"><s:property value="universityName"/></div>
										<s:hidden name="topicSelection.university.id"/>
									</div>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2 university" style="display:none;">
								<td class="table_td2" width="130"><span class="table_title5">项目来源：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" cssStyle="float:left; margin-right:10px;" id="projectTypeFlag" name="projectTypeFlag" list="#{'1': '一般项目', '2': '基地项目'}" headerKey="-1" headerValue="--请选择--"/>
									<input type="button" class="btn2 select_btn select_project_btn" value="选择"/>
									<div id="project_div" class="choose_show"><s:property value="projectName"/></div>
									<s:hidden id="projectId" name="projectId"/>
									<s:hidden id="projectName" name="projectName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2 applicant_valid" style="display:none;">
								<td class="table_td2"><span class="table_title5">推荐人类型：</span></td>
								<td class="table_td3">
									<s:select cssClass="select" name="topicSelection.applicantType" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家'}" />
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2 applicant_valid" style="display:none;">
								<td class="table_td2"><span class="table_title5">姓名：</span></td>
								<td class="table_td3">
									<input name="selectApplicant" type="button" class="btn1 select_btn" value="选择" />
									<div name="applicantNameDiv" class="choose_show"><s:property value="topicSelection.applicantName"/></div>
									<s:hidden name="topicSelection.applicantId" />
									<s:hidden name="topicSelection.applicantName"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">选题年度：</span></td>
								<td class="table_td3"><s:textfield name="topicSelection.year" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">是否采纳课题：</span></td>
								<td class="table_td3"><s:radio name="topsResult" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span>申请时间：</span></td>
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
						<input id="finish" class="btn1" type="button" value="完成" />
						<input type="button" class="btn1" value="取消" onclick="history.back()"/>
					</div>
				</div>
			</s:form>
			<script type="text/javascript">
				seajs.use(['javascript/project/key/topicSelection/edit_result.js','javascript/project/key/topicSelection/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>