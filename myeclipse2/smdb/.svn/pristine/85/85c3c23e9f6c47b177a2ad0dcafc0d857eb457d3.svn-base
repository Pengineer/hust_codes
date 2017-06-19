define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	
	var addApplication = require('javascript/project/project_share/application/apply/add');
	
	var projectType = "entrust";
	
	//初始化onclick事件
	exports.init = function() {
		addApplication.init(projectType);
		
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
				$("#addApplyFee").live("click",function() {
			var getPara = function(obj){
				return  "?applyFee=" + obj["applyFee"] + "&otherFee=" + obj["otherFee"] + 
						"&bookFee=" + obj["bookFee"] + "&bookNote=" + obj["bookNote"] + 
						"&dataFee=" + obj["dataFee"] + "&dataNote=" + obj["dataNote"] +
						"&travelFee=" + obj["travelFee"] + "&travelNote=" + obj["travelNote"] +
						"&conferenceFee=" + obj["conferenceFee"] + "&conferenceNote=" + obj["conferenceNote"] +
						"&internationalFee=" + obj["internationalFee"] + "&internationalNote=" + obj["internationalNote"] +
						"&deviceFee=" + obj["deviceFee"] + "&deviceNote=" + obj["deviceNote"] +
						"&consultationFee=" + obj["consultationFee"] + "&consultationNote=" + obj["consultationNote"] +
						"&laborFee=" + obj["laborFee"] + "&laborNote=" + obj["laborNote"] +
						"&printFee=" + obj["printFee"] + "&printNote=" + obj["printNote"] +
						"&indirectFee=" + obj["indirectFee"] + "&indirectNote=" + obj["indirectNote"] +
						"&otherFeeD=" + obj["otherFeeD"] + "&otherNote=" + obj["otherNote"] +
						"&totalFee=" + obj["totalFee"];
			};
			popAddApplyFee({
				title : "添加申请经费概算明细",
				src : "project/entrust/application/apply/toAddApplyFee.action?applyFee=" + $("#applyFee").val() + "&otherFee=" + $("#otherFee").val() + 
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
																			"&totalFee=" + $("#totalFeeD").val() + "&feeNote=" + $("#feeNote").val(),
				inData : {"applyFee" : $("#applyFee").val(),
						  "otherFee" : $("#otherFee").val(),
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
						  "feeNote" : $("#feeNote").val(),
						  "totalFee" : $("#totalFeeD").val()
						  },
				callBack : function(result){
					$("#totalFee").html(result.totalFee);
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
					$("#feeNote").val(result.feeNote);
//					var url = basePath + "project/general/application/apply/addApplyFee.action" + getPara(result);
//					location.href = url;
					this.destroy();
				}
			});
		});
		
		$("#addGrantedFee").live("click",function() {
			popAddGrantedFee({
				title : "添加立项经费预算明细",
				src : "project/entrust/application/apply/toAddGrantedFee.action?approveFee=" + $("#approveFee").val() +  
																			"&bookFee=" + $("#bookFee1").val() + "&bookNote=" + $("#bookNote1").val() + 
																			"&dataFee=" + $("#dataFee1").val() + "&dataNote=" + $("#dataNote1").val() +
																			"&travelFee=" + $("#travelFee1").val() + "&travelNote=" + $("#travelNote1").val() +
																			"&conferenceFee=" + $("#conferenceFee1").val() + "&conferenceNote=" + $("#conferenceNote1").val() +
																			"&internationalFee=" + $("#internationalFee1").val() + "&internationalNote=" + $("#internationalNote1").val() +
																			"&deviceFee=" + $("#deviceFee1").val() + "&deviceNote=" + $("#deviceNote1").val() +
																			"&consultationFee=" + $("#consultationFee1").val() + "&consultationNote=" + $("#consultationNote1").val() +
																			"&laborFee=" + $("#laborFee1").val() + "&laborNote=" + $("#laborNote1").val() +
																			"&printFee=" + $("#printFee1").val() + "&printNote=" + $("#printNote1").val() +
																			"&indirectFee=" + $("#indirectFee1").val() + "&indirectNote=" + $("#indirectNote1").val() +
																			"&otherFeeD=" + $("#otherFeeD1").val() + "&otherNote=" + $("#otherNote1").val() +
																			"&totalFee=" + $("#totalFeeD1").val() + "&feeNote=" + $("#feeNote1").val(),
				inData : {"approveFee" : $("#approveFee").val(),
						  "bookFee" : $("#bookFee1").val(),
						  "bookNote" : $("#bookNote1").val(),
						  "dataFee" : $("#dataFee1").val(),
						  "dataNote" : $("#dataNote1").val(),
						  "travelFee" : $("#travelFee1").val(),
						  "travelNote" : $("#travelNote1").val(),
						  "conferenceFee" : $("#conferenceFee1").val(),
						  "conferenceNote" : $("#conferenceNote1").val(),
						  "internationalFee" : $("#internationalFee1").val(),
						  "internationalNote" : $("#internationalNote1").val(),
						  "deviceFee" : $("#deviceFee1").val(),
						  "deviceNote" : $("#deviceNote1").val(),
						  "consultationFee" : $("#consultationFee1").val(),
						  "consultationNote" : $("#consultationNote1").val(),
						  "laborFee" : $("#laborFee1").val(),
						  "laborNote" : $("#laborNote1").val(),
						  "printFee" : $("#printFee1").val(),
						  "printNote" : $("#printNote1").val(),
						  "indirectFee" : $("#indirectFee1").val(),
						  "indirectNote" : $("#indirectNote1").val(),
						  "otherFeeD" : $("#otherFeeD1").val(),
						  "otherNote" : $("#otherNote1").val(),
						  "feeNote" : $("#feeNote1").val(),
						  "totalFee" : $("#totalFeeD1").val()
						  },
				callBack : function(result){
					$("#totalFee1").html(result.totalFee);
					$("#bookFee1").val(result.bookFee);
					$("#bookNote1").val(result.bookNote);
					$("#dataFee1").val(result.dataFee);
					$("#dataNote1").val(result.dataNote);
					$("#travelFee1").val(result.travelFee);
					$("#travelNote1").val(result.travelNote);
					$("#conferenceFee1").val(result.conferenceFee);
					$("#conferenceNote1").val(result.conferenceNote);
					$("#internationalFee1").val(result.internationalFee);
					$("#internationalNote1").val(result.internationalNote);
					$("#deviceFee1").val(result.deviceFee);
					$("#deviceNote1").val(result.deviceNote);
					$("#consultationFee1").val(result.consultationFee);
					$("#consultationNote1").val(result.consultationNote);
					$("#laborFee1").val(result.laborFee);
					$("#laborNote1").val(result.laborNote);
					$("#printFee1").val(result.printFee);
					$("#printNote1").val(result.printNote);
					$("#indirectFee1").val(result.indirectFee);
					$("#indirectNote1").val(result.indirectNote);
					$("#otherFeeD1").val(result.otherFeeD);
					$("#otherNote1").val(result.otherNote);
					$("#totalFeeD1").val(result.totalFee);
					$("#feeNote1").val(result.feeNote);
					this.destroy();
				}
			});
		});
	};
});