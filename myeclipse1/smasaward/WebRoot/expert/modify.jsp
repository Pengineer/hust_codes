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
		<title>修改专家信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/global.css" />
		<link rel="stylesheet" type="text/css" href="css/expert/expert.css" />
		<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
	</head>

	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							专家管理&nbsp;&gt;&gt;
							<span class="text_red">修改专家信息</span>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="bottom" style="margin-top:10px;">
				<s:form id="form_expert" action="modify" namespace="/expert" theme="simple">
					<s:include value="/validateError.jsp" />
					<div>
						<input type="hidden" name="expert.id" value="${expert.id}" />
						<input type="hidden" name="expertType" value="${expertType}" />
						<input type="hidden" name="isReviewer" value="${isReviewer}" />
						<table class="table_edit3" cellspacing="0" cellpadding="0">
							<tr>
								<th class="thhead" colspan="4">
									基本信息
								</th>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">姓名</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.name" value="%{expert.name}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">性别</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:select name="expert.gender" headerKey="-1" value="%{expert.gender}"
												headerValue="请选择" list="#application.gender" listKey="name"  listValue="name" cssClass="select" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									出生日期
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><input type="text" name="expert.birthday" value="<s:date name="expert.birthday" format="yyyy-MM-dd" />" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">身份证号</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.idCardNumber" value="%{expert.idCardNumber}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">所在高校</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select name="expert.universityCode" headerKey="-1" headerValue="请选择" list="#application.univCodeNameMap" value="%{expert.universityCode}" />
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									所在院系
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.department" value="%{expert.department}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">专业职称</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select name="expert.specialityTitle" list="#application.specialistTitlemap" listKey="key" listValue="value" headerKey="0" headerValue="--请选择职称--"/>
<%--													<s:textfield name="expert.specialityTitle" value="%{expert.specialityTitle}" />--%>
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									行政职务
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.job" value="%{expert.job}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									岗位等级
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.positionLevel" value="%{expert.positionLevel}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									评价等级
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select headerKey="-1" headerValue="请选择" list="%{expertService.fetchTalentLevel()}" name="expert.rating" />
												<span style="color: #8B8B83;">数字越大，级别越高。</span>
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									最后学位
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.degree" value="%{expert.degree}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									外语语种
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.language" value="%{expert.language}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									是否博导
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select headerKey="-1" headerValue="请选择" list="#{'是':'是','否':'否'}" name="expert.isDoctorTutor" value="%{expert.isDoctorTutor}" />
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">是否参与评审</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select headerKey="-1" headerValue="请选择" list="#{'1':'是','0':'否'}" name="expert.isReviewer" value="%{expert.isReviewer}" cssClass="select"/>
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">邮箱</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.email" title="多个请用英文分号加空格“; ”隔开" value="%{expert.email}" cssStyle="width:300px;"/></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									移动电话
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.mobilePhone" title="多个请用英文分号加空格“; ”隔开" value="%{expert.mobilePhone}" cssStyle="width:300px;" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									家庭电话
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.homePhone" value="%{expert.homePhone}" cssStyle="width:300px;"/></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									办公电话
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.officePhone" value="%{expert.officePhone}" cssStyle="width:300px;"/></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									办公地址
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.officeAddress" value="%{expert.officeAddress}" cssStyle="width:300px;"/></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									办公邮编
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.officePostcode" value="%{expert.officePostcode}" cssStyle="width:300px;"/></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th class="thhead" colspan="4">
									学术信息
								</th>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									研究方向
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.researchField" value="%{expert.researchField}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									学术兼职
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.partTime" value="%{expert.partTime}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									<span class="table_title">学科代码</span>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="border:0;"><s:textfield name="expert.discipline" value="%{expert.discipline}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									人才奖励资助情况
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="height:62px; border:0;">
												<s:textarea name="expert.award" value="%{expert.award}" cssStyle="height:47px; width:300px;" cssClass="textarea" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd14" colspan="2">
									备注
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td class="wd16" style="height:62px; border:0;">
												<s:textarea name="expert.remark" value="%{expert.remark}" cssStyle="height:47px; width:300px;" cssClass="textarea" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr><th class="thhead" colspan="4">承担教育部项目信息</th></tr>
							<s:if test="null != oldMoeProjectInfos && '' != oldMoeProjectInfos">
								<tr>
									<td class="wd14" colspan="2">
										往年承担教育部项目情况（供参考）
									</td>
									<td colspan="2">
										<table>
											<tr>
												<td class="wd16" colspan="2" style="height:62px; border:0;">
													<s:textarea name="oldMoeProjectInfos" value="%{oldMoeProjectInfos}" cssStyle="height:110px; width:310px;background-color:#F5F5F5;color:#716d6a;" cssClass="textarea" readonly="true"/></td>
											</tr>
										</table>
									</td>
								</tr>
							</s:if>
							<tr>
								<td class="wd24"><a class="addMoeProject" href="javascript:void(0);"><img src="image/add_item.gif" title="增加项目"/></a></td>
								<td class="wd14" style="text-align:center;">项目编号</td>
								<td class="wd14" style="text-align:center;">项目名称</td>
								<td class="wd14" style="text-align:center;">项目类型</td>
							</tr>
							<s:iterator value="newMoeProjectInfos"  status="stat">
								<tr class="newMoeProjectTr">
									<td class="wd24"><a class="delete_row" href="javascript:void(0);"><img src="image/del_entry.gif" title="删除"/></a></td>
									<td>
										<table>
											<tr>
												<td class="wd16" style="border:0;"><s:textfield cssClass="projectInput" name="%{'newMoeProjectInfos['+#stat.index+'].projectNum'}" value="%{newMoeProjectInfos[#stat.index].projectNum}"/></td>
												<td class="td_input" style="border:0;"></td>
											</tr>
										</table>
									</td>
									<td>
										<table>
											<tr>
												<td class="wd16" style="border:0;"><s:textfield cssClass="projectInput" name="%{'newMoeProjectInfos['+#stat.index+'].projectName'}" value="%{newMoeProjectInfos[#stat.index].projectName}"/></td>
												<td class="td_input" style="border:0;"></td>
											</tr>
										</table>
									</td>
									<td>
										<table>
											<tr>
												<td class="wd16" style="border:0;"><s:textfield cssClass="projectInput" name="%{'newMoeProjectInfos['+#stat.index+'].projectType'}" value="%{newMoeProjectInfos[#stat.index].projectType}"/></td>
												<td class="td_input" style="border:0;"></td>
											</tr>
										</table>
									</td>
								</tr>
							</s:iterator>
							<tr><th class="thhead" colspan="4">承担国家项目信息</th></tr>
							<s:if test="null != oldNationalProjectInfos && '' != oldNationalProjectInfos">
								<tr>
									<td class="wd14" colspan="2">
										往年承担国家项目情况（供参考）
									</td>
									<td colspan="2">
										<table>
											<tr>
												<td class="wd16" colspan="2" style="height:62px; border:0;">
													<s:textarea name="oldNationalProjectInfos" value="%{oldNationalProjectInfos}" cssStyle="height:110px; width:310px;background-color:#F5F5F5;color:#716d6a;" cssClass="textarea"  readonly="true"/></td>
											</tr>
										</table>
									</td>
								</tr>
							</s:if>
							<tr>
								<td class="wd24"><a class="addNationalProject" href="javascript:void(0);"><img src="image/add_item.gif" title="增加项目"/></a></td>
								<td class="wd14" style="text-align:center;">项目编号</td>
								<td class="wd14" style="text-align:center;">项目名称</td>
								<td class="wd14" style="text-align:center;">项目类型</td>
							</tr>
							<s:iterator value="newNationalProjectInfos"  status="stat">
								<tr class="newNationProjectTr">
									<td class="wd24"><a class="delete_row" href="javascript:void(0);"><img src="image/del_entry.gif" title="删除"/></a></td>
									<td>
										<table>
											<tr>
												<td class="wd16" style="border:0;"><s:textfield cssClass="projectInput" name="%{'newNationalProjectInfos['+#stat.index+'].projectNum'}" value="%{newNationalProjectInfos[#stat.index].projectNum}"/></td>
												<td class="td_input" style="border:0;"></td>
											</tr>
										</table>
									</td>
									<td>
										<table>
											<tr>
												<td class="wd16" style="border:0;"><s:textfield cssClass="projectInput" name="%{'newNationalProjectInfos['+#stat.index+'].projectName'}" value="%{newNationalProjectInfos[#stat.index].projectName}"/></td>
												<td class="td_input" style="border:0;"></td>
											</tr>
										</table>
									</td>
									<td>
										<table>
											<tr>
												<td class="wd16" style="border:0;"><s:textfield cssClass="projectInput" name="%{'newNationalProjectInfos['+#stat.index+'].projectType'}" value="%{newNationalProjectInfos[#stat.index].projectType}"/></td>
												<td class="td_input" style="border:0;"></td>
											</tr>
										</table>
									</td>
								</tr>
							</s:iterator>
							
								
							<table class="table_sub">
								<tr>
									<td>&nbsp;</td>
									<td class="td_sub">
										<!--  <input type="button" class="btn" value="上一步" onclick="prevStep();" />-->
										<input type="submit" class ="btn" value = "完成" />
									</td>
									<td class="td_sub">
										<input type="button" class="btn" value="取消" onclick="history.back();" />
									</td>
									<td class="td_sub">
									</td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</table>
					</div>
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<s:if test="#session.locale.equals(\"zh_CN\")">
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>		
		</s:if>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/expert_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/expert/expert_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</s:if>
		<script type="text/javascript" src="javascript/expert/expert.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/expert/modify.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>