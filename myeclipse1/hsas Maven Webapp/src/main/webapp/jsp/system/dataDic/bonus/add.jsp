<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%> <%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
	<head>
		<title>湖北省社会科学优秀成果奖励申报评审系统</title>
		<%@ include file="/jsp/base.jsp"%>
		<link rel="stylesheet" href="lib/bootstrap-datepicker-1.4.0-dist/css/bootstrap-datepicker3.css">
	</head>
	<body>
		<%@ include file="/jsp/innerNav.jsp"%>
		<a name="top" id="top"></a>
		<div class="row mySlide">
			<ol class="breadcrumb mybreadcrumb">当前位置：
				<li><a href="#">系统管理</a></li>
				<li><a href="#">数据字典管理</a></li>
				<li class="active">奖金设置</li>
			</ol>
			<div class="col-xs-12">
				<form id="bonusForm">
					<table class="table view-table">
						<tbody>
							<tr>
								<td class = "text-right required-label" width = "100">年份选择：</td>
								<td class="text-left" width = "300"><input type="text" name="year" class="form-control input-sm datePicker validate[required]" placeholder="请输入年份"></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					
					<div id="bonusSet" class="panel panel-default panel-view">
						<div class="panel-heading"><strong>论文</strong></div>
						<div class="panel-body">
							<table class="table table-striped view-table">
								<tbody>
									<%--<tr>
										<td width = "50" class = "text-right"></td>
										<td class = "text-right required-label" width = "100">年份选择：</td>
										<td class="text-left" colspan="2">
											<input type="text" name="year" class="form-control input-sm datePicker validate[required]" readonly="true" placeholder="请输入年份">
										</td>
									</tr>
									--%>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label">特等奖：</td>
										<td class = "text-left"><input type="text" name="paper.specialPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label">一等奖：</td>
										<td class = "text-left"><input type="text" name="paper.fistPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label" >二等奖：</td>
										<td class = "text-left"><input type="text" name="paper.secondPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label" >三等奖：</td>
										<td class = "text-left"><input type="text" name="paper.thirdPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div> <!-- end <div class="panel panel-default"> -->
					
					<div id="bonusSet" class="panel panel-default panel-view">
						<div class="panel-heading"><strong>著作</strong></div>
						<div class="panel-body">
							<table class="table table-striped view-table">
								<tbody>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label">特等奖：</td>
										<td class = "text-left"><input type="text" name="book.speciaPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label">一等奖：</td>
										<td class = "text-left"><input type="text" name="book.firstPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label" >二等奖：</td>
										<td class = "text-left"><input type="text" name="book.secondPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
									<tr>
										<td width = "100" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
										<td width = "80" class = "text-right required-label" >三等奖：</td>
										<td class = "text-left"><input type="text" name="book.thirdPrize" class="form-control input-sm validate[required, custom[number]]" placeholder="请输入"></td>
										<td width = "100"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div> <!-- end <div class="panel panel-default"> -->
					
					<div id="optr" class="btn_bar1">
						<input type="submit" value="提交" class="btn btn-default btn-sm" id="submit">
						<input type="button" value="取消" class="btn btn-default btn-sm" id="submit">
					</div>
				</form>
			</div>
		</div>
		
		<div class="row">
		<%@ include file="/jsp/footer.jsp"%>
	</body>
	<script>
		seajs.use("js/system/dataDic/bonus/add", function() {
			
		});
	</script>
</html>