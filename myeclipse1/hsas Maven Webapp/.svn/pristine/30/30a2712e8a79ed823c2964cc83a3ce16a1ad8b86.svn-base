<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%> <%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖励申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
        <link rel="stylesheet" href="lib/uploadify/css/uploadify.css">
    </head>
    <body>
   		<%@ include file="/jsp/innerNav.jsp"%>
   		<a name="top" id="top"></a>
			<div class="row mySlide">
				<ol class="breadcrumb mybreadcrumb">当前位置：
				  <li><a href="#">系统管理</a></li>
				  <li><a href="#">邮件管理</a></li>
				  <li class="active">添加</li>
				</ol>
			</div>
			<span class="clearfix"></span><!-- 重要!! 用于清除按键组浮动 -->
			
			<div class="panel panel-default panel-view">
			  <div class="panel-heading"><strong>编辑邮件</strong></div>
			  <div class="panel-body">
			    <table class="table table-striped view-table">
			      <tbody>
			    	<tr>
						<td class = "text-right" width = "100">收件人：</td><td class = "text-left"><input class="form-control input-sm" type="text" name=""></td>
					</tr>
					<tr>
						<td class = "text-right" width = "100">邮件主题：</td><td class = "text-left"><input class="form-control input-sm" type="text" name=""></td>
					</tr>
					
					<tr>
						<td width = "130" class = "text-right" >附件：</td>
						<td class = "text-left" >
	    					<input type="file" id="Upload"  name="" class='validate[required]'>
	    					<input type="text" class='validate[required] pull-right validation-file-input' name="solicitPapers.fileName" value="">
						</td>
					</tr>
					<tr>
						<td width = "130" class = "text-right" >邮件正文：</td><td class = "text-left"><textarea class="form-control" rows="10"></textarea></td>
					</tr>
				  </tbody>
	    		</table>
			  </div>
			</div> <!-- end <div class="panel panel-default"> -->
					
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
			seajs.use("js/system/mail/view.js", function(list) {
		         view.init(); 
		    });
		</script>
	</body>
</html>