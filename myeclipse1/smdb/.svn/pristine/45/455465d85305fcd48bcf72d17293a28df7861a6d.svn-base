<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>注册教师账号</title>
			<s:include value="/outerBase.jsp" />

			<script type="text/javascript" src="javascript/engine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/util.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="dwr/interface/personExtService.js?ver=<%=application.getAttribute("systemVersion")%>"></script>

			<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
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
				<s:form id="form_person" method="post" action="account/teacher/register.action">
					<div class="login_input_box">
						<div class="login_input_area">
							<div>
								<div class="register_title">身份验证 > 人员信息 > 联系信息 > 账号信息 > <span style="color:#FF7400;">完成</span></div>
								<div class="login_find_pw_txt login_reset_pw">
									<ul>
										<li>
											<span class="reg_success">恭喜您注册成功！</span>
										</li>
										<li>
											<span class="reg_success">请返回首页直接登录！</span>
										</li>
									</ul>
								</div>
								<div id="errorInfo" style="margin-left:70px; height:22px; line-height:22px; color:red;"></div>
								<div class="login_btn_box1" style="margin-top:0;">
									<input class="login_btn" type="button" onclick="document.location.href= basePath+ '/toIndex.action'" value="返回首页" />
								</div>
							</div>
						</div>
					</div>
				</s:form>
			</div>
			<s:include value="/outerFoot.jsp" />
		</body>
	
</html>
