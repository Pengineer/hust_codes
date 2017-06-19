/**
 * 用于立项计划审核
 * @author yangfq
 */
define(function(require, exports, module) {
	require('pop-init');
	require('form');
	require('validate');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var valid = function(){
		$(function() {
			$("#gra_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"graAuditResult":{required: true},
					"graAuditOpinion":{maxlength: 200},
					"graAuditOpinionFeedback":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var initGraAudit = function(){
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
	
	var addGraAudit = function (data, layer){
		saveGraAudit(data, 1, layer);
	};

	var modifyGraAudit = function (data, layer){
		saveGraAudit(data, 2, layer);
	};
	
	//填写审核弹出层的传值和提交函数
	var saveGraAudit = function (data, type, layer) {
		if($("#graStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		if(!$("#gra_audit").valid()){
			return;
		}
		if(data == 3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return;
			
		};
		var auditOpinion = document.getElementsByName('graAuditOpinion')[0].value;
		var graAuditOpinionFeedback = document.getElementsByName('graAuditOpinionFeedback')[0].value;
		var auditResult = $("input[name='graAuditResult'][type='radio']:checked").val();
		var dis = {
			"type":type,
			"auditResult" : auditResult,
			"auditStatus" : data,
			"auditOpinion" : auditOpinion,
			"graAuditOpinionFeedback":graAuditOpinionFeedback
		};
		layer.callBack(dis);
		layer.destroy();
	};
	exports.init = function() {
		$(".j_addSave").live('click',function(){//审核添加弹层页面暂存
			addGraAudit(2, thisPopLayer);
		});
		$(".j_addSubmit").live('click',function(){//审核添加弹层页面提交
			addGraAudit(3, thisPopLayer);
		});
		$(".j_modifySave").live('click',function(){//审核修改弹层页面暂存
			modifyGraAudit(2, thisPopLayer);
		});
		$(".j_modifySubmit").live('click',function(){//审核修改弹层页面提交
			modifyGraAudit(3, thisPopLayer);
		});
		$(".j_showOpinionFeedbackOrNot").live('click',function(){//审核修改弹层页面提交
			var data = $("input[name='graAuditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
		initGraAudit();
	};
	
	exports.valid = function(){
		valid();
	};
});



