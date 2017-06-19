<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="nav-custom">
	<ul>
		<li class="ent-logo"><a href="">EOAS</a></li>
		<li class="social-link"><a href="javascript:void(0);">社会招聘</a></li>
		<li class="campus-link"><a href="javascript:void(0);">校园招聘</a></li>
		<li class="intern-link"><a href="javascript:void(0);">实习生招聘</a></li>
	</ul>
	<div class="entry">
		<div class="welcome">
			<s:property value="#session.account.email" />&nbsp;&nbsp;|&nbsp;<a href="account/logout.action?label=2">退出</a>
		</div>
	</div>
</div>
<div class="menu">
	<ul class="nav nav-pills nav-stacked">
	  <li><a href="javascript:void(0);">简历管理</a>
		  <ul class="nav nav-pills nav-stacked">
		  <sec:authorize ifAnyGranted="ROLE_RESUME_ADD,ROLE_RESUME_MODIFY,ROLE_RESUME_DEL,ROLE_RESUME_VIEW">
		      <li><a href="resume/view.action">我的简历</a></li>
			  <li><a href="resume/toAddResume.action">创建简历</a></li>
			  <sec:authorize ifAnyGranted="ROLE_RESUME_DEL">
			      <li><a href="resume/toList.action">所有简历</a></li>
			  </sec:authorize>
		  </sec:authorize>
		  </ul>
	  </li>
	  <li><a href="javascript:void(0);">职位管理</a>
	  	  <ul class="nav nav-pills nav-stacked">
			<sec:authorize ifAnyGranted="ROLE_POSITION_VIEW">
				<li><a href="position/toList.action">所有职位列表</a></li>
				<sec:authorize ifAnyGranted="ROLE_POSITION_DEL">
					<li><a href="position/toAddPosition.action">发布职位</a></li>
				</sec:authorize>		
			</sec:authorize>
		  	  <sec:authorize ifAnyGranted="ROLE_POSITION_ADD,ROLE_POSITION_MODIFY,ROLE_POSITION_DEL,ROLE_POSITION_VIEW">
		  	      <li><a href="position/toMyApplyList.action">我的职位申请</a></li>
				  <li><a href="position/toMyFavoriteList.action">我的职位搜藏</a></li>
		  	  </sec:authorize>
		  </ul>
	  </li>
	  <sec:authorize ifAnyGranted="ROLE_POSITION_ADD,ROLE_POSITION_MODIFY,ROLE_POSITION_DEL">
		  <li><a href="javascript:void(0);">职位简历管理</a>
		  	  <ul class="nav nav-pills nav-stacked">
				  <li><a href="positionResume/toAllApplyList.action">所有职位申请</a></li>
				  <li><a href="positionResume/toAllFavoriteList.action">所有职位收藏</a></li>
			  </ul>
		  </li>
	  </sec:authorize>

	  <li><a href="account/toModifyPassword.action">修改密码</a></li>
	  <li><a href="javascript:void(0);">笔试面试记录</a>
	  	  <ul class="nav nav-pills nav-stacked">
	  	  <sec:authorize ifAnyGranted="ROLE_RECORD_VIEW">
	  	      <li><a href="record/toList.action">笔试面试记录列表</a></li>
	  	  </sec:authorize>
	  	  <sec:authorize ifAnyGranted="ROLE_RECORD_ADD">
	  	  	<li><a href="record/toAdd.action">添加笔试面试记录</a></li>
	  	  </sec:authorize>
		  </ul>
	  </li>
	  
	  <li><a href="javascript:void(0);">人才库管理</a>
	  	<sec:authorize ifAnyGranted="ROLE_RESUME_DEL">
		  	  <ul class="nav nav-pills nav-stacked">
				  <li><a href="resume/toListInnerTalent.action">内部人才库</a></li>
				  <li><a href="resume/listOuterTalent.action">外部人才库</a></li>
			  </ul>
	    </sec:authorize>
	 </li>

	</ul>
</div>
<div class="footer">
	©2014 公司|<a href="">关于公司</a>|<a href="">合作伙伴</a>
</div>
<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
<script type="text/javascript" src="javascript/recruitment/selfspace/main.js"></script>