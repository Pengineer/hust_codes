<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<base href="<%=basePath%>" />
			<title>基地项目录入变更结果信息</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
			<script type="text/javascript">
				var basePath = "<%=basePath%>";
			</script>
		</head>

		<body>
			<div style="width:700px;height:560px;overflow:scroll;">
				<s:form id="varAddForm" action="project/instp/variation/addResult.action">
					<s:hidden id="projectid" name="projectid"/>
					<table id="varItemsTb" width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td style="text-align:right;width:20%;">
								变更事项：
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="01" type="checkbox" name="selectIssue" value="01">变更项目成员<br/>&nbsp;&nbsp;（含负责人）
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="02" type="checkbox" name="selectIssue" value="02">变更项目管理机构
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="03" type="checkbox" name="selectIssue" value="03">变更成果形式
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="04" type="checkbox" name="selectIssue" value="04">变更项目名称
							</td>
						</tr>
						<tr>
							<td class="table_td1" style="text-align:right;width:20%;">
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="05" type="checkbox" name="selectIssue" value="05">研究内容有重大调整
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="06" type="checkbox" name="selectIssue" value="06">延期
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="07" type="checkbox" name="selectIssue" value="07">自行中止项目
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="08" type="checkbox" name="selectIssue" value="08">申请撤项
							</td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:20%;">
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="09" type="checkbox" name="selectIssue" value="09">变更项目经费预算
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input id="10" type="checkbox" name="selectIssue" value="10">其他
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
							</td>
						</tr>
					</table>
					<div id="varMembers" style="display:none;">
						<table class="varMemberTb" width="100%" cellspacing="2" cellpadding="0">
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">
									项目成员<span class="memberNum">1</span>
								</td>
								<td class="table_td1" colspan="4">
									<input type="button" class="btn addMember" value="添加"/>
									<input type="button" class="btn deleteMember" value="删除"/>
								</td>
							</tr>
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">成员类型：</td>
								<td class="table_td1" colspan="4"><select name="newMembers[0].memberType"><option value="1">教师</option><option value="2">专家</option><option value="3">学生</option></select></td>
							</tr>
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">姓名：</td>
								<td class="table_td1" colspan="4"><input name="newMembers[0].memberName" type="text"></td>
							</tr>
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">所在单位：</td>
								<td class="table_td1" colspan="4"><input name="newMembers[0].agencyName" type="text"></td>
							</tr>
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">所在部门：</td>
								<td class="table_td1" colspan="4"><input name="newMembers[0].divisionName" type="text"></td>
							</tr>
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">证件类型：</td>
								<td class="table_td1" colspan="4"><select name="newMembers[0].idcardType"><option value="身份证">身份证</option><option value="护照">护照</option><option value="军官证">军官证</option></select></td>
							</tr>
							<tr>
								<td class="table_td1" style="text-align:right;width:20%;">证件号码：</td>
								<td class="table_td1" colspan="4"><input name="newMembers[0].idcardNumber" type="text"></td>
							</tr>
							<tr class="table_tr1">
								<td class="table_td1" style="text-align:right;width:20%;">是否负责人：</td>
								<td class="table_td1" colspan="4">
									是<input name="newMembers[0].isDirector" type="radio" value="1">
									否<input name="newMembers[0].isDirector" type="radio" value="0">
								</td>
							</tr>
						</table>
					</div>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr1" id="varAgency" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								变更项目管理机构：
							</td>
							<td class="table_td1" valign="top" colspan="2">
								<s:hidden id="oldAgencyName" name="oldAgencyName"/>
								<s:hidden id="newAgenceName" name="newAgenceName"/>
								机构：<input type="text" id="agenceUniversity"/>
							</td>
							<td class="table_td1" valign="top" colspan="2">
								部门：<input type="text" id="agenceColleage"/>
							</td>
						</tr>
						<tr id="productType1" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
							变更成果形式：
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input type="checkbox" name="selectProductType" value="01">论文
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input type="checkbox" name="selectProductType" value="02">著作
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input type="checkbox" name="selectProductType" value="03">研究咨询报告
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input type="checkbox" name="selectProductType" value="04">电子出版物
							</td>
						</tr>
						<tr class="table_tr1" id="productType2" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input type="checkbox" name="selectProductType" value="05">专利
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
								<input type="checkbox" name="selectProductType" value="06">其他
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
							</td>
							<td class="table_td1" valign="top" style="width:20%;">
							</td>
						</tr>
						<tr class="table_tr1" id="projectName" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								变更项目名称：
							</td>
							<td class="table_td1" valign="top" colspan="2">
								中文：<input type="text" name="chineseName">
							</td>
							<td class="table_td1" valign="top" colspan="2">
							</td>
						</tr>
						<tr class="table_tr1" id="researchContent" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								研究内容有重大调整：
							</td>
							<td class="table_td1" valign="top" colspan="2">
								是
							</td>
							<td class="table_td1"></td>
							<td class="table_td1"></td>
						</tr>
						<tr class="table_tr1" id="suspension" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								自行中止项目：
							</td>
							<td class="table_td1" valign="top" colspan="2">
								是
							</td>
							<td class="table_td1"></td>
							<td class="table_td1"></td>
						</tr>
						<tr class="table_tr1" id="cancelProject" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								申请撤项：
							</td>
							<td class="table_td1" valign="top" colspan="2">
								是
							</td>
							<td class="table_td1"></td>
							<td class="table_td1"></td>
						</tr>
						<tr class="table_tr1" id="projectFee" class="table_tr1" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								变更项目经费预算：
							</td>
							<td class="table_td1" valign="top" colspan="4">
								<input type="button" class="btn modifyVarFee" value="修改">
							</td>
							<s:hidden id="feeNote" name="newFee.feeNote" />
							<s:hidden id="bookFee" name="newFee.bookFee" />
							<s:hidden id="bookNote" name="newFee.bookNote"/>
							<s:hidden id="dataFee" name="newFee.dataFee" />
							<s:hidden id="dataNote" name="newFee.dataNote"/>
							<s:hidden id="travelFee" name="newFee.travelFee" />
							<s:hidden id="travelNote" name="newFee.travelNote"/>
							<s:hidden id="conferenceFee" name="newFee.conferenceFee" />
							<s:hidden id="conferenceNote" name="newFee.conferenceNote"/>
							<s:hidden id="internationalFee" name="newFee.internationalFee" />
							<s:hidden id="internationalNote" name="newFee.internationalNote"/>
							<s:hidden id="deviceFee" name="newFee.deviceFee" />
							<s:hidden id="deviceNote" name="newFee.deviceNote"/>
							<s:hidden id="consultationFee" name="newFee.consultationFee" />
							<s:hidden id="consultationNote" name="newFee.consultationNote"/>
							<s:hidden id="laborFee" name="newFee.laborFee" />
							<s:hidden id="laborNote" name="newFee.laborNote"/>
							<s:hidden id="printFee" name="newFee.printFee" />
							<s:hidden id="printNote" name="newFee.printNote"/>
							<s:hidden id="indirectFee" name="newFee.indirectFee" />
							<s:hidden id="indirectNote" name="newFee.indirectNote"/>
							<s:hidden id="otherFee" name="newFee.otherFee" />
							<s:hidden id="otherNote" name="newFee.otherNote"/>
							<s:hidden id="totalFee" name="newFee.totalFee"/>
							<s:hidden id="surplusFee" name="newFee.surplusFee"/>
						</tr>
						<tr id="other" class="table_tr1" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								其他：
							</td>
							<td class="table_td1" valign="top" colspan="4">
								<input type="text" name="otherInfo" id="otherInfo">
							</td>
						</tr>
						
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:20%;">上传变更申请书：</td>
							<td class="table_td1" colspan="4"><input id="file_add" type="file" multiple="true"/></td>
						</tr>
						<tr id="projectDelay1" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">
								延期：
							</td>
							<td class="table_td1" valign="top" colspan="2">
								<input type="text" name="newDate1" id="newDate1"/>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr class="table_tr1" id="projectDelay2" style="display:none;">
							<td class="table_td1"></td>
							<td class="table_td1" colspan="4"><input id="file_add_1" type="file" multiple="true"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:20%;">是否同意变更：</td>
							<td class="table_td1" style="text-align:left;width:20%;"><input type="radio" name="varResult" value="2"/>同意</td>
							<td class="table_td1" style="text-align:left;width:20%;"><input type="radio" name="varResult" value="1"/>不同意</td>
							<td class="table_td1" style="text-align:left;width:20%;"></td>
							<td class="table_td1" style="text-align:left;width:20%;"></td>
						</tr>
						<tr id="varResultListTr" class="table_tr1" style="display:none;">
							<td class="table_td1" style="text-align:right;width:20%;">同意变更事项：</td>
							<td colspan="4">
								<table id="varResultTb" width="100%" border="0" cellspacing="2" cellpadding="0">
								</table>
							</td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">变更时间：</td>
							<td class="table_td1" colspan="4"><input id="varDate" name="varDate" type="text"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">变更原因：</td>
							<td class="table_td1" colspan="4"><textarea id="variationReason" name="variationReason" rows="3" style="width:100%;"></textarea></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">审核意见：
							<td class="table_td1" colspan="4"><textarea id="varImportedOpinion" name="varImportedOpinion" rows="3" style="width:100%;"></textarea></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">审核意见：<br/>（反馈给负责人）
							<td class="table_td1" colspan="4"><textarea id="varOpinionFeedback" name="varOpinionFeedback" rows="3" style="width:100%;"></textarea></td>
						</tr>
					</table>
					<div class="btn_div">
						<input type="button" class="confirm btn" value="提交" />
						<input type="button" class="cancel btn" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.cookie.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>		
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/variation/popAddResult.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>