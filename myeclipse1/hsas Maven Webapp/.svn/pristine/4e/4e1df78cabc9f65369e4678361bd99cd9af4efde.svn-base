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
				   <div class="survey_add addTable">
				    	<form id="form_role" name="form_role" action="role/add.action" method="post">
				    	<input type="hidden" name="rightNodeValues" value="" id="rightNodeValues"/>
				    		<table width="100%">
				    			<!-- 从前台向后台传数据两种方法1：直接写入name=对象名.属性值；2：加上required，在后台逐个访问标签 -->
				    			<tbody><tr>
				    				<td width="80" class="text-right required-label">角色名称：</td>
				    				<td><input type="text" name="role.name" class="form-control input-sm validate[required]" placeholder=""></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">角色描述：</td>
				    				<td><textarea  type="text" name="role.description" class="form-control input-sm validate[required]" placeholder=""></textarea></td>
				    			</tr>
				    			
				    			<%--<tr>
				    				<td width="80" class="text-right required-label">角色类型：</td>
				    				<td>
					    				<input type="radio" name="type" id="form_role_type0" checked="checked" value="0"/><label for="form_role_type0">非默认角色</label>
										<input type="radio" name="type" id="form_role_type1" value="1"/><label for="form_role_type1">指定账号类型的默认角色</label>
										<input type="radio" name="type" id="form_role_type2" value="2"/><label for="form_role_type2">指定机构的默认角色</label>
				    				</td>
				    			</tr>--%>
				    			<tr>
				    				<td width="80" class="text-right">权限名称：</td>
				    				<td class = "row">
				    				<div class="col-xs-11 no-padding"><input type="text" name="" id="keyword"  class="form-control input-sm" /></div>
					    			<div class="col-xs-1 no-padding"><input id="button_query" type="button" value="检索" class="btn btn-default btn-sm pull-right" /></div>	
				    				</td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right td-tree">角色权限：</td>
				    				<td class = "text-left td-tree" ><div id="treeparent" class="tree well"></div></td>
				    			</tr>
				    			
				    		</tbody></table>
				    		<div id="optr" class="btn_bar1">
				            	<div class="btn-group">
				            		<input type="button" value="确定" class="btn btn-default btn-sm" id="submit">
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
		    seajs.use("js/system/role/add.js", function(add) {
		         add.init(); 
		    });
		</script>
	</body>
</html>