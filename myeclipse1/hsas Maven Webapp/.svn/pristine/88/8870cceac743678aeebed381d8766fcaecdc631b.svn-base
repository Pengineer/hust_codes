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
				<div class="panel-heading"><strong>添加邮件</strong></div>
				<div class="panel-body">
				<form id="mailAdd" method="post" action="system/mail/add.action">
					<table class="table table-striped view-table">
						<tbody>
							<tr>
								<td class = "text-right required-label" width = "100">收件人：</td>
								<td class = "text-left">
									<div class="row">
										<div class="col-xs-4"><input class="validate[required]" type="checkbox" name="recieverType" value="seniorManager">高级管理员</div>
										<div class="col-xs-4"><input class="validate[required]" type="checkbox" name="recieverType" value="generalManager">一般管理员</div>
										<div class="col-xs-4"><input class="validate[required]" type="checkbox" name="recieverType" value="unitManager">机构管理员</div>
										<div class="col-xs-4"><input class="validate[required]" type="checkbox" name="recieverType" value="experts">评审专家</div>
										<div class="col-xs-4"><input class="validate[required]" type="checkbox" name="recieverType" value="applicants">申请人</div>
										<div class="col-xs-4"><input class="validate[required]" type="checkbox" name="recieverType" value="others">其他</div>
									</div>
									<textarea class="form-control input-sm" type="text" name="mail.sendTo" placeholder="请填写地址，用分号隔开" style="display:none;"></textarea>
								</td>
								<td width="50"></td>
							</tr>
							<tr>
								<td class="text-right required-label" width = "100">邮件主题：</td>
								<td class = "text-left"><input class="form-control input-sm validate[required]" type="text" name=""></td>
								<td width="50"></td>
							</tr>
						
							<tr>
								<td width = "130" class="text-right" >附件：</td>
								<td class = "text-left" >
		    						<input type="file" id="Upload"  name="">
		    						<input type="text" class='validate[required] pull-right validation-file-input' name="solicitPapers.fileName" value="">
								</td>
								<td width="50"></td>
							</tr>
							<tr>
								<td width = "130" class="text-right" >邮件正文：</td>
								<td class = "text-left">
									<textarea class="form-control" rows="10"></textarea>
								</td>
								<td width="50"></td>
							</tr>
							<tr>
								<td colspan="3">
									<div id="optr" class="btn_bar1 form-group">
										<div class="btn-group">
											<input id="submit" class="btn btn-default btn-sm" type="submit" value="确定">
											<input id="cancel" class="btn btn-default btn-sm" type="button" value="取消">
										</div>
									</div>
								</td>
							</tr>
						</tbody>
		    		</table>
	    		</form>
			  </div>
			</div> <!-- end <div class="panel panel-default"> -->
					
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
			seajs.use("js/system/mail/add.js", function() {
			});
		</script>
	</body>
</html>