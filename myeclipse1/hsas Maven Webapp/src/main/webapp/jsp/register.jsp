<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖励申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
    </head>
    <body>
    		<%@ include file="/jsp/nav.jsp"%>
			<div class="row">
				<ol class="breadcrumb mybreadcrumb">当前位置：
					<li><a href="#">首页</a></li>
					<li class="active">注册</li>
				</ol>
				<div class="col-xs-8 col-xs-offset-2">
					<form id="registerForm" class="form-horizontal">
						<div class="form-group">
							<label class="col-xs-2 control-label">用户名</label>
							<div class="col-xs-10">
								<input id="user" class="form-control validate[required, custom[onlyLetterNumber]]" type="text" placeholder="请输入数字和英文字母">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">密码</label>
							<div class="col-xs-10">
								<input id="password" class="form-control validate[required, minSize[6]]" type="password" placeholder="至少输入6个字符">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">确认密码</label>
							<div class="col-xs-10">
								<input id="rePassword" class="form-control validate[required, equals[password]]" type="password" placeholder="确认密码">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">中文名</label>
							<div class="col-xs-10">
								<input id="chineseName" class="form-control validate[required, custom[chinese]]" type="text" placeholder="中文名">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">性别</label>
							<div class="col-xs-10">
								<select name="solicitPapers.gender" class="form-control input-sm" placeholder="请输入性别">
									<option selected="selected" value="">请选择</option>
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">所在单位</label>
							<div class="col-xs-10">
								<input id="unit" class="form-control validate[required]" type="text" placeholder="所在单位">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">邮箱</label>
							<div class="col-xs-10">
								<input id="email" class="form-control validate[required, custom[email]]" type="email" placeholder="邮箱">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">联系电话</label>
							<div class="col-xs-10">
								<input id="phone" class="form-control validate[required, custom[phone]]" type="text" placeholder="联系电话">
							</div>
						</div>
						<div id="optr" class="btn_bar1 form-group">
							<div class="btn-group">
								<input type="submit" value="注册" class="btn btn-default btn-sm" id="submit">
							</div>
						</div>
					</form>
				</div>
			<%@ include file="/jsp/footer.jsp"%>
	<script>
		seajs.use('js/register.js', function(){
			
		})
	</script>
	</body>
</html>