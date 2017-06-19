/**
 * @author 余潜玉
 */
define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	require('validate');
	
	var valid_add = function() {
		$(":input[name*='awardGradeid']").rules("add",{selected:true});
		$(":input[name*='auditOpinion']").rules("add",{maxlength: 200});
	};
	var valid_del = function() {
		$(":input[name*='awardGradeid']").rules("remove");
		$(":input[name*='auditOpinion']").rules("remove");
	};
	
	exports.valid = function() {
		$(function() {
			
			valid_add();
			valid_del();
			$("#award_review").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"meaningScore":{required: true, number: true, range:[0,20]},
					"innovationScore":{required: true, number: true, range:[0,30]},
					"influenceScore":{required: true, number: true, range:[0,30]},
					"methodScore":{required: true, number: true, range:[0,20]},
					"awardGradeid":{selected:true},
					"auditOpinion":{maxlength: 200}
					},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
});