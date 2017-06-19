define(function(require, exports, module) {
	var validGra = require('javascript/project/project_share/application/validate');
	
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('uploadify');
	require('uploadify-ext');
	require('form');
	var datepick = require("datepick-init");
	var timeValidate = require('javascript/project/project_share/validate');
	
	var flag = 99999;
	
	//保存立项计划书申请结果
	var saveMidApply = function(data, type, layer, projectType) {
		if($("#graStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(data == 3) {
				if( !confirm('提交后无法修改，是否确认提交？'))
					return;
			}
			$('#garApplicantSubmitStatus').val(data);
			var url = "";
			if(type == 1){
				url = "project/" + projectType + "/application/granted/addGra.action";
			}else if(type == 2){
				url = "project/" + projectType + "/application/granted/modifyGra.action";
			}
			$("#graFile").attr("action", url);
			$("#graFile").submit();
		}else{
			return false;
		}
	};
	//添加立项计划书申请
	var addMidApply = function(data, layer, projectType){
		saveMidApply(data, 1, layer,projectType);
	};
	//修改立项计划书申请
	var modifyMidApply = function(data, layer, projectType){
		saveMidApply(data, 2, layer,projectType);
	};
	
	/**
	 * 提交前检查是否添加了项目成员及负责人
	 */
	var checkMember = function(){
		//检查是否添加了项目成员
		var allTable = $("#member .table_valid");
		if(!allTable){
			return false;
		}
		if(allTable.length<1){
			alert("请添加项目成员");
			return false;
		}
		var director = 0;
		//检查是否添加了项目负责人
		$("[name$='isDirector']").each(function(index){
			if($(this).val() == 1){
				director++;
			}
		});
		if(director == 0){
			alert("请选择项目负责人");
			return false;
//		}else if(director >1){
//			alert("只能选择一个项目负责人");
//			return false;
//		}else{
//			var $directorYes = $("[name$='isDirector'][value=1]:eq(0)");
//			$here = $directorYes.parent().parent().parent();
//			var memType = $("[name$='memberType']", $here).val();
//			if (memType == '2') {
//				alert("一般项目负责人只能是教师或学生");
//				return false;
//			}
		}
		return true;
	};
	
	/**
	 * 提交前按table重新分配下标
	 */
	var memberNum = function(projectType){
		$("#member table").each(function(key, value){
			$("input, select", $(value)).each(function(key1, value1){
				value1.name = value1.name.replace(/\[.*\]/, "[" + (key) + "]");
			});
		});
	 	validGra.valid_member(projectType);//分配下标后重新设置校验，否则修改完成时会出错
	};
	
	/**
	 * 添加一个相关人员
	 */
	var addTable = function(obj ,tableId, projectType){
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>").insertAfter(onthis);
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		validGra.valid_member(projectType);
		sortNum();
	};
	
	var addLastTable = function(topId, tableId, projectType) {
		$("#" + topId).append("<table id='newTable' width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
		var addTable = $("#newTable");
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		validGra.valid_member(projectType);
		sortNum();
		addTable.removeAttr("id");
	};
	
	/**
	 * 判断其他成果类别输入框是否要显示
	 * @param {Object} isChecked	是否选择其他成果
	 * @param {Object} name	点中的多选框的值
	 */
	function showOtherProductTypeOrNot(isChecked, value){
		if(value == undefined || typeof(value) == 'undefined'){
			if(isChecked){
				$("#productTypeOtherSpan").show();
			}else{
				$("#productTypeOtherSpan").hide();
				$("#profuctTypeOtherTd").html("");
			}
		}else{
			if(isChecked && value == '其他'){
				$("#productTypeOtherSpan").show();
			}else{
				$("#productTypeOtherSpan").hide();
				$("#profuctTypeOtherTd").html("");
			}
		}
		validGra.valid_apply();
	};

	//自动编号
	var sortNum = function() {
	 	var spans = getElementsByTagTitle("span","memberSpan");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	
	
	var addGraResult = function(data, type, layer){
		if(data == 3){
			if (!confirm("提交后不能修改，是否确认提交?")) {
				return;
			}
		}
		var graResult=$("input[name='graResult'][type='radio']:checked").val();
		var graDate = $("[name='graDate']:eq(0)").val();
		var graImportedOpinion = $("[name='graImportedOpinion']:eq(0)").val();
		var graOpinionFeedback = $("[name='graOpinionFeedback']:eq(0)").val();
		
		var bookFee = $("#bookFee").val();
		var bookNote= $("#bookNote").val();
	    var dataFee= $("#dataFee").val();
	    var dataNote= $("#dataNote").val();
	    var travelFee= $("#travelFee").val();
	    var travelNote= $("#travelNote").val();
	    var conferenceFee= $("#conferenceFee").val();
	    var conferenceNote= $("#conferenceNote").val();
	    var internationalFee= $("#internationalFee").val();
	    var internationalNote= $("#internationalNote").val();
	    var deviceFee=$("#deviceFee").val();
	    var deviceNote= $("#deviceNote").val();
	    var consultationFee= $("#consultationFee").val();
	    var consultationNote= $("#consultationNote").val();
	    var laborFee= $("#laborFee").val();
	    var laborNote= $("#laborNote").val();
	    var printFee= $("#printFee").val();
	    var printNote= $("#printNote").val();
	    var indirectFee= $("#indirectFee").val();
  	    var indirectNote= $("#indirectNote").val();
	    var otherFeeD= $("#otherFeeD").val();
	    var otherNote= $("#otherNote").val();
	    var totalFee= $("#totalFeeD").val();
	    var feeNote = $("#feeNote").val();
		
		dis = {
			"type" : type,
			"graResult" : graResult,
			"graStatus" : data,
			"graDate" : graDate,
			"graImportedOpinion" : graImportedOpinion,
			"graOpinionFeedback" : graOpinionFeedback,
			
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
			  "feeNote" : $("#feeNote").val()
			
		}
		layer.callBack(dis);
		layer.destroy();
	};
	
	module.exports = {
		 checkMember: function(){return checkMember()}, 
		 memberNum: function(projectType){memberNum(projectType)}, 
		 addTable: function(obj ,tableId, projectType){addTable(obj ,tableId, projectType)},
		 addLastTable: function(topId, tableId, projectType){addLastTable(topId, tableId, projectType)},
		 showOtherProductTypeOrNot: function(isChecked, value){showOtherProductTypeOrNot(isChecked, value)},
		 addGraResult: function(data, type, layer){addGraResult(data, type, layer)},
		 addMidApply: function(data, layer, projectType){addMidApply(data, layer, projectType)},
		 modifyMidApply: function(data, layer, projectType){modifyMidApply(data, layer, projectType)}
	};
});