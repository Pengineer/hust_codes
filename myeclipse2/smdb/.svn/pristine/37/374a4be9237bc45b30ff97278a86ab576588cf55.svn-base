<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看人文社科奖</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div class="link_bar" id="view_award_application">
			<s:if test="listflag==4">
				当前位置：评审数据&nbsp;&gt;&nbsp;评审奖励&nbsp;&gt;&nbsp;查看
			</s:if>
			<s:elseif test="listflag==5">
				当前位置：我的奖励&nbsp;&gt;&nbsp;查看
			</s:elseif>
			<s:else>
				当前位置：社科奖励数据&nbsp;&gt;&nbsp;人文社科奖&nbsp;&gt;&nbsp;
					<s:if test="listflag==3">获奖数据</s:if>
					<s:elseif test="listflag==2">公示数据</s:elseif>
					<s:else>申报数据</s:else>
				&nbsp;&gt;&nbsp;查看
			</s:else>
		</div>
		<div class="main" style="display:none;" id="view_content">
			<s:hidden id="entityId" name="entityId" />
			<s:hidden id="update" name="update"/>
			<s:hidden id="selectedTab" name="selectedTab"/>
			<s:hidden id="listflag" name="listflag" />
			<s:hidden id="subType" name="subType"/>
			<s:hidden id="audflag" name="audflag" value="０"/>
			<s:include value="/award/moesocial/application/viewChooseBar.jsp" />
			<s:include value="/award/moesocial/application/viewBasic.jsp" />
			<div class="main_content" id="submit">
				<div id="tabs" class="p_box_bar">
					<ul>
						<li><a href="#application">申报信息</a></li>
						<li id="review_li"><a href="#review">评审与公示信息</a></li>
						<li><a href="#awarded">奖励信息</a></li>
					</ul>
				</div>
				<div class="p_box">
					<div id="application">
						<s:include value="/award/moesocial/application/viewApplication.jsp" />
					</div>
					<div id="review">
						<s:include value="/award/moesocial/application/viewReview.jsp"/>
					</div>
					<div id="awarded">
						<s:include value="/award/moesocial/application/viewAwarded.jsp" />
					</div>
				</div>
			</div>
			<s:form id="audit_form" >
				<s:hidden name="auditStatus"/>
				<s:hidden name="auditResult"/>
				<s:hidden name="auditOpinion"/>
				<s:hidden name="auditOpinionFeedback"/>
				<s:hidden name="reviewWay"/>
				<s:hidden name="awardGradeid"/>
				<s:hidden name="year"/>
				<s:hidden name="number"/>
			</s:form>
		</div>
		<script type="text/javascript">
			seajs.use(['javascript/award/moesocial/application/view.js', 
						'javascript/award/moesocial/application/publicity/view.js',
						'javascript/award/moesocial/application/apply/view.js',
						'javascript/award/moesocial/application/review/view.js',
						'javascript/award/moesocial/application/apply/audit.js'],
						function(view, pub, app, review, audit) {
							$(document).ready(function() {
								view.init();
								pub.init();
								app.init();
								review.init();
								review.initClick();
								audit.init();
								audit.initClick();
							});
				});
		</script>
	</body>
</html>