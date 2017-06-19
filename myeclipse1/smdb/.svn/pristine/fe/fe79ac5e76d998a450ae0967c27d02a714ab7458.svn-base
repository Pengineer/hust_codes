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
			$("#mid_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"midAuditResult":{required: true},
					"midAuditOpinion":{maxlength: 200},
					"midAuditOpinionFeedback":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var initMidAudit = function(){
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
	
	var addMidAudit = function (data, layer){
		saveMidAudit(data, 1, layer);
	};

	var modifyMidAudit = function (data, layer){
		saveMidAudit(data, 2, layer);
	};
	
	//填写审核弹出层的传值和提交函数
	var saveMidAudit = function (data, type, layer) {
		if($("#midStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		if(!$("#mid_audit").valid()){
			return;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(data == 3) {
				if( !confirm('提交后无法修改，是否确认提交？'))
					return;
				
			};
			var auditOpinion = document.getElementsByName('midAuditOpinion')[0].value;
			var midAuditOpinionFeedback = document.getElementsByName('midAuditOpinionFeedback')[0].value;
			var auditResult = $("input[name='midAuditResult'][type='radio']:checked").val();
			var dis = {
				"type":type,
				"auditResult" : auditResult,
				"auditStatus" : data,
				"auditOpinion" : auditOpinion,
				"midAuditOpinionFeedback":midAuditOpinionFeedback
			};
			layer.callBack(dis);
			layer.destroy();
		}else{
			return false;
		}
	};
	$("#auditOption").change(function(){
		var innerText = $("textarea[name='midAuditOpinionFeedback']").val();
		if($(":selected",this).text() != "自定义"){
			if(innerText){
				innerText += ("；"+ $("#auditOption option:checked").html());
			} else {
				innerText = $("#auditOption option:checked").html();
			}
			
		} else {
			if(innerText){
				innerText += "；";
			} 					
		}
		innerText = innerText.replace(/;/g,"；");//将英文";"换为中文"；"
		innerText = innerText.replace(/^；{1,}/g,"");//删除字符窜开头的"；"		
		innerText = innerText.replace(/；{1,}/g,"；");//删掉重复的"；"
		$("textarea[name='midAuditOpinionFeedback']").val(innerText.trim());

	});
	exports.init = function() {
		$(".j_addSave").live('click',function(){//审核添加弹层页面暂存
			addMidAudit(2, thisPopLayer);
		});
		$(".j_addSubmit").live('click',function(){//审核添加弹层页面提交
			addMidAudit(3, thisPopLayer);
		});
		$(".j_modifySave").live('click',function(){//审核修改弹层页面暂存
			modifyMidAudit(2, thisPopLayer);
		});
		$(".j_modifySubmit").live('click',function(){//审核修改弹层页面提交
			modifyMidAudit(3, thisPopLayer);
		});
		$(".j_showOpinionFeedbackOrNot").live('click',function(){//审核修改弹层页面提交
			var data = $("input[name='midAuditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
//		window.addMidAudit = function(data, layer){addMidAudit(data, layer)};
//		window.modifyMidAudit = function(data, layer){modifyMidAudit(data, layer)};
//		window.showOpinionFeedbackOrNot = function(data){showOpinionFeedbackOrNot(data)};
//		window.initMidAudit = function(){initMidAudit()};
		initMidAudit();
		
	};
	
	exports.valid = function(){
		valid();
	};
});



