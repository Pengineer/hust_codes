define(function(require, exports, module) {
	require('validate');
	require('cookie');
	
	var productType;
	
	function validationRules(productType) {
		switch(productType) {
		case 1:
			return {
				"rules": [
					["paper.chineseName", {required: true, maxlength: 50}],
					["paper.englishName", {maxlength: 200}],
					["paper.author.id", {required: true}],
					["paper.type.id", {selected: true}],
					["paper.form.id", {selected: true}],
					["paper.otherAuthorName", {maxlength: 200}],
					["paper.wordNumber", {number: true, range: [0,9999]}],
					["paper.disciplineType", {required: true}],
					["paper.discipline", {required: true}],
					["paper.keyword", {maxlength: 100, keyWords:true}],
					["paper.introduction", {maxlength: 20000}],
					["paper.publication", {required: true,maxlength: 50}], 
					["paper.publicationDate", {required: true,dateISO: true}],
					["paper.publicationLevel.id", {selected: true}],
					["paper.publicationScope.id", {selected: true}],
					["paper.index", {maxlength: 100}], 
					["paper.volume", {number: true, range: [0,999]}],
					["paper.number", {number: true, range: [0,999]}],
					["paper.page", {maxlength: 40}],
					["paper.issn", {maxlength: 40}], 
					["paper.cn", {maxlength: 40}],
					["projectProduct.isMarkMoeSupport", {required: true}]
				]
			};
			break;
		case 2:
			return {
				"rules": [
					["book.chineseName", {required: true,maxlength: 50}],
					["book.englishName", {maxlength: 200}],
					["book.author.id", {required: true}],
					["book.otherAuthorName", {maxlength: 200}],
					["book.type.id", {selected: true}],
					["book.form.id", {selected: true}],
					["book.wordNumber", {number: true, range: [0,9999]}], 
					["book.disciplineType", {required: true}],
					["book.discipline", {required: true}],
					["book.disciplineType", {required: true, maxlength: 100}],
					["book.discipline", {required: true, maxlength: 100}], 
					["book.keyword", {maxlength: 100}],
					["book.introduction", {maxlength: 20000}],
					["book.publishUnit", {maxlength: 50}], 
					["book.publishDate", {dateISO: true}],
					["projectProduct.isMarkMoeSupport", {required: true}]
				]
			};
			break;
		case 3:
			return {
				"rules": [
					["consultation.chineseName", {required: true,maxlength: 50}],
					["consultation.englishName", {maxlength: 200}], 
					["consultation.otherAuthorName", {maxlength: 200}],
					["consultation.form.id", {selected: true}],
					["consultation.wordNumber", {number: true, range: [0,9999]}], 
					["consultation.disciplineType", {required: true,maxlength: 100}],
					["consultation.discipline", {required: true,maxlength: 100}], 
					["consultation.keyword", {maxlength: 100}],
					["consultation.introduction", {maxlength: 20000}],
					["consultation.useUnit", {maxlength: 50}], 
					["consultation.publicationDate", {dateISO: true}],
					["projectProduct.isMarkMoeSupport", {required: true}]
				]
			};
			break;
		case 4:
			return {
				"rules": [
					["electronic.chineseName", {required: true,maxlength: 50}],
					["electronic.englishName", {maxlength: 200}],
					["electronic.author.id", {required: true}],
					["electronic.otherAuthorName", {maxlength: 200}],
					["electronic.type.id", {selected: true}],
					["electronic.form.id", {selected: true}],
					["electronic.disciplineType", {required: true}],
					["electronic.discipline", {required: true}],
					["electronic.keyword", {required: true, maxlength: 100}],
					["electronic.disciplineType", {required: true, maxlength: 100}],
					["electronic.discipline", {required: true, maxlength: 100}], 
					["electronic.keyword", {maxlength: 100}],
					["electronic.introduction", {maxlength: 20000}],
					["electronic.publishUnit", {maxlength: 50}], 
					["electronic.publishDate", {dateISO: true}],
					["electronic.useUnit", {maxlength: 50}],
					["projectProduct.isMarkMoeSupport", {required: true}]
				]
			};
			break;
//		case 5:
//			return  {
//				"rules": [
//					["patent.chineseName", {required: true,maxlength: 50}],
//					["patent.englishName", {maxlength: 200}],
//					["patent.author.id", {required: true}],
//					["patent.otherAuthorName", {maxlength: 200}],
//					["patent.inventorName", {maxlength: 200}],
//					["patent.form.id", {selected: true}],
//					["patent.disciplineType", {required: true}],
//					["patent.discipline", {required: true}],
//					["patent.keyword", {required: true, maxlength: 100}],
//					["patent.disciplineType", {required: true, maxlength: 100}],
//					["patent.discipline", {required: true, maxlength: 100}], 
//					["patent.keyword", {maxlength: 100}],
//					["patent.introduction", {maxlength: 20000}],
//					["patent.independentClaims", {maxlength: 20000}],
//					["patent.summary", {maxlength: 20000}],
//					["patent.categotyType", {maxlength: 100},
//					["patent.countriesProviceCode", {maxlength: 100}],
//					["patent.applicationNumber", {maxlength: 200}], 
//					["patent.applicationDate", {dateISO: true}],
//					["patent.publicNumber", {maxlength: 200}], 
//					["patent.publicDate", {dateISO: true}],
//					["patent.classNumber", {maxlength: 200}],
//					["patent.priorityNumber", {maxlength: 200}],
//					["projectProduct.isMarkMoeSupport", {required: true}]
//				]
//			};
//			break;
//		case 6:
//			return {
//				"rules": [
//					["otherProduct.chineseName", {required: true,maxlength: 50}],
//					["otherProduct.englishName", {maxlength: 200}],
//					["otherProduct.author.id", {required: true}],
//					["otherProduct.otherAuthorName", {maxlength: 200}],
//					["otherProduct.type.id", {selected: true}],
//					["otherProduct.form.id", {selected: true}],
//					["otherProduct.disciplineType", {required: true}],
//					["otherProduct.discipline", {required: true}],
//					["otherProduct.disciplineType", {required: true, maxlength: 100}],
//					["otherProduct.discipline", {required: true, maxlength: 100}], 
//					["otherProduct.keyword", {maxlength: 100}],
//					["otherProduct.introduction", {maxlength: 20000}],
//					["otherProduct.evaluation", {maxlength: 20000}],
//					["otherProduct.subtype", {maxlength: 100}],
//					["otherProduct.supportProject", {maxlength: 200}],
//					["otherProduct.publishUnit", {maxlength: 50}], 
//					["otherProduct.pressDate", {dateISO: true}],
//					["projectProduct.isMarkMoeSupport", {required: true}]
//				]
//			};
//			break;
		default:
		}
	};


	exports.valid_authorTypeId = function(){//专家、教师、学生成果所属单位
		if($("select[name='authorTypeId']").length > 0) {
			$("select[name='authorTypeId']").rules("add", {selected: true});
		}
	};
	
	exports.valid = function() {
		productType = parseInt(window.productType);
		
		$("#form_product").validate({
			errorElement: "span",
			event: "blur",
			rules: {},
			errorPlacement: function(error, element){
				var fieldName = element.attr("name");
				if(fieldName == "paper.publication") {
					element = $("#select_no");
				} else if(fieldName == "book.originalLanguage") {
					element = $("#originalLanguage");
				}
				error.appendTo(element.parent("td").next("td"));
			}
		});
		
		$('#publicationDate').removeClass("hasDatepick"); // 先去除冗余的无效绑定
		$('#publicationDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
	};
});