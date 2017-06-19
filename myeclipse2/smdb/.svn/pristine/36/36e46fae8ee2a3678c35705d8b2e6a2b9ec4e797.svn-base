define(function(require, exports, module) {
	var viewMoesocial = require('javascript/award/moesocial/view');
	var viewApplication = require('javascript/award/moesocial/application/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	require('cookie');
	require('jquery-ui');
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	var listflag = "1";
	var nameSpace = "award/moesocial/application/apply";
	
	exports.init = function(){
		$(function() {
			//下载人文社科奖申请书
			$(".downlaod").live("click",function(){
				var validateUrl = "award/moesocial/application/apply/validateFile.action?entityId="+this.id+"&filePath="+this.name;
				var successUrl = "award/moesocial/application/apply/download.action?filePath="+this.name;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			//下载人文社科奖申请书模板
			$("#download_award_model").live("click",function(){
				window.location.href = basePath + 'award/moesocial/application/apply/downloadModel.action';
				return false;
			});
			//提交奖励申请
			$("#view_sub").live("click", function() {
				if (confirm("提交后不能修改，您确定要提交吗？")) {
					viewMoesocial.ajaxSubmitform("award/moesocial/application/apply/submit.action?entityId=" + $("#entityId").val(), viewApplication.showDetails);
				}
				return false;
			});
			//审核
			$("#audit").live("click", function() {//审核
				popAwardOperation({
					title : "审核",
					src : "award/moesocial/application/applyAudit/toAdd.action?entityId="+$("#entityId").val()+"&audflag=0",
					callBack : function(dis, type){
						viewMoesocial.doSubmitAudit(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//修改审核
			$("#modify_audit").live("click", function() {
				popAwardOperation({
					title : "修改审核",
					src : "award/moesocial/application/applyAudit/toModify.action?entityId="+$("#entityId").val()+"&audflag=0",
					callBack : function(dis,type){
						viewMoesocial.doSubmitAudit(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//提交审核 
			$("#submit_audit").live("click", function() {
				if (confirm("提交后不能修改，您确定要提交吗？")){
					viewMoesocial.ajaxSubmitform("award/moesocial/application/applyAudit/submit.action?entityId="+$("#entityId").val(), viewApplication.showDetails);
				}
				return false;
			});
			// 退回申请
			$("#back_audit").live("click", function() {
				if (confirm("您确定要退回这项奖励申请吗？")) {
					viewMoesocial.ajaxSubmitform("award/moesocial/application/applyAudit/back.action?entityId=" + $("#entityId").val(), viewApplication.showDetails);
				}
				return false;
			});
			//查看部门意见详情
			$("#view_dept_audit").live("click", function() {
				viewMoesocial.alertAudit("部门意见详情", 1);
				return false;
			});
			//查看高校意见详情
			$("#view_univ_audit").live("click", function() {
				viewMoesocial.alertAudit("高校详细意见", 2);
				return false;
			});
			//查看省厅意见详情
			$("#view_prov_audit").live("click", function() {
				viewMoesocial.alertAudit("省厅详细意见", 3);
				return false;
			});
			//查看教育部意见详情
			$("#view_mini_audit").live("click", function() {
				viewMoesocial.alertAudit("教育部详细意见", 4);
				return false;
			});
			
		});
	};
});
		