/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var timeValidate = require('javascript/project/project_share/validate');
	var viewProject = require('javascript/project/project_share/view');
	var viewKey = require('javascript/project/key/view');
	
	
	//查看选题
	var readTops = function() {
		viewKey.viewKey("project/key/topicSelection/apply/viewTopic.action");
	};
	
	//准备修改选题申请
	var toModifyTopsApply = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			var topsId = $("#topsId").val();
			window.location.href = basePath + "project/key/topicSelection/apply/toModify.action?topsId="+topsId;
			return false;
		}
	};
	
	//提交选题申请
	var submitTopsApply = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			if( !confirm('提交后无法修改，是否确认提交？')){
				return;
			}
			$.ajax({
				url: "project/key/topicSelection/apply/submit.action",
				type: "post",
				data: "topsId=" + $("#topsId").val() + "&appFlag=" + $("#appFlag").val(),
				dataType: "json",
				success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						readTops();
					}
					else {
						alert(result.errorInfo);
					}
				}	
			});
		}
	};
	
	//删除选题申请
	var deleteTopsApply = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			if(confirm('确认删除此选题申请？')) {
				var info=[{
						"url": "project/key/topicSelection/apply/delete.action?topsIds="+ $("#topsId").val(),
						"successUrl": "project/key/topicSelection/apply/toList.action?listType=12&update=1"
					}];
				$("#view_key").attr("action", info[0]["url"]);
				$("#view_key").ajaxSubmit({
					success:function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							window.location.href = basePath + info[0]["successUrl"];
							return false;
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
			return false;
		}
	};
	
	//添加选题审核
	var addTopsAuditPop = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var accountType = $("#accountType").val();
		var flag = timeValidate.timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			var id = document.getElementsByName('topsId')[0].value;
			popProjectOperation({
				title : "添加审核意见",
				src : "project/key/topicSelection/applyAudit/toAdd.action?topsId=" + id,
				callBack : function(dis){
					doSubmitTopsAudit(dis);
				}
			});
		}else{
			return false;
		}
	};
	
	//修改选题审核
	var modifyTopsAuditPop = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			var accountType = $("#accountType").val();
			var id = document.getElementsByName('topsId')[0].value;
			popProjectOperation({
				title : "修改审核意见",
				src : "project/key/topicSelection/applyAudit/toModify.action?topsId=" + id,
				callBack : function(dis){
					doSubmitTopsAudit(dis);
				}
			});
		}else{
			return false;
		}
	};
	
	//查看选题审核详情
	var viewTopsAuditPop = function(id, data) {
		popProjectOperation({
			title : "查看审核详细意见",
			src : 'project/key/topicSelection/applyAudit/view.action?topsId=' + id + '&vtp=' + data
		});
	};
	
	//提交审核
	var submitTopsAudit = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			if( !confirm('确认提交此审核意见？'))
				return;
			$("#topsForm").ajaxSubmit({ 
				url: "project/key/topicSelection/applyAudit/submit.action",
		       	success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						readTops();
					}
					else {
						alert(result.errorInfo);
					}
				}	 
			});
		}else{
			return false;
		}
	};
	
	//退回选题
	var backTopsApply = function() {
		if($("#topsStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate($("#deadlineTops", $("#topicSelection")).val());
		if(flag){
			if( !confirm('确认退回此选题申请？'))
				return;
			$("#topsForm").ajaxSubmit({ 
				url: "project/key/topicSelection/applyAudit/back.action",
		        success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						readTops();
					}
					else {
						alert(result.errorInfo);
					}
				}	  
			});
		}else{
			return false;
		}
	};
	
	//准备录入选题结果信息
	var addTopsResultPop = function(){
		popProjectOperation({
			title : "录入选题结果信息",
			src : "project/key/topicSelection/apply/toAddResult.action",
			callBack : function(dis){
				doSubmitTopsResult(dis);
			}
		});
		return false;
	};
	
	//准备修改选题结果信息
	var modifyTopsResultPop = function(){
		popProjectOperation({
			title : "修改选题结果信息",
			src : 'project/key/topicSelection/apply/toModifyResult.action?topsId=' + $("#topsId").val(),
			callBack : function(dis){
				doSubmitTopsResult(dis);
			}
		});
		return false;
	};

	//提交申请结果
	var submitTopsResult = function(){
		if( !confirm('提交后无法修改，是否确认提交？')){
			return;
		}
		$.ajax({
			url: "project/key/topicSelection/apply/submitResult.action",
			type: "post",
			data: "topsId=" + $("#topsId").val(),
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readTops();
				}
				else {
					alert(result.errorInfo);
				}
			}	
		});
	};

	//ajax提交选题的审核意见
	var doSubmitTopsAudit = function(data) {
		var xx = document.getElementsByName('topsAuditOpinion')[0];
		var yy = document.getElementsByName('topsAuditStatus')[0];
		var yyy = document.getElementsByName('topsAuditResult')[0];
		var tmp = document.getElementById('topsId');
		xx.value = data.auditOpinion;
		yy.value = data.auditStatus;
		yyy.value = data.auditResult;
		var type = data.type;
		var url = "";
		if(type == 1){
			url = "project/key/topicSelection/applyAudit/add.action";
		}else if(type == 2){
			url = "project/key/topicSelection/applyAudit/modify.action";
		}
		$("#topsForm").ajaxSubmit({ 
			url: url,
	      	success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readTops();
				}
				else {
					alert(result.errorInfo);
				}
			}	 
		});
	};
	
	//向后台添加或修改选题结果
	var doSubmitTopsResult = function(dis){
		url = "";
		if(dis.type == 1){
			url = "project/key/topicSelection/apply/addResult.action";
		}else if(dis.type == 2){
			url = "project/key/topicSelection/apply/modifyResult.action";
		}
		$.ajax({
			url: url,
			type: "post",
			data: "topsId=" + $("#topsId").val() + "&topsResult=" + dis.topsResult + "&topsImportedStatus=" + dis.topsStatus + "&topsDate=" + dis.topsDate + "&topsImportedOpinion=" + dis.topsImportedOpinion,
			dataType: "json",
			success:  function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readTops();
				}
				else {
					alert(result.errorInfo);
				}
			}	 
		});
	};
	
	//初始化onclick事件
	exports.initClick = function() {
		window.toModifyTopsApply = function(){toModifyTopsApply()};
		window.submitTopsApply = function(){submitTopsApply()};
		window.deleteTopsApply = function(){deleteTopsApply()};
		window.addTopsAuditPop = function(){addTopsAuditPop()};
		window.modifyTopsAuditPop = function(){modifyTopsAuditPop()};
		window.viewTopsAuditPop = function(id, data){viewTopsAuditPop(id, data)};
		window.submitTopsAudit = function(){submitTopsAudit()};
		window.backTopsApply = function(){backTopsApply()};
		window.addTopsResultPop = function(){addTopsResultPop()};
		window.modifyTopsResultPop = function(){modifyTopsResultPop()};
		window.submitTopsResult = function(){submitTopsResult()};
	};
	
});
