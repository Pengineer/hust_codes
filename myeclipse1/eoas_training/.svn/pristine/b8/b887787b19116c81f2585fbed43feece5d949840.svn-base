<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/jsp/innerBase.jsp" />
		<link href="tool/bootstrap-datepicker-1.4.0-dist/css/bootstrap-datepicker3.css" rel="stylesheet">
		<link href="tool/poplayer/css/ui-dialog.css" rel="stylesheet">
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<link href="tool/jQuery-Validation-Engine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
		<link href="tool/step/css/step.css" rel="stylesheet" type="text/css" />
	</head>

	<body class="g-pageRight">
		<div class="g-wrapper">
			<ol class="breadcrumb mybreadcrumb">当前位置：
			  <li><a href="#"></a></li>
			  <li class="active">专家顾问</li>
			  <li class="active">添加专家顾问</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="add"  namespace="/expert" id = "expert_add" theme="simple">
					<div id="procedure" class="main-content step_css">
						<ul>
							<li class="proc" name="basic"><span class="right_step">基本信息</span><span class="triangle"></span></li>
							<li class="proc" name="jobInfo"><span class="right_step">任职信息</span><span class="triangle"></span></li>
							<li class="proc" name="contact"><span class="right_step">联系信息</span><span class="triangle"></span></li>
							<li class="proc" name="bankInfo"></span><span class="right_step">银行信息</span><span class="triangle"></span></li>
							<li class="proc step_oo" name="finish"><span class="right_step">完成</span></li>
						</ul>
					</div>
					
					<div class="m-main-content">
						<!-- 基本信息 -->
						<div id="basic" style="display:none;">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">中文名</label>
								<div class="col-sm-6">
									<s:textfield name="expert.name" cssClass="form-control validate[required]" placeholder="中文名"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">曾用名</label>
								<div class="col-sm-6">
									<s:textfield  name="expert.usedname" cssClass="form-control validate[custom[chinese], maxSize[4]]" placeholder="曾用名" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">民族</label>
								<div class="col-sm-6">
									<s:select cssClass="m-select validate[required]"  name="expert.ethnic" list="#application.ethnic" listKey="name" listValue="name" headerKey="" headerValue="请选择" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">性别</label>
								<div class="col-sm-6">
									<s:select cssClass="m-select validate[required]" name="expert.gender" list="#{1:'男',2:'女'}" listKey="value" listValue="value" headerKey="" headerValue="请选择" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">出生日期</label>
								<div class="col-sm-6">
									<s:textfield id ="birthday" name="expert.birthday" cssClass="form-control validate[required, custom[date]]" placeholder="出生日期" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">证件类型</label>
								<div class="col-sm-6">
									<s:select cssClass="m-select" name="expert.idcardType" list="#{1:'身份证',2:'军官证'}" listKey="value" listValue="value" headerKey="0" headerValue="请选择" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">证件号</label>
								<div class="col-sm-6">
									<s:textfield name="expert.idcardNumber"  cssClass="form-control validate[required, custom[chinaIdLoose]]" theme="simple" placeholder="证件号" />
								</div>
								<div class="col-sm- 2"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">个人主页</label>
								<div class="col-sm-6">
									<s:textfield name="expert.homepage" cssClass="form-control" theme="simple" placeholder="个人主页" />
								</div>
								<div class="col-sm-3"></div>
							</div>
						</div>
						
						<!-- 任职信息 -->
						<div id="jobInfo" style="display:none;">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">行政职务</label>
								<div class="col-sm-6">
									<s:textfield name="expert.job" cssClass="form-control" theme="simple" placeholder="行政职务" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">专业职称</label>
								<div class="col-sm-6">
									<s:textfield name="expert.specialityTitle" cssClass="form-control validate[required]" theme="simple" placeholder="专业职称" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">是否博士后</label>
								<div class="col-sm-6">
									<s:select cssClass="m-select" name="expert.postdoctor" list="#{0:'否',1:'是'}" listValue="value" headerKey="0" headerValue="请选择" />	
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">学术兼职</label>
								<div class="col-sm-6">
									<s:textfield name="expert.parttime" cssClass="form-control" theme="simple" placeholder="学术兼职" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">最后学历</label>
								<div class="col-sm-6">
									<s:select cssClass="m-select validate[required]" name="expert.lastEducation" list="#{'本科':'本科','硕士研究生':'硕士研究生','博士研究生':'博士研究生'}" listValue="value" headerKey="" headerValue="请选择" />	
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">最后学位</label>
								<div class="col-sm-6">
									<s:select cssClass="m-select validate[required]" name="expert.degree" list="#{0:'学士',1:'硕士',2:'博士'}" listValue="value" headerKey="" headerValue="请选择" />	
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">外语语种</label>
								<div class="col-sm-6">
									<s:textfield name="expert.foreignLanguage" cssClass="form-control" theme="simple" placeholder="外语语种" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">研究方向</label>
								<div class="col-sm-6">
									<s:textfield name="expert.researchField" cssClass="form-control validate[required]" theme="simple" placeholder="研究方向" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">学科代码</label>
								<div class="col-sm-6">
									<input type="button" id="selectDiscipline" class="btn btn-default" value="选择" />
									<s:hidden name="expert.discipline" id="disciplineId" cssClass='validate[required]'/>
									<div id="discipline" class="choose_show " style="display:none" ></div>
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">所在单位</label>
								<div class="col-sm-6">
									<s:textfield name="expert.company" cssClass="form-control validate[required]" theme="simple" placeholder="所在单位" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">所在部门</label>
								<div class="col-sm-6">
									<s:textfield name="expert.department" cssClass="form-control validate[required]" theme="simple" placeholder="所在部门" />
								</div>
								<div class="col-sm-3"></div>
							</div>
						</div>
						
						<!-- 联系信息 -->
						<div id="contact" style="display:none;">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">手机</label>
								<div class="col-sm-6">
									<s:textfield name="expert.mobilePhone" cssClass="form-control validate[required, custom[phone]]"  theme="simple" placeholder="手机" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">电子邮箱</label>
								<div class="col-sm-6">
									<s:textfield name="expert.email" cssClass="form-control validate[custom[email]]" theme="simple" placeholder="电子邮箱"/>
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">家庭电话</label>
								<div class="col-sm-6">
									<s:textfield name="expert.homePhone" cssClass="form-control validate[custom[fixed-line]]" theme="simple" placeholder="家庭电话" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">家庭传真</label>
								<div class="col-sm-6">
									<s:textfield name="expert.homeFax" cssClass="form-control" theme="simple" placeholder="家庭传真" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">家庭地址</label>
								<div class="col-sm-6">
									<s:textfield name="expert.homeAddress" cssClass="form-control" theme="simple" placeholder="家庭地址" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">家庭邮编</label>
								<div class="col-sm-6">
									<s:textfield name="expert.homePostcode" cssClass="form-control validate[custom[chinaZip]]" theme="simple" placeholder="家庭邮编" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">办公电话</label>
								<div class="col-sm-6">
									<s:textfield name="expert.officePhone" cssClass="form-control validate[custom[fixed-line]]" theme="simple" placeholder="办公电话" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">办公传真</label>
								<div class="col-sm-6">
									<s:textfield name="expert.officeFax" cssClass="form-control" theme="simple" size="60" placeholder="办公传真" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">办公地址</label>
								<div class="col-sm-6">
									<s:textfield name="expert.officeAddress" cssClass="form-control" theme="simple" size="60" placeholder="办公地址" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">办公邮编</label>
								<div class="col-sm-6">
									<s:textfield name="expert.officePostcode" cssClass="form-control validate[custom[chinaZip]]" theme="simple" size="60" placeholder="办公邮编" />
								</div>
								<div class="col-sm-3"></div>
							</div>
						</div>
						
						<!-- 银行信息 -->
						<div id="bankInfo" style="display:none;"><tr>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">开户银行</label>
								<div class="col-sm-6">
									<s:textfield name="expert.bankName" cssClass="form-control" theme="simple" placeholder="开户银行" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">银行支行</label>
								<div class="col-sm-6">
									<s:textfield name="expert.bankBranch" cssClass="form-control" theme="simple" placeholder="银行支行" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">银联行号</label>
								<div class="col-sm-6">
									<s:textfield name="expert.bankCupNumber" cssClass="form-control" theme="simple" size="60" placeholder="银联行号" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">银行户名</label>
								<div class="col-sm-6">
									<s:textfield name="expert.bankAccountName" cssClass="form-control" theme="simple" size="60" placeholder="银行户名" />
								</div>
								<div class="col-sm-3"></div>
							</div>
						</div>
					</div>
					
					<div id="optr" class="btn_bar2">
						<input id="prev" class="btn btn-sm btn-default" type="button" style="display: none" value="上一步" />
						<input id="next" class="btn btn-sm btn-default" type="button" style="display: none" value="下一步" />
						<input id="finish" class="btn btn-sm btn-default" type="submit" style="display: none" value="完成" />
						<input id="retry" class="btn btn-sm btn-default" type="button" style="display: none" value="重填" />
						<input id="confirm" class="btn btn-sm btn-default" type="button" style="display: none" value="确定" />
						<input id="cancel" class="btn btn-sm btn-default" type="button" style="display: none" value="取消" />
					</div>
				</s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="tool/bootstrap-datepicker-1.4.0-dist/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="javascript/oa/expert/expert_validate.js"></script>
	<script type="text/javascript" src="tool/step/js/step.js"></script>
	<script type="text/javascript" src="javascript/oa/expert/add.js"></script>
	<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
</html>