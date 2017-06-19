define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	
	var editVariation = require('javascript/project/project_share/variation/edit');
	
	var projectType = "post";
	
	//初始化onclick事件
	exports.init = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
			var varId = $("#varId").val();
			$("#file_" + varId).uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
			$("#file_postponementPlan_" + varId).uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
		$(function() {
			$("#file_postponementPlan_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
		
		$(".j_editSave").live('click',function(){
			editVariation.submitOrNotApply(2, projectType,".j_editSave");
		});
		$(".j_editSubmit").live('click',function(){
			editVariation.submitOrNotApply(3, projectType,".j_editSubmit");
		});
		$(".j_showResultDetail").live('click',function(){
			var data = $("input[name='varResult'][type='radio']:checked").val();
			editVariation.showResultDetail(data);
		});
		$(".j_addResultSave").live('click',function(){
			editVariation.addVarResult(2, 1, projectType,".j_addResultSave");
		});
		$(".j_addResultSubmit").live('click',function(){
			editVariation.addVarResult(3, 1, projectType,".j_addResultSubmit");
		});
		$(".j_modifyResultSave").live('click',function(){
			editVariation.addVarResult(2, 2, projectType,".j_modifyResultSave");
		});
		$(".j_modifyResultSubmit").live('click',function(){
			editVariation.addVarResult(3, 2, projectType,".j_modifyResultSubmit");
		});
		
//		window.addVarResult = function(data, type){editVariation.addVarResult(data, type, projectType)};
//		window.submitOrNotApply = function(data){editVariation.submitOrNotApply(data, projectType)};
//		window.showResultDetail = function(data){editVariation.showResultDetail(data)};
		editVariation.init();
		editVariation.varInitClick();
		
		$("#addVarFee").live("click",function() {
			popAddVarFee({
				title : "添加经费预算变更明细",
				src : "project/post/variation/apply/toAddVarFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
																			"&totalFee=" + $("#totalFeeD").val(),
				inData : {"projectid" : $("#projectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "dataFee" : $("#dataFee").val(),
						  "travelFee" : $("#travelFee").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "laborFee" : $("#laborFee").val(),
						  "printFee" : $("#printFee").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "totalFee" : $("#totalFeeD").val()
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
					this.destroy();
				}
			});
		});
		
		$("#modifyVarFee").live("click",function() {
			popAddVarFee({
				title : "添加经费预算变更明细",
				src : "project/post/variation/apply/toAddVarFee.action?projectid=" + $("#projectid").val() + "&feeNote=" + $("#feeNote").val() + 
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
																			"&totalFee=" + $("#totalFeeD").val(),
				inData : {"projectid" : $("#projectid").val(),
						  "feeNote" : $("#feeNote").val(),
						  "bookFee" : $("#bookFee").val(),
						  "dataFee" : $("#dataFee").val(),
						  "travelFee" : $("#travelFee").val(),
						  "conferenceFee" : $("#conferenceFee").val(),
						  "internationalFee" : $("#internationalFee").val(),
						  "deviceFee" : $("#deviceFee").val(),
						  "consultationFee" : $("#consultationFee").val(),
						  "laborFee" : $("#laborFee").val(),
						  "printFee" : $("#printFee").val(),
						  "indirectFee" : $("#indirectFee").val(),
						  "otherFeeD" : $("#otherFeeD").val(),
						  "totalFee" : $("#totalFeeD").val()
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
					this.destroy();
				}
			});
		});
	};
});