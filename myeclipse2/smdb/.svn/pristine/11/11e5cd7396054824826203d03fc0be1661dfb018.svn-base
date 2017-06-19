/**
 * 用于公示审核
 * @author 余潜玉
 */
define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	var datepick = require("datepick-init");
	var validateApplication = require('javascript/award/moesocial/application/validate');
	require('validate');
	require('cookie');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	require('pop-init');
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	var loadAwardYear = function(){//获奖年份下拉框
		var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
		var audflag = document.getElementById('audflag').value;
		if (auditResult == 2) {
			$('#div_awarded').show();
			$("#year").rules("add", {selected: true});
			if (audflag == 1) {
				$("#awardAuditSubmit").hide();
			}else {
				$("#number").rules("remove");
				$("#awardAuditSubmit").show();
			}
		}else{
			$('#div_awarded').hide();
			$("#year").rules("remove");
			$("#number").rules("remove");
			$("#awardAuditSubmit").show();
		}
	};
	
	
	var loadNumber = function(appflag){//是否填写获奖年度和证书编号
		var audflag = document.getElementById('audflag').value;
		if (appflag == 2) {
			//公示通过显示填写获奖年度
			$("#div_awarded").show();
			$("#year").rules("add", {selected:true});
			if (audflag == 1) {
				$("#awardAuditSubmit").hide();
			}else{
				$("#awardAuditSubmit").show();
				$("#number").rules("add", {required:true, maxlength: 40});
			}
		}
		else{//公示不通过
			$("#number").attr("value", "");
			$("#year").attr("value","");
			$("#div_awarded").hide();
			$("#year").rules("remove");
			$("#awardAuditSubmit").show();
			$("#number").rules("remove");
			
		}
	};
	var isYearMax = true;
	var isNumerUnique = true;

	var valid_year = function(){//校验获奖年度
		var entityId = $("[name='entityId']").eq(0).val();
		DWREngine.setAsync(false);
		awardService.getApplyYear(entityId, yearMaxCallBack);
		DWREngine.setAsync(true);
	};
	var yearMaxCallBack = function(data){//获奖年度是否低于申请年度
		var maxYear = data;
		var $year = $("[name='year']").eq(0);
		var year = $year.val();
		var $errorTd = $year.parent("td").next("td");
		if (year < maxYear) {
			$errorTd.html('<span class="error">获奖年度不能低于申请年度</span>');// 写错误信息
			isYearMax = false;
		}else {
			$errorTd.html('');// 清错误信息
			isYearMax = true;
		}
	};
	var valid_number = function(){//校验奖励证书编号
		var entityId = $("#entityId").val();
		var number = $("#number").val();
		DWREngine.setAsync(false);
		awardService.isNumberUnique(entityId, number, isNumberUniqueCallback);
		DWREngine.setAsync(true);
	};
	var isNumberUniqueCallback = function(data){//证书编号是否唯一
		var $number = $("#number");
		var $errorTd = $number.parent("td").next("td");
		if(!data){
			$errorTd.html('<span class="error">该证书编号已存在</span>');// 写错误信息
			isNumerUnique = false;
		}else{
			$errorTd.html('');// 清错误信息
				isNumerUnique = true;;
		}
	};
	var submitOrNotAwardedAudit = function(data, layer){//是否提交公示审核
//		if(!validateApplication.awardAudit()){
//			return;
//		}
		document.getElementsByName('auditStatus')[0].value = data;
		var audflag = document.getElementById('audflag').value;
		var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
		if (auditResult == 2) {
			valid_year();
			if (audflag == 0) {
				valid_number();
			}
		}
		if(!(isYearMax && isNumerUnique)){
			return;
		}
		var entityId = document.getElementById('entityId').value;
		submitAwardedAudit(layer);
	};
	var submitAwardedAudit = function(layer){//提交公示审核请求
		var mainIfm = parent.document.getElementById("main");
		var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
		var year = document.getElementById('year').value;
		var auditOpinion = document.getElementsByName('auditOpinion')[0].value;
		var auditOpinionFeedback = document.getElementsByName('auditOpinionFeedback')[0].value;
		var auditStatus = document.getElementsByName('auditStatus')[0].value;
		var audflag = document.getElementById('audflag').value;
		var type = document.getElementsByName('type')[0].value;
		if (auditStatus == 3) {
			if (!confirm('提交后无法修改，是否确认提交？')) 
				return;
		}
		var dis = {
			"auditResult": auditResult,
			"year": year,
			"auditOpinion": auditOpinion,
			"auditOpinionFeedback": auditOpinionFeedback,
			"auditStatus": auditStatus
		};
		if (audflag == 0) {
			dis.number = document.getElementsByName('number')[0].value;
		}else {
			dis.number = ""
		}
		layer.callBack(dis, type);
		layer.destroy();
	};

	exports.init = function() {
		datepick.init();
		validateApplication.awardAudit();
		loadAwardYear();
		
		//获奖编号
		$("input[name='auditResult'][type='radio']").click(function(){
			var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
			loadNumber(auditResult);
		});
		$("#awardAuditSave").click(function(){
			submitOrNotAwardedAudit(2, thisPopLayer)
		});
		
		$("#awardAuditSubmit").click(function(){
			submitOrNotAwardedAudit(3, thisPopLayer)
		});
	};
});
