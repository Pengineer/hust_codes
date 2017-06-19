/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	var datepick = require("datepick-init");
	var viewGranted = require("javascript/project/project_share/view");
	var viewInstp = require('javascript/project/instp/view');
	var viewAppInstp = require("javascript/project/project_share/application/granted/view");
	var projectType="instp";
	
	var valid = function(){
		$("#granted_revoke").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"projectStatus":{selected:true},
				"endStopWithdrawDate":{dateISO:true, required: true},
				"projectOpinion":{required: true, maxlength: 200},
				"projectOpinionFeedback":{maxlength: 200}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			}
		});
	};
	
	//撤项
	var setUpProjectStatusPop = function(projectType, showProjectDetails) {
		var projectid = $("#projectid").val();
		popProjectOperation({
			title : "设置项目状态",
			src : 'project/instp/application/granted/toSetUpProjectStatus.action?projectid='+$("#projectid").val(),
			inData : {"varPending" : $("#varPending").val(),"midPending" : $("#midPending").val()},
			callBack : function(dis){
				$("#update").val(1);
				$.ajax({
					url: "project/instp/application/granted/setUpProjectStatus.action",
					type: "post",
					data: "projectStatus=" + dis.projectStatus + "&projectOpinion=" + dis.projectOpinion + "&projectOpinionFeedback=" + dis.projectOpinionFeedback + "&endStopWithdrawDate=" + dis.endStopWithdrawDate + "&projectid=" + $("#projectid").val(),
					dataType: "json",
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							$("#update").val(1);
							viewGranted.viewDeltails(projectType, showProjectDetails);
						}
						else {
							alert(result.errorInfo);
						}
					}	
				});
			}
		});
	};
	
	var setUpProjectStatus = function(layer){
		if(layer.inData.midPending != 0){
			alert("中检正在处理中，请先处理中检");
			return;
		}else if(layer.inData.varPending != 0){
			alert("变更正在处理中，请先处理变更");
			return;
		}
		if(!$("#granted_revoke").valid()){//用户输入校验
			return;
		}else{
			if(!confirm("此操作一旦提交无法修改，请慎重选择。您确定要提交么？")){
				return;
			}
			var projectStatus = $("[name='projectStatus']").val();
			var endStopWithdrawDate = $("[name='endStopWithdrawDate']").val();
			var projectOpinion = $("[name='projectOpinion']").val();
			var projectOpinionFeedback = $("[name='projectOpinionFeedback']").val();
			var dis={
				"projectStatus":projectStatus,
				"endStopWithdrawDate":endStopWithdrawDate,
				"projectOpinion": projectOpinion,
				"projectOpinionFeedback":projectOpinionFeedback
			};
			layer.callBack(dis);
			layer.destroy();
		}
	};
	
	exports.init = function() {
		valid();
		datepick.init();
		$(".j_setUpProjectStatusPop").live('click',function(){//进入项目状态设置弹层页面
			setUpProjectStatusPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_setUpProjectStatus").live('click',function(){//项目状态设置弹层页面设置项目状态
			setUpProjectStatus(thisPopLayer);
		});
		
		$(".j_toAddGraApplyPop").live('click',function(){//申请添加
			viewAppInstp.toAddGraPlanPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_toModifyGraApplyPop").live('click',function(){//申请修改
			viewAppInstp.toModifyMidApplyPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitGraApply").live('click',function(){//申请提交
			viewAppInstp.submitGraApply(projectType, viewInstp.showProjectDetails);
		});
		$(".j_deleteGraApply").live('click',function(){//申请删除
			viewAppInstp.deleteGraPlanPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_addGraAuditPop").live('click',function(){//添加审核
			viewAppInstp.addGraAuditPop(projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_viewGraAuditPop").live('click',function(){//审核查看
			var id = $(this).attr("grantedId");
			var data = $(this).attr("alt");
			viewAppInstp.viewGraAuditPop(id, data, projectType);
		});
		$(".j_modifyGraAuditPop").live('click',function(){//审核修改
			viewAppInstp.modifyGraAuditPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_submitGraAudit").live('click',function(){//审核提交
			viewAppInstp.submitGraAudit(projectType, viewInstp.showProjectDetails)
		});
		$(".j_backGraApply").live('click',function(){//审核退回
			viewAppInstp.backGraApply(projectType, viewInstp.showProjectDetails)
		});
		
		$(".j_modifyGraResultPop").live('click',function(){//录入修改
			viewAppInstp.modifyGraResultPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_addGraResultPop").live('click',function(){//录入添加
			viewAppInstp.addGraResultPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitGraResult").live('click',function(){//录入提交
			viewAppInstp.submitGraResult(projectType, viewInstp.showProjectDetails);
		});

//		window.setUpProjectStatusPop = function(){setUpProjectStatusPop(projectType, viewInstp.showProjectDetails)};
//		window.setUpProjectStatus = function(layer){setUpProjectStatus(layer)};
	};
	
});