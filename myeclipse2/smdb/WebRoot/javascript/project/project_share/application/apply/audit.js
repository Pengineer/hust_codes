/**
 * 用于项目申报审核
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('pop-init');
	require('form');
	require('validate');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var valid = function(){
		$(function() {
			$("#app_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"appAuditResult":{required: true},
					"appAuditOpinion":{maxlength: 200},
				 	"appAuditOpinionFeedback":{maxlength: 200}
					},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var initAppAudit = function(){
		var radioValue = $(".r_type:checked").val();
		if(radioValue == 1){//不同意时
			$("#opinion_feedback").show();
		}
	};
	
	//显示最终审核意见（反馈给项目负责人）
	var showOpinionFeedbackOrNot = function(data){
		if(data == 1){
			$("#opinion_feedback").show();
		}else{
			$("#opinion_feedback").hide();
		}
	};
	
	var addAppAudit = function (data, layer){//审核添加弹层页面操作 ，data为2表示暂存，3表示提交
		saveAppAudit(data, 1, layer);//1表示添加
	};

	var modifyAppAudit = function (data, layer){//审核修改弹层页面操作，data为2表示暂存，3表示提交
		saveAppAudit(data, 2, layer);//2表示修改
	};
	
	//填写审核弹出层的传值和提交函数
	var saveAppAudit = function (data, type, layer) {
		if($("#appStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(!$("#app_audit").valid()){
				return;
			}
			var appAuditOpinion = $("[name='appAuditOpinion']:eq(0)").val();
			var appAuditOpinionFeedback = $("[name='appAuditOpinionFeedback']:eq(0)").val();
			var appAuditResult = $("input[name='appAuditResult'][type='radio']:checked").val();
			var dis ={
				type: type,
				appAuditResult: appAuditResult,
				appAuditStatus: data,
				appAuditOpinion: appAuditOpinion,
				appAuditOpinionFeedback: appAuditOpinionFeedback
			}
			
			layer.callBack(dis);
			layer.destroy();
		}else{
			return false;
		}
	};
	exports.init = function() {
		$(".j_addSave").live('click',function(){//审核添加弹层页面暂存
			addAppAudit(2, thisPopLayer);
		});
		$(".j_addSubmit").live('click',function(){//审核添加弹层页面提交
			addAppAudit(3, thisPopLayer);
		});
		$(".j_modifySave").live('click',function(){//审核修改弹层页面暂存
			modifyAppAudit(2, thisPopLayer);
		});
		$(".j_modifySubmit").live('click',function(){//审核修改弹层页面提交
			modifyAppAudit(3, thisPopLayer);
		});
		$(".j_showOpinionFeedbackOrNot").live('click',function(){//审核修改弹层页面提交
			var data = $("input[name='appAuditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
		initAppAudit();
//		window.addAppAudit = function(data, layer){addAppAudit(data, layer)};
//		window.modifyAppAudit = function(data, layer){modifyAppAudit(data, layer)};
//		window.initAppAudit = function(){initAppAudit()};
//		window.showOpinionFeedbackOrNot = function(data){showOpinionFeedbackOrNot(data)};
	};
	
	exports.valid = function(){
		valid();
	};
});



