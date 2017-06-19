/**
 * 用于中检审核
 * @author 余潜玉、肖雅
 */
define(function(require, exports, module) {
	require('pop-init');
	require('form');
	require('validate');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var valid = function(){
		$(function() {
			$("#ann_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"annAuditResult":{required: true},
					"annAuditOpinion":{maxlength: 200},
					"annAuditOpinionFeedback":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var initAnnAudit = function(){
		var radioValue = $(".r_type:checked").val();
		if(radioValue == 1){//不同意时
			$("#opinion_feedback").show();
		}
	};
	
	//显示最终审核意见（反馈给项目负责人）
	var showOpinionFeedbackOrNot = function (data){
		if(data == 1){
			$("#opinion_feedback").show();
		}else{
			$("#opinion_feedback").hide();
		}
	};
	
	var addAnnAudit = function (data, layer){
		saveAnnAudit(data, 1, layer);
	};

	var modifyAnnAudit = function (data, layer){
		saveAnnAudit(data, 2, layer);
	};
	
	//填写审核弹出层的传值和提交函数
	var saveAnnAudit = function (data, type, layer) {
		if($("#annStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		if(!$("#ann_audit").valid()){
			return;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(data == 3) {
				if( !confirm('提交后无法修改，是否确认提交？'))
					return;
				
			};
			var auditOpinion = document.getElementsByName('annAuditOpinion')[0].value;
			var annAuditOpinionFeedback = document.getElementsByName('annAuditOpinionFeedback')[0].value;
			var auditResult = $("input[name='annAuditResult'][type='radio']:checked").val();
			var dis = {
				"type":type,
				"auditResult" : auditResult,
				"auditStatus" : data,
				"auditOpinion" : auditOpinion,
				"annAuditOpinionFeedback":annAuditOpinionFeedback
			};
			layer.callBack(dis);
			layer.destroy();
		}else{
			return false;
		}
	};
	exports.init = function() {
		$(".j_addSave").live('click',function(){//审核添加弹层页面暂存
			addAnnAudit(2, thisPopLayer);
		});
		$(".j_addSubmit").live('click',function(){//审核添加弹层页面提交
			addAnnAudit(3, thisPopLayer);
		});
		$(".j_modifySave").live('click',function(){//审核修改弹层页面暂存
			modifyAnnAudit(2, thisPopLayer);
		});
		$(".j_modifySubmit").live('click',function(){//审核修改弹层页面提交
			modifyAnnAudit(3, thisPopLayer);
		});
		$(".j_showOpinionFeedbackOrNot").live('click',function(){//审核修改弹层页面提交
			var data = $("input[name='annAuditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
//		window.addAnnAudit = function(data, layer){addAnnAudit(data, layer)};
//		window.modifyAnnAudit = function(data, layer){modifyAnnAudit(data, layer)};
//		window.showOpinionFeedbackOrNot = function(data){showOpinionFeedbackOrNot(data)};
//		window.initAnnAudit = function(){initAnnAudit()};
		initAnnAudit();
	};
	
	exports.valid = function(){
		valid();
	};
});



