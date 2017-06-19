/**
 * 用于选题审核
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('pop-init');
	require('form');
	require('validate');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var valid = function(){
		$(function() {
			$("#tops_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"topsAuditResult":{required: true},
					"topsAuditOpinion":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var addTopsAudit = function(data, layer){
		saveTopsAudit(data, 1, layer);
	};

	var modifyTopsAudit = function(data, layer){
		saveTopsAudit(data, 2, layer);
	};
	
	//填写审核弹出层的传值和提交函数
	var saveTopsAudit = function(data, type, layer) {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		if(!$("#tops_audit").valid()){
			return;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(data == 3) {
				if( !confirm('提交后无法修改，是否确认提交？'))
					return;
				
			};
			var auditOpinion = document.getElementsByName('topsAuditOpinion')[0].value;
			var auditResult = $("input[name='topsAuditResult'][type='radio']:checked").val();
			var dis = {
				"type":type,
				"auditResult" : auditResult,
				"auditStatus" : data,
				"auditOpinion" : auditOpinion,
			};
			layer.callBack(dis);
			layer.destroy();
		}else{
			return false;
		}
	};
	
	exports.init = function() {
		window.addTopsAudit = function(data, layer){addTopsAudit(data, layer)};
		window.modifyTopsAudit = function(data, layer){modifyTopsAudit(data, layer)};
	};
	
	exports.valid = function(){
		valid();
	};
	
});
