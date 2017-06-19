define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	
	var editMidinspection = require('javascript/project/project_share/midinspection/edit');
	
	var projectType = "general";
	
	//初始化onclick事件
	exports.init = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
		
		$(function() {
			var midId = $("#midId").val();
			$("#file_" +　midId).uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
		
		$(".j_addSave").live('click',function(){//申请添加弹层页面暂存
			editMidinspection.addMidApply(2, thisPopLayer, projectType);
		});
		$(".j_addSubmit").live('click',function(){//申请添加弹层页面提交
			editMidinspection.addMidApply(3, thisPopLayer, projectType);
		});
		$(".j_modifySave").live('click',function(){//申请修改弹层页面暂存
			editMidinspection.modifyMidApply(2, thisPopLayer, projectType);
		});
		$(".j_modifySubmit").live('click',function(){//申请修改弹层页面提交
			editMidinspection.modifyMidApply(3, thisPopLayer, projectType);
		});
		$(".j_addResultSave").live('click',function(){//录入添加弹层页面暂存
			editMidinspection.addMidResult(2, 1, thisPopLayer);
		});
		$(".j_modifyResultSave").live('click',function(){//录入修改弹层页面暂存
			editMidinspection.addMidResult(2, 2, thisPopLayer);
		});
		$(".j_addResultSubmit").live('click',function(){//录入添加弹层页面提交
			editMidinspection.addMidResult(3, 1, thisPopLayer);
		});
		$(".j_modifyResultSubmit").live('click',function(){//录入修改弹层页面提交
			editMidinspection.addMidResult(3, 2, thisPopLayer);
		});
//		window.addMidApply = function(data, layer){editMidinspection.addMidApply(data, layer, projectType)};
//		window.modifyMidApply = function(data, layer){editMidinspection.modifyMidApply(data, layer, projectType)};
//		window.addMidResult = function(data, type, layer){editMidinspection.addMidResult(data, type, layer)};
		window.uploadFileResult = function(data, type, layer){editMidinspection.uploadFileResult(data, type, layer)};
		editMidinspection.datepickInit();
		
		$("#addMidFee").live("click",function() {
			popAddMidFee({
				title : "添加中检经费结算明细",
				src : "project/general/midinspection/apply/toAddMidFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
																			"&bookFee=" + $("#bookFee").val() + "&bookNote=" + $("#bookNote").val() + 
																			"&dataFee=" + $("#dataFee").val() + "&dataNote=" + $("#dataNote").val() +
																			"&travelFee=" + $("#travelFee").val() + "&travelNote=" + $("#travelNote").val() +
																			"&conferenceFee=" + $("#conferenceFee").val() + "&conferenceNote=" + $("#conferenceNote").val() +
																			"&internationalFee=" + $("#internationalFee").val() + "&internationalNote=" + $("#internationalNote").val() +
																			"&deviceFee=" + $("#deviceFee").val() + "&deviceNote=" + $("#deviceNote").val() +
																			"&consultationFee=" + $("#consultationFee").val() + "&consultationNote=" + $("#consultationNote").val() +
																			"&laborFee=" + $("#laborFee").val() + "&laborNote=" + $("#laborNote").val() +
																			"&printFee=" + $("#printFee").val() + "&printNote=" + $("#printNote").val() +
																			"&indirectFee=" + $("#indirectFee").val() + "&indirectNote=" + $("#indirectNote").val() +
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#projectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
				    $("#totalFee").html(result.totalFee);
					$("#feeNote").val(result.feeNote);
					$("#bookFee").val(result.bookFee);
					$("#bookNote").val(result.bookNote);
					$("#dataFee").val(result.dataFee);
					$("#dataNote").val(result.dataNote);
					$("#travelFee").val(result.travelFee);
					$("#travelNote").val(result.travelNote);
					$("#conferenceFee").val(result.conferenceFee);
					$("#conferenceNote").val(result.conferenceNote);
					$("#internationalFee").val(result.internationalFee);
					$("#internationalNote").val(result.internationalNote);
					$("#deviceFee").val(result.deviceFee);
					$("#deviceNote").val(result.deviceNote);
					$("#consultationFee").val(result.consultationFee);
					$("#consultationNote").val(result.consultationNote);
					$("#laborFee").val(result.laborFee);
					$("#laborNote").val(result.laborNote);
					$("#printFee").val(result.printFee);
					$("#printNote").val(result.printNote);
					$("#indirectFee").val(result.indirectFee);
					$("#indirectNote").val(result.indirectNote);
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
		$("#addMidFeeApply").live("click",function() {
			popAddMidFee({
				title : "添加中检经费结算明细",
				src : "project/general/midinspection/apply/toAddMidFee.action?projectid=" + $("#midFileProjectid").val() + "&feeNote=" + $("#feeNote").val() + 
																			"&bookFee=" + $("#bookFee").val() + "&bookNote=" + $("#bookNote").val() + 
																			"&dataFee=" + $("#dataFee").val() + "&dataNote=" + $("#dataNote").val() +
																			"&travelFee=" + $("#travelFee").val() + "&travelNote=" + $("#travelNote").val() +
																			"&conferenceFee=" + $("#conferenceFee").val() + "&conferenceNote=" + $("#conferenceNote").val() +
																			"&internationalFee=" + $("#internationalFee").val() + "&internationalNote=" + $("#internationalNote").val() +
																			"&deviceFee=" + $("#deviceFee").val() + "&deviceNote=" + $("#deviceNote").val() +
																			"&consultationFee=" + $("#consultationFee").val() + "&consultationNote=" + $("#consultationNote").val() +
																			"&laborFee=" + $("#laborFee").val() + "&laborNote=" + $("#laborNote").val() +
																			"&printFee=" + $("#printFee").val() + "&printNote=" + $("#printNote").val() +
																			"&indirectFee=" + $("#indirectFee").val() + "&indirectNote=" + $("#indirectNote").val() +
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#midFileProjectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
				    $("#totalFee").html(result.totalFee);
					$("#feeNote").val(result.feeNote);
					$("#bookFee").val(result.bookFee);
					$("#bookNote").val(result.bookNote);
					$("#dataFee").val(result.dataFee);
					$("#dataNote").val(result.dataNote);
					$("#travelFee").val(result.travelFee);
					$("#travelNote").val(result.travelNote);
					$("#conferenceFee").val(result.conferenceFee);
					$("#conferenceNote").val(result.conferenceNote);
					$("#internationalFee").val(result.internationalFee);
					$("#internationalNote").val(result.internationalNote);
					$("#deviceFee").val(result.deviceFee);
					$("#deviceNote").val(result.deviceNote);
					$("#consultationFee").val(result.consultationFee);
					$("#consultationNote").val(result.consultationNote);
					$("#laborFee").val(result.laborFee);
					$("#laborNote").val(result.laborNote);
					$("#printFee").val(result.printFee);
					$("#printNote").val(result.printNote);
					$("#indirectFee").val(result.indirectFee);
					$("#indirectNote").val(result.indirectNote);
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
		$("#modifyMidFee").live("click",function() {
			popAddMidFee({
				title : "修改中检经费结算明细",
				src : "project/general/midinspection/apply/toAddMidFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
																			"&bookFee=" + $("#bookFee").val() + "&bookNote=" + $("#bookNote").val() + 
																			"&dataFee=" + $("#dataFee").val() + "&dataNote=" + $("#dataNote").val() +
																			"&travelFee=" + $("#travelFee").val() + "&travelNote=" + $("#travelNote").val() +
																			"&conferenceFee=" + $("#conferenceFee").val() + "&conferenceNote=" + $("#conferenceNote").val() +
																			"&internationalFee=" + $("#internationalFee").val() + "&internationalNote=" + $("#internationalNote").val() +
																			"&deviceFee=" + $("#deviceFee").val() + "&deviceNote=" + $("#deviceNote").val() +
																			"&consultationFee=" + $("#consultationFee").val() + "&consultationNote=" + $("#consultationNote").val() +
																			"&laborFee=" + $("#laborFee").val() + "&laborNote=" + $("#laborNote").val() +
																			"&printFee=" + $("#printFee").val() + "&printNote=" + $("#printNote").val() +
																			"&indirectFee=" + $("#indirectFee").val() + "&indirectNote=" + $("#indirectNote").val() +
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#projectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
					$("#totalFee").html(result.totalFee);
					$("#feeNote").val(result.feeNote);
					$("#bookFee").val(result.bookFee);
					$("#bookNote").val(result.bookNote);
					$("#dataFee").val(result.dataFee);
					$("#dataNote").val(result.dataNote);
					$("#travelFee").val(result.travelFee);
					$("#travelNote").val(result.travelNote);
					$("#conferenceFee").val(result.conferenceFee);
					$("#conferenceNote").val(result.conferenceNote);
					$("#internationalFee").val(result.internationalFee);
					$("#internationalNote").val(result.internationalNote);
					$("#deviceFee").val(result.deviceFee);
					$("#deviceNote").val(result.deviceNote);
					$("#consultationFee").val(result.consultationFee);
					$("#consultationNote").val(result.consultationNote);
					$("#laborFee").val(result.laborFee);
					$("#laborNote").val(result.laborNote);
					$("#printFee").val(result.printFee);
					$("#printNote").val(result.printNote);
					$("#indirectFee").val(result.indirectFee);
					$("#indirectNote").val(result.indirectNote);
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
		$("#modifyMidFeeApply").live("click",function() {
			popAddMidFee({
				title : "修改中检经费结算明细",
				src : "project/general/midinspection/apply/toAddMidFee.action?projectid=" + $("#midFileProjectid").val() + "&feeNote=" + $("#feeNote").val() + 
																			"&bookFee=" + $("#bookFee").val() + "&bookNote=" + $("#bookNote").val() + 
																			"&dataFee=" + $("#dataFee").val() + "&dataNote=" + $("#dataNote").val() +
																			"&travelFee=" + $("#travelFee").val() + "&travelNote=" + $("#travelNote").val() +
																			"&conferenceFee=" + $("#conferenceFee").val() + "&conferenceNote=" + $("#conferenceNote").val() +
																			"&internationalFee=" + $("#internationalFee").val() + "&internationalNote=" + $("#internationalNote").val() +
																			"&deviceFee=" + $("#deviceFee").val() + "&deviceNote=" + $("#deviceNote").val() +
																			"&consultationFee=" + $("#consultationFee").val() + "&consultationNote=" + $("#consultationNote").val() +
																			"&laborFee=" + $("#laborFee").val() + "&laborNote=" + $("#laborNote").val() +
																			"&printFee=" + $("#printFee").val() + "&printNote=" + $("#printNote").val() +
																			"&indirectFee=" + $("#indirectFee").val() + "&indirectNote=" + $("#indirectNote").val() +
																			"&otherFeeD=" + $("#otherFeeD").val() + "&otherNote=" + $("#otherNote").val() +
																			"&totalFee=" + $("#totalFeeD").val() + "&surplusFee=" + $("#surplusFee").val(),
				inData : {"projectid" : $("#midFileProjectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "bookNote" : $("#bookNote").val(),
						  "dataFee" : $("#dataFee").val(),
						  "dataNote" : $("#dataNote").val(),
						  "travelFee" : $("#travelFee").val(),
						  "travelNote" : $("#travelNote").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "conferenceNote" : $("#conferenceNote").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "internationalNote" : $("#internationalNote").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "deviceNote" : $("#deviceNote").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "consultationNote" : $("#consultationNote").val(),
						  "laborFee" : $("#laborFee").val(),
						  "laborNote" : $("#laborNote").val(),
						  "printFee" : $("#printFee").val(),
						  "printNote" : $("#printNote").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "indirectNote" : $("#indirectNote").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "otherNote" : $("#otherNote").val(),
						  "totalFee" : $("#totalFeeD").val(),
						  "surplusFee" : $("#surplusFee").val()
						  },
				callBack : function(result){
					$("#totalFee").html(result.totalFee);
					$("#feeNote").val(result.feeNote);
					$("#bookFee").val(result.bookFee);
					$("#bookNote").val(result.bookNote);
					$("#dataFee").val(result.dataFee);
					$("#dataNote").val(result.dataNote);
					$("#travelFee").val(result.travelFee);
					$("#travelNote").val(result.travelNote);
					$("#conferenceFee").val(result.conferenceFee);
					$("#conferenceNote").val(result.conferenceNote);
					$("#internationalFee").val(result.internationalFee);
					$("#internationalNote").val(result.internationalNote);
					$("#deviceFee").val(result.deviceFee);
					$("#deviceNote").val(result.deviceNote);
					$("#consultationFee").val(result.consultationFee);
					$("#consultationNote").val(result.consultationNote);
					$("#laborFee").val(result.laborFee);
					$("#laborNote").val(result.laborNote);
					$("#printFee").val(result.printFee);
					$("#printNote").val(result.printNote);
					$("#indirectFee").val(result.indirectFee);
					$("#indirectNote").val(result.indirectNote);
					$("#otherFeeD").val(result.otherFeeD);
					$("#otherNote").val(result.otherNote);
					$("#totalFeeD").val(result.totalFee);
					$("#surplusFee").val(result.surplusFee);
					$("#fundedFee").val(result.fundedFee);
					this.destroy();
				}
			});
		});
		
	};
});