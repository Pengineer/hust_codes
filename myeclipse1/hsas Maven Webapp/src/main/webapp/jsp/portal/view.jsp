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
   		<a name="top" id="top"></a>
			<div class="row mySlide">
			    <div class="col-xs-3 sidebar1 view-nav-bar no-padding-right">
		        	<div><a href="article/listPage?type=yyxw"><i class="fa fa-newspaper-o fa-2"></i>&nbsp;新闻通知&nbsp;>></a></div>
					<div><a href="article/listPage?type=tzgg"><i class="fa fa-book fa-2"></i>&nbsp;社科动态&nbsp;>></a></div>
					<div><a href="article/listPage?type=tzgg"><i class="fa fa-question-circle fa-2"></i>&nbsp;注意事项&nbsp;>></a></div>
			    </div>
			    
			    <div class="col-xs-9 sidebar2 no-padding-left">
		            <div class="view-content">
						<div class="col-xs-12 current-position">
							当前位置：
							<span>新闻通知</span>
							<div class="pull-right myGlyphicons">
									<a title="点击修改当前页面" href="#"><span class="glyphicon glyphicon-edit"></span></a>
									<a title="点击删除当前页面" href="javascript:void(0);" class="article-del"><span class="glyphicon glyphicon-trash"></a>
								<a title="返回" href="javascript:void(0);" class="article-back"><span class="glyphicon glyphicon-share-alt"></a>
							</div>
						</div>
						<%@include file="detail.jsp" %>
						<nav class="col-xs-12 pager-nav">
							<ul class="pager" style="font-family:微软雅黑 ;" >
								<li class=" no-padding-left" style = "margin-bottom: 11px;">
									<c:if test="${ empty preId }">没有了</c:if>
									<a href="article/viewPage?id=${preId}&type=${type}">上一篇</a>
								</li>
								<li class=""  style = "margin-bottom: 11px;">
									<c:if test="${ empty nextId }">没有了</c:if>
									<a href="article/viewPage?id=${nextId}&type=${type}">下一篇</a>
								</li>
							</ul>
						</nav>
						
					</div>
			    </div>
			</div>
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
		    seajs.use("js/portal/view.js", function(list) {
		         view.init(); 
		    });
		</script>
	</body>
</html>