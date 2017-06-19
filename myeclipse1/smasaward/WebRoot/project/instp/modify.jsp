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
			<title>查看项目信息</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								项目管理&nbsp;&gt;&gt;&nbsp;基地项目&nbsp;&gt;&gt;&nbsp;
								<span class="text_red">修改项目</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				<div id="bottom">
					<s:form id="form_project" action="modify" namespace="/project/instp" theme="simple">			
						<input type="hidden" name="project.id" value="${project.id}"/>
						<input type="hidden" name="isReviewable" value="${isReviewable}" />
						<input type="hidden" name="listType" value="${listType}" />
						<input type="hidden" name="businessType" value="${businessType}" />
						<table class="table_edit3" cellspacing="0" cellpadding="0">
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
											<td class="wd13" style="border:0;"><s:textfield name="project.projectName" value="%{project.projectName}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									项目类型
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.projectType" value="%{project.projectType}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.researchType" value="%{project.researchType}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.disciplineType" value="%{project.disciplineType}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.discipline" value="%{project.discipline}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									高校代码
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:textfield name="project.universityCode" value="%{project.universityCode}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									申报日期
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><input name="project.applyDate"   value="<s:date name='project.applyDate' format='yyyy-MM-dd'/>" /></td>
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
											<td class="wd13" style="border:0;"><input name="project.addDate"   value="<s:date name='project.addDate' format='yyyy-MM-dd'/>" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.auditStatus" value="%{project.auditStatus}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									是否参与评审
								</td>
								<td class="wd13">
									<table>
										<tr>
											<td class="wd16" style="border:0;">
												<s:select headerKey="-1" headerValue="请选择" list="#{'1':'是','0':'否'}" name="project.isReviewable" value="%{project.isReviewable}" cssClass="select"/>
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
											<td class="wd13" style="border:0;"><input name="project.planEndDate"   value="<s:date name='project.planEndDate' format='yyyy-MM-dd'/>" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.otherFee" value="%{project.otherFee}" />（万元）</td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.applyFee" value="%{project.applyFee}" />（万元）</td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.members" value="%{project.members}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.finalResultType" value="%{project.finalResultType}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.director" value="%{project.director}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="wd17">
									性别
								</td>
								<td>
									<table>
										<tr>
											<td class="wd13" style="border:0;"><s:select name="project.gender" headerKey="" value="%{project.gender}"
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
											<td class="wd13" style="border:0;"><input name="project.birthday"   value="<s:date name='project.birthday' format='yyyy-MM-dd'/>" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.idcard" value="%{project.idcard}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.foreign" value="%{project.foreign}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.education" value="%{project.education}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.degree" value="%{project.degree}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.department" value="%{project.department}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.title" value="%{project.title}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.job" value="%{project.job}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.email" value="%{project.email}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.mobile" value="%{project.mobile}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.phone" value="%{project.phone}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.address" value="%{project.address}" /></td>
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
											<td class="wd13" style="border:0;"><s:textfield name="project.postcode" value="%{project.postcode}" /></td>
											<td class="td_input" style="border:0;"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table class="table_sub">
							<tr>
								<td>&nbsp;</td>
								<td class="td_sub">
									<input type="submit" class ="btn" value = "确定" />
								</td>
								<td class="td_sub">
									<input type="button" class="btn" value="取消" onclick="history.back();" />
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/project.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/project_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>