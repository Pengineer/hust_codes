define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	require('validate');
	
	var attrRules = {
		"identifier1":[
			["person.name", {required:true, maxlength:40}],
//			["person.idcardType", {selected:true}],
			["person.idcardNumber", {cardNumber:true}]
		],
		"basic1":[
			["person.englishName", {maxlength:40}],
			["person.usedName", {maxlength:40}],
//			["person.gender", {selected:true}],
			["person.birthday", {dateISO:true}],
			["person.membership", {maxlength:80}],
			["person.countryRegion", {maxlength:80}],
			["person.ethnic", {maxlength:80}],
			["person.birthplace", {maxlength:80}]
		],
		"basic2":[
			["person.name", {required:true, maxlength:40}],
			["person.englishName", {maxlength:40}],
			["person.usedName", {maxlength:40}],
//			["person.gender", {selected:true}],
			["person.birthday", {dateISO:true}],
//			["person.idcardType", {selected:true}],
			["person.idcardNumber", {cardNumber:true}],
			["person.membership", {maxlength:80}],
			["person.countryRegion", {maxlength:80}],
			["person.ethnic", {maxlength:80}],
			["person.birthplace", {maxlength:80}]
		],
		"contact":[
		    ["comb_addr", {maxlength:200}],
		    ["comb_addr", {address:true}],
		    ["comb_postcode", {postCode:true}],
		//	["person.homeAddress", {maxlength:200}],
		//	["person.homePostcode", {postCode:true}],
			["person.homePhone", {multiplePhones:true}],
//			["person.homePhone", {phone:true}],
		//	["person.officeAddress", {maxlength:200}],
		//	["person.officePostcode", {postCode:true}],
			["person.officePhone", {multiplePhones:true}],
//			["person.officePhone", {phone:true}],
			["person.officeFax", {multipleFaxes:true}],
//			["person.officeFax", {fax:true}],
			["person.mobilePhone", {multiplePhones:true}],
//			["person.mobilePhone", {phone:true}],
			["person.email", {multipleEmails:true}],
//			["person.email", {email:true}],
			["person.qq", {multiQQs:true}],
			["person.msn", {multipleEmails:true}],
			["person.homepage", {multiUrls:true}]
		],
		"officer_affiliation":[
			["officer.type", {selected:true}],
			["officer.startDate", {dateISO:true}],
			["officer.endDate", {dateISO:true}],
			["officer.position", {maxlength:80}],
			["officer.staffCardNumber", {cardNumber:true}],
			["unitId",{required:true}]
		],
		"department_officer_affiliation":[
			["officer.type", {selected:true}],
			["officer.startDate", {dateISO:true}],
			["officer.endDate", {dateISO:true}],
			["officer.position", {maxlength:80}],
			["officer.staffCardNumber", {cardNumber:true}],
			["departmentId",{required:true}]
		],
		"institute_officer_affiliation":[
			["officer.type", {selected:true}],
			["officer.startDate", {dateISO:true}],
			["officer.endDate", {dateISO:true}],
			["officer.position", {maxlength:80}],
			["officer.staffCardNumber", {cardNumber:true}],
			["instituteId",{required:true}]
		],
		"expert_affiliation":[
			["expert.agencyName",{required:true, maxlength:40}],
			["expert.divisionName",{required:true, maxlength:40}],
			["expert.type",{selected:true}],
			["expert.position",{maxlength:80}]
		],
		"teacher_affiliation":[
			["unitType",{selected:true}],
			["startDate",{dateISO:true}],
			["endDate",{dateISO:true}],
			["position",{}],
			["workMonthPerYear",{number:true}],
			["staffCardNumber",{cardNumber:true}],
			["type",{selected:true}]
		],
		"student":[
			["unitType",{selected:true}],
//			["DIName_subjectionName",{required:true}],
			["student.status",{selected:true}],
			["student.type",{selected:true}],
			["student.studentCardNumber",{cardNumber:true}],
			["student.startDate",{dateISO:true}],
			["student.endDate",{dateISO:true}],
			["student.tutor.id",{maxlength:40}],
			["student.project",{maxlength:2000}]
		],
		"thesis":[
			["student.thesisTitle", {maxlength:200}],
			["student.thesisFee", {number:true, max:9999}],
			["student.isExcellent", {}],
			["student.excellentGrade", {}],
			["student.excellentYear", {number:true}],
			["student.excellentSession", {maxlength:40}]
		],
		"academic":[ // 对哪些是必填项不清楚  对select标签 selected:true即为必填
			["academic.lastEducation", {selected:false}],
			["academic.lastDegree", {selected:false}],
			["academic.postdoctor", {selected:false}],
			["academic.specialityTitle", {selected:false}],
			["academic.positionLevel", {maxlength:40}],
			["academic.tutorType", {selected:false}],
			["academic.talent", {selected:false}],
			["academic.ethnicLanguage", {required:false}],
			["academic.language", {required:false}],
			["academic.disciplineType", {required:false}],
			["academic.discipline", {required:false}],
			["academic.relativeDiscipline", {required:false}],
			["academic.major", {maxlength:200}],
			["academic.researchField", {maxlength:2000}],
			["academic.researchSpeciality", {maxlength:2000}],
			["academic.furtherEducation", {maxlength:2000}],
			["academic.parttimeJob", {maxlength:2000}]
		],
		"work":[
			["startDate", {required:true, dateISO:true}],
			["endDate", {required:true, dateISO:true}],
			["unit", {maxlength:200}],
			["department", {maxlength:200}],
			["position", {maxlength:80}]
		],
		"education":[
			["startDate", {required:true, dateISO:true}],
			["endDate", {required:true, dateISO:true}],
			["education", {selected:true}],
			["degree", {selected:true}],
			["countryRegion", {maxlength:80}],
			["university", {maxlength:200}],
			["department", {maxlength:200}],
			["major", {maxlength:200}]
		],
		"abroad":[
			["startDate", {dateISO:true}],
			["endDate", {dateISO:true}],
			["countryRegion", {maxlength:80}],
			["workUnit", {maxlength:200}],
			["purpose", {maxlength:800}]
		],
		"bank":[
			["bankName", {maxlength:50}],
			["bankCupNumber", {maxlength:40}],
			["accountNumber", {maxlength:50}],
			["accountName", {maxlength:40}]
		],
		"identifier":[
			["person.name", {required:true, maxlength:40}],
			["person.idcardType", {selected:true}],
			["person.idcardNumber", {cardNumber:true}]
		]
	};
	
	exports.valid_identifier1 = function(){
		for(i = 0; i < attrRules.identifier1.length; i++){
			$("':input[name=" + attrRules.identifier1[i][0] + "]'").rules("add", attrRules.identifier1[i][1] );
		}
	}
	exports.valid_basic1 = function(){
		for(i = 0; i < attrRules.basic1.length; i++){
			$("':input[name=" + attrRules.basic1[i][0] + "]'").rules("add", attrRules.basic1[i][1] );
		}
	};
	exports.valid_basic2 = function(){
		for(i = 0; i < attrRules.basic2.length; i++){
			$("':input[name=" + attrRules.basic2[i][0] + "]'").rules("add", attrRules.basic2[i][1] );
		}
	};
	exports.valid_contact = function(){
		for(i = 0; i < attrRules.contact.length; i++){
			$("':input[name*=" + attrRules.contact[i][0] + "]'").each(function(){
				$(this).rules("add",attrRules.contact[i][1]  );
			});
		}
		/*$(":input[name*='comb_postcode']").each(function(){
			$(this).rules("add",{postCode:true} );
		});*/
	};
	exports.valid_officer_affiliation = function(){
		for(i = 0; i < attrRules.officer_affiliation.length; i++){
			$(":input[name='" + attrRules.officer_affiliation[i][0] + "']").rules("add", attrRules.officer_affiliation[i][1] );
		}
	};
	exports.valid_department_officer_affiliation = function(){
		for(i = 0; i < attrRules.department_officer_affiliation.length; i++){
			$(":input[name='" + attrRules.department_officer_affiliation[i][0] + "']").rules("add", attrRules.department_officer_affiliation[i][1] );
		}
	};
	exports.valid_institute_officer_affiliation = function(){
		for(i = 0; i < attrRules.institute_officer_affiliation.length; i++){
			$(":input[name='" + attrRules.institute_officer_affiliation[i][0] + "']").rules("add", attrRules.institute_officer_affiliation[i][1] );
		}
	};
	exports.valid_expert_affiliation = function(){
		for(i = 0; i < attrRules.expert_affiliation.length; i++){
			$("':input[name=" + attrRules.expert_affiliation[i][0] + "]'").rules("add", attrRules.expert_affiliation[i][1] );
		}
	};
	exports.valid_add_teacher_affiliation = function(){
		for(i = 0; i < attrRules.teacher_affiliation.length; i++){
			$("':input[name*=" + attrRules.teacher_affiliation[i][0] + "]'").rules("add", attrRules.teacher_affiliation[i][1] );
		}
	};
	exports.valid_modify_teacher_affiliation = function(){
		$("#table_affiliation .tr_valid").each(function(){
			for(i = 0; i < attrRules.teacher_affiliation.length; i++){
				$("':input[name*=" + attrRules.teacher_affiliation[i][0] + "]'", $(this)).rules("add", attrRules.teacher_affiliation[i][1] );
			}
		});
	};
	exports.valid_student = function(){
		for(i = 0; i < attrRules.student.length; i++){
			$("':input[name=" + attrRules.student[i][0] + "]'").rules("add", attrRules.student[i][1] );
		}
	};
	exports.valid_thesis = function(){
		for(i = 0; i < attrRules.thesis.length; i++){
			$("':input[name=" + attrRules.thesis[i][0] + "]'").rules("add", attrRules.thesis[i][1] );
		}
	};
	exports.valid_academic = function(){
		for(i = 0; i < attrRules.academic.length; i++){
			$("':input[name=" + attrRules.academic[i][0] + "]'").rules("add", attrRules.academic[i][1] );
		}
	};
	exports.valid_work = function(){
		$("#table_work .tr_valid").each(function(){
			for(i = 0; i < attrRules.work.length; i++){
				$("':input[name*=" + attrRules.work[i][0] + "]'", $(this)).rules("add", attrRules.work[i][1] );
			}
		});
	};
	exports.valid_education = function(){
		$("#table_education .tr_valid").each(function(){
			for(i = 0; i < attrRules.education.length; i++){
				$("':input[name*=" + attrRules.education[i][0] + "]'", $(this)).rules("add", attrRules.education[i][1] );
			}
		});
	};
	exports.valid_abroad = function(){
		$("#table_abroad .tr_valid").each(function(){
			for(i = 0; i < attrRules.abroad.length; i++){
				$("':input[name*=" + attrRules.abroad[i][0] + "]'", $(this)).rules("add", attrRules.abroad[i][1] );
			}
		});
	};
	exports.valid_bank = function(){
		for(i = 0; i < attrRules.bank.length; i++){
			$("':input[name*=" + attrRules.bank[i][0] + "]'").rules("add", attrRules.bank[i][1] );
		}
	};
	exports.valid_identifier = function(){
		for(i = 0; i < attrRules.identifier.length; i++){
			$("':input[name=" + attrRules.identifier[i][0] + "]'").rules("add", attrRules.identifier[i][1] );
		}
	};

	exports.checkOfficerType = function(){
		var personName = $("#person_name").text().trim();
		var idcardNumber = $("#person_idcardNumber").text().trim();
		var officerId = "";
		if($("input[name='entityId']")){officerId = $("input[name='entityId']").val();}
		DWREngine.setAsync(false);
		personService.checkIfIsOfficer(idcardNumber, personName, officerId, call_back_checkIdcard);
		DWREngine.setAsync(false);
		if (result[0] == 2){
			alert("该人已有专职管理人员，只能添加兼职人员!");
			return false;
		} else {
			return true;
		}
	};

	function call_back_checkIdcard(_result){
		result = _result;
	};
	
	exports.valid = function() {
		$("#form_person").validate({
			errorElement: "span",
			event: "blur",
			rules:{
					
				},
			errorPlacement: function(error, element) {
				var obj = $(".tr_error").parents(".edit_info:visible");
				var comb= element.parent("td").parent("tr").find("td.comb-error");
				if(comb.length){
					error.appendTo(comb);
				}else if(obj.length){
					var idx = element.parent().eq(0).index();
					$(".tr_error td:eq(" + idx + ")", obj).empty();
					error.appendTo( $(".tr_error td", obj).eq(idx) );
				}
				else {error.appendTo( element.parent("td").next("td") );}
			}
		});
	};
});
