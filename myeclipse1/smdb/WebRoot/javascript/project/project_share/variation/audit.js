/**
 * 用于变更审核
 * @author 余潜玉、肖雅
 */
define(function(require, exports, module) {
	require('pop-init');
	require('form');
	require('validate');
	var datepick = require("datepick-init");
	
	var timeValidate = require('javascript/project/project_share/validate');
	
	var valid = function(){
		$(function() {
			$("#var_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"varAuditResult":{required: true},
					"varAuditOpinion":{maxlength: 200},
					"varAuditOpinionFeedback":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					var fieldName=element.attr("name");
					if(fieldName=="varSelectIssue"){
						error.appendTo( element.parent().parent().parent().parent().parent().next("td"));  
					}else{
						error.appendTo( element.parents("td").next("td") );
					}
				}
			});
		});
	};
	
	var initVarAudit = function(){
		var radioValue = $(".r_type:checked").val();
		if(radioValue == 1){//不同意时
			$("#opinion_feedback").show();
		}else if(radioValue == 2){//同意时
			$("#var_result_item_tr").show();
			$("input[name='varSelectIssue']").rules("add",{required: true});
		}
		var $table = ("#var_result_item_list");
		var defaultSelectItem = $("#varAuditSelectIssue").val();
		if (defaultSelectItem != undefined && defaultSelectItem != null && defaultSelectItem != "") {
			var defaultSelectItems = defaultSelectItem.split(",");
			for (var i = 0; i < defaultSelectItems.length; i++) {
				$("#" + defaultSelectItems[i], $table).attr("checked", "checked");
			}
		}
		checkAllOrNot(true, 'varSelectIssue', 'checkAllVarItem');
	};
	
	//显示最终审核意见（反馈给项目负责人）及可以同意的变更事项
	var showOpinionFeedbackOrNot = function(data){
		if(data == 1){//不同意
			$("#var_result_item_tr").hide();
			$("input[name='varSelectIssue']").rules("remove");
			$("#opinion_feedback").show();
		}else{//同意
			$("#var_result_item_tr").show();
			$("input[name='varSelectIssue']").rules("add",{required: true});
			$("#opinion_feedback").hide();
		}
	};
	
	var addVarAudit = function (data, layer){
		saveVarAudit(data, 1, layer);
	};

	var modifyVarAudit = function (data, layer){
		saveVarAudit(data, 2, layer);
	};
	
	//审核是否提交(包括院系，校级，省厅，部级审核)
	var saveVarAudit = function (data, type, layer) {
		if($("#varStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(!$("#var_audit").valid()){
				return;
			}
			if(data==3) {
				if( !confirm("提交后无法修改，是否确认提交"))
					return ;
			}
			var auditOpinion = document.getElementsByName('varAuditOpinion')[0].value;
			var varId = document.getElementsByName('varId')[0].value;
			var varAuditOpinionFeedback = document.getElementsByName('varAuditOpinionFeedback')[0].value;
			var auditResult = $("input[name='varAuditResult'][type='radio']:checked").val();
			var	$varAuditSelectIssues = $("input[name='varSelectIssue'][type='checkbox']:checked");
			var varAuditSelectIssue = "";
			var varDate = $("#startDate1").val();
			if($varAuditSelectIssues.length > 0){
				for(var i = 0; i < $varAuditSelectIssues.length; i++){
					var $selectIssue = $varAuditSelectIssues[i];
					varAuditSelectIssue = varAuditSelectIssue + $selectIssue.value + ",";
				}
				varAuditSelectIssue = varAuditSelectIssue.substring(0, varAuditSelectIssue.length - 1);
			}
			var mainIfm = parent.document.getElementById("main");
			var dis = {
				"type":type,
				"auditResult":auditResult,
				"auditStatus":data,
				"auditOpinion":auditOpinion,
				"varAuditOpinionFeedback":varAuditOpinionFeedback,
				"varAuditSelectIssue":varAuditSelectIssue,
				"varId":varId,
				"varDate":varDate,
			};
			layer.callBack(dis);
			layer.destroy();
		}else{
			return false;
		}
	};
	exports.init = function() {
		datepick.init();// 初始化日期选择器
		$(".j_showOpinionFeedbackOrNot").live('click',function(){
			var data = $("input[name='varAuditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
		$(".j_addVarAudit").live('click',function(){
			var data = $(this).attr("data");
			addVarAudit(data, thisPopLayer);
		});
		$(".j_modifyVarAudit").live('click',function(){
			var data = $(this).attr("data");
			addVarAudit(data, thisPopLayer);
		});
		initVarAudit();
		
//		window.addVarAudit = function(data, layer){addVarAudit(data, layer)};
//		window.modifyVarAudit = function(data, layer){modifyVarAudit(data, layer)};
//		window.initVarAudit = function(){initVarAudit()};
//		window.showOpinionFeedbackOrNot = function(data){showOpinionFeedbackOrNot(data)};
	};
	
	exports.valid = function(){
		valid();
	};
});



