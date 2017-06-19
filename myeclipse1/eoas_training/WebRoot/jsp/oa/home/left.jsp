<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>导航菜单</title>
		<s:include value="/jsp/innerBase.jsp" />
	</head>
	<body class="g-pageLeft">
	<div>
		<ul>
			<sec:authorize ifAnyGranted="ROLE_EXPERT_VIEW">
				<li class="g-subMenu">
					<a class="j-subMenu" href="javascript:void(0);">
						<span class="glyphicon glyphicon-user"></span>
						<span>&nbsp;&nbsp;专家顾问</span>
						<span class="glyphicon glyphicon-plus u-titleIcon"></span>
					</a>
					<ul class="g-subMenuList j-subMenuList">
						<li><a target="right" href="expert/toAdd.action"><i class="fa fa-plus"></i>&nbsp;添加</a></li>
						<li><a target="right" href="expert/toList.action"><i class="fa fa-list"></i>&nbsp;列表</a></li>
					</ul>
				</li>
			</sec:authorize>
			
			
				<li class="g-subMenu">
					<a class="j-subMenu" href="javascript:void(0);">
						<span class="glyphicon glyphicon-retweet"></span>
						<span>&nbsp;&nbsp;审批流转</span>
						<span class="glyphicon glyphicon-plus u-titleIcon"></span>
					</a>
					<ul class="g-subMenuList j-subMenuList">
						<li><a target="right" href="workflow/toList.action">流程定义及部署管理</a></li>
						
						
						<li><a target="right" href="workflow/toRunningList.action">运行中的流程</a></li>
						<li><a target="right" href="workflow/toFinishedList.action">已结束的流程</a></li>
						<li><a target="right" href="project/toApply.action">项目申请</a></li>
						<li><a target="right" href="project/toTaskList.action">任务受理</a></li>
						
						<li><a target="right" href="Flow_FormTemplate/list.html">表单模板管理</a></li>
						<li><a target="right" href="Flow_Formflow/myTaskList.html"> 待我审批</a></li>
						<li><a target="right" href="Flow_FormFlow_Old/mySubmittedList.html">我的申请查询</a></li>
					</ul>
				</li>
			
			
			<sec:authorize ifAnyGranted="ROLE_DOCUMENT_ADD">
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-briefcase"></span>
					<span>&nbsp;&nbsp;个人办公</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="document/toAdd.action"><i class="fa fa-user"></i>&nbsp;个人考勤</a></li>
					<li><a target="right" href="document/toList.action"><i class="fa fa-list"></i>&nbsp;工作计划</a></li>
					<li><a target="right" href="document/toList.action"><i class="fa fa-phone"></i>&nbsp;通讯录</a></li>
				</ul>
			</li>
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-briefcase"></span>
					<span>&nbsp;&nbsp;公文流转</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="document/toAdd.action">发文拟稿</a></li>
					<li><a target="right" href="document/toList.action">发文列表</a></li>
					<li><a target="right" href="document/toList.action">收文登记</a></li>
				</ul>
			</li>
			
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-random"></span>
					<span>&nbsp;&nbsp;客户关系管理</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="customer/toList.action">客户列表</a></li>
					<li><a target="right" href="customer/toAdd.action">客户添加</a></li>
				</ul>
			</li>
			
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-comment"></span>
					<span>&nbsp;&nbsp;解决方案管理</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="solution/toList.action">列表</a></li>
					<li><a target="right" href="solution/toAdd.action">添加</a></li>
				</ul>
			</li>


			

			
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-th-list"></span>
					<span>&nbsp;&nbsp;行政资源管理</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="person/toList.action">人员信息查询</a></li>
					<li><a target="right" href="asset/toList.action">资产管理</a></li>
					<li><a target="right" href="asset/toAdd.action">添加资产</a></li>
				</ul>
			</li>
			
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-th-large"></span>
					<span>&nbsp;&nbsp;人力资源</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="assess/toList.action">人员考核</a></li>
					<li><a target="right" href="salary/toList.action">薪酬列表</a></li>
					<li><a target="right" href="#">职位变更</a></li>
					<li><a target="right" href="#">人事合同</a></li>
				</ul>
			</li>
			
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-bullhorn"></span>
					<span>&nbsp;&nbsp;信息发布</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="news/toList.action">新闻列表</a></li>
					<li><a target="right" href="news/toAdd.action">发布新闻</a></li>
					<li><a target="right" href="notice/toList.action">通知列表</a></li>
					<li><a target="right" href="notice/toAdd.action">发布通知</a></li>
					<li><a target="right" href="article/toAdd.action">发布新闻</a></li>
				</ul>
			</li>

			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-th-list"></span>
					<span>&nbsp;&nbsp;考勤管理</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="attendance/toList.action?tag=12">实时考勤表</a></li>
					<li><a target="right" href="attendance/toAdd.action?tag=1">考勤签到</a></li>
					<li><a target="right" href="attendance/toAdd.action?tag=2">考勤签退</a></li>
					<li><a target="right" href="attendance/toList.action?tag=3">请假申请</a></li>
					<li><a target="right" href="attendance/toList.action?tag=4">出差申请</a></li>
					<li><a target="right" href="attendance/toList.action?tag=5">加班申请</a></li>
				</ul>
			</li>

			

			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-th-list"></span>
					<span>&nbsp;&nbsp;系统管理</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="right" href="account/toList.action">帐号列表</a></li>
					<li><a target="right" href="role/toList.action">角色列表</a></li>
					<li><a target="right" href="right/toList.action">权限列表</a></li>
					<li><a target="right" href="mail/toList.action">系统邮件</a></li>
					<li><a target="right" href="department/toView.action">部门管理</a></li>
					<li><a target="right" href="systemOption/toView.action">系统选项管理</a></li>
					<li><a target="right" href="article/toList.action?type=qyxw">信息发布管理</a></li>
				</ul>
			</li>

			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-th-list"></span>
					<span>&nbsp;&nbsp;实用工具</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<li><a target="_blank" href="http://www.baidu.com">公司网站</a></li>
					<li><a target="_blank" href="http://qq.ip138.com/train/">火车时刻</a></li>
					<li><a target="_blank" href="http://www.airchina.com.cn/"">飞机航班</a></li>
					<li><a target="_blank" href="http://www.ip138.com/post/">邮编/区号</a></li>
					<li><a target="_blank" href="http://www.timedate.cn/">国际时间</a></li>
				</ul>
			</li>
			
			<li class="g-subMenu">
				<a class="j-subMenu" href="javascript:void(0);">
					<span class="glyphicon glyphicon-th-list"></span>
					<span>&nbsp;&nbsp;个人空间</span>
					<span class="glyphicon glyphicon-plus u-titleIcon"></span>
				</a>
				<ul class="g-subMenuList j-subMenuList">
					<!-- <li><a target="right" href="person/view.action">个人信息</a></li> -->
					<!-- <li><a target="right" href="person/toAdd.action">编辑个人信息</a></li>
					<li><a target="right" href="person/toList.action">个人信息列表</a></li> -->
					<li><a target="right" href="account/toModifyPassword.action">密码修改</a></li>
				</ul>
			</li>
			</sec:authorize>
			
			
<!-- 			<li class="level1">
				<div onClick="menuClick(this)" class="level1Style"><img src="style/images/MenuIcon/FUNC20001.gif" class="Icon" />个人办公</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> 个人考勤</div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> 工作计划</div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> 通讯录</div>
						</li>
					</ul>
			</li> -->
			<%--<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20057.gif" class="Icon" />专家顾问</div>
					<ul style="display: none;" class="MenuLevel2">
						<sec:authorize ifAnyGranted="ROLE_EXPERT_ADD">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="expert/toAdd.action">添加专家顾问</a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_EXPERT_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="expert/toList.action">专家顾问列表</a></div>
							</li>
						</sec:authorize>
					</ul>
			</li>

			--%><%--<sec:authorize ifAnyGranted="ROLE_DOCUMENT_ADD">
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20057.gif" class="Icon" />公文流转</div>
					<ul style="display: none;" class="MenuLevel2">
						<sec:authorize ifAnyGranted="ROLE_DOCUMENT_ADD">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="document/toAdd.action">发文拟稿</a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_DOCUMENT_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="document/toList.action">发文列表</a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_DOCUMENT_VIEW">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="document/toList.action">收文登记</a></div>
						</li>
						</sec:authorize>
					</ul>
			</li>
			
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20057.gif" class="Icon" />客户关系管理</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="customer/toList.action">客户列表</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="customer/toAdd.action">客户添加</a></div>
						</li>
					</ul>
			</li>
	
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20057.gif" class="Icon" />解决方案管理</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="solution/toList.action">列表</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><a target="right" href="solution/toAdd.action">添加</a></div>
						</li>
					</ul>
			</li>		
	
<!-- 			<li class="level1">
					<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20057.gif" class="Icon" />审批流转</div>
						<ul style="display: none;" class="MenuLevel2">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="Flow_ProcessDefinition/list.html">审批流程管理</a></div>
							</li>
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="Flow_FormTemplate/list.html">表单模板管理</a></div>
							</li>
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="Flow_Formflow/formTemplateList.html">起草申请</a></div>
							</li>
							<li class="level2">
									<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="Flow_Formflow/myTaskList.html"> 待我审批</a></div>
							</li>
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="Flow_FormFlow_Old/mySubmittedList.html">我的申请查询</a></div>
							</li>
						</ul>
			</li> -->
			
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20070.gif" class="Icon" />行政资源管理</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="person/toList.action">人员信息查询</s:a></div>
						</li>
<!-- 						<li class="level2">
							<div id="salary" onClick="subMenuClick(this);" class="level2Style"><img id="salary_img" src="style/images/MenuIcon/menu_arrow_close.gif" /></div>
								<ul style="display: none;" class="MenuLevel2" id="salaryd">
									<li class="level3Head">到勤天数</li>
									<li class="level33">工资审核</li>
								</ul>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" />部门考核</div>
						</li> -->
						<sec:authorize ifAllGranted="ROLE_ASSET_ADD,ROLE_ASSET_MODIFY,ROLE_ASSET_DEL,ROLE_ASSET_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="asset/toList.action">资产管理</s:a></div>
							</li>					
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_ASSET_ADD">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="asset/toAdd.action">添加资产</s:a></div>
							</li>					
						</sec:authorize>
					</ul>
			</li>
			
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC261000.gif" class="Icon" />人力资源</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="assess/toList.action">人员考核</s:a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="salary/toList.action">薪酬列表</s:a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" />职位变更</div>
						</li>
					<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> 人事合同</div>
						</li>
				</ul>
			</li>

			
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC261000.gif" class="Icon" />信息发布</div>
					<ul style="display: none;" class="MenuLevel2">
						<sec:authorize ifAnyGranted="ROLE_NEWS_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="news/toList.action">新闻列表</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_NEWS_ADD">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="news/toAdd.action">发布新闻</s:a></div>
						</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_NOTICE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="notice/toList.action">通知列表</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_NOTICE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="notice/toAdd.action">发布通知</s:a></div>
							</li>
						</sec:authorize>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="article/toAdd.action">发布新闻</s:a></div>
						</li>
					</ul>
			</li>
			
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC261000.gif" class="Icon" />考勤管理</div>
					<ul style="display: none;" class="MenuLevel2">
						<sec:authorize ifAnyGranted="ROLE_ATTENDANCE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="attendance/toList.action?tag=12">实时考勤表</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_ATTENDANCE_ADD">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="attendance/toAdd.action?tag=1">考勤签到</s:a></div>
						</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_ATTENDANCE_ADD">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="attendance/toAdd.action?tag=2">考勤签退</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_ATTENDANCE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="attendance/toList.action?tag=3">请假申请</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_ATTENDANCE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="attendance/toList.action?tag=4">出差申请</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_ATTENDANCE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="attendance/toList.action?tag=5">加班申请</s:a></div>
							</li>
						</sec:authorize>
					</ul>
			</li>
			
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20077.gif" class="Icon" />个人空间</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <s:a target="right" href="person/view.action">个人信息</s:a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <s:a target="right" href="person/toAdd.action">编辑个人信息</s:a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="person/toList.action">个人信息列表</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="account/toModifyPassword.action">密码修改</a></div>
						</li>
					</ul>
			</li>
	
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20082.gif" class="Icon" /> 系统管理</div>
					<ul style="display: none;" class="MenuLevel2">
						<sec:authorize ifAnyGranted="ROLE_ACCOUNT_VIEW">					
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="account/toList.action">帐号列表</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_ROLE_ADD,ROLE_ROLE_MODIFY,ROLE_ROLE_DEL,ROLE_ROLE_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="role/toList.action"> 角色列表</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_ROLE_ADD">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="role/toAdd.action"> 添加角色</s:a></div>
							</li>
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_RIGHT_ADD,ROLE_RIGHT_MODIFY,ROLE_RIGHT_DEL,ROLE_RIGHT_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="right/toList.action"> 权限列表</s:a></div>
							</li>
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="right/toAdd.action"> 添加权限</s:a></div>
							</li>
						</sec:authorize>
	
						<sec:authorize ifAnyGranted="ROLE_MAIL_ADD,ROLE_MAIL_MODIFY,ROLE_MAIL_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="mail/toList.action"> 系统邮件</s:a></div>
							</li>
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="mail/toAdd.action"> 添加邮件</s:a></div>
							</li>					
						</sec:authorize>
						
						<sec:authorize ifAllGranted="ROLE_DEPARTMENT_ADD,ROLE_DEPARTMENT_MODIFY,ROLE_DEPARTMENT_DEL,ROLE_DEPARTMENT_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="department/toView.action"> 部门管理</s:a></div>
							</li>					
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_SYSTEMOPTION_VIEW">
							<li class="level2">
								<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /><s:a target="right" href="systemOption/toView.action">系统选项管理</s:a></div>
							</li>					
						</sec:authorize>
					</ul>
			</li>
	        
			<li class="level1">
				<div onClick="menuClick(this);" class="level1Style"><img src="style/images/MenuIcon/FUNC20076.gif" class="Icon" /> 实用工具</div>
					<ul style="display: none;" class="MenuLevel2">
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="_blank" href="http://www.baidu.com">公司网站</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="_blank" href="http://qq.ip138.com/train/">火车时刻</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="_blank" href="http://www.airchina.com.cn/">飞机航班</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="_blank" href="http://www.ip138.com/post/">邮编/区号</a></div>
						</li>
						<li class="level2">
							<div class="level2Style"><img src="style/images/MenuIcon/menu_arrow_single.gif" /> <a target="_blank" href="http://www.timedate.cn/">国际时间</a></div>
						</li>
					</ul>
			</li>
			
			</sec:authorize>
		--%></ul>
	</div>
	<script rel="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script rel="text/javascript" src="javascript/oa/menu.js"></script>
	</body>
</html>

