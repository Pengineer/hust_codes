/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('validate');
	
	var valid = function(){
		$(function() {
			$("#app_review").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"qualitativeOpinion":{selected:true},
					"opinion":{maxlength: 2000},
					"innovationScore":{required: true, number: true, range: [0,50]},
					"scientificScore":{required: true, number: true, range: [0,25]},
					"benefitScore":{required: true, number: true, range: [0,25]}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
			$("#app_review_group").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"reviewWay": {required:true},
					"reviewResult":{required:true},
					"reviewOpinionQualitative":{selected:true},
					"reviewOpinion":{maxlength: 2000}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	exports.valid = function() {
		valid();
	};
});