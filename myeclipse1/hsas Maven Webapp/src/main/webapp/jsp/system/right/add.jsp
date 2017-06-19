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
			  <li><a href="#">系统角色 </a></li>
			  <li class="active">添加</li>
			</ol>
			    <div class="col-xs-10 main-content">
				   <div class="addTable">
				    	<form id="form_right" name="form_right" action="right/add.action" method="post">
				    		<table width="100%">
				    			<!-- 从前台向后台传数据两种方法1：直接写入name=对象名.属性值；2：加上required，在后台逐个访问标签 -->
				    			<tbody><tr>
				    				<td width="80" class="text-right required-label">权限名称：</td>
				    				<td><input type="text" name="right.name" id="form_right_right_name" class="form-control input-sm validate[required]" placeholder=""></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">权限代码：</td>
				    				<td><input  type="text" name="right.code" id="form_right_right_code" class="form-control input-sm validate[required]" placeholder=""/></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">权限描述：</td>
				    				<td><textarea  type="text" name="right.description" id="form_right_right_description" class="form-control input-sm validate[required]" placeholder=""></textarea></td>
				    			</tr>
				    			<tr>
				    				<td width="100" class="text-right required-label">权限节点值：</td>
				    				<td><input  type="text" name="right.nodevalue" id="form_right_right_nodevalue" class="form-control input-sm validate[required]" placeholder=""/></td>
				    			</tr>
				    			
				    		</tbody></table>
				    		<div id="optr" class="btn_bar1">
				            	<div class="btn-group">
				            		<input type="submit" value="确定" class="btn btn-default btn-sm" id="submit">
				            		<input type="button" value="取消" class="btn btn-default btn-sm" id="cancel">
				            	</div>
				            </div>
				   		</form>
				    </div>
			    </div>
			</div>
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
		    seajs.use("js/system/right/add.js", function(add) {
		         add.init(); 
		    });
		</script>
	</body>
</html>