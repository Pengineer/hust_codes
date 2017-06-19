/**
 * @author 王燕
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var viewMoesocial = require('javascript/award/moesocial/view');
	var viewApplication = require('javascript/award/moesocial/application/view');
	var validate = require('javascript/award/moesocial/application/review/validate');
	require('validate');
	require('pop-init');
	
	var listflag = "4";
	var nameSpace = "award/moesocial/application/review";
		
	var addReviewResultPop = function(data) {//录入奖励评审
		var id = document.getElementsByName('entityId')[0].value;
		popAwardOperation({
			title : "录入奖励评审意见",
			src : 'award/moesocial/application/review/toAddResult.action?entityId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewMoesocial.readApply(viewApplication.showDetails);
				layer.destroy();
			}
		});
	};
	var modifyReviewResultPop = function(data) {//修改录入评审
		popAwardOperation({
			title : "修改奖励评审意见",
			src : 'award/moesocial/application/review/toModifyResult.action?entityId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewMoesocial.readApply(viewApplication.showDetails);
				layer.destroy();
			}
		});
	};
	var expertReviewResultPop = function(data) {//修改录入评审
		popAwardOperation({
			title : "选择评审专家",
			src : 'award/moesocial/application/review/toAddExpert.action?entityId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewMoesocial.readApply(viewApplication.showDetails);
				layer.destroy();
			}
		});
	};
	var submitReviewResult = function(data){//提交录入评审
		var url = 'award/moesocial/application/review/submitResult.action';
		if( !confirm('提交后无法修改，是否确认提交？'))
			return;
		$.ajax({
			url: url,
			type: "post",
			data: "entityId=" + $("#entityId").val(),
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewMoesocial.readApply(viewApplication.showDetails);
				}
				else {
					alert(result.errorInfo);
				}
			}	
		});
	};

	var initReview = function(){//用于专家评审，小组评审
		var radioValue = $(".r_type:checked").val();
		if(radioValue == 1){//不同意时
			$("#opinion_feedback").show();
		}
	};
	var showOpinionFeedbackOrNot = function(data){//显示最终审核意见（反馈给项目负责人）
		if(data == 1){
			$("#opinion_feedback").show();
		}else{
			$("#opinion_feedback").hide();
		}
	};
	var getResult = function(){//获取总分**旧方法
		validate.valid_del();
		if(!$("#award_review").valid()){
			validate.valid_add();
			return;
		}
		var total =  Number($("[name='meaningScore']:eq(0)").val()) + Number($("[name='innovationScore']:eq(0)").val()) +
			Number($("[name='influenceScore']:eq(0)").val()) + Number($("[name='methodScore']:eq(0)").val());
		document.getElementById("totalScore").value = total;
		validate.valid_add();
	};
	
	var calcuateScore = function($table){//获得当前评审专家的打分，如果四项都有，则计算总分
		var calcuateFlag = true;
		var score = 0;
		for(var i = 0; i < 4; i++){
			if($(".cal_score", $table).eq(i).val() == "" ){
				calcuateFlag = false;
			} else {
				score += parseFloat($(".cal_score", $table).eq(i).val());
			}
		}
		if(calcuateFlag){
			$("[name*='totalScore']", $table).eq(0).attr('value', score);
			var $grade = $("[name*='reviewGrade']", $table).get(0);
			if (score > 95) {
				$grade.value = "优秀";
			} else if (score >= 80 && score <= 95) {
				$grade.value = "合格";
			} else if (score < 80) {
				$grade.value = "不合格";
			}
			showResult();
		}
	};
	var submitOrNotReview = function(data, type, layer){//提交或保存专家评审信息
		if(!$("#award_review").valid()){
			return;
		}
		if(data==3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return ;
		}
		var mainIfm = parent.document.getElementById("main");
		var dis = {
			"meaningScore":$("[name='meaningScore']:eq(0)").val(),
			"innovationScore":$("[name='innovationScore']:eq(0)").val(),
			"influenceScore":$("[name='influenceScore']:eq(0)").val(),
			"methodScore":$("[name='methodScore']:eq(0)").val(),
			"auditStatus":data,
			"awardGradeid":$("#gradeid").val(),
			"auditOpinion":$("[name='auditOpinion']:eq(0)").val()
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	var submitOrNotGroupReview = function(data, type, layer){//提交或保存小组评审信息
		if(!$("#award_audit").valid()){
			return;
		}
		if(data == 3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return ;
		}
		var mainIfm = parent.document.getElementById("main");
		var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
		var auditOpinion = document.getElementsByName('auditOpinion')[0].value;
		var auditOpinionFeedback = document.getElementsByName('auditOpinionFeedback')[0].value;
		var reviewWay = document.getElementById('reviewWay').value;
		var awardGradeid = document.getElementById('awardGradeid').value;
		var dis = {
			"auditResult":auditResult,
			"auditStatus":data,
			"auditOpinion":auditOpinion,
			"auditOpinionFeedback":auditOpinionFeedback,
			"reviewWay":reviewWay,
			"awardGradeid":awardGradeid
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	
//	(function(){
//		if($("input[name='auditResult'][type='radio']:checked").val() == 2){
//			$("#award_grade_info").show();
//			$("#awardGradeid").rules("add",{selected:true});
//		}else{
//			$("#award_grade_info").hide();
//			$("#awardGradeid").rules("remove");
//		}
//	})();
	
	//添加专家评审
	var submitOrNotAddReview = function(data, layer){
		submitOrNotReview(data, 1, layer);
	};
	//修改专家评审
	var submitOrNotModifyReview = function(data, layer){
		submitOrNotReview(data, 2, layer);
	};
	//添加小组评审
	var submitOrNotAddGroupReview = function(data, layer){
		submitOrNotGroupReview(data, 1, layer);
	};
	//修改小组评审
	var submitOrNotModifyGroupReview = function(data, layer){
		submitOrNotGroupReview(data, 2, layer);
	};
	
	exports.initClick = function() {
		window.initReview = function(){initReview()};
		window.submitOrNotGroupReview = function(data, type, layer){submitOrNotGroupReview(data, type, layer)};
		window.submitOrNotReview = function(data, type, layer){submitOrNotReview(data, type, layer)};
		window.doSubmitReview = function(dis, type){viewMoesocial.doSubmitReview(dis, type)};
		window.doSubmitGroupReview = function(dis, type){viewMoesocial.doSubmitGroupReview(dis, type)};
	};
	exports.init = function(){
		$(function() {
			
			$("#view_all_review").live("click",function(){//查看小组总评详情
				popAwardOperation({
					title : "本组专家所有意见",
					src : "award/moesocial/application/review/viewGroupOpinion.action?entityId="+$("#entityId").val()
				});
				return false;
			});
	//		//判断是否填写获奖等级
	//		$(".pass_or_not").live("change",function(){
	//			if($(this).val() == 2){//同意
	//				$("#award_grade_info").show();
	//				$("#awardGradeid").rules("add",{selected:true});
	//			}else{
	//				$("#award_grade_info").hide();
	//				$("#awardGradeid").rules("remove");
	//			}
	//		});
			$("#add_review").live("click", function(){//专家评审
				popAwardOperation({
					title : "专家评审",
					src : "award/moesocial/application/review/toAdd.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitReview(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			$(".view_review").live("click", function(){//查看专家评审详情
				popAwardOperation({
					title : "查看专家评审详情",
					src : "award/moesocial/application/review/viewReview.action?entityId="+this.id
				});
				return false;
			});
			$("#modify_review").live("click", function(){//修改专家评审
				popAwardOperation({
					title : "修改专家评审",
					src : "award/moesocial/application/review/toModify.action?entityId="+this.alt,
					callBack : function(dis, type){
						viewMoesocial.doSubmitReview(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//提交专家评审
			$("#submit_review").live("click", function() {
				if (confirm("提交后不能修改，您确定要提交吗？")) {
					viewMoesocial.ajaxSubmitform("award/moesocial/application/review/submit.action?entityId=" + this.alt, viewApplication.showDetails);
				}
				return false;
			});
			$("#add_group_review").live("click", function(){//小组总评
				popAwardOperation({
					title : "小组总评",
					src : "award/moesocial/application/review/toAddGroup.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitGroupReview(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			$("#view_group_review").live("click", function(){//查看小组总评详情
				popAwardOperation({
					title : "小组总评详细信息",
					src : "award/moesocial/application/review/viewGroup.action?entityId="+$("#entityId").val()
				});
				return false;
			});
			$("#modify_group_review").live("click", function(){//修改小组总评
				popAwardOperation({
					title : "修改小组总评",
					src : "award/moesocial/application/review/toModifyGroup.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitGroupReview(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//提交小组总评
			$("#submit_group_review").live("click", function() {
				if (confirm("提交后不能修改，您确定要提交吗？")) {
					viewMoesocial.ajaxSubmitform("award/moesocial/application/review/submitGroup.action?entityId=" + $("#entityId").val(), viewApplication.showDetails);
				}
				return false;
			});
			
			$("#add_review_audit").live("click", function(){//评审结果审核
				popAwardOperation({
					title : "评审结果审核",
					src : "award/moesocial/application/reviewAudit/toAdd.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitAudit(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			$("#view_review_audit").live("click", function(){//查看评审结果审核详情
				popAwardOperation({
					title : "评审结果审核详细信息",
					src : "award/moesocial/application/reviewAudit/view.action?entityId="+$("#entityId").val()
				});	
				return false;
			});
			$("#modify_review_audit").live("click", function(){//修改评审结果审核
				popAwardOperation({
					title : "修改评审结果审核",
					src : "award/moesocial/application/reviewAudit/toModify.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitAudit(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//提交评审结果审核
			$("#submit_review_audit").live("click", function() {
				if (confirm("提交后不能修改，您确定要提交吗？")){
					viewMoesocial.ajaxSubmitform("award/moesocial/application/reviewAudit/submit.action?entityId="+$("#entityId").val(), viewApplication.showDetails);
				}
				return false;
			});
			
			//以下是改写的onclick事件部分
			$(".j_getResult").live("click", function() {
				getResult();
			});
			
			$("input[name='auditResult'][type='radio']").click(function(){
				var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
				showOpinionFeedbackOrNot(auditResult);
			}); 

			$(".j_saveAddGroupReview").live("click", function() {
				submitOrNotAddGroupReview(2, thisPopLayer);
				alert(submitOrNotAddGroupReview);
			});
			
			$(".j_submitAddGroupReview").live("click", function() {
				submitOrNotAddGroupReview(3, thisPopLayer);
				alert(submitOrNotAddGroupReview);
			});
			
			$(".j_saveModifyGroupReview").live("click", function() {
				submitOrNotModifyGroupReview(2, thisPopLayer);
			});
			
			$(".j_submitModifyGroupReview").live("click", function() {
				submitOrNotModifyGroupReview(3, thisPopLayer);
			});
			
			$(".j_saveAddReview").live("click", function() {
				submitOrNotAddReview(2, thisPopLayer);
			});
			
			$(".j_submitAddReview").live("click", function() {
				submitOrNotAddReview(3, thisPopLayer);
			});
			
			$(".j_saveModifyReview").live("click", function() {
				submitOrNotModifyReview(2, thisPopLayer);
			});
			
			$(".j_submitModifyReview").live("click", function() {
				submitOrNotModifyReview(3, thisPopLayer);
			});
			
			$(".j_addReviewResult").live("click", function() {
				var awardApplicationId = $(this).attr("awardApplicationId");
				addReviewResultPop(awardApplicationId);
			});
			
			$(".j_modifyReviewResul").live("click", function() {
				var awardApplicationId = $(this).attr("awardApplicationId");
				modifyReviewResultPop(awardApplicationId);
			});
			
			$(".j_expertReviewResul").live("click", function() {
				var awardApplicationId = $(this).attr("awardApplicationId");
				expertReviewResultPop(awardApplicationId);
			});
			
			$(".j_submitReviewResult").live("click", function() {
				var awardApplicationId = $(this).attr("awardApplicationId");
				submitReviewResult(awardApplicationId);
			});
			//计算详细分数
			$(".cal_score").live("change", function(){
				calcuateScore($(this).parent().parent().parent().parent());
			});
		});
	};
});