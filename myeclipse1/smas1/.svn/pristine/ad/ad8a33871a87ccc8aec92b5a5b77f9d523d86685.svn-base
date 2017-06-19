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
			<title>项目清单</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
		</head>

		<body>
			<div style="width:700px;overflow-y:scroll; overflow-x:hidden;" >
				<s:form id="form_inform" action="addInform" theme="simple">
					<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0;">
						<tr>
							<th class="thhead" colspan="4">
								项目信息
							</th>
						</tr>
						<tr>
							<td class="wd17">
								项目名称
							</td>
							<td class="wd21" colspan="3">
								<s:property value="project.projectName" />
							</td>
						</tr>
						<tr>
							<td class="wd17">
								项目类型
							</td>
							<td class="wd18">
								<s:property value="project.projectType" />
							</td>
							<td class="wd17">
								研究类型
							</td>
							<td class="wd18">
								<s:property value="project.researchType" />
							</td>
						</tr>
						<tr>
							<td class="wd17">
								其它费用
							</td>
							<td class="wd18">
								<s:property value="project.otherFee" />（万元）
							</td>
							<td class="wd17">
								申请经费
							</td>
							<td class="wd18">
								<s:property value="project.applyFee" />（万元）
							</td>
						</tr>
						<tr>
							<td class="wd17">
								项目成员
							</td>
							<td class="wd21" colspan="3">
								<s:property value="project.members" />
							</td>
						</tr>
						<tr>
							<th class="thhead" colspan="4">
								举报信息
							</th>
						</tr>
						
						<tr>
							<td class="wd17">
								<span class="table_title">举报标题</span>
							</td>
							<td colspan="3">
								<table style="width: 100%">
									<tr>
										<td class="wd13" style="border:0;"><s:textfield theme="simple" name="project.informTitle" cssStyle="width:99%" /></td>
										<td class="td_input" style="border:0;"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="wd17">
								<span class="table_title">举报事由</span>
							</td>
							<td colspan="3">
								<table style="width: 100%">
									<tr>
										<td class="wd13" style="border:0;"><s:textarea theme="simple" name="project.informContent" cols="49" rows="3"/></td>
										<td class="td_input" style="border:0;"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="wd17">
								<span class="table_title">举报人</span>
							</td>
							<td colspan="3">
								<table style="width: 100%">
									<tr>
										<td class="wd13" style="border:0;"><s:textfield theme="simple" name="project.informer" /></td>
										<td class="td_input" style="border:0;"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="wd17">
								<span class="table_title">举报人电话</span>
							</td>
							<td colspan="3">
								<table style="width: 100%">
									<tr>
										<td class="wd13" style="border:0;"><s:textfield theme="simple" name="project.informerPhone"/></td>
										<td class="td_input" style="border:0;"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="wd17">
								<span class="table_title">举报人邮箱</span>
							</td>
							<td colspan="3">
								<table style="width: 100%">
									<tr>
										<td class="wd13" style="border:0;"><s:textfield theme="simple" name="project.informerEmail"/></td>
										<td class="td_input" style="border:0;"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="wd17">
								<span class="table_title">举报时间</span>
							</td>
							<td colspan="3">
								<table style="width: 100%">
									<tr>
										<td class="wd13" colspan="2" style="border:0;">
											<s:textfield theme="simple" name="project.informDate" id="datepick">
												<s:param name="value">
													<s:date name="project.informDate" format="yyyy-MM-dd"/>
												</s:param>
											</s:textfield>
										</td>
										<td class="td_input" style="border:0;"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
					<div class="btn_div">
						<input id="submit" type="button" class="btn" value="确定" />
						<input type="button" class="cancel btn" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/general/publicity/popInform.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>