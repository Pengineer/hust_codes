define(function(require, exports, module) {
	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	var datepick = require("datepick-init");
	
	/**
	 * 判断其他成果类别输入框是否要显示
	 * @param {Object} isChecked	是否选择其他成果
	 * @param {Object} name	点中的多选框的值
	 */
	var showOtherProductTypeOrNot = function(isChecked, value){
		if(value == undefined || typeof(value) == 'undefined'){
			if(isChecked){
				$("input[name='productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true});
				$("#productTypeOtherSpan").show();
			}else{
				$("input[name='productTypeOther']").rules("remove");
				$("#productTypeOtherSpan").hide();
				$("#profuctTypeOtherTd").html("");
			}
		}else{
			if(isChecked && value == '其他'){
				$("input[name='productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true});
				$("#productTypeOtherSpan").show();
			}else{
				$("input[name='productTypeOther']").rules("remove");
				$("#productTypeOtherSpan").hide();
				$("#profuctTypeOtherTd").html("");
			}
		}
	};

	var init = function() {
		init_hint();
		$("#list_button_advSearch").click(function(){
			$("#expFlag").val(1);
			if($("#startDate1").val() == "--不限--"){
				$("#startDate").val("");
			}else {
				$("#startDate").val($("#startDate1").val());
			}
			if($("#endDate1").val() == "--不限--"){
				$("#endDate").val("");
			}else {
				$("#endDate").val($("#endDate1").val());
			}
		});
		
		$("#select_disciplineType_btn").click(function(){
			$("#disptr").parent("td").next("td").html("");
			popSelect({
				type : 11,
				inData : $("#dispName").val(),
				callBack : function(result){
					doWithXX(result, "dispName", "disptr");
				}
			});
		});
		$("#select_discipline_btn").click(function(){
			$("#disciplineId").parent("td").next("td").html("");
			 popSelect({
			    type: 12,
			    inData: $("#disciplineId").val(),
			    callBack: function(result){
//			    	result = result.split("/")[0];
			      doWithXX(result, "disciplineId", "discipline");
			      $("#disciplineId").valid();
			 }
			});
		});
		
		//是否全选成果类别
		$("#checkAllProductTypeItem").live("click",function() { 
			checkAll(this.checked, 'productType');
			showOtherProductTypeOrNot(this.checked);
		});
		
		$("#productType").val($("#productTypeId").val());
		$("#productTypeOther").val($("#productTypeOtherId").val());
		$("#selectIssue").val($("#selectIssueId").val());
		
		//判断是否全选成果类别
		$("input[name='productType'][type='checkbox']").live("click",function() { 
			checkAllOrNot(this.checked, 'productType', 'checkAllProductTypeItem'); 
			if(this.value == '其他'){//如果是点击其他成果类别
				showOtherProductTypeOrNot(this.checked, this.value);
			}
		});
		
	};
	
	exports.valid = function(){
		$("#advSearch").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"minScore":{number: true, range: [0,100]},
				"maxScore":{number: true, range: [0,100]}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parents("td").next("td") );
			}
		});
	};
	
	exports.initPop = function(){//设置弹出层的原始值
		var dtypeNames = document.getElementById("dispName").value;
		doWithXX(dtypeNames, "dispName", "disptr");
		var discipline = $("#disciplineId").val();
		doWithXX(discipline, "disciplineId", "discipline");
	};
	
	exports.init = function() {
		datepick.init();// 初始化日期选择器
		init();
	};
	
	(function(){//保留多选框的值
		 if($("#checkedIssue").val() != '' && $("#checkedIssue").val() != undefined) {
			var test = $("#checkedIssue").val();
			var testinit1 = test.split('[');
			var testinit2 = testinit1[1].split(']');
			var testArray = testinit2[0].split(', ');
			for(i = 0; i<testArray.length; i++){
				$("input[name='selectIssue'][type='checkbox'][value='" + testArray[i] + "']").attr("checked", true);
			}
		}
		if($("#checkedProIssue").val() != '' && $("#checkedProIssue").val() != undefined) {
			var test = $("#checkedProIssue").val();
			var testArray = test.split(', ');
			for(i = 0; i<testArray.length; i++){
				$("input[name='productType'][type='checkbox'][value='" + testArray[i] + "']").attr("checked", true);
			}
		}
	})();
});
