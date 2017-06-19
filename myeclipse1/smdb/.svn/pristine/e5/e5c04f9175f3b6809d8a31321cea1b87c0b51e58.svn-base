define(function(require, exports, module) {
	require('validate');
	require('cookie');
	
	var productType;
	
	function attrRules(productType) {
		switch(productType) {
		case 1:
			return {
				"base_info": [
					["paper.chineseName", {required: true, maxlength: 50}],
					["paper.englishName", {maxlength: 200}], 
					["paper.author.id", {required: true}], 
					["paper.type.id", {selected: true}],
					["paper.form.id", {selected: true}],
					["paper.otherAuthorName", {maxlength: 200}],
					["paper.wordNumber", {number: true, range: [0, 9999]}], 
					["paper.disciplineType", {required: true, maxlength: 100}],
					["paper.discipline", {required: true, maxlength: 100}], 
					["paper.keyword", {maxlength: 100}],
					["paper.introduction", {maxlength: 20000}]
				],
				"publication_info": [
					["paper.publication", {required: true,maxlength: 50}], 
					["paper.publicationDate", {required: true, dateSMDB: true}],
					["paper.publicationLevel.id", {selected: true}],
					["paper.publicationScope.id", {selected: true}],
					["paper.index", {maxlength: 400}], 
					["paper.volume", {number: true, range: [0, 999]}],
					["paper.number", {number: true, range: [0, 999]}],
					["paper.page", {maxlength: 40}],
					["paper.issn", {maxlength: 40}], 
					["paper.cn", {maxlength: 40}]
				]
			};
			break;
		case 2:
			return {
				"base_info": [
					["book.chineseName", {required: true, maxlength: 50}],
					["book.englishName", {maxlength: 200}], 
					["book.author.id", {required: true}], 
					["book.otherAuthorName", {maxlength: 200}],
					["book.type.id", {selected: true}],
					["book.form.id", {selected: true}],
					["book.wordNumber", {number: true, range: [0, 9999]}], 
					["book.disciplineType", {required: true, maxlength: 100}],
					["book.discipline", {required: true, maxlength: 100}], 
					["book.keyword", {maxlength: 100}],
					["book.introduction", {maxlength: 20000}]
				],
				"publication_info": [
					["book.publishUnit", {maxlength: 50}], 
					["book.publishDate", {dateSMDB: true}]
				]
			};
			break;
		case 3:
			return {
				"base_info": [
					["consultation.chineseName", {required: true, maxlength: 50}],
					["consultation.englishName", {maxlength: 200}], 
					["consultation.author.id", {required: true}], 
					["consultation.otherAuthorName", {maxlength: 200}],
					["consultation.form.id", {selected: true}],
					["consultation.wordNumber", {number: true, range: [0, 9999]}], 
					["consultation.disciplineType", {required: true, maxlength: 100}],
					["consultation.discipline", {required: true, maxlength: 100}], 
					["consultation.keyword", {maxlength: 100}],
					["consultation.introduction", {maxlength: 20000}]
				],
				"publication_info": [
					["consultation.useUnit", {maxlength: 50}], 
					["consultation.publicationDate", {dateSMDB: true}]
				]
			};
			break;
		case 4:
			return {
				"base_info": [
					["electronic.chineseName", {required: true, maxlength: 50}],
					["electronic.englishName", {maxlength: 200}], 
					["electronic.author.id", {required: true}], 
					["electronic.otherAuthorName", {maxlength: 200}],
					["electronic.form.id", {selected: true}],
					["electronic.type.id", {selected: true}],
					["electronic.disciplineType", {required: true, maxlength: 100}],
					["electronic.discipline", {required: true, maxlength: 100}], 
					["electronic.keyword", {maxlength: 100}],
					["electronic.introduction", {maxlength: 20000}]
				],
				"publication_info": [
					["electronic.useUnit", {maxlength: 50}], 
					["electronic.publishDate", {maxlength: 50}],
					["electronic.publishDate", {dateSMDB: true}]
				]
			};
			break;
		case 5:
			return  {
				"base_info": [
					["patent.chineseName", {required: true, maxlength: 50}],
					["patent.englishName", {maxlength: 200}], 
					["patent.author.id", {required: true}], 
					["patent.otherAuthorName", {maxlength: 200}],
					["patent.form.id", {selected: true}],
					["patent.wordNumber", {number: true, range: [0, 9999]}], 
					["patent.disciplineType", {required: true, maxlength: 100}],
					["patent.discipline", {required: true, maxlength: 100}], 
					["patent.keyword", {maxlength: 100}],
					["patent.introduction", {maxlength: 20000}],
					["patent.independentClaims", {maxlength: 20000}],
					["patent.summary", {maxlength: 20000}],
					["patent.categotyType", {maxlength: 100}],
					["patent.countriesProviceCode", {maxlength: 100}]
				],
				"publication_info": [
					["patent.inventorName", {maxlength: 200}],
					["patent.applicationNumber", {maxlength: 200}], 
					["patent.applicationDate", {dateSMDB: true}],
					["patent.publicNumber", {maxlength: 200}], 
					["patent.publicDate", {dateSMDB: true}],
					["patent.priorityNumber", {maxlength: 200}],
					["patent.classNumber", {maxlength: 200}]
				]
			};
			break;
		case 6:
			return {
				"base_info": [
					["otherProduct.chineseName", {required: true, maxlength: 50}],
					["otherProduct.englishName", {maxlength: 200}], 
					["otherProduct.author.id", {required: true}], 
					["otherProduct.otherAuthorName", {maxlength: 200}],
					["otherProduct.form.id", {selected: true}],
					["otherProduct.disciplineType", {required: true, maxlength: 100}],
					["otherProduct.discipline", {required: true, maxlength: 100}], 
					["otherProduct.keyword", {maxlength: 100}],
					["otherProduct.introduction", {maxlength: 20000}],
					["otherProduct.evaluation", {maxlength: 20000}],
					["otherProduct.supportProject", {maxlength: 200}],
					["otherProduct.subtype", {maxlength: 200}]
				],
				"publication_info": [
					["otherProduct.publishUnit", {maxlength: 50}], 
					["otherProduct.pressDate", { dateSMDB: true}]
				]
			};
			break;
		default:
		}
	};
	
	var person_team_dynamic_rules = {
		"person" : {
			"research_person" : [
				["authorTypeId", {selected: true}]
			],
			"office_person" : [
				["authorId", {required: true}]
			]
		},
		"team" : {
			"research_person" : [
				["organizationAuthorTypeId", {selected: true}],
				["organization.name", {required: true}],
				["organization.agencyName", {required: true}]
			],
			"office_person" : [
				["organizationAuthorId", {required: true}],
				["organization.name", {required: true}],
				["organization.agencyName", {required: true}]
			]
		}
	};
	
	exports.valid_person_team = function() {
		var accountType = $("#accountType").val(),
			accountTypeKey = (accountType != "EXPERT" && accountType != "TEACHER" && accountType != "STUDENT") ? "office_person" : "research_person";
		for(var key in person_team_dynamic_rules) {
			var remove_rules = person_team_dynamic_rules[key][accountTypeKey];
			for(var i = 0; i < remove_rules.length; i++) {
				if($("':input[name=" + remove_rules[i][0] + "]'").length != 0)
					$("':input[name=" + remove_rules[i][0] + "]'").rules("remove");	
			};
		};
		var applyType = $(".applyType:checked").val(),
			applyTypeKey = (applyType == 0) ? "person" : "team";
		var rules = person_team_dynamic_rules[applyTypeKey][accountTypeKey];
		for(var i = 0; i < rules.length; i++) {
			if($("':input[name=" + rules[i][0] + "]'").length != 0)
				$("':input[name=" + rules[i][0] + "]'").rules("add", rules[i][1]);
		};
	};

	exports.valid_basic = function(){
		for(var i = 0; i < attrRules(productType).base_info.length; i++){
			if($("':input[name=" + attrRules(productType).base_info[i][0] + "]'").length != 0)
				$("':input[name=" + attrRules(productType).base_info[i][0] + "]'").rules("add", attrRules(productType).base_info[i][1] );
		};
	};

	exports.valid_project = function(){
		if($("input[name='isProRel'][type='radio']").length != 0) {
			if ($("input[name='isProRel'][type='radio']:checked").val() == 1) {
				$("#proj_name").rules("add", {required: true});
			} else {
				$("#proj_name").rules("remove");
			}
		};
	};

	exports.valid_publication = function(){
		for(var i = 0; i < attrRules(productType).publication_info.length; i++){
			if($("':input[name=" + attrRules(productType).publication_info[i][0] + "]'").length != 0)
				$("':input[name=" + attrRules(productType).publication_info[i][0] + "]'").rules("add", attrRules(productType).publication_info[i][1] );
		};
	};
	
	exports.valid = function() {
		productType = parseInt(window.productType);
		
		$("#form_product").validate({
			errorElement: "span",
			event: "change",
			rules: {},
			errorPlacement: function(error, element) { 
				var fieldName = element.attr("name");
				if(fieldName == "paper.publication") {
					element = $("#select_no");
				} else if(fieldName == "book.originalLanguage") {
					element = $("#originalLanguage");
				}
				error.appendTo(element.parent("td").next("td"));
			}
		});
	};
	
});
