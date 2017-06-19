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
			  <li><a href="#">系统权限 </a></li>
			  <li class="active">查看</li>
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
								<td width = "100" class = "text-right">权限名称：</td>
								<td width ='300' class = "text-left">${name }</td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "100" class = "text-right">权限节点值：</td>
								<td width ='' class = "text-left" >${nodeValue }</td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width = "100" class = "text-right">权限代码：</td>
								<td class = "text-left" colspan = "4">${code }</td>
							</tr>
							<tr>
								
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td width ='100' class = "text-right">权限描述：</td>
								<td class = "text-left" colspan = "4">${description }</td>
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
		    seajs.use("js/system/right/view.js", function(view) {
		         view.init(); 
		    });
		</script>
	</body>
</html>