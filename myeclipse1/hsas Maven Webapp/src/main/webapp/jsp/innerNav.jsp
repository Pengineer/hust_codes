<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="container">
	<div id="header">
	<!-- 头部图片 -->
		<div class="row myHeader">
			<div class="col-xs-7">
				<div class="headImg">
					<p>湖北省社会科学优秀成果奖励申报评审系统</p>
				</div>
			</div>
			<div class="col-xs-3 col-xs-offset-2">
				<form action="toSearch" method="post">
					<div class="input-group input-group-sm">
						<input type="text" class="form-control" name="query">
						<span class="input-group-btn">
							<input  id = "searchButton" type="button"  value="搜索"  class="btn search-btn"  style="background-color:#274472;font-size:15px;line-height: 0em;" />
						</span>
					</div>
				</form>
	
				<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">提示：</h4>
							</div>
							<div class="modal-body">
								<p>您好~输入不能为空&hellip;</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>			     
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
				
			</div>
		</div>
	<!-- 头部导航 --> 
		<div class="header">
			<ul class="nav nav-pills myNav">
				<li><a class="fnav" href="toIndex.action">网站首页</a></li>
				<li>
					<a class="fnav" href="javasript:void(0);">新闻公告</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:void(0);">社科动态</a></li>
						<li><a href="javascript:void(0);">通知公告</a></li>
						<li><a href="javascript:void(0);">政策文件</a></li>
						<li><a href="javascript:void(0);">注意事项</a></li>
					</ul>
				</li>
				<li>
					<a class="fnav" href="javasript:void(0);">评审管理</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:void(0);">申请</a></li>
						<li><a href="javascript:void(0);">初审</a></li>
						<li><a href="javascript:void(0);">初评</a></li>
						<li><a href="javascript:void(0);">复评</a></li>
						<li><a href="javascript:void(0);">终审</a></li>
					</ul>
				</li>
				<li>
					<a class="fnav" href="javasript:void(0);">专家管理</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:void(0);">参评专家</a></li>
						<li><a href="javascript:void(0);">专家库</a></li>
					</ul>
				</li>
				<li>
					<a class="fnav" href="javasript:void(0);">个人空间</a>
					<ul class="dropdown-menu">
						<li><a href="jsp/selfspace/selfInfo.jsp">个人信息</a></li>
						<li><a href="jsp/selfspace/modifyPassword.jsp">密码修改</a></li>
					</ul>
				</li>
				<li>
					<a class="fnav" href="javasript:void(0);">系统管理</a>
					<ul class="dropdown-menu">
						<li><a href="account/toList.action">账号管理</a></li>
						<li><a href="role/toList.action">角色管理</a></li>
						<li><a href="right/toList.action">权限管理</a></li>
						<li><a href="solicitpapers/toAdd.action">投稿管理</a></li>
						<li class="dropdown-submenu"><a href="javascript:void(0);">数据字典管理</a>
							<ul class="dropdown-menu">
								<li><a href="system/dataDic/bonus/toList.action">奖金管理</a></li>
								<li><a href="jsp/system/dataDic/disciplineGroup/list.jsp">学科管理</a></li>
								<li><a href="jsp/system/dataDic/unit/list.jsp">机构管理</a></li>
							</ul>
						</li>
						<li><a href="jsp/system/mail/list.jsp">邮件管理</a></li>
					</ul>
				</li>
				<li><a class="fnav" href="jsp/download/list.jsp">常用下载</a></li>
				<li><a class="fnav" href="jsp/portal/aboutUs.jsp">关于我们</a></li>
			</ul>
		</div>
	</div>
 <div id="content">