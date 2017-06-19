/**
 * @author 王燕
 */

//添加或修改奖励申请校验
define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	require('validate');
	
	var attrRules = {
		"apply":[
			["awardApplication.applicationType",{selected:true}],
			["person.name",{required:true, maxlength:200}],
			["person.gender",{selected:true}],
			["person.officeAddress", {required:true, maxlength:200}],
			["person.officePostcode", {postCode:true}],
			["person.officePhone", {phone:true}],
			["person.email", {required:true, email:true}],
			["person.mobilePhone", {required:true, cellPhone:true}],	
			["person.birthday", {dateISO:true}],
			["person.idcardType", {required:true, selected:true}],
			["person.idcardNumber", {required:true, cardNumber:true}],
			["academic.lastDegree", {selected:false}],
			["academic.postdoctor", {selected:false}],
			["academic.specialityTitle", {selected:false}],
			["academic.tutorType", {selected:false}],
			["academic.language", {required:false}],
			["academic.discipline", {required:false}]
		],
		
		"apply_organ":[
	//		["organization.name", {required:true, maxlength:200}],
			["organization.officeAddress", {required:true, maxlength:200}],
			["organization.officePostcode", {postCode:true}],
			["organization.officePhone", {required:true, phone:true}],
			["organization.email", {required:true, email:true}],
			["organization.mobilePhone", {required:true, cellPhone:true}],	
			["organization.discipline", {required:false}],
			["organization.agencyName", {required:true, maxlength:80}]
		],
		
		"product":[
			["resultType", {required:true}],
			["proId", {selected:true}],
			["awardApplication.disciplineType", {selected:true}],
	//		["awardApplication.session", {selected:true}],
			["unitId", {selected:true}],
			["awardApplication.prizeObtained", {maxlength:500}],
			["awardApplication.response", {maxlength:500}],
			["awardApplication.adoption", {maxlength:500}],
			["awardApplication.introduction", {maxlength:20000}]
		]
	};
	exports.valid_apply = function(applicationType){
		$(["apply", "apply_organ"]).each(function(){
			for(j = 0; j < attrRules[this].length; j++) {
				if ($("':input[name=" + attrRules[this][j][0] + "]'").length != 0)
					$("':input[name=" + attrRules[this][j][0] + "]'").rules("remove");
			}
		});
		if(applicationType == 2){//以团队等申报
			for(i = 0; i < attrRules.apply_organ.length; i++){
				if ($("':input[name=" + attrRules.apply_organ[i][0] + "]'").length != 0) {
					$("':input[name=" + attrRules.apply_organ[i][0] + "]'").rules("add", attrRules.apply_organ[i][1]);
				}
			}
		}else{//以个人申报
			for(i = 0; i < attrRules.apply.length; i++){
				$("':input[name=" + attrRules.apply[i][0] + "]'").rules("add", attrRules.apply[i][1]);
			}
		}
	};
	exports.valid_product = function(){
		for(i = 0; i < attrRules.product.length; i++){
			$("':input[name=" + attrRules.product[i][0] + "]'").rules("add", attrRules.product[i][1] );
		}
	};
	exports.valid = function(){
		$(function() {
			$("#application_form").validate({
				errorElement: "span",
				event: "change",
				rules:{ },
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
});