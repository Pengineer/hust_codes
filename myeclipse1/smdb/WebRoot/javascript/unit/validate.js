define(function(require, exports, module) {
	require('validate');
	
	var attrRules = {
		"managementInfo":[
			["agency.sname", {maxlength:40}],
			//["agencyDirname", {required:true}],
			//["agencyLinkname", {required:true}],
//			["agency.saddress", {maxlength:200}],
//			["agency.spostcode", {postCode:true}],
			["agency.sphone", {multiplePhones:true}],
			["agency.sfax", {multipleFaxes:true}],
			["agency.semail", {multipleEmails:true}],
			["agency.shomepage", {url:true}]
		],
		"financialInfo":[
//			["agency.fname",{maxlength:40}],
//			["agency.fbankAccountName", {maxlength:50}],
//			["agency.fbankAccount", {maxlength:40}],
//			["agency.fbankBranch", {maxlength:50}],
//			["agency.fcupNumber", {maxlength:40}],
//			["agency.faddress", {maxlength:200}],
//			["agency.fpostcode", {postCode:true}],
			["agency.fphone", {multiplePhones:true}],
			["agency.ffax", {multipleFaxes:true}],
			["agency.femail", {multipleEmails:true}]
		],
		"bank":[
				["bankName", {maxlength:50}],
				["bankCupNumber", {maxlength:40}],
				["accountNumber", {maxlength:50}],
				["accountName", {maxlength:40}]
			],
		"agency_contactInfo":[
//			["agency.address", {maxlength:200}],
//			["agency.postcode", {postCode:true}],
			["agency.phone", {multiplePhones:true}],
			["agency.fax", {multipleFaxes:true}],
			["agency.email", {multipleEmails:true}],
			["agency.homepage", {url:true}]
		],
		"institute_contactInfo":[
//			["institute.address", {maxlength:200}],
//			["institute.postcode", {postCode:true}],
			["institute.phone", {multiplePhones:true}],
			["institute.fax", {multipleFaxes:true}],
			["institute.email", {multipleEmails:true}],
			["institute.homepage", {multiUrls:true}]//由url的校验修改为multiUrls的校验
		],
		"academicInfo":[
			["institute.researchActivityType.id", {maxlength:40}],
			["institute.researchArea", {maxlength:40}],
			["institute.disciplineType", {maxlength:400}]
			//["institute.relyDisciplineId", {maxlength:400}],
			//["institute.relyDoctoral", {maxlength:40}]
		],
		"resourceInfo":[
			["institute.officeArea", {number:true}],
			["institute.dataroomArea", {number:true}],
			["institute.chineseBookAmount", {maxlength:40}],
			["institute.chinesePaperAmount", {maxlength:40}],
			["institute.foreignBookAmount", {maxlength:40}],
			["institute.foreignPaperAmount", {maxlength:40}]
		],
		"doctorial":[
			["name", {required:true, maxlength:40}],
			["code", {required:true, maxlength:40}],
			["date", {dateISO:true}],
			["discipline", {required:true, maxlength:40}],
			["isKey", {required:true}],
			["introduction", {maxlength:400}]
		],
		"discipline":[
			["name", {required:true, maxlength:40}],
			["code", {required:true, maxlength:40}],
			["date", {dateISO:true}],
			["discipline", {required:true, maxlength:40}],
			["introduction", {maxlength:400}]
		]};
	
	exports.validAgency = function() {
		$("#form_agency").validate({
			errorElement: "span",
			event: "blur",
			rules:{},
			errorPlacement: function(error, element) { 
				var obj = $(".tr_error").parents(".edit_info:visible");
				if(obj.length){
					var idx = element.parent().eq(0).index();
					$(".tr_error td:eq(" + idx + ")", obj).empty();
					error.appendTo( $(".tr_error td", obj).eq(idx) );
				}
				else {error.appendTo( element.parent("td").next("td") );}
				}
		});
	};
	
	exports.validDepartment = function() {
		$("#form_department").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"department.name":{required:true,maxlength:200},
				"department.code":{maxlength:40},
				"department.university.id":{required:true},
//				"department.address":{maxlength:200},
//				"department.postcode":{postCode:true},
				"department.phone":{multiplePhones:true},
				"department.fax":{multipleFaxes:true},
				"department.email":{multipleEmails:true},
				"department.homepage":{url:true},
				"department.introduction":{maxlength:20000},
				name:{maxlength:40},
				iCode:{maxlength:40},
				iUniversity:{},
				iDirectorName:{maxlength:40}
				},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
				}
		});
	};
	
	exports.validInstitute = function() {
		$("#form_institute").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"institute.name":{required:true,maxlength:40},
				"institute.englishName":{maxlength:200},
				"institute.code":{maxlength:40},
				"institute.abbr":{maxlength:40},
				"institute.type.id":{selected:true},
				"institute.type":{selected:true},
				"institute.subjection.id":{required:true},
				"subjectionName":{maxlength:40},
				"institute.isIndependent":{selected:true},
				"institute.form":{maxlength:40},
				"institute.approveSession":{maxlength:40},
				"institute.approveDate":{dateISO:true},
				"institute.introduction":{maxlength:20000},
				
				name:{maxlength:40},
				iCode:{maxlength:40},
				iType:{},
				iUniversity:{},
				iDirectorName:{maxlength:40}
				},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
				}
		});
	};
	
	exports.valid_managementInfo = function(){
		for(var i = 0; i < attrRules.managementInfo.length; i++){
			$("':input[name=" + attrRules.managementInfo[i][0] + "]'").rules("add", attrRules.managementInfo[i][1] );
		}
	};
	exports.remove_managementInfo = function(){
		for(var i = 0; i < attrRules.managementInfo.length; i++){
			$("':input[name=" + attrRules.managementInfo[i][0] + "]'").rules("remove");
		}
	};
	exports.valid_financialInfo = function(){
		for(var i = 0; i < attrRules.financialInfo.length; i++){
			$("':input[name=" + attrRules.financialInfo[i][0] + "]'").rules("add", attrRules.financialInfo[i][1] );
		}
		for(i = 0; i < attrRules.bank.length; i++){
			$("':input[name*=" + attrRules.bank[i][0] + "]'").rules("add", attrRules.bank[i][1] );
		}
	};
	exports.remove_financialInfo = function(){
		for(var i = 0; i < attrRules.financialInfo.length; i++){
			$("':input[name=" + attrRules.financialInfo[i][0] + "]'").rules("remove");
		}
	};
	exports.valid_agency_contactInfo = function(){
		for(var i = 0; i < attrRules.agency_contactInfo.length; i++){
			$("':input[name=" + attrRules.agency_contactInfo[i][0] + "]'").rules("add", attrRules.agency_contactInfo[i][1] );
		}
	};
	exports.remove_agency_contactInfo = function(){
		for(var i = 0; i < attrRules.agency_contactInfo.length; i++){
			$("':input[name=" + attrRules.agency_contactInfo[i][0] + "]'").rules("remove");
		}
	};
	exports.valid_institute_contactInfo = function(){
		for(var i = 0; i < attrRules.institute_contactInfo.length; i++){
			$("':input[name=" + attrRules.institute_contactInfo[i][0] + "]'").rules("add", attrRules.institute_contactInfo[i][1] );
		}
	};
	exports.remove_institute_contactInfo = function(){
		for(var i = 0; i < attrRules.institute_contactInfo.length; i++){
			$("':input[name=" + attrRules.institute_contactInfo[i][0] + "]'").rules("remove");
		}
	};
	exports.valid_academicInfo = function(){
		for(var i = 0; i < attrRules.academicInfo.length; i++){
			$("':input[name=" + attrRules.academicInfo[i][0] + "]'").rules("add", attrRules.academicInfo[i][1] );
		}
	};
	exports.remove_academicInfo = function(){
		for(var i = 0; i < attrRules.academicInfo.length; i++){
			$("':input[name=" + attrRules.academicInfo[i][0] + "]'").rules("remove");
		}
	};
	exports.valid_resourceInfo = function(){
		for(var i = 0; i < attrRules.resourceInfo.length; i++){
			$("':input[name=" + attrRules.resourceInfo[i][0] + "]'").rules("add", attrRules.resourceInfo[i][1] );
		}
	};
	exports.remove_resourceInfo = function(){
		for(var i = 0; i < attrRules.resourceInfo.length; i++){
			$("':input[name=" + attrRules.resourceInfo[i][0] + "]'").rules("remove");
		}
	};
	exports.valid_doctorial = function(){
		$("#table_doctorial .tr_valid").each(function(){
			for(var i = 0; i < attrRules.doctorial.length; i++){
				$("':input[name*=" + attrRules.doctorial[i][0] + "]'", $(this)).rules("add", attrRules.doctorial[i][1] );
			}
		});
	};
	exports.valid_discipline = function(){
		$("#table_discipline .tr_valid").each(function(){
			for(var i = 0; i < attrRules.discipline.length; i++){
				$("':input[name*=" + attrRules.discipline[i][0] + "]'", $(this)).rules("add", attrRules.discipline[i][1] );
			}
		});
	};
	
});