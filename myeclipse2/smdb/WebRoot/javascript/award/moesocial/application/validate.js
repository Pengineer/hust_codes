/**
 * @author 余潜玉
 */

define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	require('validate');
	
	exports.advSearch = function() {
		$("#advSearch").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			}
		});
	};
	exports.awardAudit = function() {
		$("#award_audit").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"auditResult":{required: true},
				"auditOpinion":{maxlength: 200}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			}
		});
	};
});