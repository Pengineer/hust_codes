define(function(require, exports, module) {
	var view = require('javascript/view');
	var viewMoesocial = require('javascript/award/moesocial/view');
	var viewApplication = require('javascript/award/moesocial/application/view');
	
	var listflag = "2";
	var nameSpace = "award/moesocial/application/publicity";
	
	//提交奖励获奖审核
	function submitAwardedAudit(result, number){
		if(result == 2 && (number == "" || number == null)){
			alert("您还没有填写证书编号！请先点击修改填写证书编号再提交！");
			return  false;
		}
		if (confirm("提交后不能修改，您确定要提交吗？")) 
			viewMoesocial.ajaxSubmitform("award/moesocial/application/publicityAudit/submit.action?entityId=" + $("#entityId").val(), viewApplication.showDetails);
		return false;
	}
	
	exports.init = function(){
		$(function() {
			//奖励审核
			$("#add_awarded_audit").live("click", function(){
				popAwardOperation({
					title : "奖励审核",
					src : "award/moesocial/application/publicityAudit/toAdd.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitAwardedAudit(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//查看奖励审核详情
			$("#view_awarded_audit").live("click", function(){
				popAwardOperation({
					title : "奖励审核详细信息",
					src : "award/moesocial/application/publicityAudit/view.action?entityId="+$("#entityId").val()
				});
				return false;
			});
			//修改奖励审核
			$("#modify_awarded_audit").live("click", function(){
				popAwardOperation({
					title : "修改奖励审核",
					src : "award/moesocial/application/publicityAudit/toModify.action?entityId="+$("#entityId").val(),
					callBack : function(dis, type){
						viewMoesocial.doSubmitAwardedAudit(dis, type, viewApplication.showDetails);
					}
				});
				return false;
			});
			//提交获奖审核结果
			$("#submit_awarded_audit").live("click", function() {
				var finalAuditResult = $(this).attr("awardApplication.finalAuditResult");
				var number = $(this).attr("awardApplication.number");
				submitAwardedAudit(finalAuditResult, number);
			});
		});
	};
});