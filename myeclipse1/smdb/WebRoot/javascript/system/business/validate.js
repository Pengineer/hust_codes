// ========================================================================
// 文件名：validate.js
//
// 文件说明：
//     本文件主要实现业务模块中页面的输入验证，包括添加、修改。
//
// 历史记录：
// 2011-12-29  肖雅     创建文件，给添加、修改绑定校验。
// ========================================================================

define(function(require, exports, module) {
	require('validate');
	
	var valid = function(){
		$(function() {
			$("#form_business").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"business.type":{selected:true},
					"business.subType.id":{selected:true},
					"business.status":{selected:true},
					"business.startDate":{required:true, dateSMDB:true},
//				"business.startYear":{selected:true},
//				"business.endYear":{selected:true},
//				"business.businessYear":{selected:true}
					//"business.applicantDeadline":{dateISO:true},
					//"business.deptInstDeadline":{dateISO:true},
					//"business.univDeadline":{dateISO:true},
					//"business.provDeadline":{dateISO:true},
					//"business.reviewDeadline":{dateISO:true}
				},
				errorPlacement: function(error, element) { 
					error.appendTo( element.parent("td").next("td") ); 
				}
			});
		});
	};
	
	var valid_deadline = function() {
		var elems = [
     		$("[name*='applicantDeadline']"), 
     		$("[name*='deptInstDeadline']"), 
     		$("[name*='univDeadline']"),
     		$("[name*='provDeadline']"),
     		$("[name*='reviewDeadline']")
     	];
     	var errors = ["日期不能小于下级时间", "日期不能大于上级时间"];
     	var flag = true;
     	$(elems).each(function(index){
     		if(!!$(this).val()) {
     			for(var i = 0; i < index; i++) {
     				if(!!$(elems[i]).val()) {
     					var t1 = new Date(Date.parse($(this).val().replace(/-/g, "/"))); //转换成Date();
     					var t2 = new Date(Date.parse($(elems[i]).val().replace(/-/g, "/"))); //转换成Date();
     					if(t1 < t2) {
     						$(this).parent("td").next("td").empty().append($("<span class='error'>" + errors[0] + "</span>"));
     						flag = false;
     						break;
     					} else {
     						$(this).parent("td").next("td").empty();
     					}
     				}
     			}
     		}
     	})
     	return flag;
	};
	
	var valid_selectBusinessType = function(){
		var $form = $("#form_business");
		var $subType = $("[name='business.subType.id']", $form).get(0);
		var $startYear = $("[name='business.startYear']", $form).get(0);
		var $endYear = $("[name='business.endYear']", $form).get(0);
		var $businessId = $("[name='entityId']", $form).get(0);
		var subType = $subType.value;
		var businessId = "";
		if($businessId != undefined){
			businessId = $businessId.value;
		}
		var startYear = $startYear.value;
		var endYear = $endYear.value;
		DWREngine.setAsync(false);
		businessService.checkBusinessType(subType, businessId, startYear, endYear, isBusinessTypeExistCallback);
		DWREngine.setAsync(true);
		return isBusinessTypeExist;
	};

	var isBusinessTypeExistCallback = function(data){
		var $form = $("#form_business");
		var $subType = $("[name='business.subType.id']", $form);
		var $subTypeTd = $subType.parent("td");
		if(!data){//未通过校验
			isBusinessTypeExist = false;
			$subTypeTd.next("td").append('<span class="error">该业务已存在</span>');// 写错误信息
		} else {//通过校验
			isBusinessTypeExist = true;
		}
	};

	var valid_isBusinessYearEqual = function(){
		var $form = $("#form_business");
		var $subType = $("[name='business.subType.id']", $form).get(0);
		var $startYear = $("[name='business.startYear']", $form).get(0);
		var $endYear = $("[name='business.endYear']", $form).get(0);
		var $businessId = $("[name='entityId']", $form).get(0);
		var subType = $subType.value;
		var businessId = "";
		if($businessId != undefined){
			businessId = $businessId.value;
		}
		var startYear = $startYear.value;
		var endYear = $endYear.value;
		//校验申请、年检的业务对象起止年度是否一致
		DWREngine.setAsync(false);
		businessService.isBusinessYearEqual(subType, startYear, endYear, isBusinessYearEqualCallback);
		DWREngine.setAsync(true);
		return isBusinessYearEqual;
	};

	var isBusinessYearEqualCallback = function(data){
		var $form = $("#form_business");
		var $startYear = $("[name='business.startYear']", $form);
		var $startYearTd = $startYear.parent("td");
		if(!data){//未通过校验
			isBusinessYearEqual = false;
			$startYearTd.next("td").append('<span class="error">该业务对象起止年份必须一致</span>');// 写错误信息
		} else {//通过校验
			isBusinessYearEqual = true;
		}
	};

	var valid_isBusinessYearCrossed = function(){
		var $form = $("#form_business");
		var $subType = $("[name='business.subType.id']", $form).get(0);
		var $startYear = $("[name='business.startYear']", $form).get(0);
		var $endYear = $("[name='business.endYear']", $form).get(0);
		var $businessId = $("[name='entityId']", $form).get(0);
		var subType = $subType.value;
		var businessId = "";
		if($businessId != undefined){
			businessId = $businessId.value;
		}
		var startYear = $startYear.value;
		var endYear = $endYear.value;
		//校验业务对象起止年度是否交叉
		DWREngine.setAsync(false);
		businessService.isBusinessYearCrossed(subType, businessId, startYear, endYear, isBusinessYearCrossedCallback);
		DWREngine.setAsync(true);
		return isBusinessYearCrossed;
	};

	var isBusinessYearCrossedCallback = function(data){
		var $form = $("#form_business");
		var $startYear = $("[name='business.startYear']", $form);
		var $startYearTd = $startYear.parent("td");
		if(!data){//未通过校验
			isBusinessYearCrossed = false;
			$startYearTd.next("td").append('<span class="error">该业务对象年份交叉</span>');// 写错误信息
		} else {//通过校验
			isBusinessYearCrossed = true;
		}
	};

	var attrRules = {
		"year":[
	        ["business.startYear", {selected:true}],
	        ["business.endYear", {selected:true}]
		],
	};

	var valid_year = function(isNeed){
		for(var i = 0; i < attrRules.year.length; i++){
			if(!isNeed){
				$("':input[name=" + attrRules.year[i][0] + "]'").rules("remove");
			}else{
				$("':input[name=" + attrRules.year[i][0] + "]'").rules("add", attrRules.year[i][1]);
			}
		}
	};
	
	module.exports = {
			valid: function(){valid()},
			valid_deadline: function(){return valid_deadline()}, 
			valid_selectBusinessType: function(){return valid_selectBusinessType()},
			valid_isBusinessYearEqual: function(){return valid_isBusinessYearEqual()},
			valid_isBusinessYearCrossed: function(){return valid_isBusinessYearCrossed()},
			valid_year: function(isNeed){valid_year(isNeed)}
	};
	
});

