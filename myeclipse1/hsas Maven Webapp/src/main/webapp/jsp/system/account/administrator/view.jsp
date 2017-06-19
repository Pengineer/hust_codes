<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%> <%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖励申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
    </head>
    <body>
   		<%@ include file="/jsp/nav.jsp"%>
   		<s:hidden id="entityId" name="entityId" value="%{entityId}" />
   		<a name="top" id="top"></a>
			<div class="row mySlide">
			<ol class="breadcrumb mybreadcrumb">当前位置：
			  <li><a href="#">系统管理</a></li>
			  <li><a href="#">账户管理</a></li>
			  <li><a href="#">社科联管理员账号</a></li>
			  <li class="active">详情</li>
			</ol>
			    <div class="col-xs-12 main-content">
				     <div class="btn-group pull-right view-controler" role="group" aria-label="...">
			  			<button type="button" class="btn btn-sm btn-default" id = "view_add">添加</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_mod">修改</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_del">删除</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_prev">上一条</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_next">下一条</button>
			  			<button type="button" class="btn btn-sm btn-default" id = "view_back">返回</button>
					</div>
					<span class="clearfix"></span><!-- 重要!! 用于清除按键组浮动 -->
					 <textarea id = "view_template" style = "display:none"><!-- 前台模版 -->
					    <table class="table table-striped view-table">
					      <tbody>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "100" class = "text-right">用户名：</td>
								<td width ='100' class = "text-left">${username }</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "100" class = "text-right">账号类型：</td>
								<td width ='200' class = "text-left" >${ type}</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "120" class = "text-right" >是否为admin：</td>
								<td class = "text-left">{if isSuperUser == 1} 是 {else} 否 {/if} </td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "100" class = "text-right">创建类型：</td>
								<td width ='100' class = "text-left" >${createType }</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width ='100' class = "text-right">账号状态：</td>
								<td width ='100' class = "text-left" colspan=''>${status }</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width ='100' class = "text-right">创建日期：</td>
								<td class = "text-left" >${startDate }</td>
							</tr>
							<tr>
								
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width ='100' class = "text-right">启用日期：</td>
								<td width ='100' class = "text-left" >${approveTime }</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width ='100' class = "text-right">失效日期：</td>
								<td width ='100' class = "text-left">${expireDate }</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width ='100' class = "text-right">上次登录时间：</td>
								<td class = "text-left">${lastLoginDate }</td>
							</tr>
						  </tbody>
			    		</table>
			    	</textarea>
			    	<div id = "view_container" style = "display:none"></div><!-- 模版解析后的容器 -->
			    	
			    </div>
			</div>
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
		    seajs.use("js/system/account/administrator/view.js", function(view) {
		         view.init(); 
		    });
		</script>
	</body>
</html>