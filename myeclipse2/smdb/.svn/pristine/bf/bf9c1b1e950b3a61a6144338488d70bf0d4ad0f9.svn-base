define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	
	var editAnninspection = require('javascript/project/project_share/anninspection/edit');
	
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
		var annId = $("#annId").val();
		$("#file_" +　annId).uploadifyExt({
			uploadLimitExt : 1, //文件上传个数的限制
			fileSizeLimit : '300MB',//文件上传大小的限制
			fileTypeDesc : '附件',//可以不用管
			fileTypeExts : '*.doc; *.docx; *.pdf'
		});
		$(".j_addSave").live('click',function(){//申请添加弹层页面暂存
			editAnninspection.addAnnApply(2, thisPopLayer, projectType);
		});
		$(".j_addSubmit").live('click',function(){//申请添加弹层页面提交
			editAnninspection.addAnnApply(3, thisPopLayer, projectType);
		});
		$(".j_modifySave").live('click',function(){//申请修改弹层页面暂存
			editAnninspection.modifyAnnApply(2, thisPopLayer, projectType);
		});
		$(".j_modifySubmit").live('click',function(){//申请修改弹层页面提交
			editAnninspection.modifyAnnApply(3, thisPopLayer, projectType);
		});
		$(".j_addResultSave").live('click',function(){//录入添加弹层页面暂存
			editAnninspection.addAnnResult(2, 1, thisPopLayer, projectType);
		});
		$(".j_modifyResultSave").live('click',function(){//录入修改弹层页面暂存
			editAnninspection.addAnnResult(2, 2, thisPopLayer, projectType);
		});
		$(".j_addResultSubmit").live('click',function(){//录入添加弹层页面提交
			editAnninspection.addAnnResult(3, 1, thisPopLayer, projectType);
		});
		$(".j_modifyResultSubmit").live('click',function(){//录入修改弹层页面提交
			editAnninspection.addAnnResult(3, 2, thisPopLayer, projectType);
		});
//		window.addAnnApply = function(data, layer){editAnninspection.addAnnApply(data, layer, projectType)};
//		window.modifyAnnApply = function(data, layer){editAnninspection.modifyAnnApply(data, layer, projectType)};
//		window.addAnnResult = function(data, type, layer){editAnninspection.addAnnResult(data, type, layer, projectType)};
		window.uploadFileResult = function(data, type, layer){editAnninspection.uploadFileResult(data, type, layer)};
		editAnninspection.datepickInit();
		
		$("#addAnnFee").live("click",function() {
			popAddAnnFee({
				title : "添加年检经费结算明细",
				src : "project/general/anninspection/apply/toAddAnnFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
		
		$("#addAnnFeeApply").live("click",function() {
			popAddAnnFee({
				title : "添加年检经费结算明细",
				src : "project/general/anninspection/apply/toAddAnnFee.action?projectid=" + $("#annFileProjectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
				inData : {"projectid" : $("#annFileProjectid").val(),
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
					$("#fundedFee").val(result.fundedFee);
					$("#surplusFee").val(result.surplusFee);
					this.destroy();
				}
			});
		});
		
		$("#modifyAnnFee").live("click",function() {
			popAddAnnFee({
				title : "修改年检经费结算明细",
				src : "project/general/anninspection/apply/toAddAnnFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
					$("#fundedFee").val(result.fundedFee);
					$("#surplusFee").val(result.surplusFee);
					this.destroy();
				}
			});
		});
		
		$("#modifyAnnFeeApply").live("click",function() {
			popAddAnnFee({
				title : "修改年检经费结算明细",
				src : "project/general/anninspection/apply/toAddAnnFee.action?projectid=" + $("#annFileProjectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
				inData : {"projectid" : $("#annFileProjectid").val(),
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
					$("#fundedFee").val(result.fundedFee);
					$("#surplusFee").val(result.surplusFee);
					this.destroy();
				}
			});
		});
	};
});