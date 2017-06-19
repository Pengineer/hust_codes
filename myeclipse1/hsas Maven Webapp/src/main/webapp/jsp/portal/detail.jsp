<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-xs-12 news-content">
	<div class="col-xs-12 news-content-title">
		${article.title}
	</div>
	<div class="col-xs-12 news-content-subtitle" >
		<i class="fa fa-cube"></i>&nbsp;来源: <span class="news-source">xxx</span>&nbsp;&nbsp;
		<i class="fa fa-eye"></i>&nbsp;点击次数：<span class="click-times">xx</span>次&nbsp;&nbsp;
		<i class="fa fa-calendar"></i>&nbsp;发布时间：<span class="release-time">2015-08-05 16：10：10</span>&nbsp;&nbsp;
		<i class="fa fa-user"></i>&nbsp;编辑：<span class="editor">xxx</span>&nbsp;&nbsp;
	</div>
	<div class="col-xs-12 news-content-para" id="itemContainer">
		${article.content}
	</div>
	<div class="controler">
		<div class = "holder"></div>
		</div>
	<div class="col-xs-12">
		<i class="fa fa-paperclip fa-2"></i>&nbsp;附件：<br>
		<c:forEach items="${attachmentNameList}" var="attachment" varStatus="varStatus">
			<a class="attach" href="javascript:void(0);" id="${varStatus.index}">${varStatus.current}</a>
			<c:choose>
				<c:when test="${attachmentSizeList[varStatus.index] != null}">(${attachmentSizeList[varStatus.index]})</c:when>
				<c:otherwise>(附件不存在)</c:otherwise>
			</c:choose>
			<br>
			
		</c:forEach>
		<input type="hidden" value="" id="articleId">
		<input type="hidden" value="" id="articleType">
	</div>
</div>