<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>添加项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					项目管理&nbsp;&gt;&gt;&nbsp;一般项目&nbsp;&gt;&gt;&nbsp;<span class="text_red">添加项目</span>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form id="form_project" action="add" namespace="/project/general" theme="simple">
						<s:include value="/validateError.jsp" />
						<s:hidden id="isReviewable" name="isReviewable" />
						<s:hidden id="businessType" name="businessType" />
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr>
								<th class="thhead" colspan="2">
									基本信息
								</th>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">项目名称</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.projectName" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">项目类型</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:select name="project.projectType" list="{'青年基金项目','规划基金项目','自筹经费项目','专项任务'}"></s:select></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									研究类型
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.researchType" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									学科类型
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.disciplineType" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									依托学科
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.discipline" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">高校代码</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select name="project.universityCode" headerKey="-1" headerValue="请选择" list="#application.univCodeNameMap" ></s:select>
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									申请日期
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.applyDate" id="datepicks" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									添加日期
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.addDate" id="datepickr" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									审核状态
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.auditStatus" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">是否参与评审</span>
								</td>
								<td class="wd13">
									<table>
										<tr>
											<td class="wd13" style="border:0;">
												<s:select headerKey="-1" headerValue="请选择" list="#{'1':'是','0':'否'}" name="project.isReviewable" cssClass="select" />
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									预计结项日期
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.planEndDate" id="datepicke" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									其它费用
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.otherFee" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									申请经费
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.applyFee" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									项目成员
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.members" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									最终成果类型
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.finalResultType" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th class="thhead" colspan="2">
									负责人信息
								</th>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">姓名</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.director" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">性别</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:select name="project.gender" headerKey="-1"
												headerValue="请选择" list="#application.gender" listKey="name"  listValue="name" cssClass="select" />
											</td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									出生日期
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.birthday" id="birthday" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">身份证号</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.idcard" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									外语
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.foreign" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									学历
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.education" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									学位
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.degree" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									院系
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.department" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									职称
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.title" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									职务
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.job" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									<span class="table_title">邮箱</span>
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.email" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									移动电话
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.mobile" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									固定电话
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.phone" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									地址
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.address" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									邮编
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.postcode" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<s:include value="/submit.jsp" />
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"zh_CN\")">
				<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/project_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"en_US\")">
				<script type="text/javascript" src="javascript/project/project_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
			<script type="text/javascript" src="javascript/project/general/general.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>