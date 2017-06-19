<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
    </head>
    <body>
    		<%@ include file="/jsp/nav.jsp"%>
			<div class="row mySlide">
				<div class="col-xs-9 slide-pic">
					<div class="row">
						<div class="col-xs-6 ">
							<div class="myPanel myPanel-notice">
								<div class="panel-heading">
									<h3 class="panel-title"><i class="fa fa-book fa-2"></i>&nbsp;社科动态
										<a href="#" title='更多' class="pull-right">更多>></a>
									</h3>
								</div>
								<div class="panel-body myNews">
									<ul class="list-unstyled"><li><a href="article/viewPage?id=4028d8874e433df7014e436bc70d0000&amp;type=yyxw" title="测试">测试<br></a></li><li><a href="article/viewPage?id=ff8080814d947a61014d948b5ea40038&amp;type=yyxw" title="精密重力测量国家重大科技基础设施获立项">精密重力测量国家重大科技基础设施获立项<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b3f4090001&amp;type=yyxw" title="湖北省纪委来校督导调研“两个责任”落实情况">湖北省纪委来校督导调研“两个责任”落实情况<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b307d40000&amp;type=yyxw" title="《自然》发表生命学院张贤钦教授研究论文">《自然》发表生命学院张贤钦教授研究论文<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815c4bcb000a&amp;type=yyxw" title="2014级新生欢迎教官进校仪式举行">2014级新生欢迎教官进校仪式举行<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815356e90008&amp;type=yyxw" title="智能机器人团队9月例会畅谈团队文化建设">智能机器人团队9月例会畅谈团队文化建设<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815098ec0007&amp;type=yyxw" title="校党委积极落实主体责任">校党委积极落实主体责任<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815794fa0009&amp;type=yyxw" title="校招：特斯拉接你去海豚">校招：特斯拉接你去海豚<br></a></li></ul>
								</div>
							</div>
						</div>
						<div class="col-xs-6">
							<div class="myPanel myPanel-notice">
								<div class="panel-heading">
									<h3 class="panel-title"><i class="fa fa-newspaper-o fa-2"></i>&nbsp;通知公告
										<a href="jsp/portal/list.jsp" title='更多' class="pull-right">更多>></a>
									</h3>
								</div>
									<div class="panel-body myNews">
									<ul class="list-unstyled"><li><a href="article/viewPage?id=4028d8874e433df7014e436bc70d0000&amp;type=yyxw" title="测试">测试<br></a></li><li><a href="article/viewPage?id=ff8080814d947a61014d948b5ea40038&amp;type=yyxw" title="精密重力测量国家重大科技基础设施获立项">精密重力测量国家重大科技基础设施获立项<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b3f4090001&amp;type=yyxw" title="湖北省纪委来校督导调研“两个责任”落实情况">湖北省纪委来校督导调研“两个责任”落实情况<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b307d40000&amp;type=yyxw" title="《自然》发表生命学院张贤钦教授研究论文">《自然》发表生命学院张贤钦教授研究论文<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815c4bcb000a&amp;type=yyxw" title="2014级新生欢迎教官进校仪式举行">2014级新生欢迎教官进校仪式举行<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815356e90008&amp;type=yyxw" title="智能机器人团队9月例会畅谈团队文化建设">智能机器人团队9月例会畅谈团队文化建设<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815098ec0007&amp;type=yyxw" title="校党委积极落实主体责任">校党委积极落实主体责任<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815794fa0009&amp;type=yyxw" title="校招：特斯拉接你去海豚">校招：特斯拉接你去海豚<br></a></li></ul>
								</div>
							</div>
						</div>
						<div class="col-xs-6">
							
						</div>
					</div>
				</div>
				<div class="col-xs-3 myIcon quick-channels">
			    	<h4 class="text-center login">登陆入口</h4>
			    	<form class="form-signin" role="form" action="login" method="post">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-user"></span>
								</span>
								<input type="text" class="form-control" name="j_username" placeholder="用户名" required autofocus>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-lock"></span>
								</span>
								<input type="password" class="form-control" name="j_password" placeholder="密码" required>
							</div>
						</div>
						<div class="myBtn">
							<button class="btn btn-sm btn-success btn-submit" type="submit" data-loading-text="登录中...">登录</button>
							<button class="btn btn-sm btn-primary" type="reset">重置</button>
						</div>
						<p>&nbsp;</p>
						<div class="signup">
						<p>没有账号？ &nbsp;&nbsp;<a href="jsp/register.jsp" class="btn btn-primary">立即注册</a></p>
						</div>
					</form>
					<div>
					</div>
			    </div>
			</div>
			<div class="row">
				<div class="col-xs-9 slide-pic ">
					<div class="row">
						<div class="col-xs-6 ">
							<div class="myPanel myPanel-notice">
								<div class="panel-heading">
									<h3 class="panel-title"><i class="fa fa-file-text fa-2"></i>&nbsp;政策文件
										<a href="#" title='更多' class="pull-right">更多>></a>
									</h3>
								</div>
								<div class="panel-body myNews">
									<ul class="list-unstyled"><li><a href="article/viewPage?id=4028d8874e433df7014e436bc70d0000&amp;type=yyxw" title="测试">测试<br></a></li><li><a href="article/viewPage?id=ff8080814d947a61014d948b5ea40038&amp;type=yyxw" title="精密重力测量国家重大科技基础设施获立项">精密重力测量国家重大科技基础设施获立项<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b3f4090001&amp;type=yyxw" title="湖北省纪委来校督导调研“两个责任”落实情况">湖北省纪委来校督导调研“两个责任”落实情况<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b307d40000&amp;type=yyxw" title="《自然》发表生命学院张贤钦教授研究论文">《自然》发表生命学院张贤钦教授研究论文<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815c4bcb000a&amp;type=yyxw" title="2014级新生欢迎教官进校仪式举行">2014级新生欢迎教官进校仪式举行<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815356e90008&amp;type=yyxw" title="智能机器人团队9月例会畅谈团队文化建设">智能机器人团队9月例会畅谈团队文化建设<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815098ec0007&amp;type=yyxw" title="校党委积极落实主体责任">校党委积极落实主体责任<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815794fa0009&amp;type=yyxw" title="校招：特斯拉接你去海豚">校招：特斯拉接你去海豚<br></a></li></ul>
								</div>
							</div>
						</div>
						<div class=" col-xs-6 ">
							<div class = "myPanel myPanel-news">
								<div class="panel-heading">
									<h3 class="panel-title"><i class="fa fa-question-circle fa-2"></i>&nbsp;注意事项
										<a href="#" title='更多' class="pull-right">更多>></a>
									</h3>
								</div>
								<div class="panel-body myNotice">
									<ul class="list-unstyled"><li><a href="article/viewPage?id=4028d8874e433df7014e436bc70d0000&amp;type=yyxw" title="测试">测试<br></a></li><li><a href="article/viewPage?id=ff8080814d947a61014d948b5ea40038&amp;type=yyxw" title="精密重力测量国家重大科技基础设施获立项">精密重力测量国家重大科技基础设施获立项<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b3f4090001&amp;type=yyxw" title="湖北省纪委来校督导调研“两个责任”落实情况">湖北省纪委来校督导调研“两个责任”落实情况<br></a></li><li><a href="article/viewPage?id=ff8080814911a62a014911b307d40000&amp;type=yyxw" title="《自然》发表生命学院张贤钦教授研究论文">《自然》发表生命学院张贤钦教授研究论文<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815c4bcb000a&amp;type=yyxw" title="2014级新生欢迎教官进校仪式举行">2014级新生欢迎教官进校仪式举行<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815356e90008&amp;type=yyxw" title="智能机器人团队9月例会畅谈团队文化建设">智能机器人团队9月例会畅谈团队文化建设<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815098ec0007&amp;type=yyxw" title="校党委积极落实主体责任">校党委积极落实主体责任<br></a></li><li><a href="article/viewPage?id=ff808081488109810148815794fa0009&amp;type=yyxw" title="校招：特斯拉接你去海豚">校招：特斯拉接你去海豚<br></a></li></ul>
								</div>
							</div>
						</div>
					</div>
			    </div>
				<div class="col-xs-3 quick-channels" style="margin-top:10px">
					<div class="row">
							<div class="col-xs-12 list-group no-padding-right">
								<h3 class="panel-title-sidebar">快速通道</h3>
								<a class="list-group-item" href="solicitpapers/toAdd.action?flag=0" >2015年论坛投稿入口</a>
								<a class="list-group-item" href="solicitpapers/toAdd.action?flag=1" >2015年课题投稿入口</a>
							</div>
					</div>
					<div class="row">
						<div class="col-xs-12 list-group no-padding-right">
							<h3 class="panel-title-sidebar">友情链接</h3>
							<a href="#" class="list-group-item">友情链接1</a>
							<a href="#" class="list-group-item">友情链接2</a>
							<a href="#" class="list-group-item">友情链接3</a>
						</div>
					</div>
				</div>
			
			<%@ include file="/jsp/footer.jsp"%>
		<script src="js/index.js" type="text/javascript"></script>
	</body>
</html>