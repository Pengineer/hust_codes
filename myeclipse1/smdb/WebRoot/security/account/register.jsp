<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>注册教师账号</title>
			<s:include value="/outerBase.jsp" />
			<link href="css/jquery/jquery-ui-1.8.5.custom.css" rel="stylesheet" type="text/css" />

			<script type="text/javascript" src="javascript/engine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/util.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/interface/personExtService.js?ver=<%=application.getAttribute("systemVersion")%>"></script>

			<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery.ui/js/jquery-ui-1.8.5.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery.datepick/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery.datepick/jquery.datepick-zh-CN.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.datepick.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/poplayer/js/pop-self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/security/account/register.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/security/account/register_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</head>

		<body>
			<div class="login_box">
				<s:include value="/outerHead.jsp" />
				<s:form id="form_person" method="post" action="register" theme="simple">
					<div class="login_input_box">
						<div class="login_input_area">
							<div id="identifier">
								<div class="register_title"><span style="color:#FF7400;">身份验证</span> > 人员信息 > 联系信息 > 账号信息 > 完成</div>
								<div class="login_find_pw_txt login_reset_pw">
									<ul>
										<li>
											<span class="reg_required">中文名</span>
											<span><s:textfield name="person.name" cssClass="register_input" /></span>
										</li>
										<li>
											<span class="reg_title">证件类型</span>
											<span><s:select cssClass="select" headerKey="" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}" name="person.idcardType" /></span>
										</li>
										<li>
											<span class="reg_required">证件号</span>
											<span><s:textfield name="person.idcardNumber" cssClass="register_input" /></span>
										</li>
									</ul>
								</div>
								<div id="errorInfo" style="margin-left:70px; height:22px; line-height:22px; color:red;"></div>
								<div class="login_btn_box1" style="margin-top:0;">
									<input id="valid" class="login_btn" type="button" value="验证" />
									<input name="cancel" class="login_btn" type="button" value="取消" onclick="history.back();" />
								</div>
							</div>
							<s:hidden name="registerType" id="registerType" />
							<div id="personInfo" style="display:none;">
								<div class="register_title">身份验证 > <span style="color:#FF7400;">人员信息</span> > 联系信息 > 账号信息 > 完成</div>
								
								<div class="login_find_pw_txt login_reset_pw">
									<ul>
										<li>
											<span class="reg_required">所在高校</span>
											<span><s:textfield id="university" name="university" value="输入首字母，选择匹配结果" cssStyle="color:gray;" cssClass="register_input" /><s:hidden name="universityId" id="universityId" /></span>
										</li>
										<li>
											<span class="reg_required">所在院系</span>
											<span><s:select cssClass="select" cssStyle="" list="#{'-1':'--请选择--'}" name="departmentId" id="departmentId" onfocus="selectDepartment()" /></span>
										</li>
										<li>
											<span class="reg_required">专业职称</span>
											<span><s:select cssClass="select" cssStyle="" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('GBT8561-2001', null)}" name="academic.specialityTitle" /></span>
										</li>
									</ul>
								</div>
								
								<div id="" style="margin-left:70px; height:22px; line-height:22px; color:red;"></div>
								<div class="login_btn_box1" style="margin-top:0;">
									<input id="prev2" class="login_btn" type="button" value="上一步" />
									<input id="next2" class="login_btn" type="button" value="下一步" />
									<input name="cancel" class="login_btn" type="button" value="取消" onclick="history.back();" />
								</div>
							</div>
							
							<div id="contactInfo" style="display:none;">
								<div class="register_title">身份验证 > 人员信息 > <span style="color:#FF7400;">联系信息</span> > 账号信息 > 完成</div>
								
								<div class="login_find_pw_txt login_reset_pw">
									<ul>
										<li>
											<span class="reg_title">手机</span>
											<span><s:textfield name="person.mobilePhone" cssClass="register_input" /></span>
										</li>
										<li>
											<span class="reg_title">办公电话</span>
											<span><s:textfield name="person.officePhone" cssClass="register_input" /></span>
										</li>
										<li>
											<span class="reg_required">邮箱</span>
											<span><s:textfield name="person.email" cssClass="register_input" /></span>
										</li>
									</ul>
								</div>
								<div id="" style="margin-left:70px; height:22px; line-height:22px; color:red;"></div>
								<div class="login_btn_box1" style="margin-top:0;">
									<input id="prev3" class="login_btn" type="button" value="上一步" />
									<input id="next3" class="login_btn" type="button" value="下一步" />
									<input name="cancel" class="login_btn" type="button" value="取消" onclick="history.back();" />
								</div>
							</div>
							
							
							<div id="accountInfo" style="display:none;">
								<div class="register_title">身份验证 > 人员信息 > 联系信息 > <span style="color:#FF7400;">账号信息</span> > 完成</div>
								<div class="login_find_pw_txt login_reset_pw">
									<ul>
										<li>
											<span class="reg_required">账号名</span>
											<span><s:textfield name="passport.name" cssClass="register_input" /></span>
										</li>
										<li>
											<span class="reg_required">密码</span>
											<span><s:password id="newPassword" name="newPassword" cssClass="register_input" /></span>
										</li>
										<li>
											<span class="reg_required">重复密码</span>
											<span><s:password name="rePassword" cssClass="register_input" /></span>
										</li>
									</ul>
								</div>
								<div id="" style="margin-left:70px; height:22px; line-height:22px; color:red;"></div>
								<div class="login_btn_box1" style="margin-top:0;">
									<input id="prev4" class="login_btn" type="button" value="上一步" />
									<input id="register" class="login_btn" type="submit" value="注册" />
								</div>
							</div>
						</div>
					</div>
				</s:form>
			</div>
			<s:include value="/outerFoot.jsp" />
		</body>
</html>