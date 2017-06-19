// ========================================================================
// 文件名：validate.js
//
// 文件说明：一般项目添加修改的前台校验
//
// 历史记录：
// 2010-12-19  周中坚    添加文件
// ========================================================================

define(function(require, exports, module) {
	require('validate');

	var attrRules = {
		"apply":[
			["application1.name",{required:true, maxlength:50}],
			["application1.englishName",{maxlength:50}],
			["application1.agencyName",{required:true, maxlength:80}],
			["application1.subtype.id",{selected:true}],
			["typeFlag",{selected:true}],
//			["application1.institute.id",{selected:true}],
			["application1.department.id",{selected:true}],
//				["application1.topic.id",{selected:true}],
			["application1.applicantSubmitDate",{dateISO:true}],
//				["application1.planEndDate",{required:true, dateISO:true}],
//				["application1.applyFee",{required:true, number:true}],
//				["application1.otherFee",{required:true, number:true}],
			["application1.planEndDate",{dateISO:true}],
			["application1.applyFee",{required:true, number:true}],
			["application1.otherFee",{number:true}],
//				["application1.researchType.id",{selected: true}],
			["application1.disciplineType",{required:true, maxlength:100}],
			["application1.discipline",{maxlength:100}],
			["application1.relativeDiscipline",{maxlength:100}],
			["application1.keywords",{maxlength:100, keyWords:true}],
			["application1.summary",{maxlength:200}],
//			["file",{required:function(){return($(".uploadify-queue-item").size()==0);}}]
//			["file_add",{required:function(){
//					var test = $("#file_add-queue").html()
//					if(test == ""){
//						return true;
//					}
//				}
//			}]
		],
		"granted":[
			["granted.number", {required:true, maxlength:40}],
//				["granted.subtype.id",{selected:true}],
//				["granted.approveDate", {required:true, dateISO:true}],
//				["granted.approveFee", {required:true, number:true}],
			["granted.approveDate", {dateISO:true}],
			["granted.approveFee", {required:true, number:true}]
		],
		"member_moe":[
			["memberType", {selected:true}],
			["member.id", {required:true, maxlength:50}]
		],
		"member_man":[
			["idcardType", {selected:true}],
			["idcardNumber", {required:true, cardNumber:true}],
			["memberType", {selected:true}],
			["memberName", {required:true, maxlength:80}],
			["gender", {selected:true}],
			["agencyName", {required:true, maxlength:80}],
			["divisionType", {selected:true}],
			["divisionName", {required:true, maxlength:80}]
		],
		"member_same":[
//				["specialistTitle", {selected:true}],
			["major", {maxlength:50}],
//				["workMonthPerYear", {required:true, number:true, range:[0,12]}],
			["workMonthPerYear", {number:true, digits:true, range:[0,12]}],
			["workDivision", {maxlength:200}],
			["isDirector", {selected:true}]
		]
	};
	
	var valid = function(){
		$(function() {
			$("#graFile").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"note":{maxlength: 200},
					"graResult":{required: true},
					"gra_file":{required:function(){return($(".uploadify-queue-item").size()==0);}}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
			$("#application_form").validate({
				errorElement: "span",
				event: "change",
				rules:{
					
					},
				errorPlacement: function(error, element) { 
					error.appendTo( element.parents("td").next("td") );
				}
			});
			
			$("#select_form").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"entityId":{required: true},
					"granted.number":{required:true, maxlength:40},
//					"granted.subtype.id":{selected:true},
//					"granted.approveDate": {required:true, dateISO:true},
//					"granted.approveFee": {required:true, number:true},
					"granted.approveDate": {dateISO:true},
					"granted.approveFee": {required:true, number:true}
					},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var isGrantedNumberUnique;

	var valid_apply = function(){
		for(i = 0; i < attrRules.apply.length; i++){
			if($("':input[name=" + attrRules.apply[i][0] + "]'").length != 0){
				$("':input[name=" + attrRules.apply[i][0] + "]'").rules("add", attrRules.apply[i][1] );
			}
		}
		var $other = $("input[name='application1.productType'][type='checkbox'][value='其他']");
		if($other.attr("checked") == true){
			$("input[name='application1.productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true });
		}else{
			$("input[name='application1.productTypeOther']").rules("remove");
		}
		if($("#accountType").val() == "EXPERT" && $("#accountType").val() == "TEACHER" && $("#accountType").val() == "STUDENT"){//研究人员
			$("textarea[name='application1.note']").rules("add", {maxlength:200});
		}
		if($("#accountType").val() == "MINISTRY"){
			$("input[name='application1.year']").rules("add", {required:true, year:true});
		}
	};

	var valid_granted = function(){
		var $form = $("#application_form");
		var $radio = $("[name='application1.finalAuditResult']");
		if($radio==undefined || $radio==null || $radio.length<1){//无是否立项单选框
			for(i = 0; i < attrRules.granted.length; i++){
				if($("':input[name=" + attrRules.granted[i][0] + "]'").length != 0){
					$("':input[name=" + attrRules.granted[i][0] + "]'", $form).rules("add", attrRules.granted[i][1] );
				}
			}
		}else{//存在是否立项单选框
			var $isCheck = $radio.filter('[value="2"]');
			if($isCheck.attr("checked") == true){//需校验立项信息
				for(i = 0; i < attrRules.granted.length; i++){
					if($("':input[name=" + attrRules.granted[i][0] + "]'").length != 0){
						$("':input[name=" + attrRules.granted[i][0] + "]'", $form).rules("add", attrRules.granted[i][1] );
					}
				}
			}else{//不需校验立项信息
				for(i = 0; i < attrRules.granted.length; i++){
					if($("':input[name=" + attrRules.granted[i][0] + "]'").length != 0){
						$("':input[name*=" + attrRules.granted[i][0] + "]'", $form).rules("remove");
					}
				}
			}
		}
	};

	var valid_member = function(){
		//各成员信息校验
		$("#member .table_valid").each(function(key1, value1){
			if($("#accountType").val() == "MINISTRY"){//教育部录入
				for(i = 0; i < attrRules.member_moe.length; i++){
					if($("':input[name=" + attrRules.member_moe[i][0] + "]'").length != 0){
						$("':input[name*=" + attrRules.member_moe[i][0] + "]'", $(this)).rules("add", attrRules.member_moe[i][1]);
					}
				}
			}else{
				for(i = 0; i < attrRules.member_man.length; i++){
					if($("':input[name=" + attrRules.member_man[i][0] + "]'").length != 0){
						$("':input[name*=" + attrRules.member_man[i][0] + "]'", $(this)).rules("add", attrRules.member_man[i][1]);
					}
				}
			}
			for(i = 0; i < attrRules.member_same.length; i++){
				if($("':input[name=" + attrRules.member_same[i][0] + "]'").length != 0){
					$("':input[name*=" + attrRules.member_same[i][0] + "]'", $(this)).rules("add", attrRules.member_same[i][1]);
				}
			}
		});
//		$("#member .table_valid").each(function(){
//			for(i = 0; i < attrRules.member.length; i++){
//				$("':input[name*=" + attrRules.member[i][0] + "]'", $(this)).rules("add", attrRules.member[i][1]);
//			}
//		});
//		$("input[name*='agencyName']", $(".validTr")).each(function(){
//			$(this).rules("add", {required: true});
//		});
//		$("input[name*='divisionName']", $(".validTr")).each(function(){
//			$(this).rules("add", {required: true});
//		});
	};

	var remove_member = function(){
		$("#member .table_valid").each(function(){
			for(i = 0; i < attrRules.member.length; i++){
				if($("':input[name=" + attrRules.member[i][0] + "]'").length != 0){
					$("':input[name*=" + attrRules.member[i][0] + "]'", $(this)).rules("remove");
				}
			}
		});
	};

	//var valid_prodType = function(){
//		var flag = 1;// 校验通过与否标识位
//		var $checkbox = $("#__multiselect_application_form_application_productType").parent("td");//checkbox所在td
//		$(":input", $checkbox).each(function(key, value){
//			if(value.checked == true){flag = 0;}// checkbox有一个被选择了就将标识位设为0
//		});
//		$checkbox.next("td").html('');//校验前清空错误区域
//		if(flag){//标识位为1，未通过校验
//			$checkbox.next("td").append('<span class="error">请选择</span>');// 写错误信息
//			return false;
//		} else {//通过校验，清空错误区域
//			$checkbox.next("td").html('');
//			return true;
//		}
	//};

	var valid_number = function(projectType){
		var $form = $("#application_form");
		var $number = $("[name='number']", $form).get(0) || $("[name='granted.number']", $form).get(0);
		var $appId = $("[name='application1.id']", $form).get(0);
		var number = $number.value;
		var appId = "";
		if($appId != undefined){
			appId = $appId.value;
		}
		var granted = "";
		if(projectType == "general"){
			granted = "GeneralGranted";
		}else if(projectType == "instp"){
			granted = "InstpGranted";
		}else if(projectType == "post"){
			granted = "PostGranted";
		}else if(projectType == "key"){
			granted = "KeyGranted";
		}else if(projectType == "entrust"){
			granted = "EntrustGranted";
		}else if(projectType == "special"){
			granted = "SpecialGranted";
		}
		DWREngine.setAsync(false);
		projectService.isGrantedNumberUnique(granted, number, appId, isGrantedNumberUniqueCallback);
		DWREngine.setAsync(true);
		return isGrantedNumberUnique;
	};

	var isGrantedNumberUniqueCallback = function(data){
		var $form = $("#application_form");
		var $number = $("[name='number']", $form);
		var $numberTd = $number.parent("td");
		if(!data){//未通过校验
			isGrantedNumberUnique = false;
			$numberTd.next("td").append('<span class="error">该批准号已存在</span>');// 写错误信息
		} else {//通过校验
			isGrantedNumberUnique = true;
		}
	};
	
	module.exports = {
		 valid: function(){valid()}, 
		 valid_apply: function(){valid_apply()},
//		 valid_granted: function(){valid_granted()}, 
		 valid_member: function(){valid_member()},
//		 valid: function(){
//			 $(function() {
//				 valid()
//			 })
//		 }, 
//		 valid_apply: function(){
//			 $(function() {
//				 valid_apply()
//			 })
//		 },
		 valid_granted: function(){
		 $(function() {
			 valid_granted()
			 })
		 }, 
//		 valid_member: function(){
//			 $(function() {
//				 valid_member()
//			 })
//		 },
//		 remove_member: function(){remove_member()},
		 valid_number: function(projectType){return valid_number(projectType)}
	};
});
