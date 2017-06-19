<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖励申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
    </head>
    <body>
    		<%@ include file="/jsp/innerNav.jsp"%>
			<div class="row">
				<ol class="breadcrumb mybreadcrumb">当前位置：
					<li><a href="#">个人空间</a></li>
					<li class="active">密码修改</li>
				</ol>
				<div class="col-xs-6 col-xs-offset-3">
					<form id="modifyPwdForm" class="form-horizontal">
						<div class="form-group">
							<label class="col-xs-3 control-label">原始密码</label>
							<div class="col-xs-9">
								<input id="oldPassword" class="form-control validate[required]" name="oldPassword" type="password" placeholder="原始密码">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">新密码</label>
							<div class="col-xs-9">
								<input id="newPassword" class="form-control validate[required, minSize[6]]" name="newPassword" type="password" placeholder="新密码">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">再次输入</label>
							<div class="col-xs-9">
								<input id="reNewPassword" class="form-control validate[required, equals[newPassword]]" name="reNewPassword" type="password" placeholder="再次输入新密码">
							</div>
						</div>
						<div id="optr" class="btn_bar1 form-group">
							<div class="btn-group">
								<input id="submit" class="btn btn-default btn-sm" type="submit" value="确定">
								<input id="cancel" class="btn btn-default btn-sm" type="button" value="取消">
							</div>
						</div>
					</form>
				</div>
			<%@ include file="/jsp/footer.jsp"%>
	<script>
		seajs.use('js/selfspace/modifyPassword', function(){
			
		})
	</script>
	</body>
</html>