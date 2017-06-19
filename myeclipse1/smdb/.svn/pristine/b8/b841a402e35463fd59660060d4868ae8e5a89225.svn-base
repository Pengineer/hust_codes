define(function(require, exports, module) {
	var view = require('javascript/view');

	var initAward = function() {
		$(function() {
			//查看一个人名义申请的申请人详细信息
			$(".viewapplicant").live("click", function() {
				popPerson(this.id, 7);
				return false;
			});
			//查看以团队等名义申请的团队详细信息
			$(".viewOrganization").live("click", function() {
				popOrganization(this.id);
				return false;
			});
			//查看学校详细信息
			$(".viewuniversity").live("click", function() {
				popAgency(this.id, 1);
				return false;
				
			});
			// 院系
			$(".linkDep").live("click", function() {
				popDept(this.id, 2);
				return false;
			});
			// 基地
			$(".linkIns").live("click", function() {
				popInst(this.id, 3);
				return false;
			});
		});
	};
	//获得命名空间
	function getNamespace(){
		var listflag = $("#listflag").val();
		var namespace;
		if (listflag == 1){
			namespace = "award/moesocial/application/apply";
		}else if (listflag == 2) {
			namespace = "award/moesocial/application/publicity";
		}else if (listflag == 3) 
			namespace = "award/moesocial/application/awarded";
		else if (listflag == 4){
			namespace = "award/moesocial/application/review";
		}else if (listflag == 5){
			namespace = "award/myAward";
		}
		return namespace;
	}
	// 返回列表
	var backToList = function(url) {
		$("#view_award").attr("action", url);
		$("#view_award").submit();
	};
	// 查看上条
	var prev = function(showDetails) {
		$("#view_prev").live("click", function() {
			if($("#listflag").val() == 1){
				viewAward("award/moesocial/application/apply/prev.action", showDetails);
			}
			if($("#listflag").val() == 2){
				viewAward("award/moesocial/application/publicity/prev.action", showDetails);
			}
			if($("#listflag").val() == 3){
				viewAward("award/moesocial/application/awarded/prev.action", showDetails);
			}
			if($("#listflag").val() == 4){
				viewAward("award/moesocial/application/review/prev.action", showDetails);
			}
			if($("#listflag").val() == 5){
				viewAward("award/myAward/prev.action", showDetails);
			}
			return false;
		});
	};
	// 查看下条
	var next = function(showDetails) {
		$("#view_next").live("click", function() {
			if($("#listflag").val() == 1){
				viewAward("award/moesocial/application/apply/next.action", showDetails);
			}
			if($("#listflag").val() == 2){
				viewAward("award/moesocial/application/publicity/next.action", showDetails);
			}
			if($("#listflag").val() == 3){
				viewAward("award/moesocial/application/awarded/next.action", showDetails);
			}
			if($("#listflag").val() == 4){
				viewAward("award/moesocial/application/review/next.action", showDetails);
			}
			if($("#listflag").val() == 5){
				viewAward("award/myAward/next.action", showDetails);
			}
			return false;
		});
	};
	//返回
	var back = function(){
		$(function() {
			$("#view_back").live("click", function() {
				if($("#listflag").val() < 5){
					backToList(getNamespace() + "/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}else{
					backToList("award/toSearchMyAward.action");
				}
				return false;
			});
		});
	};
	//添加奖励申请
	var add = function(){
		$("#view_add").live("click",function() {
			var url = "award/moesocial/application/apply/toAdd.action";
			url += "?listflag=" + $("#listflag").val();
			window.location.href = basePath + url;
			return false;
		});
	};
	//修改奖励申请
	var mod = function(){
		$("#view_mod").live("click", function() {
			var url = "award/moesocial/application/apply/toModify.action?entityId=" + $("#entityId").val();
			url += "&listflag=" + $("#listflag").val();
			window.location.href = basePath + url;
			return false;
		});
	};
	//删除奖励申请
	var del = function(){
		$("#view_del").live("click", function() {
			var url = "award/moesocial/application/apply/delete.action?entityIds=" + $("#entityId").val();
				if (confirm("您确定要删除此奖励申请吗？")) {
					$("#view_award").attr("action", url);
					$("#view_award").ajaxSubmit({
						success: function(result){
							if (result.errorInfo == null || result.errorInfo == "") {
								backToList(getNamespace() + "/toList.action?entityId=" + $("#entityId").val() + "&update=1");
								return false;
							} else {
								alert(result.errorInfo);
							}
						}
					});
				}
			return false;
		});
	};
	var readApply = function(showDetails){
		if($("#listflag").val() == 1){
			viewAward("award/moesocial/application/apply/view.action", showDetails);
		}
		if($("#listflag").val() == 2){
			viewAward("award/moesocial/application/publicity/view.action", showDetails);
		}
		if($("#listflag").val() == 3){
			viewAward("award/moesocial/application/awarded/view.action", showDetails);
		}
		if($("#listflag").val() == 4){
			viewAward("award/moesocial/application/review/view.action", showDetails);
		}
		if($("#listflag").val() == 5){
			viewAward("award/viewMyAward.action", showDetails);
		}
	};
	//加载查看数据
	var viewAward = function(url, showDetails) {
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		}
		$.ajax({
			url: url,
			type: "post",
			data: "entityId=" + $("#entityId").val() + "&subType=" + $("#subType").val(),
			dataType: "json",
			success: showDetails
		});
	};
	//ajax提交审核意见
	var doSubmitAudit = function(dis, type, showDetails){
		document.getElementsByName('auditResult')[0].value = dis.auditResult;
		document.getElementsByName('auditStatus')[0].value = dis.auditStatus;
		document.getElementsByName('auditOpinion')[0].value = dis.auditOpinion;
		$("input[name='auditOpinionFeedback']").val(dis.auditOpinionFeedback);
		document.getElementsByName('auditOpinionFeedback')[0].value = dis.auditOpinionFeedback;
		var audflag = document.getElementById('audflag').value;
		if (audflag == 1) {
			if(type == 1) {
				$("#list").attr("action", "award/moesocial/application/applyAudit/add.action");
			}else if(type == 3){
				$("#list").attr("action", "award/moesocial/application/reviewAudit/add.action");
			}
			$("#list").submit();
		}
		else {
			var entityId = document.getElementById('entityId').value;
			var url;
			if(type == 1)
				url = "award/moesocial/application/applyAudit/add.action?entityIds=" + entityId;
			else if(type == 2)
				url = "award/moesocial/application/applyAudit/modify.action?entityIds=" + entityId;
			else if(type == 3)
				url="award/moesocial/application/reviewAudit/add.action?entityIds=" + entityId;
			else if(type == 4)
				url = "award/moesocial/application/reviewAudit/modify.action?entityIds=" + entityId;
				$("#audit_form").ajaxSubmit({
					url: url,
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							$("#update").val(1);
							readApply(showDetails);
						} else {
							alert(result.errorInfo);
						}
					}
				});
		}
	};
	//ajax提交专家评审信息
	var doSubmitReview = function(dis, type, showDetails){
		var entityId=document.getElementById('entityId').value;
		var url = "";
		if(type == 1){
			url = "award/moesocial/application/review/add.action?&entityId=" + entityId;
		}else if(type == 2){
			url = "award/moesocial/application/review/modify.action?&entityId=" + entityId;
		}
		url = url + "&meaningScore=" + dis.meaningScore + "&innovationScore=" + dis.innovationScore + "&influenceScore=" + dis.influenceScore + "&methodScore=" + dis.methodScore + 
				"&awardGradeid=" + dis.awardGradeid + "&auditStatus=" + dis.auditStatus + "&auditOpinion=" + dis.auditOpinion;
		$.ajax({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readApply(showDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	//ajax提交小组评审信息
	var doSubmitGroupReview = function(dis, type, showDetails){
		document.getElementsByName('auditResult')[0].value=dis.auditResult;
		document.getElementsByName('auditStatus')[0].value=dis.auditStatus;
		document.getElementsByName('auditOpinion')[0].value=dis.auditOpinion;
		document.getElementsByName('auditOpinionFeedback')[0].value=dis.auditOpinionFeedback;
		document.getElementsByName('reviewWay')[0].value=dis.reviewWay;
		document.getElementsByName('awardGradeid')[0].value=dis.awardGradeid;
		var entityId=document.getElementById('entityId').value;
		var url;
		if(type==1)
			url="award/moesocial/application/review/addGroup.action?&entityId=" + entityId;
		else if(type==2)
			url="award/moesocial/application/review/modifyGroup.action?&entityId=" + entityId;
		$("#audit_form").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readApply(showDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	//ajax提交公示审核意见
	var doSubmitAwardedAudit = function(dis, type, showDetails){
		var audflag = document.getElementById('audflag').value;
		document.getElementsByName('auditResult')[0].value = dis.auditResult;
		document.getElementsByName('year')[0].value = dis.year;
		document.getElementsByName('auditOpinion')[0].value = dis.auditOpinion;
		document.getElementsByName('auditOpinionFeedback')[0].value = dis.auditOpinionFeedback;
		document.getElementsByName('auditStatus')[0].value = dis.auditStatus;
		if (audflag == 1) {
			$("#list").attr("action", "award/moesocial/application/publicityAudit/add.action");
			$("#list").submit();
		}
		else {
			document.getElementsByName('number')[0].value = dis.number;
			var entityId = document.getElementById('entityId').value;
			var url;
			if(type == 1)
				url ="award/moesocial/application/publicityAudit/add.action?entityIds=" + entityId;
			else url = "award/moesocial/application/publicityAudit/modify.action?entityIds=" + entityId;
			$("#audit_form").ajaxSubmit({
				url: url,
				success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						readApply(showDetails);
					} else {
						alert(result.errorInfo);
					}
				}
			});
		}
	};
	//提交表单
	var ajaxSubmitform = function(url, showDetails){
		$("#view_award").attr("action", url);
		$("#view_award").ajaxSubmit({
			success:function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					if(result.entityId != null && result.entityId != ""){
						$("#entityId").val(result.entityId);
					}
					readApply(showDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	//查看审核详情
	var alertAudit = function(title, viewflag){//查看审核详情
		popAwardOperation({
			title : title,         
			src : "award/moesocial/application/applyAudit/view.action?entityId=" + $("#entityId").val()+ "&viewflag=" + viewflag
		});
		return false;
	};
	
	module.exports = {
		initAward: function(){initAward()},
		getNamespace: function(){getNamespace()},
		prev: function(showDetails){prev(showDetails)},
		next: function(showDetails){next(showDetails)},
		back: function(){back()},
		add: function(){add()},
		mod: function(){mod()},
		del: function(){del()},
		readApply: function(showDetails){readApply(showDetails)},	
		viewAward: function(url, showDetails){viewAward(url, showDetails)},
		doSubmitAudit: function(dis, type, showDetails){doSubmitAudit(dis, type, showDetails)},
		doSubmitReview: function(dis, type, showDetails){doSubmitReview(dis, type, showDetails)},
		doSubmitGroupReview: function(dis, type, showDetails){doSubmitGroupReview(dis, type, showDetails)},
		doSubmitAwardedAudit: function(dis, type, showDetails){doSubmitAwardedAudit(dis, type, showDetails)},
		ajaxSubmitform: function(url, showDetails){ajaxSubmitform(url, showDetails)},
		alertAudit: function(title, viewflag){alertAudit(title, viewflag)}
	};
});
