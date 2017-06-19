<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加</title>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript">
			seajs.use(['javascript/comprehensiveAuxiliary/person/edit.js','javascript/comprehensiveAuxiliary/validate.js'], function(person, validate) {
				$(function(){
					person.init();
					validate.valid();
				})
			});
		</script>
	</head>

	<body>
		<div class="link_bar">
			当前位置：综合辅助信息&nbsp;&gt;&nbsp;社科人员查询
		</div>

		<div class="choose_bar">
			<ul>
				<li><input id="listSearch1" class="btn1" type="button" value="编辑+" style="display:none;"/></li>
			</ul>
		</div>
		<div class="choose_bar">
			<ul>
				<li><input id="listSearch2" class="btn1" type="button" value="编辑-" style="display:none;"/></li>
			</ul>
		</div>
		<div  id = "config" style="display:">
			<s:form id="form_stat" action="personQuery" namespace="/auxiliary/person" theme="simple">
				<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<div class="title_statistic">参数配置</div>
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="110"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">人员类型：</span></td>
								<td class="table_td31">
									<table width="100%" border="0" cellspacing="0" cellpadding="2" >
										<tr>
											<td width="110">
												<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@ADMINISTRATOR)"><!-- 系统管理员 -->
													<span class="choose_bar"><s:select cssClass="select" name="personType" id="personType" headerKey="-1" headerValue="--请选择--" 
													list="#{'1':'部级管理人员','2':'省级管理人员','3':'高校管理人员',
														'4':'院系管理人员','5':'基地管理人员','6':'外部专家 ','7':'教师','8':'学生'}" /></span>
												</s:if>
												<s:else>
													<span class="choose_bar"><s:select cssClass="select" name="personType" id="personType" headerKey="-1" headerValue="--请选择--" 
													list="#{'6':'外部专家','7':'教师','8':'学生'}" /></span>
												</s:else>
											</td>
											<td id="chose" style="display: none">
												<input type="button" class="btn1 select_btn" id="selectPerson" value="选择" />
												<div id="entityName" class="choose_show"></div>
												<s:hidden id="entityId" name="entityId" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">查询数据：</span></td>
								<td class="table_td31">
									<table width="100%" border="0" cellspacing="0" >
										<tr>
											<td style="width:20%; " class = "j_query_type">
												<input type="checkbox" name="query_data" id="query_project" value="2" />
												<span>参与项目</span>
											</td>
											<td style="width:20%; " class = "j_query_type">
												<input type="checkbox" name="query_data" value="3" />
												<span>奖励情况</span>
											</td>
											<td style="width:20%; " class = "j_query_type">
												<input type="checkbox" name="query_data" value="4" />
												<span>所获成果</span>
											</td>
											<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_VIEW">
												<td style="width:20%;">
													<input type="checkbox" name="query_data" id="query_visit" value="1" />
													<span>访问记录</span>
												</td>
											</sec:authorize>
											<td ></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="table_tr2 j_project_set" style="display: none;">
								<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">项目数据：</span></td>
								<td class="table_td31">
									<table width="100%" border="0" cellspacing="0" >
										<tr>
											<td style="width:20%;">
												<input type="radio" name="director" checked="checked" value="1"/>
												<span>作为负责人的项目</span>
											</td>
											<td style="width:20%;">
												<input type="radio" name="director" value="0"/>
												<span>全部的项目</span>
											</td>
											<td style="width:20%;"></td>
											<td style="width:20%;"></td>
											<td></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="table_tr2 j_time" style="width:15%; display:none;">
								<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计时间：</span></td>
								<td class="table_td31">
									<table width="100%" border="0" cellspacing="0" >
										<tr>
											<td class="adv_td2"><s:textfield id="startDate" name="startDate" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate" name="endDate" value="%{searchQuery.endDate}" cssClass="input_css_self younger date_hint FloraDatepick" disabled="disabled" size="10" /></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="table_tr2 j_time" style="width:15%; display:none;">
								<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">显示条数：</span></td>
								<td class="table_td3">
									<table width="100%" border="0" cellspacing="0" cellpadding="2">
										<tr>
											<td style="width:20%;">
												<input type="radio" name="showLineNum" checked="checked" value="10"/>
												<span>前10条</span>
											</td>
											<td style="width:20%;">
												<input type="radio" name="showLineNum" value="15"/>
												<span>前15条</span>
											</td>
											<td style="width:20%;">
												<input type="radio" name="showLineNum" value="20"/>
												<span>前20条</span>
											</td>
											<td>
<%--													<span>自定义：</span>--%>
<%--													<s:textfield id="showLineNum" name="num" cssClass="input_css_self" />--%>
<%--													<s:hidden id="lineNum" name="lineNum"></s:hidden>--%>
											</td>
											<td></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="table_tr2 j_time" style="width:15%; display:none;">
								<td class="table_td2"><span class="table_title3" style="_padding-top:5px;font-weight:bold;">统计图表：</span></td>
								<td class="table_td31">
									<table width="100%" border="0" cellspacing="0" cellpadding="2">
										<tr>
											<td style="width:20%;">
												<input type="radio" name="chartType" checked="checked" value="1"/>
												<span>垂直柱图</span>
											</td>
											<td style="width:20%;">
												<input type="radio" name="chartType" value="2"/>
												<span>水平柱图</span>
											</td>
											<td style="width:20%;">
												<input type="radio" name="chartType" value="3"/>
												<span>饼状图</span>
											</td>
											<td style="width:20%;">
												<input type="radio" name="chartType" value="4"/>
												<span>折线图</span>
											</td>
											<td></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div> 
				</div>
			</s:form>
			<div class="btn_bar3" style="padding：0 0 10px 0;">
				<input id="submit" class="btn1" type="button" value="确定" />
			</div>
		</div>
		<div class="right2" id="chartIframe" style="display:none;">
			<div class="main_content">
				<div id="view_parm_container" style="display:none;"></div>
				<textarea id="view_parm" style="display:none;">
					<div class="p_box_body" style="padding:10px 0 10px 0;">
						<table id="list_parm" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tbody>
								{for item in query_parm}
									<tr>
										<td class="title_bar_td" width="30" align="right">
											<img width="5" height="9" src="image/ico08.gif"/>
										</td>
										<td width="30"></td>
										<td>
											${item}
										</td>
									</tr>
									<tr><td>　</td><td></td><td></td></tr>
								{/for}
							</tbody>
						</table>
					</div>
				</textarea>
				<br/>
			
				<div id="tabs" class="p_box_bar">
					<ul>
						<li class="j_project" style="display:none;"><a href="#project">参与项目</a></li>
						<li  class="j_award" style="display:none;"><a href="#award">所获奖励</a></li>
						<li  class="j_product" style="display:none;"><a href="#product">所获成果</a></li>
						<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
							<li class="j_log" style="display:none;"><a href="#log">访问记录</a></li>
						</s:if>
					</ul>
				</div>
	
				<div class="p_box">
					<div id="project">
						<s:include value="viewProject.jsp" />
					</div>
					<div id="award">
						<s:include value="viewAward.jsp" />
					</div>
					<div id="product">
						<s:include value="viewProduct.jsp" />
					</div>
					<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
						<div id="log">
							<s:include value="viewLog.jsp" />
						</div>
					</s:if>
				</div>
			</div>
		</div>
	</body>
</html>